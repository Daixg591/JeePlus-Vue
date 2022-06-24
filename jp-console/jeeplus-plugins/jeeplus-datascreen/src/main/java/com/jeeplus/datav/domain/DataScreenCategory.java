/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.datav.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 大屏分类Entity
 * @author 刘高峰
 * @version 2021-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("plugin_datascreen_category")
public class DataScreenCategory extends TreeEntity<DataScreenCategory> {

	private static final long serialVersionUID = 1L;
	/**
	 * 备注
	 */
	private String remarks;

}
