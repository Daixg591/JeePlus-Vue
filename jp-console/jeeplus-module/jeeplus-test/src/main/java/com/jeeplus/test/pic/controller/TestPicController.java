/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.pic.controller;

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
import com.jeeplus.test.pic.domain.TestPic;
import com.jeeplus.test.pic.service.dto.TestPicDTO;
import com.jeeplus.test.pic.service.mapstruct.TestPicWrapper;
import com.jeeplus.test.pic.service.TestPicService;

/**
 * 图片管理Controller
 * @author lgf
 * @version 2021-10-17
 */

@Api(tags ="图片管理")
@RestController
@RequestMapping(value = "/test/pic/testPic")
public class TestPicController {

	@Autowired
	private TestPicService testPicService;

	@Autowired
	private TestPicWrapper testPicWrapper;

	/**
	 * 图片管理列表数据
	 */
	@ApiLog("查询图片管理列表数据")
	@ApiOperation(value = "查询图片管理列表数据")
	@PreAuthorize("hasAuthority('test:pic:testPic:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestPic>> list(TestPicDTO testPicDTO, Page<TestPic> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testPicDTO, TestPicDTO.class);
		IPage<TestPic> result = testPicService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取图片管理数据
	 */
	@ApiLog("根据Id获取图片管理数据")
	@ApiOperation(value = "根据Id获取图片管理数据")
	@PreAuthorize("hasAnyAuthority('test:pic:testPic:view','test:pic:testPic:add','test:pic:testPic:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestPicDTO> queryById(String id) {
		return ResponseEntity.ok ( testPicWrapper.toDTO ( testPicService.getById ( id ) ) );
	}

	/**
	 * 保存图片管理
	 */
	@ApiLog("保存图片管理")
	@ApiOperation(value = "保存图片管理")
	@PreAuthorize("hasAnyAuthority('test:pic:testPic:add','test:pic:testPic:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestPicDTO testPicDTO) {
		//新增或编辑表单保存
		testPicService.saveOrUpdate (testPicWrapper.toEntity (testPicDTO));
        return ResponseEntity.ok ( "保存图片管理成功" );
	}


	/**
	 * 删除图片管理
	 */
	@ApiLog("删除图片管理")
	@ApiOperation(value = "删除图片管理")
	@PreAuthorize("hasAuthority('test:pic:testPic:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testPicService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除图片管理成功" );
	}

}
