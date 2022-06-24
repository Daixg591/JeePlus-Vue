/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.form.service.dto;


import com.jeeplus.core.query.Query;
import com.jeeplus.core.service.dto.BaseDTO;
import com.jeeplus.database.datalink.domain.DataSource;
import lombok.Data;

/**
 * 数据表单Entity
 * @author 刘高峰
 * @version 2021-09-24
 */
@Data
public class FormDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
	 * 表单编码
	 */
	private String code;

	/**
	 * 是否自动建表
	 */
	private String autoCreate;

	/**
	 * 数据库连接
	 */
	private DataSource dataSource;

	/**
	 * 表单名称
	 */
	@Query(tableColumn = "a.name")
	private String name;

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 表单结构
	 */
	private String source;

	/**
	 * 版本号
	 */
	private String version;

	/**
	 * 备注
	 */
	private String remarks;

}
