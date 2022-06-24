package com.jeeplus.common.repository;

import cn.hutool.core.io.FileUtil;
import com.jeeplus.common.utils.FileUtils;
import io.minio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

/**
 * minIOOSS存储服务类
 *
 * @author 滕鑫源
 * @date 2020/8/20 14:42
 */

@Component
@ConditionalOnProperty(name = "config.accessory.type", havingValue = "minIO")
public class MinIORepository implements AccessoryRepository {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${config.accessory.baseDir}")
    private String accessoryBaseDir;

    @Value("${config.accessory.minIO.endpoint}")
    private String endpoint;
    @Value("${config.accessory.minIO.accessKey}")
    private String accessKey;
    @Value("${config.accessory.minIO.secretKey}")
    private String secretKey;
    @Value("${config.accessory.minIO.bucketName}")
    private String bucketName;


    @Override
    public File get(String path, String fileName) throws FileNotFoundException {
        logger.debug("开始从minIO-oss获取文件: {}{}{}", path, "/", fileName);
        long begin = System.currentTimeMillis();
        try {
            if (path == null) {
                path = "";
            }
            MinioClient client = MinioClient.builder ().endpoint (endpoint).credentials (accessKey, secretKey).build ();
            GetObjectArgs objectArgs = GetObjectArgs.builder ().bucket (bucketName).object (accessoryBaseDir + "/" + path + "/" + fileName).build ();
            InputStream is = client.getObject(objectArgs);
            File localTempDir = new File(FileUtils.getTempDirectory() + File.separator + accessoryBaseDir + File.separator + path);
            FileUtils.createDirectory(localTempDir.getPath());
            File dest = new File(localTempDir, fileName);
            FileUtils.copyInputStreamToFile(is, dest);
            is.close();
            return dest;
        } catch (Exception e) {
            throw new FileNotFoundException("指定的文件不存在");
        } finally {
            logger.debug("完成从minIO-oss获取文件: {}", System.currentTimeMillis() - begin);
        }
    }


    @Override
    public URL getURL(String path, String fileName) throws FileNotFoundException {
        logger.debug("开始从minIO-oss获取文件: {}{}{}", path, "/", fileName);
        long begin = System.currentTimeMillis();
        URL downloadURL = null;
        try {
            if (path == null) {
                path = "";
            }
            MinioClient client = MinioClient.builder ().endpoint (endpoint).credentials (accessKey, secretKey).build ();
            GetPresignedObjectUrlArgs presignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder().bucket (bucketName).object ( accessoryBaseDir + "/" + path + "/" + fileName).expiry (60*60).build();
            String url = client.getPresignedObjectUrl (presignedObjectUrlArgs);
            downloadURL = new URL(url);
            return downloadURL;
        } catch (Exception e) {
            throw new FileNotFoundException("指定的文件不存在");
        } finally {
            logger.debug("完成从minIO-oss获取文件临时授权路径 {}, 耗时: {}", downloadURL, System.currentTimeMillis() - begin);
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
        logger.debug("开始向minIO-oss保存文件: {}", fileName);
        long begin = System.currentTimeMillis();
        try {
            MinioClient client = MinioClient.builder ().endpoint (endpoint).credentials (accessKey, secretKey).build ();
            boolean exists = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());

            }
            InputStream in = new FileInputStream (file);
            //这里用下hutool的工具类-根据文件扩展名获得MimeType
            String mime = FileUtil.getMimeType(file.getName());
            if (mime == null) {
                //二进制流，不知道下载文件类型
                mime = "application/octet-stream";
            }
            PutObjectArgs putObjectArgs = PutObjectArgs.builder ().bucket (bucketName).object (accessoryBaseDir + "/" + path + "/" + fileName).stream (in, file.length (), -1).contentType (mime).build ();
            client.putObject(putObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.debug("完成向minIO-oss保存文件: {}", System.currentTimeMillis() - begin);
        }

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
        logger.debug("开始向minIO-oss保存文件: {}", fileName);
        long begin = System.currentTimeMillis();
        try {
            MinioClient client = MinioClient.builder ().endpoint (endpoint).credentials (accessKey, secretKey).build ();
            boolean exists = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());

            }
            PutObjectArgs putObjectArgs = PutObjectArgs.builder ().bucket (bucketName).contentType (file.getContentType ()).object (accessoryBaseDir + "/" + path + "/" + fileName).stream (file.getInputStream (), file.getSize (), -1).build ();
            client.putObject(putObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug("完成向minIO-oss保存文件: {}", System.currentTimeMillis() - begin);
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
        logger.debug("开始向minIO-oss保存文件: {}", fileName);
        long begin = System.currentTimeMillis();
        try {
            MinioClient client = MinioClient.builder ().endpoint (endpoint).credentials (accessKey, secretKey).build ();
            boolean exists = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());

            }
            PutObjectArgs putObjectArgs = PutObjectArgs.builder ().bucket (bucketName).object ( accessoryBaseDir + "/" + path + "/" + fileName).stream (is, is.available (), -1).build ();
            client.putObject(putObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug("完成向minIO-oss保存文件: {}", System.currentTimeMillis() - begin);
        return null;
    }

}
