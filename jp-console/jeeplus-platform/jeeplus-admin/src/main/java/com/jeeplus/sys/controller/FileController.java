/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.common.utils.ResponseUtil;
import com.jeeplus.config.properties.FileProperties;
import com.jeeplus.sys.model.FileData;
import com.jeeplus.sys.utils.FileKit;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 文件管理Controller
 * @author liugf
 * @version 2021-01-21
 */

@Api(tags = "文件管理")
@RestController
@RequestMapping("/sys/file")
public class FileController {

	@Autowired
	FileProperties fileProperties;

	public void init() {
		FileUtils.createDirectory( FileKit.getAttachmentDir());
		FileUtils.createDirectory(FileKit.getMyDocDir());
		FileUtils.createDirectory(FileKit.getShareBaseDir());
	}

	/**
	 * 获取文件列表
	 * @return
	 */
	@ApiLog("查询文件列表")
	@GetMapping("list")
	public ResponseEntity<List<FileData>> data() {
	    init();
		List <File> targetFiles = Lists.newArrayList();
		targetFiles.add(new File(FileKit.getAttachmentDir()));
		targetFiles.add(new File(FileKit.getMyDocDir()));
		targetFiles.add(new File(FileKit.getShareBaseDir()));
		return ResponseEntity.ok (FileKit.getFileList("files", Lists.newArrayList(targetFiles)));
	}


	/**
	 * 移动文件或者文件夹
	 * @param source
	 * @param target
	 * @return
	 * @throws IOException
	 */
	@ApiLog("移动文件或者文件夹")
	@RequestMapping("move")
	public ResponseEntity<List> move(@Param("source") String source, @Param("target") String target) throws IOException{
		List list = Lists.newArrayList();
		target = FileKit.getFileDir (target);
		String[] sourceArra = source.split(",");
		for(String s:sourceArra){
			s = FileKit.getFileDir (s);
			String fileName = StrUtil.subAfter (s.replace("\\","/"),"/", true);
			if(FileUtils.isFolder(s)){
				File targetFolder = FileUtils.getAvailableFolder(target+"/"+fileName, 0);
				FileUtils.moveDirectory(new File(s),targetFolder);
				Map map = new HashMap();
				map.put("id", FileKit.transDirToUrl(targetFolder.getAbsolutePath()));
				map.put("value", targetFolder.getName());
				list.add(map);
			}else{
					File targetFile = FileUtils.getAvailableFile(target+"/"+fileName, 0);
					FileUtils.moveFile(new File(s), targetFile);
					new File(s).deleteOnExit();
					Map map = new HashMap();
					map.put("id",FileKit.transDirToUrl(targetFile.getAbsolutePath()));
					map.put("value", targetFile.getName());
					list.add(map);
			}

		}

		return ResponseEntity.ok (list);
	}

	/**
	 * copy文件以及文件夹
	 * @param source
	 * @param target
	 * @return
	 */
	@ApiLog("copy文件以及文件夹")
	@RequestMapping("copy")
	public ResponseEntity<List> copy(@Param("source") String source, @Param("target") String target) {
		List list = Lists.newArrayList();
		String[] sourceArra = source.split(",");
		for(String s:sourceArra){
			s = FileKit.getFileDir (s);
			String fileName = StrUtil.subAfter (s.replace("\\","/"),"/", true);
			if(FileUtils.isFolder(s)){
				File targetFolder =  FileUtils.getAvailableFolder(target+"/"+fileName, 0);
				if(FileUtils.copyDirectory(s, targetFolder.getAbsolutePath())){
					Map map = new HashMap();
					map.put("id",FileKit.transDirToUrl(targetFolder.getAbsolutePath()));
					map.put("value", targetFolder.getName());
					list.add(map);
				}
			}else{
				File targetFile = FileUtils.getAvailableFile(target+"/"+fileName, 0);
				if(FileUtils.copyFile(s,targetFile.getAbsolutePath())){
					Map map = new HashMap();
					map.put("id",FileKit.transDirToUrl(targetFile.getAbsolutePath()));
					map.put("value", targetFile.getName());
					list.add(map);
				}
			}

		}

		return ResponseEntity.ok (list);
	}

	/**
	 * 下载文件
	 * @param source
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ApiLog("下载文件")
	@RequestMapping("download")
	public void download(@Param("source") String source, HttpServletRequest request, HttpServletResponse response) throws Exception{
		source = FileKit.getFileDir (source);
		File file = new File(source);
		if (file == null || !file.exists()) {
			throw new FileNotFoundException("请求的文件不存在");
		}
		OutputStream out = null;
		try {
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			String agent = request.getHeader("USER-AGENT");
			String fileName = file.getName();
			if(agent != null && agent.indexOf("MSIE") == -1) {
// FF
				String enableFileName = "=?UTF-8?B?" + (new String(Base64.getEncoder().encode(fileName.getBytes("UTF-8")))) + "?=";
				response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName); }
			else {
// IE
				String enableFileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
			}
//			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
			out = response.getOutputStream();
			out.write(FileUtils.readFileToByteArray(file));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 删除文件
	 * @param source
	 * @return
	 */
	@ApiLog("删除文件")
	@RequestMapping("remove")
	public ResponseEntity<List> delete(@Param("source") String source) {
		List list = Lists.newArrayList();
		//先删除文件
		String[] sourceArra = source.split(",");
		for(String s:sourceArra){
			s = FileKit.getFileDir (s);
			FileUtils.delFile(s);
			Map map = new HashMap();
			map.put("id",FileKit.transDirToUrl(s));
			map.put("value", StrUtil.subAfter (s.replace("\\","/"),"/", true));
			list.add(map);
		}

		return ResponseEntity.ok (list);
	}

	/**
	 * 创建文件夹
	 * @param source
	 * @param target
	 * @return
	 */
	@ApiLog("创建文件夹")
	@RequestMapping("createFolder")
	public ResponseEntity<Map> create(@Param("source") String source, @Param("target") String target) {
		Map map = new HashMap();
		target = FileKit.getFileDir (target);
		String targetFolderPath = target + "/" + source;
		File targetFolder = FileUtils.getAvailableFolder(targetFolderPath, 0);
		boolean result = FileUtils.createDirectory(targetFolder.getAbsolutePath());
		if(result){
			map.put("id", FileKit.transDirToUrl(targetFolder.getAbsolutePath()));
			map.put("value", targetFolder.getName());

		}
		return ResponseEntity.ok (map);
	}

	/**
	 * 重命名文件
	 * @param source
	 * @param target
	 * @return
	 */
	@ApiLog("重命名文件")
	@RequestMapping("rename")
	public ResponseEntity<Map> rename(@Param("source") String source, @Param("target") String target) {
		Map map = new HashMap();
		source = FileKit.getFileDir (source);
		File sourceFile = new File(source);
		File targetFile = new File(sourceFile.getParent()+"/"+target);
		if(sourceFile.isDirectory()){
			targetFile = FileUtils.getAvailableFolder(targetFile.getAbsolutePath(),0);
		}else{
			targetFile = FileUtils.getAvailableFile(targetFile.getAbsolutePath(), 0);
		}
		boolean result = sourceFile.renameTo(targetFile);
		if(result){
			map.put("id", FileKit.transDirToUrl(targetFile.getAbsolutePath()));
			map.put("value", targetFile.getName());

		}
		return ResponseEntity.ok (map);
	}


	/**
	 * 上传文件
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@ApiLog("上传文件")
	@RequestMapping("upload")
	public ResponseEntity<Map> upload( HttpServletRequest request, MultipartFile upload) throws Exception {
		String target = request.getParameter("target");
		String realPath = FileKit.getFileDir (target);
		Map map = new HashMap();
		// 判断文件是否为空
		if (!upload.isEmpty()) {
			String name = upload.getOriginalFilename();
			if(fileProperties.isAvailable (name)){
				// 文件保存路径
				// 转存文件
				FileUtils.createDirectory(realPath);
				String filePath = realPath +"/"+  name;
				File newFile = FileUtils.getAvailableFile(filePath,0);
				upload.transferTo(newFile);
				map.put("id", FileKit.transDirToUrl(newFile.getAbsolutePath()));
				map.put("value", newFile.getName());
				map.put("type", FileKit.getFileType(newFile.getName()));
			}else{
				map.put("id", "");
				map.put("value", "");
				map.put("type", "");
				System.out.println ("非法文件，不允许上传!");
			}
		}
		return ResponseEntity.ok (map);
	}

	/**
	 * 获取文件网络地址
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@ApiLog("获取文件网络地址")
	@GetMapping("getUrl")
	public ResponseEntity getUrl(HttpServletRequest request){
		String dir = request.getParameter ( "dir" );
		String type = FileKit.getFileType(dir);
		return ResponseUtil.newInstance ().add("url", dir).add("type",type).ok ();
	}

	/**
	 * 上传文件
	 * @return
	 * @throws IOException
	 */
	@ApiLog("上传文件")
	@RequestMapping("webupload/upload")
	public ResponseEntity webupload( HttpServletRequest request, MultipartFile file) throws IOException {

		String uploadPath = request.getParameter("uploadPath");
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH )+1;
		String fileUrl = FileKit.getAttachmentUrl()+uploadPath+"/"+year+"/"+month+"/";
		String fileDir = FileKit.getAttachmentDir()+uploadPath+"/"+year+"/"+month+"/";
		// 判断文件是否为空
		if (!file.isEmpty()) {
			String name = file.getOriginalFilename ();
			if(fileProperties.isAvailable (name)) {
				// 文件保存路径
				// 转存文件
				FileUtils.createDirectory (fileDir);
				String filePath = fileDir + name;
				File newFile = FileUtils.getAvailableFile (filePath, 0);
				file.transferTo (newFile);
				return ResponseUtil.newInstance ().add ("id", FileKit.transDirToUrl (newFile.getAbsolutePath ())).add ("url", fileUrl + newFile.getName ()).ok ();
			}else{
				return ResponseEntity.badRequest().body ("请勿上传非法文件!");
			}
		}else {
			return ResponseEntity.badRequest().body  ("文件不存在!");
		}
	}

	/**
	 * 根据删除文件
	 * @param id
	 * @return
	 */
	@ApiLog("删除文件")
	@RequestMapping("webupload/delete")
	public ResponseEntity delFile(String id) {
		id = FileKit.getFileDir (id);
		if(FileUtils.delFile(id)){
			return ResponseEntity.ok ("删除文件成功");
		}else{
			return ResponseEntity.badRequest ().body ("删除文件失败");
		}
	}

	/**
	 * 根据url删除文件
	 * @param url
	 * @return
	 */
	@ApiLog("根据url删除文件")
	@RequestMapping("webupload/deleteByUrl")
	public ResponseEntity delFileByUrl(String url) {
		String id = FileKit.getFileDir(url);
		if(FileUtils.delFile(id)){
			return ResponseEntity.ok ("删除文件成功");
		}else{
			return ResponseEntity.badRequest().body ("删除文件失败");
		}

	}

}
