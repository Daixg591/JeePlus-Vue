/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.office.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文档模板Entity
 * @version 2021-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("plugin_doc_category")
public class DocCategory extends TreeEntity<DocCategory> {

	private static final long serialVersionUID = 1L;
	/**
	 * 备注
	 */
	private String remarks;
}
