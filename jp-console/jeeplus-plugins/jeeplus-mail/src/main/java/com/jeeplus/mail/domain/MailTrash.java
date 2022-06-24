/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.mail.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 垃圾箱
 * @author 刘高峰
 * @version 2021-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("plugin_mail_trash")
public class MailTrash extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 邮件类型
	 */
	private String status;
	/**
	 *  发件人
	 */
	private String senderId;
	/**
	 * 收件人
	 */
	private String receiverIds;
	/**
	 * 发送时间
	 */
	private Date sendTime;
	/**
	 * 邮件Id
	 */
	private String mailId;		// 邮件id

}
