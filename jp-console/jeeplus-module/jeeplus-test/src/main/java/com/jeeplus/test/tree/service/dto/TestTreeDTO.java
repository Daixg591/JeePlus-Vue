/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.tree.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.jeeplus.core.service.dto.TreeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 组织机构DTO
 * @author lgf
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestTreeDTO extends TreeDTO<TestTreeDTO> {

	private static final long serialVersionUID = 1L;

	/**
     * 备注信息
     */
	@NotNull(message="备注信息不能为空")
	@Size(min=3, max=6, message="备注信息长度必须介于 3 和 6 之间")
	private String remarks1;


}
