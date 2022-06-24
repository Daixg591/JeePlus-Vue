/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service.dto;


import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程表达式DTO
 * @author liugf
 * @version 2021-09-29
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class ConditionDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	/**
     * 名称
	 */
	private String name;
	/**
     * 表达式
	 */
	private String expression;
	/**
	 * 备注
	 */
	private String remarks;

}
