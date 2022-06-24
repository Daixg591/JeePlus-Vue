/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.treetable.service.dto;

import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 车辆DTO
 * @author lgf
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CarDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 品牌
     */
	private String name;
	/**
     * 车系
     */
	@Query(tableColumn = "a.kind_id", javaField = "kind.id", type = QueryType.EQ)
	private CarKindDTO kind;
	/**
     * 简介
     */
	private String remarks;

}
