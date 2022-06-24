/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.tools.controller;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import com.jeeplus.sys.domain.SysConfig;
import com.jeeplus.sys.service.SysConfigService;
import com.jeeplus.tools.model.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发送外部邮件
 *
 * @author lgf
 * @version 2021-01-07
 */
@RestController
@RequestMapping("/tools/email")
public class EmailController {

    @Autowired
    private SysConfigService systemConfigService;

    /**
     * 发送邮件
     */
    @RequestMapping("send")
    public ResponseEntity<String> send(@RequestBody EmailRequest emailRequest) throws Exception {
        SysConfig config = systemConfigService.getById ("1");
        String[] addresses = emailRequest.getEmailAddress ().split(";");
        String result = "";
        MailAccount mailAccount = new MailAccount();
        mailAccount.setAuth(true);
        mailAccount.setSslEnable(true);
        mailAccount.setHost(config.getSmtp ());
        mailAccount.setPort(Integer.valueOf (config.getPort ()));
        mailAccount.setAuth(true);
        mailAccount.setFrom(config.getMailName ());
        mailAccount.setPass(config.getMailPassword ());
        mailAccount.setSocketFactoryClass ("mail.smtp.ssl.socketFactory");

        for (String address : addresses) {
            try {
                Mail.create ( mailAccount )
                        .setTos ( address )
                        .setTitle ( emailRequest.getTitle () )
                        .setContent ( emailRequest.getContent () )
                        .setHtml ( true )
                        .send ( );
                result += address + ":<font color='green'>发送成功!</font><br/>";
            } catch (Exception e){
                result += address + ":<font color='red'>发送失败!</font><br/>";
            }
        }
        return ResponseEntity.ok (result);
    }

}
