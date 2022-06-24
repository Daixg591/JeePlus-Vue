/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.office.service.dto;


import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文书模板Entity
 * @author sunyinhui
 * @version 2021-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DocTemplateDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	/**
	 * 文书名称
	 */
	private String name;
	/**
	 * 存储路径
	 */
	private String path;
	/**
	 * 版本
	 */
	private String version;
	/**
	 * 类型 父类
	 */
	@Query(javaField = "docCategoryDTO.id", type = QueryType.EQ, tableColumn = "a.category_id")
	private DocCategoryDTO docCategoryDTO;
	/**
	 * 备注
	 */
	private String remarks;


}
