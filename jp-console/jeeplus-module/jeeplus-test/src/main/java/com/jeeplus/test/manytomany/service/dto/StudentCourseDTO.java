/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.manytomany.service.dto;

import com.jeeplus.test.manytomany.service.dto.StudentDTO;
import javax.validation.constraints.NotNull;
import com.jeeplus.test.manytomany.service.dto.CourseDTO;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 学生课程记录DTO
 * @author lgf
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StudentCourseDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 学生
     */
	@NotNull(message="学生不能为空")
    @Query(tableColumn = "a.student_id", javaField = "student.id", type = QueryType.LIKE)
	private StudentDTO student;
	/**
     * 课程
     */
	@NotNull(message="课程不能为空")
    @Query(tableColumn = "a.course_id", javaField = "course.id", type = QueryType.LIKE)
	private CourseDTO course;
	/**
     * 分数
     */
	@NotNull(message="分数不能为空")
	private Double score;
	/**
     * 备注信息
     */
	private String remarks;

}
