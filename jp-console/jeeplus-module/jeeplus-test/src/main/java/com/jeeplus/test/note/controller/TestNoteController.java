/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.note.controller;

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
import com.jeeplus.test.note.domain.TestNote;
import com.jeeplus.test.note.service.dto.TestNoteDTO;
import com.jeeplus.test.note.service.mapstruct.TestNoteWrapper;
import com.jeeplus.test.note.service.TestNoteService;

/**
 * 富文本测试Controller
 * @author liugf
 * @version 2021-10-17
 */

@Api(tags ="富文本测试")
@RestController
@RequestMapping(value = "/test/note/testNote")
public class TestNoteController {

	@Autowired
	private TestNoteService testNoteService;

	@Autowired
	private TestNoteWrapper testNoteWrapper;

	/**
	 * 富文本测试列表数据
	 */
	@ApiLog("查询富文本测试列表数据")
	@ApiOperation(value = "查询富文本测试列表数据")
	@PreAuthorize("hasAuthority('test:note:testNote:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestNote>> list(TestNoteDTO testNoteDTO, Page<TestNote> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testNoteDTO, TestNoteDTO.class);
		IPage<TestNote> result = testNoteService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取富文本测试数据
	 */
	@ApiLog("根据Id获取富文本测试数据")
	@ApiOperation(value = "根据Id获取富文本测试数据")
	@PreAuthorize("hasAnyAuthority('test:note:testNote:view','test:note:testNote:add','test:note:testNote:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestNoteDTO> queryById(String id) {
		return ResponseEntity.ok ( testNoteWrapper.toDTO ( testNoteService.getById ( id ) ) );
	}

	/**
	 * 保存富文本测试
	 */
	@ApiLog("保存富文本测试")
	@ApiOperation(value = "保存富文本测试")
	@PreAuthorize("hasAnyAuthority('test:note:testNote:add','test:note:testNote:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestNoteDTO testNoteDTO) {
		//新增或编辑表单保存
		testNoteService.saveOrUpdate (testNoteWrapper.toEntity (testNoteDTO));
        return ResponseEntity.ok ( "保存富文本测试成功" );
	}


	/**
	 * 删除富文本测试
	 */
	@ApiLog("删除富文本测试")
	@ApiOperation(value = "删除富文本测试")
	@PreAuthorize("hasAuthority('test:note:testNote:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testNoteService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除富文本测试成功" );
	}

}
