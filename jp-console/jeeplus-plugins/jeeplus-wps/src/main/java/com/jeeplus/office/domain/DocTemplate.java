/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.office.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文书模板Entity
 * @author sunyinhui
 * @version 2021-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("plugin_doc_template")
public class DocTemplate extends BaseEntity {

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
	private String categoryId;
	/**
	 * 备注
	 */
	private String remarks;


}
