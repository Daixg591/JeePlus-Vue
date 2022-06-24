/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.grid.controller;

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
import com.jeeplus.test.grid.service.dto.TestCountryDTO;
import com.jeeplus.test.grid.service.mapstruct.TestCountryWrapper;
import com.jeeplus.test.grid.service.TestCountryService;

/**
 * 国家Controller
 * @author 刘高峰
 * @version 2021-10-17
 */

@Api(tags ="国家")
@RestController
@RequestMapping(value = "/test/grid/testCountry")
public class TestCountryController {

	@Autowired
	private TestCountryService testCountryService;

	@Autowired
	private TestCountryWrapper testCountryWrapper;

	/**
	 * 国家列表数据
	 */
	@ApiLog("查询国家列表数据")
	@ApiOperation(value = "查询国家列表数据")
	@PreAuthorize("hasAuthority('test:grid:testCountry:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestCountryDTO>> list(TestCountryDTO testCountryDTO, Page<TestCountryDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testCountryDTO, TestCountryDTO.class);
		IPage<TestCountryDTO> result = testCountryService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取国家数据
	 */
	@ApiLog("根据Id获取国家数据")
	@ApiOperation(value = "根据Id获取国家数据")
	@PreAuthorize("hasAnyAuthority('test:grid:testCountry:view','test:grid:testCountry:add','test:grid:testCountry:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestCountryDTO> queryById(String id) {
		return ResponseEntity.ok ( testCountryService.findById ( id ) );
	}

	/**
	 * 保存国家
	 */
	@ApiLog("保存国家")
	@ApiOperation(value = "保存国家")
	@PreAuthorize("hasAnyAuthority('test:grid:testCountry:add','test:grid:testCountry:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestCountryDTO testCountryDTO) {
		//新增或编辑表单保存
		testCountryService.saveOrUpdate (testCountryWrapper.toEntity (testCountryDTO));
        return ResponseEntity.ok ( "保存国家成功" );
	}


	/**
	 * 删除国家
	 */
	@ApiLog("删除国家")
	@ApiOperation(value = "删除国家")
	@PreAuthorize("hasAuthority('test:grid:testCountry:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testCountryService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除国家成功" );
	}

}
