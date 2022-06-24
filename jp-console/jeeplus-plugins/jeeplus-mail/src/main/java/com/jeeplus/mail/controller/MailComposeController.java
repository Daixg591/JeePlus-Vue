/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.mail.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.mail.domain.Mail;
import com.jeeplus.mail.domain.MailBox;
import com.jeeplus.mail.domain.MailCompose;
import com.jeeplus.mail.domain.MailTrash;
import com.jeeplus.mail.service.MailBoxService;
import com.jeeplus.mail.service.MailComposeService;
import com.jeeplus.mail.service.MailService;
import com.jeeplus.mail.service.MailTrashService;
import com.jeeplus.mail.service.dto.MailComposeDTO;
import com.jeeplus.mail.service.mapstruct.MailComposeWrapper;
import com.jeeplus.mail.service.mapstruct.MailWrapper;
import com.jeeplus.sys.domain.User;
import com.jeeplus.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 发件箱Controller
 *
 * @author jeeplus
 * @version 2021-06-12
 */
@RestController
@RequestMapping("/mail/compose")
public class MailComposeController {

    @Autowired
    private MailComposeService mailComposeService;

    @Autowired
    private MailBoxService mailBoxService;

    @Autowired
    private MailService mailService;

    @Autowired
    private MailTrashService mailTrashService;

    @Autowired
    private MailComposeWrapper mailComposeWrapper;

    @Autowired
    private MailWrapper mailWrapper;


    @GetMapping("list")
    public ResponseEntity <IPage <MailComposeDTO>> list(MailComposeDTO mailComposeDTO, Page <MailComposeDTO> page) throws Exception {
        mailComposeDTO.setSender ( UserUtils.getCurrentUserDTO ( ) );
        QueryWrapper <MailComposeDTO> queryWrapper = QueryWrapperGenerator.buildQueryCondition ( mailComposeDTO, MailComposeDTO.class );
        IPage <MailComposeDTO> result = mailComposeService.findPage ( page, queryWrapper );
        return ResponseEntity.ok ( result );
    }

    @GetMapping("queryById")//打开已发送信件
    public ResponseEntity queryById(String id) {
        MailComposeDTO mailComposeDTO = mailComposeService.get ( id );
        return ResponseEntity.ok ( mailComposeDTO );
    }

    @PostMapping("save")
    public ResponseEntity save(@RequestBody MailComposeDTO mailComposeDTO) {

        //保存邮件内容
        Mail mail = mailWrapper.toEntity ( mailComposeDTO.getMailDTO ( ) );
        mailService.saveOrUpdate ( mail );
        mailComposeDTO.getMailDTO ().setId ( mail.getId () );
        Date date = new Date ( System.currentTimeMillis ( ) );
        mailComposeDTO.setSender ( UserUtils.getCurrentUserDTO ( ) );
        mailComposeDTO.setSendTime ( date );

        //保存草稿
        if ( "0".equals ( mailComposeDTO.getStatus ( ) ) ) {
            mailComposeService.saveOrUpdate ( mailComposeWrapper.toEntity ( mailComposeDTO ) );//0 显示在草稿箱，1 显示在已发送需同时保存到收信人的收件箱。

            return ResponseEntity.ok ( "保存成功!" );
        }

        //发送邮件
        if ( "1".equals ( mailComposeDTO.getStatus ( ) ) )//已发送，同时保存到收信人的收件箱
        {
            mailComposeService.saveOrUpdate ( mailComposeWrapper.toEntity ( mailComposeDTO ) );//0 显示在草稿箱，1 显示在已发送需同时保存到收信人的收件箱。
            for (User receiver : mailComposeDTO.getReceiverList ( )) {
                MailBox mailBox = new MailBox ( );
                mailBox.setReadStatus ( "0" );
                mailBox.setReceiverIds ( mailComposeDTO.getReceiverIds ( ) );
                mailBox.setReceiverId ( receiver.getId ( ) );
                mailBox.setSenderId ( UserUtils.getCurrentUserDTO ( ).getId ( ) );
                mailBox.setMailId ( mailComposeDTO.getMailDTO ( ).getId ( ) );
                mailBox.setSendTime ( date );
                mailBoxService.saveOrUpdate ( mailBox );
            }
        }

        return ResponseEntity.ok ( "发送成功!" );
    }


    /**
     * 批量删除已发送
     */
    @DeleteMapping("delete")
    public ResponseEntity deleteAllCompose(String ids) {
        String idArray[] = ids.split ( "," );
        for (String id : idArray) {
            MailCompose mailCompose = mailComposeService.getById ( id );
            MailTrash mailTrash = new MailTrash ( );
            mailTrash.setMailId ( mailCompose.getMailId ( ) );
            mailTrash.setReceiverIds ( mailCompose.getReceiverIds ( ) );
            mailTrash.setSenderId ( mailCompose.getSenderId ( ) );
            mailTrash.setSendTime ( mailCompose.getSendTime ( ) );
            mailTrash.setStatus ( mailCompose.getStatus ( ) );
            mailTrashService.save ( mailTrash );
            mailComposeService.removeById ( id );
        }
        return ResponseEntity.ok ( "删除站内信成功" );
    }


}
