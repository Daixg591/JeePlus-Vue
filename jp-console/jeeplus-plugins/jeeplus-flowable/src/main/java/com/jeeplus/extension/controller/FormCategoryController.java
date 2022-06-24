/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.extension.domain.FormCategory;
import com.jeeplus.extension.service.FormCategoryService;
import com.jeeplus.extension.service.dto.FormCategoryDTO;
import com.jeeplus.extension.service.mapstruct.FormCategoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程分类Controller
 * @author 刘高峰
 * @version 2021-02-02
 */
@RestController
@RequestMapping(value = "/extension/formCategory")
public class FormCategoryController {

	@Autowired
	private FormCategoryService formCategoryService;
	@Autowired
	private FormCategoryWrapper formCategoryWrapper;

	/**
	 * 流程分类树表数据
	 */
	@GetMapping("list")
	public ResponseEntity list(FormCategory formCategory, Page <FormCategory> page) throws Exception {
		QueryWrapper <FormCategory> queryWrapper = QueryWrapperGenerator.buildQueryCondition (formCategory, FormCategory.class);
		IPage <FormCategory> result = formCategoryService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取流程分类数据
	 */
	@GetMapping("queryById")
	public ResponseEntity<FormCategoryDTO> queryById(String id) {
		return ResponseEntity.ok ( formCategoryWrapper.toDTO ( formCategoryService.getById ( id ) ));
	}

	/**
	 * 保存流程分类
	 */
	@PostMapping("save")
	public ResponseEntity save(@RequestBody FormCategoryDTO formCategoryDTO) {
		//新增或编辑表单保存
		formCategoryService.saveOrUpdate (formCategoryWrapper.toEntity ( formCategoryDTO ));//保存
		return ResponseEntity.ok ("保存流程分类成功");
	}

	/**
	 * 删除流程分类
	 */
	@DeleteMapping("delete")
	public ResponseEntity delete(String id) {
		formCategoryService.removeById ( id );
		return ResponseEntity.ok ("删除流程分类成功");
	}

	/**
	     * 获取JSON树形数据。
	     * @param extId 排除的ID
	     * @return
	*/
	@GetMapping("treeData")
	public ResponseEntity treeData(@RequestParam(required = false) String extId) {
		List rootTree = formCategoryService.treeData (extId);
		return ResponseEntity.ok ( rootTree );
	}

}
