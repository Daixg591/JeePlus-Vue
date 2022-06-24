/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.controller;

import javax.validation.Valid;
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
import com.jeeplus.test.onetomany.service.dto.TestDataMainFormDTO;
import com.jeeplus.test.onetomany.service.TestDataMainFormService;

/**
 * 票务代理Controller
 * @author liugf
 * @version 2021-10-17
 */
@Api(tags ="票务代理")
@RestController
@RequestMapping(value = "/test/onetomany/testDataMainForm")
public class TestDataMainFormController {

	@Autowired
	private TestDataMainFormService testDataMainFormService;

	/**
	 * 票务代理列表数据
	 */
	@ApiLog("查询票务代理列表数据")
	@ApiOperation(value = "查询票务代理列表数据")
	@PreAuthorize("hasAuthority('test:onetomany:testDataMainForm:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestDataMainFormDTO>> list(TestDataMainFormDTO testDataMainFormDTO, Page<TestDataMainFormDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testDataMainFormDTO, TestDataMainFormDTO.class);
		IPage<TestDataMainFormDTO> result = testDataMainFormService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取票务代理数据
	 */
	@ApiLog("根据Id获取票务代理数据")
	@ApiOperation(value = "根据Id获取票务代理数据")
	@PreAuthorize("hasAnyAuthority('test:onetomany:testDataMainForm:view','test:onetomany:testDataMainForm:add','test:onetomany:testDataMainForm:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestDataMainFormDTO> queryById(String id) {
		return ResponseEntity.ok ( testDataMainFormService.findById ( id ) );
	}

	/**
	 * 保存票务代理
	 */
	@ApiLog("保存票务代理")
	@ApiOperation(value = "保存票务代理")
	@PreAuthorize("hasAnyAuthority('test:onetomany:testDataMainForm:add','test:onetomany:testDataMainForm:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestDataMainFormDTO testDataMainFormDTO) {
		//新增或编辑表单保存
		testDataMainFormService.saveOrUpdate (testDataMainFormDTO);
        return ResponseEntity.ok ( "保存票务代理成功" );
	}


	/**
	 * 删除票务代理
	 */
	@ApiLog("删除票务代理")
	@ApiOperation(value = "删除票务代理")
	@PreAuthorize("hasAuthority('test:onetomany:testDataMainForm:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
		for(String id: idArray){
			testDataMainFormService.removeById ( id );
		}
		return ResponseEntity.ok( "删除票务代理成功" );
	}

}
