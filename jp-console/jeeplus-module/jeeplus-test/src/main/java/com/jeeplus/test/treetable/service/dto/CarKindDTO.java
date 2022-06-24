/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.treetable.service.dto;

import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;
import com.jeeplus.core.service.dto.TreeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 车系DTO
 * @author lgf
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CarKindDTO extends TreeDTO<CarKindDTO> {

	private static final long serialVersionUID = 1L;

	/**
     * 备注信息
     */
	private String remarks;
    /**
     *子表列表
     */
	private List<CarDTO> carDTOList = Lists.newArrayList();


}
