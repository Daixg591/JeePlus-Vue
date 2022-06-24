/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.manytomany.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 学生Entity
 * @author 刘高峰
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_student")
public class Student extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 姓名
     */
	private String name;
	/**
     * 备注信息
     */
	private String remarks;

}
