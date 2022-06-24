/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service.dto;


import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 常用按钮DTO
 * @author 刘高峰
 * @version 2021-10-07
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class ButtonDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	private String name;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 排序
	 */
	private String sort;


}
