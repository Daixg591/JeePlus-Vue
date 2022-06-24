/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程抄送Entity
 * @author 刘高峰
 * @version 2021-10-10
 */

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_extension_cc")
public class FlowCopy extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 抄送用户id
	 */
	private String userId;
	/**
	 * 流程定义id
	 */
	private String procDefId;
	/**
	 * 流程实例id
	 */
	private String procInsId;
	/**
	 * 流程标题
	 */
	private String procDefName;
	/**
	 * 实例标题
	 */
	private String procInsName;
	/**
	 * 流程节点
	 */
	private String taskName;

	public FlowCopy() {
		super();
	}
}
