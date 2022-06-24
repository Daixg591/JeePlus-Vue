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
 * 按钮设置Entity
 * @author 刘高峰
 * @version 2021-09-23
 */


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_extension_buttons")
public class FlowButton extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 按钮名称
	 */
	private String name;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 是否隐藏
	 */
	private String isHide;
	/**
	 * 下一节点审核人
	 */
	@TableField("`next`")
	private String next;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 任务节点外键 父类
	 */
	private String  taskDefId;

	public FlowButton() {
		super();
	}

}
