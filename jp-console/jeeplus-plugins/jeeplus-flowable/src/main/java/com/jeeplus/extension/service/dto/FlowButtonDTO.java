/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 按钮设置Entity
 * @author 刘高峰
 * @version 2021-09-23
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class FlowButtonDTO extends BaseDTO {

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
	private String next;
    /**
     * 排序
	 */
	private Integer sort;
    /**
     * 任务节点外键 父类
	 */
    @JsonIgnore
	private TaskDefExtensionDTO taskDef;

	public FlowButtonDTO() {
		super();
	}


}
