/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.manytomany.controller;

import javax.validation.Valid;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.aop.logging.annotation.ApiLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.manytomany.service.dto.StudentCourseDTO;
import com.jeeplus.test.manytomany.service.mapstruct.StudentCourseWrapper;
import com.jeeplus.test.manytomany.service.StudentCourseService;

/**
 * 学生课程记录Controller
 * @author lgf
 * @version 2021-10-17
 */

@Api(tags ="学生课程记录")
@RestController
@RequestMapping(value = "/test/manytomany/studentCourse")
public class StudentCourseController {

	@Autowired
	private StudentCourseService studentCourseService;

	@Autowired
	private StudentCourseWrapper studentCourseWrapper;

	/**
	 * 学生课程记录列表数据
	 */
	@ApiLog("查询学生课程记录列表数据")
	@ApiOperation(value = "查询学生课程记录列表数据")
	@PreAuthorize("hasAuthority('test:manytomany:studentCourse:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<StudentCourseDTO>> list(StudentCourseDTO studentCourseDTO, Page<StudentCourseDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (studentCourseDTO, StudentCourseDTO.class);
		IPage<StudentCourseDTO> result = studentCourseService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取学生课程记录数据
	 */
	@ApiLog("根据Id获取学生课程记录数据")
	@ApiOperation(value = "根据Id获取学生课程记录数据")
	@PreAuthorize("hasAnyAuthority('test:manytomany:studentCourse:view','test:manytomany:studentCourse:add','test:manytomany:studentCourse:edit')")
	@GetMapping("queryById")
	public ResponseEntity<StudentCourseDTO> queryById(String id) {
		return ResponseEntity.ok ( studentCourseService.findById ( id ) );
	}

	/**
	 * 保存学生课程记录
	 */
	@ApiLog("保存学生课程记录")
	@ApiOperation(value = "保存学生课程记录")
	@PreAuthorize("hasAnyAuthority('test:manytomany:studentCourse:add','test:manytomany:studentCourse:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody StudentCourseDTO studentCourseDTO) {
		//新增或编辑表单保存
		studentCourseService.saveOrUpdate (studentCourseWrapper.toEntity (studentCourseDTO));
        return ResponseEntity.ok ( "保存学生课程记录成功" );
	}


	/**
	 * 删除学生课程记录
	 */
	@ApiLog("删除学生课程记录")
	@ApiOperation(value = "删除学生课程记录")
	@PreAuthorize("hasAuthority('test:manytomany:studentCourse:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        studentCourseService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除学生课程记录成功" );
	}

}
