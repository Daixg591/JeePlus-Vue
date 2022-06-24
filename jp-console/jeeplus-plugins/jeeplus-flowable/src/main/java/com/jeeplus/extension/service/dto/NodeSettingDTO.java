/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service.dto;


import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 节点设置Entity
 * @author 刘高峰
 * @version 2021-01-11
 */



@Data
@EqualsAndHashCode(callSuper = false)
public class NodeSettingDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	/**
	 * 流程定义id
	 */
	private String processDefId;
	/**
	 * 节点id
	 */
	private String taskDefId;
	/**
	 * 变量名
	 */
	private String key;
	/**
	 * 变量值
	 */
	private String value;

	public NodeSettingDTO() {
		super();
	}

	public NodeSettingDTO(String id){
		super(id);
	}
}
