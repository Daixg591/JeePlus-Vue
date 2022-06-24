/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程分类Entity
 * @author 刘高峰
 * @version 2021-02-02
 */


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_extension_form_category")
public class FormCategory extends TreeEntity<FormCategory> {

	private static final long serialVersionUID = 1L;

	public FormCategory() {
		super();
	}

}
