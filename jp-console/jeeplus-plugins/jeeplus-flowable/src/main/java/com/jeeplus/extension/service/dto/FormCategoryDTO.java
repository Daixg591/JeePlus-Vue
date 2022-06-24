/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service.dto;

import com.google.common.collect.Lists;
import com.jeeplus.core.service.dto.TreeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 流程分类Entity
 * @author 刘高峰
 * @version 2021-02-02
 */


@Data
@EqualsAndHashCode(callSuper = false)
public class FormCategoryDTO extends TreeDTO <FormCategoryDTO> {

	private static final long serialVersionUID = 1L;

	private List<FormDefinitionDTO> formDefinitionList = Lists.newArrayList();		// 子表列表

	public FormCategoryDTO() {
		super();
	}

	public FormCategoryDTO(String id){
		super(id);
	}

}
