/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程条件Entity
 * @author 刘高峰
 * @version 2021-09-23
 */


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_extension_conditions")
public class FlowCondition extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 变量名
	 */
	private String field;
	/**
	 * 比较类型
	 */
	private String compare;
	/**
	 * 比较值
	 */
	private String value;
	/**
	 * 运算类型
	 */
	private String logic;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 外键 父类
	 */
	private String taskDefId;

	public FlowCondition() {
		super();
	}

}
