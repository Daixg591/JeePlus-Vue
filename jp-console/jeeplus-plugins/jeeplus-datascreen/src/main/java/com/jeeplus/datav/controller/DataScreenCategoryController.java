/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.datav.controller;

import com.google.common.collect.Lists;
import com.jeeplus.datav.domain.DataScreenCategory;
import com.jeeplus.datav.service.DataScreenCategoryService;
import com.jeeplus.datav.service.dto.DataScreenCategoryDTO;
import com.jeeplus.datav.service.mapstruct.DataScreenCategoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 大屏分类Controller
 * @author 刘高峰
 * @version 2021-03-29
 */
@RestController
@RequestMapping(value = "/datav/dataScreenCategory")
public class DataScreenCategoryController {

	@Autowired
	private DataScreenCategoryService dataScreenCategoryService;
	@Autowired
	private DataScreenCategoryWrapper dataScreenCategoryWrapper;

	/**
	 * 根据Id获取大屏分类数据
	 */
	@PreAuthorize("hasAnyAuthority('datav:dataScreenCategory:view','datav:dataScreenCategory:add','datav:dataScreenCategory:edit')")
	@GetMapping("queryById")
	public ResponseEntity<DataScreenCategoryDTO> queryById(String id) {
		DataScreenCategory dataScreenCategory = dataScreenCategoryService.getById ( id );
		return ResponseEntity.ok (dataScreenCategoryWrapper.toDTO (  dataScreenCategory ));
	}

	/**
	 * 保存大屏分类
	 */
	@PreAuthorize ("hasAnyAuthority('datav:dataScreenCategory:add','datav:dataScreenCategory:edit')")
	@PostMapping("save")
	public ResponseEntity<String> save(@RequestBody @Valid DataScreenCategoryDTO dataScreenCategoryDTO) {
		//新增或编辑表单保存
		DataScreenCategory dataScreenCategory = dataScreenCategoryWrapper.toEntity ( dataScreenCategoryDTO );
		dataScreenCategoryService.saveOrUpdate (dataScreenCategory);
		return ResponseEntity.ok ("保存大屏分类成功");
	}

	/**
	 * 删除大屏分类
	 */
	@PreAuthorize("hasAuthority('datav:dataScreenCategory:del')")
	@DeleteMapping("delete")
	public ResponseEntity<String> delete(String ids) {
		String idArray[] =ids.split(",");
		dataScreenCategoryService.removeWithChildrenByIds ( Lists.newArrayList ( idArray) );
		return ResponseEntity.ok ("删除大屏分类成功");
	}

	/**
     * 获取JSON树形数据。
     * @param extId 排除的ID
     * @return
	*/
	@GetMapping("treeData")
	public ResponseEntity<List<DataScreenCategory>> treeData(@RequestParam(required = false) String extId) {
	    List<DataScreenCategory> rootTrees =  dataScreenCategoryService.treeData ( extId );
		return ResponseEntity.ok ( rootTrees );
	}

}
