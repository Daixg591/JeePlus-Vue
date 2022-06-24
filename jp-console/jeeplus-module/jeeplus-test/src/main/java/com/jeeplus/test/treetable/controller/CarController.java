/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.treetable.controller;

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
import com.jeeplus.test.treetable.service.dto.CarDTO;
import com.jeeplus.test.treetable.service.mapstruct.CarWrapper;
import com.jeeplus.test.treetable.service.CarService;

/**
 * 车辆Controller
 * @author lgf
 * @version 2021-10-17
 */
@Api(tags ="车辆")
@RestController
@RequestMapping(value = "/test/treetable/car")
public class CarController {

	@Autowired
	private CarService carService;

	@Autowired
	private CarWrapper carWrapper;

	/**
	 * 车辆列表数据
	 */
	@ApiLog("查询车辆列表数据")
	@ApiOperation(value = "查询车辆列表数据")
	@PreAuthorize("hasAuthority('test:treetable:car:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<CarDTO>> list(CarDTO carDTO, Page<CarDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (carDTO, CarDTO.class);
		IPage<CarDTO> result = carService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取车辆数据
	 */
	@ApiLog("根据Id获取车辆数据")
	@ApiOperation(value = "根据Id获取车辆数据")
	@PreAuthorize("hasAnyAuthority('test:treetable:car:view','test:treetable:car:add','test:treetable:car:edit')")
	@GetMapping("queryById")
	public ResponseEntity<CarDTO> queryById(String id) {
		return ResponseEntity.ok ( carService.findById ( id ) );
	}

	/**
	 * 保存车辆
	 */
	@ApiLog("保存车辆")
	@ApiOperation(value = "保存车辆")
	@PreAuthorize("hasAnyAuthority('test:treetable:car:add','test:treetable:car:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody CarDTO carDTO) {
		//新增或编辑表单保存
		carService.saveOrUpdate (carWrapper.toEntity (carDTO));
        return ResponseEntity.ok ( "保存车辆成功" );
	}


	/**
	 * 删除车辆
	 */
	@ApiLog("删除车辆")
	@ApiOperation(value = "删除车辆")
	@PreAuthorize("hasAuthority('test:treetable:car:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        carService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除车辆成功" );
	}

}
