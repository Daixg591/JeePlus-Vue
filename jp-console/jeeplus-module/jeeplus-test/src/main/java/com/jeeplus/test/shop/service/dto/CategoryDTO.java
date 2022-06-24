/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.service.dto;

import javax.validation.constraints.NotNull;
import com.jeeplus.core.service.dto.TreeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品类型DTO
 * @author liugf
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CategoryDTO extends TreeDTO<CategoryDTO> {

	private static final long serialVersionUID = 1L;

	/**
     * 备注信息
     */
	private String remarks;


}
