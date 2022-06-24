/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 区域Entity
 * @author jeeplus
 * @version 2021-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_area")
public class Area extends TreeEntity<Area> {

	private static final long serialVersionUID = 1L;

	/**
	 * 区域编码
	 */
	private String code;

	/**
	 * 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）
	 */
	private String type;

	/**
	 * 备注
	 */
	private String remarks;

}
