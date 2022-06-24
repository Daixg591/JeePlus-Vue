/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import com.jeeplus.core.query.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 常用按钮Entity
 * @author 刘高峰
 * @version 2021-10-07
 */

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_extension_button")
public class Button extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@Query
	private String name;
	/**
	 * 编码
	 */
	@Query
	private String code;
	/**
	 * 排序
	 */
	private String sort;


}
