package com.jeeplus.common.repository;

import com.jeeplus.common.repository.Exception.FunctionNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

public interface AccessoryRepository {


    /**
     * 根据保存的相对路径获取存储的文件对象
     *
     * @return java.io.File
     * @Author 滕鑫源
     * @Date 2020/8/20 14:46
     * @Param path
     * @Param fileName
     **/
    File get(String path, String fileName) throws FileNotFoundException;

    URL getURL(String path, String fileName) throws FunctionNotSupportedException, FileNotFoundException;

    /**
     * 根据保存的相对路径获取可下载文件的完整http路径
     *
     * @Author 滕鑫源
     * @Date 2020/8/26 10:46
     * @param path
     * @param fileName
     * @return
     **/
//    String getUrl(String path, String fileName);

    /**
     * 根据给定的多个相对路径获取多个存储的文件对象
     *
     * @Author 滕鑫源
     * @Date 2020/8/26 10:46
     * @param urls
     * @return
     **/
//    List<File> findList(String... urls);


    /**
     * 保存文件对象
     *
     * @Author 滕鑫源
     * @Date 2020/8/20 14:47
     * @Param file
     * @Param fileName
     **/
    File save(File file, String path, String fileName);

    /**
     * 保存文件流文件为文件对象
     *
     * @Author 滕鑫源
     * @Date 2020/8/20 14:49
     * @Param file
     * @Param fileName
     **/
    File save(MultipartFile file, String path, String fileName);

    /**
     * 保存输入流为文件对象
     *
     * @Author 滕鑫源
     * @Date 2020/8/20 14:47
     * @Param file
     * @Param fileName
     **/
    File save(InputStream is, String path, @NotNull String fileName);
}
