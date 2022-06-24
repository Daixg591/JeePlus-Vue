/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.datav.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import com.jeeplus.core.query.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 地图Entity
 * @author 刘高峰
 * @version 2021-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("plugin_datascreen_map")
public class DataMap extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 地图名称
	 */
	@Query
	private String name;

	/**
	 * 地图数据
	 */
	private String data;

	/**
	 * 备注
	 */
	private String remarks;
}
