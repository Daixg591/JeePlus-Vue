/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.tools.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.jeeplus.common.utils.ResponseUtil;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.sys.utils.FileKit;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 二维码Controller
 *
 * @author jeeplus
 * @version 2021-06-30
 */
@RestController
@RequestMapping("/tools/TwoDimensionCodeController")
public class TwoDimensionCodeController {

    /**
     * 生成二维码
     *
     * @throws Exception
     */
    @GetMapping("createTwoDimensionCode")
    public ResponseEntity createTwoDimensionCode(@Param ( "encoderContent" ) String encoderContent) throws Exception {
        String realPath = FileKit.getAttachmentDir() + "qrcode/";
        FileUtils.createDirectory(realPath);
        String name = "test.png"; //encoderImgId此处二维码的图片名
        // 生成指定url对应的二维码到文件，宽和高都是300像素
        QrCodeUtil.generate(encoderContent, 300, 300, FileUtil.file(realPath + name));

        return ResponseUtil.newInstance ().add("filePath", FileKit.getAttachmentUrl() + "qrcode/" + name).ok ("二维码生成成功");
    }

}
