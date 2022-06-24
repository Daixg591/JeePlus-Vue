package com.jeeplus.common.repository;

import com.jeeplus.common.repository.Exception.FunctionNotSupportedException;
import com.jeeplus.common.utils.FileUtils;
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

/**
 * 本地存储服务类
 *
 * @author 滕鑫源
 * @date 2020/8/20 14:42
 */
@Component
@ConditionalOnProperty(name = "config.accessory.type", havingValue = "local")
public class LocalRepository implements AccessoryRepository {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${config.accessory.baseDir}")
    private String accessoryBaseDir;

    @Value("${config.accessory.local.location}")
    private String location;

    /**
     * 根据保存的相对路径获取存储的文件对象
     *
     * @param path     指定文件的路径, 不含文件名
     * @param fileName 指定文件的文件名, 不含路径
     * @return java.io.File
     * @Author 滕鑫源
     * @Date 2020/8/20 14:46
     */
    @Override
    public File get(String path, String fileName) throws FileNotFoundException {
        logger.debug("开始从本地存储获取文件: {}{}{}", location + File.separator + accessoryBaseDir + File.separator + path, File.separator, fileName);
        long begin = System.currentTimeMillis();
        File rootDir = new File(location + File.separator + accessoryBaseDir);
        if (path == null) {
            path = "";
        }
        File fileDir = new File(rootDir, path);
        if (!fileDir.exists() || !fileDir.isDirectory()) {
            throw new FileNotFoundException("指定的目录不存在");
        }
        File srcFile = new File(fileDir, fileName);
        if (!srcFile.exists() || !srcFile.isFile()) {
            throw new FileNotFoundException("指定的文件不存在");
        }
        logger.debug("完成从本地存储获取文件: {}", System.currentTimeMillis() - begin);
        return srcFile;
    }

    @Override
    public URL getURL(String path, String fileName) throws FunctionNotSupportedException {
        throw new FunctionNotSupportedException("本地存储不支持获取下载路径");
    }

    /**
     * 保存文件对象
     *
     * @param file     需要保存的文件对象
     * @param path     存储的中段路径
     * @param fileName 存储的文件名
     * @Author 滕鑫源
     * @Date 2020/8/20 14:47
     */
    @Override
    public File save(File file, String path, String fileName) {
        logger.debug("开始向本地存储保存文件: {}{}{}", location + File.separator + accessoryBaseDir + File.separator + path, File.separator, fileName);
        long begin = System.currentTimeMillis();
        try {
            File destFile = createDestFile(path, fileName);
            checkFile(destFile);
            FileUtils.copyFile(file, destFile);
            return destFile;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            logger.debug("完成从本地存储获取文件: {}", System.currentTimeMillis() - begin);
        }
        return null;
    }

    /**
     * 保存文件流文件为文件对象
     *
     * @param file     需要保存的文件对象
     * @param path     存储的中段路径
     * @param fileName 存储的文件名
     * @Author 滕鑫源
     * @Date 2020/8/20 14:49
     */
    @Override
    public File save(MultipartFile file, String path, String fileName) {
        logger.debug("开始向本地存储保存文件: {}{}{}", location + File.separator + accessoryBaseDir + File.separator + path, File.separator, fileName);
        long begin = System.currentTimeMillis();
        try {
            File destFile = createDestFile(path, fileName);
            checkFile(destFile);
            FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
            return destFile;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            logger.debug("完成从本地存储获取文件: {}", System.currentTimeMillis() - begin);
        }
        return null;
    }

    /**
     * 保存输入流为文件对象
     *
     * @param is       需要保存的文件对象
     * @param path     存储的中段路径
     * @param fileName 存储的文件名
     * @Author 滕鑫源
     * @Date 2020/8/20 14:47
     */
    @Override
    public File save(InputStream is, String path, @NotNull String fileName) {
        logger.debug("开始向本地存储保存文件: {}{}{}", location + File.separator + accessoryBaseDir + File.separator + path, File.separator, fileName);
        long begin = System.currentTimeMillis();
        try {
            File destFile = createDestFile(path, fileName);
            checkFile(destFile);
            FileUtils.copyInputStreamToFile(is, destFile);
            return destFile;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            logger.debug("完成从本地存储获取文件: {}", System.currentTimeMillis() - begin);
        }
        return null;
    }

    /**
     * 根据存储地址, 根路径和文件名, 创建本地文件
     *
     * @param path     存储的中段路径
     * @param fileName 存储的文件名
     * @return java.io.File
     * @Author 滕鑫源
     * @Date 2020/8/26 11:57
     **/
    private File createDestFile(String path, String fileName) {
        File rootDir = new File(location + File.separator + accessoryBaseDir);
        if (path == null) {
            path = "";
        }
        File destDir = new File(rootDir, path);
        FileUtils.createDirectory(destDir.getAbsolutePath());
        return new File(destDir, fileName);
    }

    /**
     * 校验文件是否存在, 并给出异常信息
     *
     * @param file 需要校验的文件
     * @Author 滕鑫源
     * @Date 2020/8/26 11:57
     **/
    private void checkFile(File file) {
        if (file.exists()) {
            logger.warn("指定的文件已存在, 本次操作将会覆盖: {}", file.getAbsolutePath());
            if (file.isDirectory()) {
                logger.error("指定的文件是文件夹, 保存文件操作无法进行.");
                throw new RuntimeException("指定的文件是文件夹, 保存文件操作无法进行.");
            }
        }
    }
}
