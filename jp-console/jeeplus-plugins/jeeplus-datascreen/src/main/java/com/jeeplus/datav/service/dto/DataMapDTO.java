/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.datav.service.dto;


import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 地图DTO
 * @author 刘高峰
 * @version 2021-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataMapDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	/**
     * 地图名称
	 */
	private String name;
	/**
     * 地图数据
	 */
	private String data;
	/**
	 * 备注
	 */
	private String remarks;
}
