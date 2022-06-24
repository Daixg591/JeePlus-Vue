package com.jeeplus.common.repository;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.jeeplus.common.utils.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * aliyunOSS存储服务类
 *
 * @author 滕鑫源
 * @date 2020/8/20 14:42
 */

@Component
@ConditionalOnProperty(name = "config.accessory.type", havingValue = "aliyun")
public class AliyunOSSRepository implements AccessoryRepository {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${config.accessory.baseDir}")
    private String accessoryBaseDir;

    @Value("${config.accessory.aliyun.endpoint}")
    private String endpoint;
    @Value("${config.accessory.aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${config.accessory.aliyun.accessKeySecret}")
    private String accessKeySecret;
    @Value("${config.accessory.aliyun.bucketName}")
    private String bucketName;


    @Override
    public File get(String path, String fileName) throws FileNotFoundException {
        logger.debug("开始从aliyun-oss获取文件: {}{}{}", path, "/", fileName);
        long begin = System.currentTimeMillis();
        try {
            if (path == null) {
                path = "";
            }
            OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            OSSObject object = client.getObject(bucketName, accessoryBaseDir + "/" + path + "/" + fileName);
            InputStream is = object.getObjectContent();
            File localTempDir = new File(FileUtils.getTempDirectory() + File.separator + accessoryBaseDir + File.separator + path);
            FileUtils.createDirectory(localTempDir.getPath());
            File dest = new File(localTempDir, fileName);
            FileUtils.copyInputStreamToFile(is, dest);
            is.close();
            client.shutdown();
            return dest;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSSException e) {
            throw new FileNotFoundException("指定的文件不存在");
        } finally {
            logger.debug("完成从aliyun-oss获取文件: {}", System.currentTimeMillis() - begin);
        }
        return null;
    }


    @Override
    public URL getURL(String path, String fileName) throws FileNotFoundException {
        logger.debug("开始从aliyun-oss获取文件: {}{}{}", path, "/", fileName);
        long begin = System.currentTimeMillis();
        URL downloadURL = null;
        try {
            if (path == null) {
                path = "";
            }
            OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            Date expireDate = DateUtils.addHours(new Date(), 1);
            downloadURL = client.generatePresignedUrl(bucketName, accessoryBaseDir + "/" + path + "/" + fileName, expireDate);
            return downloadURL;
        } catch (OSSException e) {
            throw new FileNotFoundException("指定的文件不存在");
        } finally {
            logger.debug("完成从aliyun-oss获取文件临时授权路径 {}, 耗时: {}", downloadURL, System.currentTimeMillis() - begin);
        }
    }


    /**
     * 保存文件对象
     *
     * @param file
     * @param path
     * @param fileName
     * @Author 滕鑫源
     * @Date 2020/8/20 14:47
     */
    @Override
    public File save(File file, String path, String fileName) {
        logger.debug("开始向aliyun-oss保存文件: {}", fileName);
        long begin = System.currentTimeMillis();
        OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentDisposition("attachment;filename=" + new String(fileName.getBytes(), StandardCharsets.ISO_8859_1));
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, accessoryBaseDir + "/" + path + "/" + fileName, file, meta);
        client.putObject(putObjectRequest);
        client.shutdown();
        logger.debug("完成向aliyun-oss保存文件: {}", System.currentTimeMillis() - begin);
        return file;
    }

    /**
     * 保存文件流文件为文件对象
     *
     * @param file
     * @param path
     * @param fileName
     * @Author 滕鑫源
     * @Date 2020/8/20 14:49
     */
    @Override
    public File save(MultipartFile file, String path, String fileName) {
        logger.debug("开始向aliyun-oss保存文件: {}", fileName);
        long begin = System.currentTimeMillis();
        try {
            OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentDisposition("attachment;filename=" + new String(fileName.getBytes(), StandardCharsets.ISO_8859_1));
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, accessoryBaseDir + "/" + path + (path.endsWith("/") ? "" : "/") + fileName, file.getInputStream(), meta);
            client.putObject(putObjectRequest);
            client.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("完成向aliyun-oss保存文件: {}", System.currentTimeMillis() - begin);
        return null;
    }

    /**
     * 保存输入流为文件对象
     *
     * @param is
     * @param path
     * @param fileName
     * @Author 滕鑫源
     * @Date 2020/8/20 14:47
     */
    @Override
    public File save(InputStream is, String path, @NotNull String fileName) {
        logger.debug("开始向aliyun-oss保存文件: {}", fileName);
        long begin = System.currentTimeMillis();
        try {
            OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentDisposition("attachment;filename=" + new String(fileName.getBytes(), StandardCharsets.ISO_8859_1));
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, accessoryBaseDir + "/" + path + (path.endsWith("/") ? "" : "/") + fileName, is, meta);
            client.putObject(putObjectRequest);
            client.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug("完成向aliyun-oss保存文件: {}", System.currentTimeMillis() - begin);
        return null;
    }

}
