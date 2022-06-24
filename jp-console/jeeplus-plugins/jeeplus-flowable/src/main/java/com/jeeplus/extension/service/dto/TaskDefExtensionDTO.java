/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service.dto;

import com.google.common.collect.Lists;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 工作流扩展Entity
 * @author 刘高峰
 * @version 2021-09-23
 */


@Data
@EqualsAndHashCode(callSuper = false)
public class TaskDefExtensionDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	/**
     * 流程定义id
	 */
	private String processDefId;
	/**
     * 任务定义id
	 */
	private String taskDefId;

	private List<FlowAssigneeDTO> flowAssigneeList = Lists.newArrayList();

	private List<FlowButtonDTO> flowButtonList = Lists.newArrayList();

	private List<FlowConditionDTO> flowConditionList = Lists.newArrayList();

	public TaskDefExtensionDTO() {
		super();
	}

	public TaskDefExtensionDTO(String id){
		super(id);
	}
}
