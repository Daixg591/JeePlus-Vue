package com.jeeplus.wps;

import com.jeeplus.wps.model.FileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

    public static String appid = "";
    public static String appSecret = "";
    public static String domain = "";
    public static String download_host = "";

    @Autowired
    public ApplicationProperties(
            @Value("${wps.appid}") String appid,
            @Value("${wps.appsecret}") String appSecret,
            @Value("${wps.download_host}") String download_host,
            @Value("${wps.domain}") String domain) {
        ApplicationProperties.appid = appid;
        ApplicationProperties.appSecret = appSecret;
        ApplicationProperties.domain = domain;
        ApplicationProperties.download_host = download_host;
        if (download_host != null)
            FileModel.download_url = download_host + "/weboffice/getFile?_w_fname=";
    }

}
