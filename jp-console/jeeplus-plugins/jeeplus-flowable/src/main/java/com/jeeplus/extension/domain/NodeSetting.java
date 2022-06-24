/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 节点设置Entity
 * @author 刘高峰
 * @version 2021-01-11
 */



@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_extension_node_setting")
public class NodeSetting extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 流程定义id
	 */
	private String processDefId;
	/**
	 * 节点id
	 */
	private String taskDefId;
	/**
	 * 变量名
	 */
	@TableField("`key`")
	private String key;
	/**
	 * 变量值
	 */
	@TableField("`value`")
	private String value;

	public NodeSetting() {
		super();
	}
}
