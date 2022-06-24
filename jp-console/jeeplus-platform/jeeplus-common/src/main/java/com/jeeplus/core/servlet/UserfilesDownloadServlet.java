package com.jeeplus.core.servlet;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.jeeplus.config.properties.JeePlusProperties;
import org.springframework.web.util.UriUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author jeeplus
 * @version 2021-06-25
 */
@WebServlet(urlPatterns = "/userfiles/*")
public class UserfilesDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void fileOutputStream(HttpServletRequest req, HttpServletResponse resp) {

		String filepath = req.getRequestURI();
		int index = filepath.indexOf( JeePlusProperties.USERFILES_BASE_URL);
		if(index >= 0) {
			filepath = filepath.substring(index + JeePlusProperties.USERFILES_BASE_URL.length());
		}

		filepath = UriUtils.decode(filepath, "UTF-8");
		JeePlusProperties jeePlusProperites = SpringUtil.getBean( JeePlusProperties.class);
		File file = new File(jeePlusProperites.getUserfilesBaseDir() + JeePlusProperties.USERFILES_BASE_URL + filepath);
		//此处修改为断点续传模式，实现视频分段解析，解决视频加载缓慢及微信解析异常问题
		try{
			downRangeFile(file,resp,req);
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		fileOutputStream(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		fileOutputStream(req, resp);
	}

	public void downRangeFile(File downloadFile, HttpServletResponse response, HttpServletRequest request) throws Exception {

		String extName = "";
		// 文件不存在
		if (!downloadFile.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		long fileLength = downloadFile.length();// 记录文件大小
		long pastLength = 0;// 记录已下载文件大小
		int rangeSwitch = 0;// 0：从头开始的全文下载；1：从某字节开始的下载（bytes=27000-）；2：从某字节开始到某字节结束的下载（bytes=27000-39000）
		long toLength = 0;// 记录客户端需要下载的字节段的最后一个字节偏移量（比如bytes=27000-39000，则这个值是为39000）
		long contentLength = 0;// 客户端请求的字节总量
		String rangeBytes = "";// 记录客户端传来的形如“bytes=27000-”或者“bytes=27000-39000”的内容
		RandomAccessFile raf = null;// 负责读取数据
		OutputStream os = null;// 写出数据
		OutputStream out = null;// 缓冲
		int bsize = 1024;// 缓冲区大小
		byte b[] = new byte[bsize];// 暂存容器

		String range = request.getHeader("Range");
		int responseStatus = 206;
		if (range != null && range.trim().length() > 0 && !"null".equals(range)) {// 客户端请求的下载的文件块的开始字节
			responseStatus =HttpServletResponse.SC_PARTIAL_CONTENT;
//			logger.info("request.getHeader(\"Range\")=" + range);
			rangeBytes = range.replaceAll("bytes=", "");
			if (rangeBytes.endsWith("-")) {// bytes=969998336-
				rangeSwitch = 1;
				rangeBytes = rangeBytes.substring(0, rangeBytes.indexOf('-'));
				pastLength = Long.parseLong(rangeBytes.trim());
				contentLength = fileLength - pastLength;// 客户端请求的是
				// 969998336之后的字节（包括bytes下标索引为969998336的字节）
			} else {// bytes=1275856879-1275877358
				rangeSwitch = 2;
				String temp0 = rangeBytes.substring(0, rangeBytes.indexOf('-'));
				String temp2 = rangeBytes.substring(rangeBytes.indexOf('-') + 1, rangeBytes.length());
				// bytes=1275856879-1275877358，从第1275856879个字节开始下载
				pastLength = Long.parseLong(temp0.trim());
				toLength = Long.parseLong(temp2);// bytes=1275856879-1275877358，到第
				// 1275877358 个字节结束
				contentLength = toLength - pastLength + 1;// 客户端请求的是
				// 1275856879-1275877358
				// 之间的字节
			}
		} else {// 从开始进行下载
			contentLength = fileLength;// 客户端要求全文下载
		}

		/**
		 * 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。 响应的格式是:
		 * Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
		 * ServletActionContext.getResponse().setHeader("Content-Length", new
		 * Long(file.length() - p).toString());
		 */
		// 来清除首部的空白行
		response.reset();
		// 告诉客户端允许断点续传多线程连接下载,响应的格式是:Accept-Ranges: bytes
		response.setHeader("Accept-Ranges", "bytes");
		// 如果是第一次下,还没有断点续传,状态是默认的 200,无需显式设置;响应的格式是:HTTP/1.1
		if (rangeSwitch != 0) {
			response.setStatus(responseStatus);
			// 不是从最开始下载，断点下载响应号为206
			// 响应的格式是:
			// Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
//			logger.info("----------------------------片段下载，服务器即将开始断点续传...");
			switch (rangeSwitch) {
				case 1: {// 针对 bytes=27000- 的请求
					String contentRange = new StringBuffer("bytes ")
							.append(new Long(pastLength).toString()).append("-")
							.append(new Long(fileLength - 1).toString())
							.append("/").append(new Long(fileLength).toString())
							.toString();
					response.setHeader("Content-Range", contentRange);
					break;
				}
				case 2: {// 针对 bytes=27000-39000 的请求
					String contentRange = range.replace("=", " ") + "/"
							+ new Long(fileLength).toString();
					response.setHeader("Content-Range", contentRange);
					break;
				}
				default: {
					break;
				}
			}
		} else {
			String contentRange = new StringBuffer("bytes ").append("0-")
					.append(fileLength - 1).append("/").append(fileLength)
					.toString();
			response.setHeader("Content-Range", contentRange);
			// 是从开始下载
//			logger.info("----------------------------是从开始到最后完整下载！");
		}

		try {
			// /////////////////////////设置文件Content-Type///////////////////////////
			String finalFileName = null;
			//获得浏览器代理信息
			final String userAgent = request.getHeader("USER-AGENT");
			if(StrUtil.contains(userAgent, "MSIE")||StrUtil.contains(userAgent,"Trident")||StrUtil.contains(userAgent,"Edge")){//IE浏览器
				finalFileName = URLEncoder.encode(downloadFile.getName(),"UTF8");
			}else if(StrUtil.contains(userAgent, "Mozilla")){//google,火狐浏览器
				finalFileName = new String(downloadFile.getName().getBytes("GBK"), "iso8859-1");
			}else{
				finalFileName = URLEncoder.encode(downloadFile.getName(),"UTF8");//其他浏览器
			}
			response.setHeader("Content-Disposition" ,"attachment;filename=" +finalFileName+"");//下载文件的名称
			response.setHeader("Content-Length", String.valueOf(contentLength));
			os = response.getOutputStream();
			out = new BufferedOutputStream(os);
			raf = new RandomAccessFile(downloadFile, "r");
			try {
				long outLength = 0;// 实际输出字节数
				switch (rangeSwitch) {
					case 0: {// 普通下载，或者从头开始的下载
						// 同1，没有break
					}
					case 1: {// 针对 bytes=27000- 的请求
						raf.seek(pastLength);// 形如 bytes=969998336- 的客户端请求，跳过
						// 969998336 个字节
						int n = 0;
						while ((n = raf.read(b)) != -1) {
							out.write(b, 0, n);
							outLength += n;
						}
						// while ((n = raf.read(b, 0, 1024)) != -1) {
						// out.write(b, 0, n);
						// }
						break;
					}
					case 2: {
						// 针对 bytes=27000-39000 的请求，从27000开始写数据
						raf.seek(pastLength);
						int n = 0;
						long readLength = 0;// 记录已读字节数
						while (readLength <= contentLength - bsize) {// 大部分字节在这里读取
							n = raf.read(b);
							readLength += n;
							out.write(b, 0, n);
							outLength += n;
						}
						if (readLength <= contentLength) {// 余下的不足 1024 个字节在这里读取
							n = raf.read(b, 0, (int) (contentLength - readLength));
							out.write(b, 0, n);
							outLength += n;
						}
						break;
					}
					default: {
						break;
					}
				}
//				logger.info("Content-Length为：" + contentLength + "；实际输出字节数：" + outLength);
				out.flush();
			} catch (IOException ie) {
				/**
				 * 在写数据的时候， 对于 ClientAbortException 之类的异常，
				 * 是因为客户端取消了下载，而服务器端继续向浏览器写入数据时， 抛出这个异常，这个是正常的。
				 * 尤其是对于迅雷这种吸血的客户端软件， 明明已经有一个线程在读取 bytes=1275856879-1275877358，
				 * 如果短时间内没有读取完毕，迅雷会再启第二个、第三个。。。线程来读取相同的字节段， 直到有一个线程读取完毕，迅雷会 KILL
				 * 掉其他正在下载同一字节段的线程， 强行中止字节读出，造成服务器抛 ClientAbortException。
				 * 所以，我们忽略这种异常
				 */
				// ignore
			}
		} catch (Exception e) {
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
				}
			}
		}

	}
}
