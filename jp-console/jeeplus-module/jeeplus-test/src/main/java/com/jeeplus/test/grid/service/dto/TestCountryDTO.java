/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.grid.service.dto;

import com.jeeplus.test.grid.service.dto.TestContinentDTO;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 国家DTO
 * @author 刘高峰
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestCountryDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 国名
     */
    @Query(tableColumn = "a.name", javaField = "name", type = QueryType.LIKE)
	private String name;
	/**
     * 人口
     */
	private String sum;
	/**
     * 所属洲
     */
	private TestContinentDTO continent;
	/**
     * 备注信息
     */
	private String remarks;

}
