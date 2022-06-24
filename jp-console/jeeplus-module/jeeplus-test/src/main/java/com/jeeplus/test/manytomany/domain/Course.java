/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.manytomany.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 课程Entity
 * @author lgf
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_course")
public class Course extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 课程名
     */
	private String name;
	/**
     * 备注信息
     */
	private String remarks;

}
