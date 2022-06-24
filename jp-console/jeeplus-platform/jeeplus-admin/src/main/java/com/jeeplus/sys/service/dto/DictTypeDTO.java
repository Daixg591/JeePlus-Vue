/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.google.common.collect.Lists;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 数据字典Entity
 * @author lgf
 * @version 2021-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictTypeDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
	 * 类型
	 */
	@Excel (name="类型")
	private String type;

	/**
	 * 备注
	 */
	private String remarks;

	/**
	 * 子表列表
	 */
	private List<DictValueDTO> dictValueDTOList = Lists.newArrayList();
}
