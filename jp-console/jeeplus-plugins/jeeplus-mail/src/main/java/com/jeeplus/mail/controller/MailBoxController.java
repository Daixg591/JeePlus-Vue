/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.mail.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.common.utils.ResponseUtil;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.mail.domain.MailBox;
import com.jeeplus.mail.domain.MailCompose;
import com.jeeplus.mail.domain.MailTrash;
import com.jeeplus.mail.service.MailBoxService;
import com.jeeplus.mail.service.MailComposeService;
import com.jeeplus.mail.service.MailTrashService;
import com.jeeplus.mail.service.dto.MailBoxDTO;
import com.jeeplus.mail.service.mapstruct.MailBoxWrapper;
import com.jeeplus.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 收件箱Controller
 *
 * @author jeeplus
 * @version 2021-06-12
 */
@RestController
@RequestMapping("/mail/box")
public class MailBoxController {

    @Autowired
    private MailComposeService mailComposeService;

    @Autowired
    private MailBoxService mailBoxService;

    @Autowired
    private MailTrashService mailTrashService;

    @Autowired
    private MailBoxWrapper mailBoxWrapper;

    @GetMapping("list")
    public ResponseEntity list(MailBoxDTO mailBoxDTO, Page <MailBoxDTO> page) throws Exception {
        mailBoxDTO.setReceiver ( UserUtils.getCurrentUserDTO () );
        QueryWrapper <MailBoxDTO> queryWrapper = QueryWrapperGenerator.buildQueryCondition (mailBoxDTO, MailBoxDTO.class);
        IPage <MailBoxDTO> result = mailBoxService.findPage (page, queryWrapper);
        return ResponseEntity.ok (result);
    }

    // 查询信箱邮件状态
    @GetMapping("queryStatus")
    public ResponseEntity queryStatus() {
        ResponseUtil responseUtil = ResponseUtil.newInstance ();
        //查询未读的条数
        int noReadCount = mailBoxService.lambdaQuery ().eq ( MailBox::getReadStatus, "0" ).eq ( MailBox::getReceiverId , UserUtils.getCurrentUserDTO ().getId ()).count ();
        responseUtil.add ("noReadCount", noReadCount);

        //查询总条数
        int mailBoxCount = mailBoxService.lambdaQuery ().eq ( MailBox::getReceiverId , UserUtils.getCurrentUserDTO ().getId ()).count ();
        responseUtil.add ("mailBoxCount", mailBoxCount);

        //查询已发送条数
        int mailComposeCount = mailComposeService.lambdaQuery ().eq(MailCompose::getStatus, "1").eq ( MailCompose::getSenderId , UserUtils.getCurrentUserDTO ().getId ()).count ();
        responseUtil.add ("mailComposeCount", mailComposeCount);


        //查询草稿箱条数
        int mailDraftCount = mailComposeService.lambdaQuery ().eq(MailCompose::getStatus, "0").eq ( MailCompose::getSenderId , UserUtils.getCurrentUserDTO ().getId ()).count ();
        responseUtil.add ("mailDraftCount", mailDraftCount);

        //查询垃圾箱数
        int mailTrashCount = mailTrashService.lambdaQuery ().eq ( MailTrash::getCreateBy, UserUtils.getCurrentUserDTO ().getId () ).count ();
        responseUtil.add ("mailTrashCount", mailTrashCount);
        return responseUtil.ok ();
    }


    @GetMapping("queryById")
    public ResponseEntity queryById(String id) {
        MailBoxDTO mailBoxDTO = mailBoxService.get ( id );
        if (mailBoxDTO.getReadStatus().equals("0")) {//更改未读状态为已读状态
            mailBoxDTO.setReadStatus("1");//1表示已读
            mailBoxService.updateById (mailBoxWrapper.toEntity ( mailBoxDTO ));
        }
        return ResponseEntity.ok (  mailBoxDTO );
    }


    /**
     * 批量删除
     */
    @DeleteMapping("delete")
    public ResponseEntity delete(String ids) {
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            MailBox mailBox = mailBoxService.getById (id);
            MailTrash mailTrash = new MailTrash ();
            mailTrash.setMailId (mailBox.getMailId ());
            mailTrash.setReceiverIds (mailBox.getReceiverIds ());
            mailTrash.setSenderId (mailBox.getSenderId ());
            mailTrash.setSendTime (mailBox.getSendTime ());
            mailTrash.setStatus (String.valueOf (Integer.valueOf (mailBox.getReadStatus ())+2));
            mailTrashService.save (mailTrash);
            mailBoxService.removeById ( id );
        }
        return ResponseEntity.ok ("删除站内信成功!");
    }
}
