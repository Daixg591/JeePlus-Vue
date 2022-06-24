/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.TreeEntity;
import lombok.Data;

/**
 * 流程分类Entity
 * @author 刘高峰
 * @version 2021-10-09
 */
@Data
@TableName("act_extension_category")
public class ActCategory extends TreeEntity<ActCategory> {

	private static final long serialVersionUID = 1L;

	private String remarks;

}
