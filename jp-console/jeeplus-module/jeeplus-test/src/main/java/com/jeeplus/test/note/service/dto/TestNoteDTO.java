/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.note.service.dto;

import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 富文本测试DTO
 * @author liugf
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestNoteDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 标题
     */
    @Query(type = QueryType.LIKE)
	private String title;
	/**
     * 富文本1
     */
	private String contents1;
	/**
     * 富文本2
     */
	private String contents2;
	/**
     * 备注信息
     */
	private String remarks;

}
