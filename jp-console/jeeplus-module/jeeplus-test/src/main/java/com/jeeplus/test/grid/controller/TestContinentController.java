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
import com.jeeplus.test.grid.domain.TestContinent;
import com.jeeplus.test.grid.service.dto.TestContinentDTO;
import com.jeeplus.test.grid.service.mapstruct.TestContinentWrapper;
import com.jeeplus.test.grid.service.TestContinentService;

/**
 * 洲Controller
 * @author lgf
 * @version 2021-10-17
 */

@Api(tags ="洲")
@RestController
@RequestMapping(value = "/test/grid/testContinent")
public class TestContinentController {

	@Autowired
	private TestContinentService testContinentService;

	@Autowired
	private TestContinentWrapper testContinentWrapper;

	/**
	 * 洲列表数据
	 */
	@ApiLog("查询洲列表数据")
	@ApiOperation(value = "查询洲列表数据")
	@PreAuthorize("hasAuthority('test:grid:testContinent:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestContinent>> list(TestContinentDTO testContinentDTO, Page<TestContinent> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testContinentDTO, TestContinentDTO.class);
		IPage<TestContinent> result = testContinentService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取洲数据
	 */
	@ApiLog("根据Id获取洲数据")
	@ApiOperation(value = "根据Id获取洲数据")
	@PreAuthorize("hasAnyAuthority('test:grid:testContinent:view','test:grid:testContinent:add','test:grid:testContinent:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestContinentDTO> queryById(String id) {
		return ResponseEntity.ok ( testContinentWrapper.toDTO ( testContinentService.getById ( id ) ) );
	}

	/**
	 * 保存洲
	 */
	@ApiLog("保存洲")
	@ApiOperation(value = "保存洲")
	@PreAuthorize("hasAnyAuthority('test:grid:testContinent:add','test:grid:testContinent:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestContinentDTO testContinentDTO) {
		//新增或编辑表单保存
		testContinentService.saveOrUpdate (testContinentWrapper.toEntity (testContinentDTO));
        return ResponseEntity.ok ( "保存洲成功" );
	}


	/**
	 * 删除洲
	 */
	@ApiLog("删除洲")
	@ApiOperation(value = "删除洲")
	@PreAuthorize("hasAuthority('test:grid:testContinent:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testContinentService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除洲成功" );
	}

}
