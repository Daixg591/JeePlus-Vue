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
 * 发件箱Entity
 *
 * @author jeeplus
 * @version 2021-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MailComposeDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    /**
     *  状态 0 草稿 1 已发送
     */
    @Query(tableColumn = "a.status", type = QueryType.EQ)
    private String status;
    /**
     * 发送者
     */
    @Query(tableColumn = "a.sender_id", javaField ="sender.id", type = QueryType.EQ)
    private UserDTO sender;
    /**
     * 收信人ID
     */
    private String receiverIds;
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
