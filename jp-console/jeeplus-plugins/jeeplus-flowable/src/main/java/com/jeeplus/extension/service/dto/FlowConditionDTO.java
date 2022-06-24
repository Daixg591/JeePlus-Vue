/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service.dto;


import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程条件Entity
 * @author 刘高峰
 * @version 2021-09-23
 */


@Data
@EqualsAndHashCode(callSuper = false)
public class FlowConditionDTO extends BaseDTO {

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
	private TaskDefExtensionDTO taskDef;

	public FlowConditionDTO() {
		super();
	}

	public FlowConditionDTO(String id){
		super(id);
	}

	public FlowConditionDTO(TaskDefExtensionDTO taskDef){
		this.taskDef = taskDef;
	}

}
