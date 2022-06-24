/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.mail.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 收件箱Entity
 *
 * @author jeeplus
 * @version 2021-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("plugin_mail_box")
public class MailBox extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 状态 0 未读 1 已读
     */
    @Query(type = QueryType.EQ)
    private String readStatus;
    /**
     *  发件人
     */
    private String senderId;
    /**
     * 全部收件人Id
     */
    private String receiverIds;
    /**
     * 当前收件人
     */
    @Query(type = QueryType.EQ)
    private String receiverId;
    /**
     * 发送时间
     */
    private Date sendTime;
    /**
     * 邮件外键 父类
     */
    private String mailId;


}
