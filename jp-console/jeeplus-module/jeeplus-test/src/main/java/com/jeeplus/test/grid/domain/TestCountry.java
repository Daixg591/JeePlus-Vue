/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.grid.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 国家Entity
 * @author 刘高峰
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_country")
public class TestCountry extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 国名
     */
	private String name;
	/**
     * 人口
     */
	private String sum;
	/**
     * 所属洲
     */
	private String continentId;
	/**
     * 备注信息
     */
	private String remarks;

}
