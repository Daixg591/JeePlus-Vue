/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.service.dto;

import javax.validation.constraints.NotNull;
import com.jeeplus.test.shop.service.dto.CategoryDTO;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 商品DTO
 * @author liugf
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 商品名称
     */
	@NotNull(message="商品名称不能为空")
    @Query(tableColumn = "a.name", javaField = "name", type = QueryType.LIKE)
	private String name;
	/**
     * 所属类型
     */
	@NotNull(message="所属类型不能为空")
    @Query(tableColumn = "a.category_id", javaField = "category.id", type = QueryType.EQ)
	private CategoryDTO category;
	/**
     * 价格
     */
	@NotNull(message="价格不能为空")
	private String price;
	/**
     * 备注信息
     */
	private String remarks;

}
