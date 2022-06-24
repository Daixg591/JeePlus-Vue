/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 审核人Entity
 * @author 刘高峰
 * @version 2021-09-23
 */


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_extension_assignee")
public class FlowAssignee extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户类型
	 */
	private String type;
	/**
	 * 用户来自
	 */
	@TableField("`value`")
	private String value;
	/**
	 * 附加条件
	 */
	@TableField("`condition`")
	private String condition;
	/**
	 * 运算类型
	 */
	private String operationType;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 外键 父类
	 */
	private String taskDefId;

	public FlowAssignee() {
		super();
	}

}
