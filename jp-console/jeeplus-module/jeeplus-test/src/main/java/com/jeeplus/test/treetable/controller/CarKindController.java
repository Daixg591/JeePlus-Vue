/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.treetable.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import com.google.common.collect.Lists;
import com.jeeplus.aop.logging.annotation.ApiLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jeeplus.test.treetable.service.dto.CarKindDTO;
import com.jeeplus.test.treetable.service.mapstruct.CarKindWrapper;
import com.jeeplus.test.treetable.service.CarKindService;

/**
 * 车系Controller
 * @author lgf
 * @version 2021-10-17
 */
@RestController
@RequestMapping(value = "/test/treetable/carKind")
public class CarKindController {

	@Autowired
	private CarKindService carKindService;

	@Autowired
	private CarKindWrapper carKindWrapper;

	/**
	 * 根据Id获取车系数据
	 */
	@ApiLog("根据Id获取车系数据")
	@ApiOperation(value = "根据Id获取车系数据")
	@GetMapping("queryById")
	public ResponseEntity<CarKindDTO> queryById(String id) {
		return ResponseEntity.ok ( carKindWrapper.toDTO ( carKindService.getById ( id ) ) );
	}

	/**
	 * 保存车系
	 */
	@ApiLog("保存车系")
	@ApiOperation(value = "保存车系")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody CarKindDTO carKindDTO) {
		//新增或编辑表单保存
		carKindService.saveOrUpdate (carKindWrapper.toEntity (carKindDTO));
        return ResponseEntity.ok ( "保存车系成功" );
	}

	/**
	 * 删除车系
	 */
	@ApiLog("删除车系")
	@ApiOperation(value = "删除车系")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        carKindService.removeWithChildrenByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除车系成功" );
	}

	/**
     * 获取JSON树形数据。
     * @param extId 排除的ID
     * @return
	*/
	@ApiLog("查询车系树表数据")
	@ApiOperation(value = "查询车系树表数据")
	@GetMapping("treeData")
	public ResponseEntity <List <CarKindDTO> > treeData(String extId) {
		List <CarKindDTO> rootTree = carKindService.treeData ( extId ).stream ().map ( carKindWrapper::toDTO ).collect( Collectors.toList());
		return ResponseEntity.ok ( rootTree );
	}

}
