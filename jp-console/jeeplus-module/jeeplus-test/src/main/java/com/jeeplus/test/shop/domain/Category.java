/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品类型Entity
 * @author liugf
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_category")
public class Category extends TreeEntity<Category> {

	private static final long serialVersionUID = 1L;

	/**
     * 备注信息
     */
	private String remarks;

}
