/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.manytomany.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 学生课程记录Entity
 * @author lgf
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_student_course")
public class StudentCourse extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 学生
     */
	private String studentId;
	/**
     * 课程
     */
	private String courseId;
	/**
     * 分数
     */
	private Double score;
	/**
     * 备注信息
     */
	private String remarks;

}
