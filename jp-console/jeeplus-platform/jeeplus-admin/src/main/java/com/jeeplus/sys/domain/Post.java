/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import com.jeeplus.core.query.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位Entity
 *
 * @author 刘高峰
 * @version 2021-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_post")
public class Post extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 岗位名称
	 */
	@Query
	private String name;

	/**
	 * 岗位编码
	 */
	@Query
	private String code;

	/**
	 * 岗位类型
	 */
	private String type;

	/**
	 * 岗位状态
	 */
	private String status;

	/**
	 * 岗位排序
	 */
	private Integer sort;

	/**
	 * 备注
	 */
	private String remarks;

}
