/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 商品Entity
 * @author liugf
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_goods")
public class Goods extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 商品名称
     */
	private String name;
	/**
     * 所属类型
     */
	private String categoryId;
	/**
     * 价格
     */
	private String price;
	/**
     * 备注信息
     */
	private String remarks;

}
