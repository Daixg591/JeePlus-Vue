/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service.dto;


import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程表单Entity
 * @author 刘高峰
 * @version 2021-02-02
 */


@Data
@EqualsAndHashCode(callSuper = false)
public class FormDefinitionDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	/**
	 * 父类
	 */
	@Query(tableColumn = "a.category_id", javaField = "category.id", type= QueryType.EQ)
	private FormCategoryDTO category;
	/**
	 * 表单名称
	 */
	@Query(tableColumn = "a.name")
	private String name;

	private FormDefinitionJsonDTO formDefinitionJson = new FormDefinitionJsonDTO ();

}
