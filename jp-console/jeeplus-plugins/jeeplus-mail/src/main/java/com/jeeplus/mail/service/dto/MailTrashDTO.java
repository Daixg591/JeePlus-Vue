/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
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
 * 垃圾箱
 * @author 刘高峰
 * @version 2021-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MailTrashDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	/**
	 * 邮件类型
	 */
	@Query(tableColumn = "a.status", type = QueryType.EQ)
	private String status;
	/**
	 * 发件人
	 */
	private UserDTO sender;
	/**
	 * 收件人
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
