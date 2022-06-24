/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.mail.service.dto;

import com.jeeplus.core.service.dto.BaseDTO;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.mail.utils.MailUtils;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;


/**
 * 收件箱Entity
 *
 * @author jeeplus
 * @version 2021-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MailBoxDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    /**
     *  状态 0 未读 1 已读
     */
    @Query(tableColumn = "a.read_status", type = QueryType.EQ)
    private String readStatus;
    /**
     * 发件人
     */
    private UserDTO sender;
    /**
     * 全部收件人Id
     */
    private String receiverIds;
    /**
     * 当前收件人
     */
    @Query(tableColumn = "a.receiver_id", javaField = "receiver.id", type = QueryType.EQ)
    private UserDTO receiver;
    /**
     * 发送时间
     */
    private Date sendTime;
    /**
     * 邮件内容
     */
    @Query(tableColumn = "mail.title", javaField = "mailDTO.title", type = QueryType.LIKE)
    private MailDTO mailDTO;


    /**
     * 获取收件人用户Name
     *
     * @return
     */
    public String getReceiverNames() {
        return MailUtils.getReceiverNames ( receiverIds );
    }

    /**
     * 获取收件人用户
     *
     * @return
     */
    public List<User> getReceiverList() {
       return MailUtils.getReceiverList ( receiverIds );
    }
}
