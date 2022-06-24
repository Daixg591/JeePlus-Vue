/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 数据Entity类
 *
 * @author jeeplus
 * @version 2021-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class TreeEntity<T> extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 父级编号
	 */
	protected String parentId;

	/**
	 * 所有父级编号
	 */
	protected String parentIds;

	/**
	 * 名称
	 */
	protected String name;

	/**
	 * 排序
	 */
	protected Integer sort;

	/**
	 * 子元素集合
	 */
	@TableField(exist = false)
	protected List<T> children;

	/**
	 * 构造函数
	 */
	public TreeEntity () {
		super();
	}

	/**
	 * 构造函数
	 * @param id
	 */
	public TreeEntity (String id) {
		super(id);
	}




}
