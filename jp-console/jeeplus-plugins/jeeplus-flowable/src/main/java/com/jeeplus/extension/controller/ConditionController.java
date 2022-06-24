/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.extension.domain.Condition;
import com.jeeplus.extension.service.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 流程表达式Controller
 * @author liugf
 * @version 2021-09-29
 */
@RestController
@RequestMapping("/extension/condition")
public class ConditionController {

	@Autowired
	private ConditionService conditionService;

	/**
	 * 流程表达式列表数据
	 */
	@GetMapping("list")
	public ResponseEntity list(Condition condition, Page <Condition> page) throws Exception {
		QueryWrapper <Condition> queryWrapper = QueryWrapperGenerator.buildQueryCondition (condition, Condition.class);
		IPage <Condition> result = conditionService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}

	/**
	 * 根据id查询数据
	 * @param id
	 * @return
	 */
	@GetMapping("queryById")
	public ResponseEntity queryById(String id) {
		return ResponseEntity.ok ( conditionService.getById ( id ) );
	}

	/**
	 * 保存流程表达式
	 */
	@PostMapping("save")
	public ResponseEntity save(@RequestBody Condition condition) {
		//新增或编辑表单保存
		conditionService.saveOrUpdate (condition);//保存
		return ResponseEntity.ok ("保存流程表达式成功");
	}


	/**
	 * 批量删除流程表达式
	 */
	@DeleteMapping("delete")
	public ResponseEntity delete(String ids) {
		String idArray[] =ids.split(",");
		conditionService.removeByIds ( Lists.newArrayList ( idArray) );
		return ResponseEntity.ok ("删除流程表达式成功");
	}


}
