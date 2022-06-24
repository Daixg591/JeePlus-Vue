package com.jeeplus.wps.utils;

import com.jeeplus.config.properties.JeePlusProperties;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64String;

public class WpsUtils {

    public static String getUrlParam(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (builder.length() > 0) {
                builder.append('&');
            }
            builder.append(URLEncoder.encode(entry.getKey(), "utf-8")).append('=').append(URLEncoder.encode(entry.getValue(), "utf-8"));
        }
        return  builder.toString();
    }

    public static String getSignature(Map<String, String> params, String appSecret) {
        List<String> keys=new ArrayList ();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            keys.add(entry.getKey());
        }

        // 将所有参数按key的升序排序
        Collections.sort(keys, new Comparator<String> () {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        // 构造签名的源字符串
        StringBuilder contents=new StringBuilder("");
        for (String key : keys) {
            if (key=="_w_signature"){
                continue;
            }
            contents.append(key+"=").append(params.get(key));
            System.out.println("key:"+key+",value:"+params.get(key));
        }
        contents.append("_w_secretkey=").append(appSecret);

        // 进行hmac sha1 签名
        byte[] bytes= hmacSha1(appSecret.getBytes(),contents.toString().getBytes());
        //字符串经过Base64编码
        String sign= encodeBase64String(bytes);
        try {
            sign = URLEncoder.encode( sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(sign);
        return sign;
    }

    /**
     * 程序附件物理存储地址
     * @return
     */
    public static String getAttachmentDir(String id){
        String dir = JeePlusProperties.newInstance().getUserfilesBaseDir() + JeePlusProperties.USERFILES_BASE_URL + id + "/程序附件/";
        com.jeeplus.common.utils.FileUtils.createDirectory(dir);
        return dir;
    }

    /**
     * 程序附件网络访问地址
     * @return
     */
    public static String getAttachmentUrl(String id){

        return  JeePlusProperties.USERFILES_BASE_URL + id + "/程序附件/";
    }

    public static byte[] hmacSha1(byte[] key, byte[] data) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");
            Mac mac = Mac.getInstance(signingKey.getAlgorithm());
            mac.init(signingKey);
            return mac.doFinal(data);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
