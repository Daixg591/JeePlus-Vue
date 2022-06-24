/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service.dto;


import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程抄送Entity
 * @author 刘高峰
 * @version 2021-10-10
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class FlowCopyDTO extends BaseDTO {

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

	public FlowCopyDTO() {
		super();
	}

	public FlowCopyDTO(String id){
		super(id);
	}

}
