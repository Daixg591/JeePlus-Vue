/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.one.controller;

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
import com.jeeplus.test.one.service.dto.TestFormLeaveDTO;
import com.jeeplus.test.one.service.mapstruct.TestFormLeaveWrapper;
import com.jeeplus.test.one.service.TestFormLeaveService;

/**
 * 请假表单Controller
 * @author 刘高峰
 * @version 2021-10-17
 */

@Api(tags ="请假表单")
@RestController
@RequestMapping(value = "/test/one/testFormLeave")
public class TestFormLeaveController {

	@Autowired
	private TestFormLeaveService testFormLeaveService;

	@Autowired
	private TestFormLeaveWrapper testFormLeaveWrapper;

	/**
	 * 请假表单列表数据
	 */
	@ApiLog("查询请假表单列表数据")
	@ApiOperation(value = "查询请假表单列表数据")
	@PreAuthorize("hasAuthority('test:one:testFormLeave:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestFormLeaveDTO>> list(TestFormLeaveDTO testFormLeaveDTO, Page<TestFormLeaveDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testFormLeaveDTO, TestFormLeaveDTO.class);
		IPage<TestFormLeaveDTO> result = testFormLeaveService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取请假表单数据
	 */
	@ApiLog("根据Id获取请假表单数据")
	@ApiOperation(value = "根据Id获取请假表单数据")
	@PreAuthorize("hasAnyAuthority('test:one:testFormLeave:view','test:one:testFormLeave:add','test:one:testFormLeave:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestFormLeaveDTO> queryById(String id) {
		return ResponseEntity.ok ( testFormLeaveService.findById ( id ) );
	}

	/**
	 * 保存请假表单
	 */
	@ApiLog("保存请假表单")
	@ApiOperation(value = "保存请假表单")
	@PreAuthorize("hasAnyAuthority('test:one:testFormLeave:add','test:one:testFormLeave:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestFormLeaveDTO testFormLeaveDTO) {
		//新增或编辑表单保存
		testFormLeaveService.saveOrUpdate (testFormLeaveWrapper.toEntity (testFormLeaveDTO));
        return ResponseEntity.ok ( "保存请假表单成功" );
	}


	/**
	 * 删除请假表单
	 */
	@ApiLog("删除请假表单")
	@ApiOperation(value = "删除请假表单")
	@PreAuthorize("hasAuthority('test:one:testFormLeave:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testFormLeaveService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除请假表单成功" );
	}

}
