/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.datav.service.dto;

import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 大屏DTO
 * @author 刘高峰
 * @version 2021-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataScreenDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	/**
     * 大屏分类
	 */
	private DataScreenCategoryDTO category;
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
