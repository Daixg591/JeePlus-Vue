/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service.dto;

import com.jeeplus.core.service.dto.TreeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程分类DTO
 * @author 刘高峰
 * @version 2021-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActCategoryDTO extends TreeDTO <ActCategoryDTO> {

	private static final long serialVersionUID = 1L;

	public String remarks;

}
