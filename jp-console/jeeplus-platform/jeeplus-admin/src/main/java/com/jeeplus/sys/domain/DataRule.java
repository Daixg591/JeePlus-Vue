/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据权限Entity
 * @author lgf
 * @version 2021-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_datarule")
public class DataRule extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 所属菜单
	 */
	private String menuId;

	/**
	 * 数据规则名称
	 */
	private String name;

	/**
	 * 实体类名
	 */
	private String className;

	/**
	 * 规则字段
	 */
	@TableField("t_field")
	private String field;

	/**
	 * 规则条件
	 */
	@TableField("t_express")
	private String express;

	/**
	 * 规则值
	 */
	@TableField("t_value")
	private String value;

	/**
	 * 自定义sql
	 */
	private String sqlSegment;

	/**
	 * 备注
	 */
	private String remarks;
}
