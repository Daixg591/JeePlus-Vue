/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.office.service.dto;

import com.google.common.collect.Lists;
import com.jeeplus.core.service.dto.TreeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 文档模板Entity
 * @version 2021-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DocCategoryDTO extends TreeDTO <DocCategoryDTO> {

	private static final long serialVersionUID = 1L;
	/**
	 * 备注
	 */
	private String remarks;

	/**
	 * 子表列表
	 */
	private List<DocTemplateDTO> docTemplateDTOList = Lists.newArrayList();
}
