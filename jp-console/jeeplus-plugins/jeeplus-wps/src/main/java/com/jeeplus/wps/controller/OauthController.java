package com.jeeplus.wps.controller;

import com.jeeplus.wps.ApplicationProperties;
import com.jeeplus.wps.model.UrlModel;
import com.jeeplus.wps.utils.WpsUtils;
import com.jeeplus.sys.utils.UserUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.*;


@RestController
public class OauthController  {
    private static Map<String, String> fileTypeMap = new HashMap<String, String>();

    @RequestMapping(value="/weboffice/url", method = RequestMethod.GET)
    public Object  getapp_Token(@RequestParam("_w_fname") String filename, @RequestParam("_w_fileid") String fileid) throws UnsupportedEncodingException {
        if (fileid == null || fileid.isEmpty()) {
            return null;
        }
        String url = ApplicationProperties.domain + "/office";
        if (filename.endsWith("xls") || filename.endsWith("xlsx")) {
            url += "/s";
        } else if (filename.endsWith("ppt") || filename.endsWith("pptx")) {
            url += "/p";
        } else if (filename.endsWith("pdf")) {
            url += "/f";
        } else {
            url += "/w";
        }
        url = url + "/" + fileid + "?" ;
        //// TODO: 注意：签名前，参数不要urlencode,要签名以后统一处理url编码，防止签名不过，带中文等字符容易导致签名不过，要注意签名与编成的顺序，最好不要带中文等特殊字符
        Map paramMap= new HashMap<String, String>();
        paramMap.put("_w_appid", ApplicationProperties.appid);
        paramMap.put("_w_fname", filename);
        paramMap.put("_w_fileid", fileid);
        paramMap.put("_w_userid", UserUtils.getCurrentUserDTO ().getId ());
        String signature = WpsUtils.getSignature(paramMap, ApplicationProperties.appSecret);
        url += WpsUtils.getUrlParam(paramMap) + "&_w_signature=" + signature;
        UrlModel urlModel = new UrlModel();
        urlModel.wpsUrl = url;
        urlModel.token = "1";
        return  urlModel;
    }

}
