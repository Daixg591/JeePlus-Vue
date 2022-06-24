/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import com.jeeplus.core.query.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.Max;

/**
 * 流程表达式Entity
 * @author liugf
 * @version 2021-09-29
 */

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_extension_condition")
public class Condition extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 名称
	 */
	@Query
	private String name;
	/**
	 * 表达式
	 */
	private String expression;
	/**
	 * 备注
	 */
	private String remarks;  //备注

}
