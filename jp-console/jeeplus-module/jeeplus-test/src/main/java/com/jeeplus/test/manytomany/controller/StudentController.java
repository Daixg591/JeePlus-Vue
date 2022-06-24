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
import com.jeeplus.test.manytomany.domain.Student;
import com.jeeplus.test.manytomany.service.dto.StudentDTO;
import com.jeeplus.test.manytomany.service.mapstruct.StudentWrapper;
import com.jeeplus.test.manytomany.service.StudentService;

/**
 * 学生Controller
 * @author 刘高峰
 * @version 2021-10-17
 */

@Api(tags ="学生")
@RestController
@RequestMapping(value = "/test/manytomany/student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private StudentWrapper studentWrapper;

	/**
	 * 学生列表数据
	 */
	@ApiLog("查询学生列表数据")
	@ApiOperation(value = "查询学生列表数据")
	@PreAuthorize("hasAuthority('test:manytomany:student:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<Student>> list(StudentDTO studentDTO, Page<Student> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (studentDTO, StudentDTO.class);
		IPage<Student> result = studentService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取学生数据
	 */
	@ApiLog("根据Id获取学生数据")
	@ApiOperation(value = "根据Id获取学生数据")
	@PreAuthorize("hasAnyAuthority('test:manytomany:student:view','test:manytomany:student:add','test:manytomany:student:edit')")
	@GetMapping("queryById")
	public ResponseEntity<StudentDTO> queryById(String id) {
		return ResponseEntity.ok ( studentWrapper.toDTO ( studentService.getById ( id ) ) );
	}

	/**
	 * 保存学生
	 */
	@ApiLog("保存学生")
	@ApiOperation(value = "保存学生")
	@PreAuthorize("hasAnyAuthority('test:manytomany:student:add','test:manytomany:student:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody StudentDTO studentDTO) {
		//新增或编辑表单保存
		studentService.saveOrUpdate (studentWrapper.toEntity (studentDTO));
        return ResponseEntity.ok ( "保存学生成功" );
	}


	/**
	 * 删除学生
	 */
	@ApiLog("删除学生")
	@ApiOperation(value = "删除学生")
	@PreAuthorize("hasAuthority('test:manytomany:student:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        studentService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除学生成功" );
	}

}
