/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.office.conroller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.office.domain.DocCategory;
import com.jeeplus.office.service.DocCategoryService;
import com.jeeplus.office.service.dto.DocCategoryDTO;
import com.jeeplus.office.service.mapstruct.DocCategoryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 文档类型Controller
 * @version 2021-06-23
 */
@Api(tags ="文档类型")
@RestController
@RequestMapping(value = "/wps/docCategory")
public class DocCategoryController {

	@Autowired
	private DocCategoryService docCategoryService;
	@Autowired
	private DocCategoryWrapper docCategoryWrapper;

	/**
	 * 文档类型树表数据
	 */
	@ApiOperation("获取文档类型树表数据")
	@GetMapping("list")
	public ResponseEntity <IPage <DocCategory>> list(DocCategory docCategory, Page <DocCategory> page) throws Exception {
		QueryWrapper <DocCategory> queryWrapper = QueryWrapperGenerator.buildQueryCondition (docCategory, DocCategory.class);
		IPage <DocCategory> result = docCategoryService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}

	/**
	 * 根据Id获取文档类型数据
	 */
	@ApiOperation("根据Id获取文档类型数据")
	@GetMapping("queryById")
	public ResponseEntity<DocCategoryDTO> queryById(String id) {
		return ResponseEntity.ok ( docCategoryWrapper.toDTO ( docCategoryService.getById ( id ) ));
	}

	/**
	 * 保存文档类型
	 */
	@ApiOperation("保存文档类型")
	@PostMapping("save")
	public ResponseEntity<String> save(@Valid @RequestBody DocCategoryDTO docCategoryDTO) {
		//新增或编辑表单保存
		DocCategory docCategory = docCategoryWrapper.toEntity ( docCategoryDTO );
		docCategoryService.saveOrUpdate (docCategory);//保存
		return ResponseEntity.ok ("保存文档类型成功");
	}

	/**
	 * 删除文档类型
	 */
	@ApiOperation("删除文档类型")
	@DeleteMapping("delete")
	public ResponseEntity delete(String ids) {
		String idArray[] =ids.split(",");
		docCategoryService.removeWithChildrenByIds (Lists.newArrayList (idArray));
		return ResponseEntity.ok ("删除成功");
	}

	/**
	 * 获取JSON树形数据。
	 * @param extId 排除的ID
	 * @return
	*/
	@ApiOperation("获取JSON树形数据")
	@GetMapping("treeData")
	public ResponseEntity<List<DocCategoryDTO>> treeData(@RequestParam(required = false) String extId) {
		List<DocCategory> rootTree = docCategoryService.treeData ( extId );
		return ResponseEntity.ok ( docCategoryWrapper.toDTO (  rootTree ));
	}



}
