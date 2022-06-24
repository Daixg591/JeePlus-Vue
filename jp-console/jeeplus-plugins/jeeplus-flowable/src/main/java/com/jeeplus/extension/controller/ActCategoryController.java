/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.controller;

import com.google.common.collect.Lists;
import com.jeeplus.extension.domain.ActCategory;
import com.jeeplus.extension.service.ActCategoryService;
import com.jeeplus.extension.service.dto.ActCategoryDTO;
import com.jeeplus.extension.service.mapstruct.ActCategoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 流程分类Controller
 * @author 刘高峰
 * @version 2021-10-09
 */
@RestController
@RequestMapping("/extension/actCategory")
public class ActCategoryController {

	@Autowired
	private ActCategoryService actCategoryService;
	@Autowired
	private ActCategoryWrapper actCategoryWrapper;

	/**
	 * 流程分类树表数据
	 */
	@PreAuthorize("hasAuthority('extension:actCategory:list')")
	@GetMapping("list")
	public ResponseEntity list() {
		return ResponseEntity.ok ( actCategoryService.list () );
	}

	/**
	 * 根据id查询实例
	 * @param id
	 * @return
	 */
	@PreAuthorize ("hasAnyAuthority('extension:actCategory:view','extension:actCategory:add','extension:actCategory:edit')")
	@GetMapping("queryById")
	public ResponseEntity queryById(String id) {
		return ResponseEntity.ok ( actCategoryWrapper.toDTO ( actCategoryService.getById ( id ) ));
	}

	/**
	 * 保存流程分类
	 */
	@PreAuthorize ("hasAnyAuthority('extension:actCategory:add','extension:actCategory:edit')")
	@PostMapping("save")
	public ResponseEntity save(@RequestBody @Valid ActCategoryDTO actCategoryDTO) {
		//新增或编辑表单保存
		actCategoryService.saveOrUpdate (actCategoryWrapper.toEntity ( actCategoryDTO ));//保存
		return ResponseEntity.ok ("保存流程分类成功");
	}

	/**
	 * 删除流程分类
	 */
	@PreAuthorize ("hasAuthority('extension:actCategory:del')")
	@DeleteMapping("delete")
	public ResponseEntity delete(String ids) {
		String idArray[] =ids.split(",");
		actCategoryService.removeByIds ( Lists.newArrayList (idArray) );
		return ResponseEntity.ok ("删除流程分类成功");
	}

	/**
	     * 获取JSON树形数据。
	     * @param extId 排除的ID
	     * @return
	*/
	@GetMapping("treeData")
	public ResponseEntity treeData(@RequestParam(required = false) String extId) {
		List<ActCategory> list = actCategoryService.treeData ( extId );
		return ResponseEntity.ok ( list );
	}



}
