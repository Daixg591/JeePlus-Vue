/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.datav.service.dto;

import com.jeeplus.core.service.dto.TreeDTO;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * 大屏分类DTO
 * @author 刘高峰
 * @version 2021-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataScreenCategoryDTO extends TreeDTO <DataScreenCategoryDTO> {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String name;
	/**
	 * 备注
	 */
	private String remarks;

}
