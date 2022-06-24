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
import com.jeeplus.test.manytomany.domain.Course;
import com.jeeplus.test.manytomany.service.dto.CourseDTO;
import com.jeeplus.test.manytomany.service.mapstruct.CourseWrapper;
import com.jeeplus.test.manytomany.service.CourseService;

/**
 * 课程Controller
 * @author lgf
 * @version 2021-10-17
 */

@Api(tags ="课程")
@RestController
@RequestMapping(value = "/test/manytomany/course")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseWrapper courseWrapper;

	/**
	 * 课程列表数据
	 */
	@ApiLog("查询课程列表数据")
	@ApiOperation(value = "查询课程列表数据")
	@PreAuthorize("hasAuthority('test:manytomany:course:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<Course>> list(CourseDTO courseDTO, Page<Course> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (courseDTO, CourseDTO.class);
		IPage<Course> result = courseService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取课程数据
	 */
	@ApiLog("根据Id获取课程数据")
	@ApiOperation(value = "根据Id获取课程数据")
	@PreAuthorize("hasAnyAuthority('test:manytomany:course:view','test:manytomany:course:add','test:manytomany:course:edit')")
	@GetMapping("queryById")
	public ResponseEntity<CourseDTO> queryById(String id) {
		return ResponseEntity.ok ( courseWrapper.toDTO ( courseService.getById ( id ) ) );
	}

	/**
	 * 保存课程
	 */
	@ApiLog("保存课程")
	@ApiOperation(value = "保存课程")
	@PreAuthorize("hasAnyAuthority('test:manytomany:course:add','test:manytomany:course:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody CourseDTO courseDTO) {
		//新增或编辑表单保存
		courseService.saveOrUpdate (courseWrapper.toEntity (courseDTO));
        return ResponseEntity.ok ( "保存课程成功" );
	}


	/**
	 * 删除课程
	 */
	@ApiLog("删除课程")
	@ApiOperation(value = "删除课程")
	@PreAuthorize("hasAuthority('test:manytomany:course:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        courseService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除课程成功" );
	}

}
