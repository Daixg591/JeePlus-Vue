/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.controller;

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
import com.jeeplus.test.shop.service.dto.GoodsDTO;
import com.jeeplus.test.shop.service.mapstruct.GoodsWrapper;
import com.jeeplus.test.shop.service.GoodsService;

/**
 * 商品Controller
 * @author liugf
 * @version 2021-10-17
 */

@Api(tags ="商品")
@RestController
@RequestMapping(value = "/test/shop/goods")
public class GoodsController {

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private GoodsWrapper goodsWrapper;

	/**
	 * 商品列表数据
	 */
	@ApiLog("查询商品列表数据")
	@ApiOperation(value = "查询商品列表数据")
	@PreAuthorize("hasAuthority('test:shop:goods:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<GoodsDTO>> list(GoodsDTO goodsDTO, Page<GoodsDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (goodsDTO, GoodsDTO.class);
		IPage<GoodsDTO> result = goodsService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取商品数据
	 */
	@ApiLog("根据Id获取商品数据")
	@ApiOperation(value = "根据Id获取商品数据")
	@PreAuthorize("hasAnyAuthority('test:shop:goods:view','test:shop:goods:add','test:shop:goods:edit')")
	@GetMapping("queryById")
	public ResponseEntity<GoodsDTO> queryById(String id) {
		return ResponseEntity.ok ( goodsService.findById ( id ) );
	}

	/**
	 * 保存商品
	 */
	@ApiLog("保存商品")
	@ApiOperation(value = "保存商品")
	@PreAuthorize("hasAnyAuthority('test:shop:goods:add','test:shop:goods:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody GoodsDTO goodsDTO) {
		//新增或编辑表单保存
		goodsService.saveOrUpdate (goodsWrapper.toEntity (goodsDTO));
        return ResponseEntity.ok ( "保存商品成功" );
	}


	/**
	 * 删除商品
	 */
	@ApiLog("删除商品")
	@ApiOperation(value = "删除商品")
	@PreAuthorize("hasAuthority('test:shop:goods:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        goodsService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除商品成功" );
	}

}
