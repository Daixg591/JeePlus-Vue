/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service.dto;


import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 审核人DTO
 * @author 刘高峰
 * @version 2021-09-23
 */


@Data
@EqualsAndHashCode(callSuper = false)
public class FlowAssigneeDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户类型
	 */
	private String type;
	/**
	 * 用户来自
	 */
	private String value;
	/**
	 * 附加条件
	 */
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
	private TaskDefExtensionDTO taskDef;

	public FlowAssigneeDTO() {
		super();
	}

	public FlowAssigneeDTO(String id){
		super(id);
	}

	public FlowAssigneeDTO(TaskDefExtensionDTO taskDef){
		this.taskDef = taskDef;
	}

}
