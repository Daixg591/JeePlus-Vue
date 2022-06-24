package com.jeeplus.wps.controller;

import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.config.properties.FileProperties;
import com.jeeplus.config.properties.JeePlusProperties;
import com.jeeplus.office.domain.DocTemplate;
import com.jeeplus.office.service.DocTemplateService;
import com.jeeplus.wps.ApplicationProperties;
import com.jeeplus.wps.model.FileModel;
import com.jeeplus.wps.utils.WpsUtils;
import com.jeeplus.sys.utils.UserUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WebOfficeController {
    @Value("${wps.downloadCallbackPath}")
    private String downloadCallbackPath;
    @Autowired
    private DocTemplateService docTemplateService;
    @Autowired
    FileProperties fileProperties;

    // 在线编辑时,wps会根据文件名_w_fname,从这里获取file信息,其中包含了文档的下载url
    @RequestMapping(value = "/v1/3rd/file/info", method = RequestMethod.GET)
    public Object fileInfo(@RequestParam("_w_fname") String filename, String _w_userid, @RequestParam("_w_fileid") String fileid) throws Exception {
        System.out.println("Method fileIno(" + filename + ") is invoked");
        JSONObject jsonObject = new JSONObject();
        JSONObject file = new JSONObject();
        JSONObject user = new JSONObject();
        File f = null;
        String file1 = JeePlusProperties.newInstance().getUserfilesBaseDir() +filename;
        f = new File(file1);

        try {
            FileModel fileModel = new FileModel();
            file.put("id", fileid);
            file.put("name", f.getName());
            file.put("version", 1);
            file.put("size", f.length());
            file.put("creator", _w_userid);
            file.put("modifier", _w_userid);
            // 文档url
            file.put("download_url", fileModel.download_url +  URLEncoder.encode(filename, "utf-8"));
            jsonObject.put("file", file);
            user.put("id", _w_userid);
            user.put("name", UserUtils.get (_w_userid).getName ());
            user.put("permission", "write");
            user.put("avatar_url",  "");
            jsonObject.put("user", user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();

    }

    // 文档修改后调用的回调接口
    @RequestMapping(value = "/v1/3rd/file/save", method = RequestMethod.POST)
    public Object save(@RequestParam("file") MultipartFile file, HttpServletRequest request, String _w_userid, @RequestParam("_w_fname") String filename) throws Exception{
        String file1 = JeePlusProperties.newInstance().getUserfilesBaseDir() +filename;

        File f = new File(file1);
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(f));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JSONObject jsonObject = new JSONObject();
        FileModel fileModel = new FileModel();
        JSONObject file2 = new JSONObject();
        String name = filename.substring(filename.lastIndexOf("/")+1);
        String path = filename.substring(0,filename.lastIndexOf("/")+1 );
        String fileid = request.getHeader("x-weboffice-file-id");
        file2.put("id", fileid);
        file2.put("name", name);
        file2.put("version", 1);
        file2.put("size", file.getSize());
        file2.put("creator", _w_userid);
        file2.put("modifier",_w_userid);
        // 文档url
        file2.put("download_url", fileModel.download_url + path+ URLEncoder.encode(name, "utf-8"));
        jsonObject.put("file", file2);


        return jsonObject.toString();
    }

    // 文档的下载url
    @GetMapping(value="/weboffice/getFile", produces="application/octet-stream;charset=UTF-8")
    public ResponseEntity<byte[]> getFile(@RequestParam("_w_fname") String filename) throws Exception {
        String file1 = JeePlusProperties.newInstance().getUserfilesBaseDir() +filename;
        System.out.println("Method getFile(" + filename + ") is invoked");
        File file = new File(file1);
        InputStream inputStream = new FileInputStream(file);
        byte[] body = new byte[inputStream.available()];
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        inputStream.read(body);



        return new ResponseEntity(body, headers, HttpStatus.OK);

    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[10240];

        int len = 0;

        while ((len = inStream.read(buffer)) != -1) {

            outStream.write(buffer, 0, len);

        }

        inStream.close();

        return outStream.toByteArray();

    }


    @RequestMapping(value = "/v1/3rd/file/version/{version}", method = RequestMethod.GET)
    public Object fileVersionInfo(@PathVariable("version") Long version, String _w_userid, @RequestParam("_w_fname") String filename) {
        JSONObject jsonObject = new JSONObject();
        JSONObject file = new JSONObject();
        JSONObject user = new JSONObject();
        try {
            FileModel fileModel = new FileModel();
            file.put("id", fileModel.id);
            file.put("name", filename);
            file.put("version", fileModel.version);
            file.put("size", fileModel.size);
            file.put("creator", _w_userid);
            file.put("modifier", _w_userid);
            file.put("download_url", filename);
            jsonObject.put("file", file);
            user.put("id", _w_userid);
            user.put("name", UserUtils.get (_w_userid).getName ());
            user.put("permission", "write");
            user.put("avatar_url", "");
            jsonObject.put("user", user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/v1/3rd/user/info", method = RequestMethod.POST)
    public Object userInfo( String _w_userid) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject user = new JSONObject();
        try {
            user.put("id", _w_userid);
            user.put("name", UserUtils.get (_w_userid).getName ());
            user.put("permission", "write");
            user.put("avatar_url",  "");
            jsonArray.put(user);
            jsonObject.put("users", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }



    /**
     * 新建文件
     */
    @PostMapping("/v1/3rd/file/new")
    public String fileNew(@RequestBody MultipartFile file, String _w_userid, @RequestParam("_w_fname") String filename) throws Exception{



        String uploadPath = "wps/docTemplate/";
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH )+1;

        String fileUrl = WpsUtils.getAttachmentUrl(_w_userid)+uploadPath+"/"+year+"/"+month+"/";
        String fileDir = WpsUtils.getAttachmentDir(_w_userid)+uploadPath+"/"+year+"/"+month+"/";
        String url = "";
        String type = "/w";
        // 判断文件是否为空
        if (!file.isEmpty()) {
            String name = file.getOriginalFilename ();
            if (fileProperties.isAvailable (name)) {
                // 文件保存路径
                // 转存文件
                FileUtils.createDirectory (fileDir);
                String filePath = fileDir + name;
                File newFile = FileUtils.getAvailableFile (filePath, 0);
                file.transferTo (newFile);
                url = fileUrl + newFile.getName ();
                filename = newFile.getName ();


            }
        }

        if (filename.endsWith("xls") || filename.endsWith("xlsx")) {
            type = "/s";
        } else if (filename.endsWith("ppt") || filename.endsWith("pptx")) {
            type = "/p";
        } else if (filename.endsWith("pdf")) {
            type = "/f";
        } else {
            type = "/w";
        }

        DocTemplate docTemplate = new DocTemplate ();
        docTemplate.setName (filename.split ("\\.")[0]);
        docTemplate.setPath (url);
        docTemplateService.save(docTemplate);//保存

        String fileid = String.valueOf (new Date().getTime ());




        String redirectUrl = ApplicationProperties.domain + "/office"+type+"/" + fileid + "?";

        //// TODO: 注意：签名前，参数不要urlencode,要签名以后统一处理url编码，防止签名不过，带中文等字符容易导致签名不过，要注意签名与编成的顺序，最好不要带中文等特殊字符
        Map paramMap= new HashMap<String, String> ();
        paramMap.put("_w_userid", _w_userid);
        paramMap.put("_w_appid", ApplicationProperties.appid);
        paramMap.put ("_w_fname", url);
        paramMap.put ("_w_fileid", fileid);
        String signature = WpsUtils.getSignature(paramMap, ApplicationProperties.appSecret);
        redirectUrl += WpsUtils.getUrlParam(paramMap) + "&_w_signature=" + signature;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put ("redirect_url", redirectUrl);
        jsonObject.put("user_id", _w_userid);
        return jsonObject.toString ();
    }
    @RequestMapping(value = "/v1/3rd/file/online", method = RequestMethod.POST)
    public void online() {
    }

    @RequestMapping(value = "/v1/3rd/file/history", method = RequestMethod.POST)
    public Object history() {
        JSONObject jsonObject = new JSONObject();
        return jsonObject.toString();
    }

}
