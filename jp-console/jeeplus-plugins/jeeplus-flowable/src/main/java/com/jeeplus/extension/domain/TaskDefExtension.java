/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.collect.Lists;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 工作流扩展Entity
 * @author 刘高峰
 * @version 2021-09-23
 */


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_extension_taskdef")
public class TaskDefExtension extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 流程定义id
	 */
	private String processDefId;
	/**
	 * 任务定义id
	 */
	private String taskDefId;


	public TaskDefExtension() {
		super();
	}

}
