/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.datav.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 大屏Entity
 * @author 刘高峰
 * @version 2021-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("plugin_datascreen")
public class DataScreen extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 大屏分类
	 */
	@Query(type = QueryType.EQ)
	private String categoryId;

	/**
	 * 组件
	 */
	private String component;

	/**
	 * 配置详情
	 */
	private String detail;

	/**
	 * 是否启用
	 */
	private String status;

	/**
	 * 大屏名称
	 */
	@Query
	private String name;

	/**
	 * 缩略图
	 */
	private String screenShot;

	/**
	 * 背景图片
	 */
	private String backgroundUrl;

	/**
	 * 备注
	 */
	private String remarks;

}
