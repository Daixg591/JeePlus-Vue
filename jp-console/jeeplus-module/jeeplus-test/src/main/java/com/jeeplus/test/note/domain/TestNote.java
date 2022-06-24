/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.note.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 富文本测试Entity
 * @author liugf
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_note")
public class TestNote extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 标题
     */
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
