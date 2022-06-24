/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.validation.controller;

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
import com.jeeplus.test.validation.domain.TestValidation;
import com.jeeplus.test.validation.service.dto.TestValidationDTO;
import com.jeeplus.test.validation.service.mapstruct.TestValidationWrapper;
import com.jeeplus.test.validation.service.TestValidationService;

/**
 * 测试校验功能Controller
 * @author lgf
 * @version 2021-10-17
 */

@Api(tags ="测试校验功能")
@RestController
@RequestMapping(value = "/test/validation/testValidation")
public class TestValidationController {

	@Autowired
	private TestValidationService testValidationService;

	@Autowired
	private TestValidationWrapper testValidationWrapper;

	/**
	 * 测试校验列表数据
	 */
	@ApiLog("查询测试校验列表数据")
	@ApiOperation(value = "查询测试校验列表数据")
	@PreAuthorize("hasAuthority('test:validation:testValidation:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestValidation>> list(TestValidationDTO testValidationDTO, Page<TestValidation> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testValidationDTO, TestValidationDTO.class);
		IPage<TestValidation> result = testValidationService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取测试校验数据
	 */
	@ApiLog("根据Id获取测试校验数据")
	@ApiOperation(value = "根据Id获取测试校验数据")
	@PreAuthorize("hasAnyAuthority('test:validation:testValidation:view','test:validation:testValidation:add','test:validation:testValidation:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestValidationDTO> queryById(String id) {
		return ResponseEntity.ok ( testValidationWrapper.toDTO ( testValidationService.getById ( id ) ) );
	}

	/**
	 * 保存测试校验
	 */
	@ApiLog("保存测试校验")
	@ApiOperation(value = "保存测试校验")
	@PreAuthorize("hasAnyAuthority('test:validation:testValidation:add','test:validation:testValidation:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestValidationDTO testValidationDTO) {
		//新增或编辑表单保存
		testValidationService.saveOrUpdate (testValidationWrapper.toEntity (testValidationDTO));
        return ResponseEntity.ok ( "保存测试校验成功" );
	}


	/**
	 * 删除测试校验
	 */
	@ApiLog("删除测试校验")
	@ApiOperation(value = "删除测试校验")
	@PreAuthorize("hasAuthority('test:validation:testValidation:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testValidationService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除测试校验成功" );
	}

}
