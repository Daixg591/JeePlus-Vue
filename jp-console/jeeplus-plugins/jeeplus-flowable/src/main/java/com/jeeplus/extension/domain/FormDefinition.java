/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程表单Entity
 * @author 刘高峰
 * @version 2021-02-02
 */


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_extension_form_def")
public class FormDefinition extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 父类
	 */
	private String categoryId;
	/**
	 * 表单名称
	 */
	private String name;

}
