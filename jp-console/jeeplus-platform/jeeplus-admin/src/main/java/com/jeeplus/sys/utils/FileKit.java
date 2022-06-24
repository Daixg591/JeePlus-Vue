/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.utils;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.jeeplus.config.properties.JeePlusProperties;
import com.jeeplus.sys.model.FileData;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 刘高峰 on 2018/3/18.
 */
public class FileKit {
    public static List<FileData> getFileList(File[] files) {
        List fileDataList = Lists.newArrayList();
        if (files != null) {
            for (File file : files) {
                FileData fileData = new FileData();
                if (file.isDirectory()) { // 判断是文件还是文件夹
                    fileData.setId(file.getName());
                    fileData.setType("folder");
                    fileData.setOpen(true);
                    fileData.setValue(file.getName());
                    fileData.setData(getFileList(file.listFiles()));
                } else  { // 判断文件名是否以.avi结尾
                    fileData.setId(file.getName());
                    fileData.setType("file");
                    fileData.setSize(String.valueOf(file.getTotalSpace()));
                    fileData.setValue(file.getName());
                }
                fileDataList.add(fileData);
            }

        }
        return fileDataList;
    }

    public  static String getFileType(String fileName){
        String type = "file";
        String suffix = StrUtil.subAfter (fileName, ".", true);
        switch (suffix){
            //html
            case "htm":
            case "html":
            case "css":
            case "less":
            case "asp":
            case "php":
            case "jsp":
            case "js":
            case "java":
            case "class":
            case "c":
            case "sql":
                type = "code";
                break;
            //word
            case "doc":
                type = "word";
                break;
            case "txt":
                type = "text";
                break;
            case "wps":
                type = "word";
                break;
            case "xls":
            case "xlsx":
                type = "excel";
                break;
            case "ppt":
            case "pptx":
                type = "pp";
                break;
            case "pdf":
                //压缩文件
                type = "pdf";
                break;
            case "rar":
            case "zip":
                type = "archive";
                break;
            //
            case "exe":
                type = "fa-windows";
                break;
            //视频
            case "rmvb":
            case "wmv":
            case "asf":
            case "avi":
            case "3gp":
            case "mpg":
            case "mkv":
            case "mp4":
            case "dvd":
            case "ogm":
            case "mov":
            case "mpeg2":
            case "mpeg4":
                type = "video";
                break;
            //音频
            case "mp3":
            case "ogg":
            case "wav":
            case "ape":
            case "cda":
            case "au":
            case "midi":
            case "mac":
            case "aac":
                type = "audio";
                break;
            //flash
            case "flv":
            case "swf":
            case "m4v":
            case "f4v":
                type = "flash";
                break;
            //图片
            case "gif":
            case "jpeg":
            case "bmp":
            case "tif":
            case "png":
            case "jpg":
            case "pcd":
            case "qti":
            case "qtf":
            case "tiff":
                type = "image";
                break;
            default:
                type = "file";
        }
        return type;
    }

    private static Long getCreateTime(String fullFileName){
        Path path= Paths.get(fullFileName);
        BasicFileAttributeView basicview= Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS );
        BasicFileAttributes attr;
        try {
            attr = basicview.readAttributes();
            Date createDate = new Date(attr.creationTime().toMillis());
            return createDate.getTime()/1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.set(1970, 0, 1, 0, 0, 0);
        return cal.getTime().getTime()/1000;
    }

    public static List<FileData> getFileList(String pId,List<File> files) {
        List fileDataList = Lists.newArrayList();
        if (files != null) {
            for (File file : files) {
                FileData fileData = new FileData();
                if (file.isDirectory()) { // 判断是文件还是文件夹
                    fileData.setId(transDirToUrl (file.getAbsolutePath ()));
                    fileData.setType("folder");
                    fileData.setOpen(true);
                    fileData.setPId (pId);
                    fileData.setDate(getCreateTime(file.getAbsolutePath()));
                    fileData.setValue(file.getName());
                    file.listFiles();
                    fileData.setData(getFileList(file.getName(), 	Lists.newArrayList(file.listFiles())));
                } else  { // 判断文件
                    fileData.setId(transDirToUrl (file.getAbsolutePath()));
                    fileData.setType(getFileType(file.getName()));
                    fileData.setPId (pId);
                    fileData.setSize(String.valueOf(file.length()));
                    fileData.setDate(getCreateTime(file.getAbsolutePath()));
                    fileData.setValue(file.getName());
                }
                fileDataList.add(fileData);
            }

        }
        return fileDataList;
    }

    /**
     * 网络地址转为绝对地址
     * @return
     */
    public static  String getFileDir(String fileUrl){
        return  (JeePlusProperties.newInstance().getUserfilesBaseDir() + fileUrl).replace("\\","/");
    }

    /**
     * 绝对地址转换为网络地址
     * @return
     */
    public static String transDirToUrl(String dir){
        return   dir.substring( JeePlusProperties.newInstance().getUserfilesBaseDir().length());
    }


    /**
     * 共享文档物理存储地址
     * @return
     */
    public static String getShareBaseDir(){
        String dir =  JeePlusProperties.newInstance().getUserfilesBaseDir() + JeePlusProperties.USERFILES_BASE_URL  + "共享文档/";
        com.jeeplus.common.utils.FileUtils.createDirectory(dir);
        return dir;
    }
    /**
     * 共享文档网络访问地址
     * @return
     */
    public static String getShareBaseUrl(){
        return  JeePlusProperties.USERFILES_BASE_URL +  "/共享文档/";
    }

    /**
     * 我的文档物理存储地址
     * @return
     */
    public static String getMyDocDir(){
        String id = UserUtils.getCurrentUserDTO ().getId();
        String dir = JeePlusProperties.newInstance().getUserfilesBaseDir() + JeePlusProperties.USERFILES_BASE_URL + id + "/我的文档/";
        com.jeeplus.common.utils.FileUtils.createDirectory(dir);
        return dir;
    }
    /**
     * 我的文档网络访问地址
     * @return
     */
    public static String getMyDocUrl(){
        String id = UserUtils.getCurrentUserDTO ().getId();
        return  JeePlusProperties.USERFILES_BASE_URL + id + "/我的文档/";
    }

    /**
     * 程序附件物理存储地址
     * @return
     */
    public static String getAttachmentDir(){
        String id = UserUtils.getCurrentUserDTO ().getId();
        String dir = JeePlusProperties.newInstance().getUserfilesBaseDir() + JeePlusProperties.USERFILES_BASE_URL + id + "/程序附件/";
        com.jeeplus.common.utils.FileUtils.createDirectory(dir);
        return dir;
    }

    /**
     * 程序附件网络访问地址
     * @return
     */
    public static String getAttachmentUrl(){

        String id = UserUtils.getCurrentUserDTO ().getId();
        return  JeePlusProperties.USERFILES_BASE_URL + id + "/程序附件/";
    }


    public static String getFileSize(String fileDir){
        File file = new File(fileDir);
        long size = file.length()*100;
        String label;
        if (size == 0F){
            label = "0";
        }else if(size < 1024*100){
            label = String.valueOf(size/100)+"b";
        }else if(size <1024*1024*100){
            label = String.valueOf(size/1024/100F)+"KB";
        }else{
            label = String.valueOf(size/(1024*1024)/100F)+"M";
        }
        return label;
    }

}
