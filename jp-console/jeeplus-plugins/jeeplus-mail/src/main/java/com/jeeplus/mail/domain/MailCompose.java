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
 * 发件箱Entity
 *
 * @author jeeplus
 * @version 2021-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("plugin_mail_compose")
public class MailCompose extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 状态 0 草稿 1 已发送
     */
    @Query(type = QueryType.EQ)
    private String status;
    /**
     * 发送者
     */
    @Query(type = QueryType.EQ)
    private String senderId;
    /**
     * 收信人ID
     */
    private String receiverIds;
    /**
     * 发送时间
     */
    private Date sendTime;
    /**
     *  邮件id 父类
     */
    private String mailId;


}
