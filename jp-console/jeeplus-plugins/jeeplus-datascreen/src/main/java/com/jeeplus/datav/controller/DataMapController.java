/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.datav.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.datav.domain.DataMap;
import com.jeeplus.datav.service.DataMapService;
import com.jeeplus.datav.service.dto.DataMapDTO;
import com.jeeplus.datav.service.mapstruct.DataMapWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 地图Controller
 * @author 刘高峰
 * @version 2021-04-11
 */
@RestController
@RequestMapping(value = "/datav/dataMap")
public class DataMapController {

	@Autowired
	private DataMapService dataMapService;
	@Autowired
	private DataMapWrapper dataMapWrapper;

	/**
	 * 查询地图列表
	 * @param dataMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ApiLog("查询地图列表")
	@PreAuthorize("hasAuthority('datav:dataMap:list')")
	@GetMapping("list")
	public ResponseEntity<IPage <DataMap>> list(DataMap dataMap, Page <DataMap> page) throws Exception {
		QueryWrapper <DataMap> queryWrapper = QueryWrapperGenerator.buildQueryCondition ( dataMap, DataMap.class );
		IPage <DataMap> result = dataMapService.page ( page, queryWrapper );
		return ResponseEntity.ok ( result );
	}

	/**
	 * 根据Id获取地图数据
	 */
	@PreAuthorize ("hasAnyAuthority('datav:dataMap:view','datav:dataMap:add','datav:dataMap:edit')")
	@GetMapping("queryById")
	public ResponseEntity<DataMap> queryById(String id) {
		DataMap dataMap = dataMapService.getById ( id );
		return ResponseEntity.ok ( dataMap );
	}


	/**
	 * 获取地图数据
	 * @param id
	 * @return
	 */

	@GetMapping("getMapData")
	public Object getMapData(String id) {
		DataMap dataMap = dataMapService.getById ( id );
		return dataMap.getData ();
	}

	/**
	 * 保存地图
	 */
	@PreAuthorize ("hasAnyAuthority('datav:dataMap:add','datav:dataMap:edit')")
	@PostMapping("save")
	public ResponseEntity<String> save(@RequestBody @Valid DataMapDTO dataMapDTO) {
		//新增或编辑表单保存
		DataMap dataMap = dataMapWrapper.toEntity ( dataMapDTO );
		dataMapService.saveOrUpdate (dataMap);//保存
		return ResponseEntity.ok ("保存地图成功");
	}


	/**
	 * 批量删除地图
	 */
	@PreAuthorize("hasAuthority('datav:dataMap:del')")
	@DeleteMapping("delete")
	public ResponseEntity<String> delete(String ids) {
		String idArray[] =ids.split(",");
		dataMapService.removeByIds ( Lists.newArrayList ( idArray) );
		return ResponseEntity.ok ("删除地图成功");
	}

}
