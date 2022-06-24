/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.controller;

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

import com.jeeplus.test.shop.service.dto.CategoryDTO;
import com.jeeplus.test.shop.service.mapstruct.CategoryWrapper;
import com.jeeplus.test.shop.service.CategoryService;

/**
 * 商品类型Controller
 * @author liugf
 * @version 2021-10-17
 */
@Api(tags ="商品类型") 
@RestController
@RequestMapping(value = "/test/shop/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategoryWrapper categoryWrapper;

	/**
	 * 根据Id获取商品类型数据
	 */
	@ApiLog("根据Id获取商品类型数据")
	@ApiOperation(value = "根据Id获取商品类型数据")
	@PreAuthorize("hasAnyAuthority('test:shop:category:view','test:shop:category:add','test:shop:category:edit')")
	@GetMapping("queryById")
	public ResponseEntity<CategoryDTO> queryById(String id) {
		return ResponseEntity.ok ( categoryService.findById ( id ) );
	}

	/**
	 * 保存商品类型
	 */
	@ApiLog("保存商品类型")
	@ApiOperation(value = "保存商品类型")
	@PreAuthorize("hasAnyAuthority('test:shop:category:add','test:shop:category:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody CategoryDTO categoryDTO) {
		//新增或编辑表单保存
		categoryService.saveOrUpdate (categoryWrapper.toEntity (categoryDTO));
        return ResponseEntity.ok ( "保存商品类型成功" );
	}

	/**
	 * 删除商品类型
	 */
	@ApiLog("删除商品类型")
	@ApiOperation(value = "删除商品类型")
	@PreAuthorize("hasAuthority('test:shop:category:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        categoryService.removeWithChildrenByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除商品类型成功" );
	}

	/**
     * 获取JSON树形数据。
     * @param extId 排除的ID
     * @return
	*/
	@ApiLog("查询商品类型树表数据")
	@ApiOperation(value = "查询商品类型树表数据")
	@GetMapping("treeData")
	public ResponseEntity <List <CategoryDTO> > treeData(String extId) {
		List <CategoryDTO> rootTree = categoryService.treeDataDTO ( extId );
		return ResponseEntity.ok ( rootTree );
	}

}
