/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jeeplus.core.service.dto.TreeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

/**
 * 区域DTO
 * @author jeeplus
 * @version 2021-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AreaDTO extends TreeDTO <AreaDTO> {

	private static final long serialVersionUID = 1L;

	/**
	 * 区域编码
	 */
	@Size(min = 0, max = 64)
	private String code;

	/**
	 * 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）
	 */
	@Size(min = 1, max = 1)
	private String type;

	/**
	 * 备注
	 */
	private String remarks;

}
