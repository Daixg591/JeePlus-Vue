/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.tree.controller;

import java.util.List;
import javax.validation.Valid;
import com.google.common.collect.Lists;
import com.jeeplus.aop.logging.annotation.ApiLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jeeplus.test.tree.service.dto.TestTreeDTO;
import com.jeeplus.test.tree.service.mapstruct.TestTreeWrapper;
import com.jeeplus.test.tree.service.TestTreeService;

/**
 * 组织机构Controller
 * @author lgf
 * @version 2021-10-17
 */
@RestController
@RequestMapping(value = "/test/tree/testTree")
public class TestTreeController {

	@Autowired
	private TestTreeService testTreeService;

	@Autowired
	private TestTreeWrapper testTreeWrapper;

	/**
	 * 根据Id获取组织机构数据
	 */
	@ApiLog("根据Id获取组织机构数据")
	@ApiOperation(value = "根据Id获取组织机构数据")
	@PreAuthorize("hasAnyAuthority('test:tree:testTree:view','test:tree:testTree:add','test:tree:testTree:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestTreeDTO> queryById(String id) {
		return ResponseEntity.ok ( testTreeService.findById ( id ) );
	}

	/**
	 * 保存组织机构
	 */
	@ApiLog("保存组织机构")
	@ApiOperation(value = "保存组织机构")
	@PreAuthorize("hasAnyAuthority('test:tree:testTree:add','test:tree:testTree:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestTreeDTO testTreeDTO) {
		//新增或编辑表单保存
		testTreeService.saveOrUpdate (testTreeWrapper.toEntity (testTreeDTO));
        return ResponseEntity.ok ( "保存组织机构成功" );
	}

	/**
	 * 删除组织机构
	 */
	@ApiLog("删除组织机构")
	@ApiOperation(value = "删除组织机构")
	@PreAuthorize("hasAuthority('test:tree:testTree:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testTreeService.removeWithChildrenByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除组织机构成功" );
	}

	/**
     * 获取JSON树形数据。
     * @param extId 排除的ID
     * @return
	*/
	@ApiLog("查询组织机构树表数据")
	@ApiOperation(value = "查询组织机构树表数据")
	@GetMapping("treeData")
	public ResponseEntity <List <TestTreeDTO> > treeData(String extId) {
		List <TestTreeDTO> rootTree = testTreeService.treeDataDTO ( extId );
		return ResponseEntity.ok ( rootTree );
	}

}
