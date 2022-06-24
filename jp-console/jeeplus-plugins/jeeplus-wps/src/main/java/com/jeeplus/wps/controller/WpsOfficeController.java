package com.jeeplus.wps.controller;

import com.jeeplus.wps.ApplicationProperties;
import com.jeeplus.wps.utils.WpsUtils;
import com.jeeplus.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WpsOfficeController {
    @Value("${wps.webctx}")
    private String webctx;
    @RequestMapping("/weboffice/index")
    public String index(String filename, String fileid,  Model model) {

        model.addAttribute("webctx", webctx);
        model.addAttribute("filename", filename);
        model.addAttribute("fileid", fileid);
        return "office";

    }

    @ResponseBody
    @RequestMapping(value="/weboffice/new/url", method = RequestMethod.GET)
    public ResponseEntity getapp_Token(@RequestParam("type") String type) throws UnsupportedEncodingException {

        String url = ApplicationProperties.domain + "/office/"+type+"/new/0?";
        String _w_userid = UserUtils.getCurrentUserDTO ().getId ();
        //// TODO: 注意：签名前，参数不要urlencode,要签名以后统一处理url编码，防止签名不过，带中文等字符容易导致签名不过，要注意签名与编成的顺序，最好不要带中文等特殊字符
        Map paramMap= new HashMap<String, String> ();
        paramMap.put("_w_userid", _w_userid);
        paramMap.put("_w_appid", ApplicationProperties.appid);
        paramMap.put("_w_redirectkey", "123456");
        String filename = "未命名";
        if (type.equals ("s")) {
            filename += ".xlsx";
        } else if (type.equals ("p")) {
            filename += ".pptx";
        } else {
            filename += ".docx";
        }
        paramMap.put ("_w_fname", filename);
        String signature = WpsUtils.getSignature(paramMap, ApplicationProperties.appSecret);
        url += WpsUtils.getUrlParam(paramMap) + "&_w_signature=" + signature;
        return ResponseEntity.ok ( url );

    }

}
