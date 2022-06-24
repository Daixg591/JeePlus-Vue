/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.extension.domain.Button;
import com.jeeplus.extension.service.ButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 常用按钮Controller
 * @author 刘高峰
 * @version 2021-09-07
 */
@RestController
@RequestMapping("/extension/button")
public class ButtonController {

	@Autowired
	private ButtonService buttonService;

	/**
	 * 常用按钮列表数据
	 */
	@GetMapping("list")
	public ResponseEntity<IPage<Button>> list(Button button, Page <Button> page) throws Exception {
		QueryWrapper <Button> queryWrapper = QueryWrapperGenerator.buildQueryCondition (button, Button.class);
		IPage <Button> result = buttonService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}

	/**
	 * 根据id查询数据
	 * @param id
	 * @return
	 */
	@GetMapping("queryById")
	public ResponseEntity queryById(String id) {
		Button button = buttonService.getById ( id );
		return ResponseEntity.ok ( button );
	}

	/**
	 * 保存常用按钮
	 */
	@PostMapping("save")
	public ResponseEntity save(@RequestBody Button button) {
		//新增或编辑表单保存
		buttonService.saveOrUpdate (button);//保存
		return ResponseEntity.ok ("保存常用按钮成功");
	}


	/**
	 * 批量删除常用按钮
	 */
	@DeleteMapping("delete")
	public ResponseEntity delete(String ids) {
		String idArray[] =ids.split(",");
		buttonService.removeByIds ( Lists.newArrayList ( idArray) );
		return ResponseEntity.ok ("删除常用按钮成功");
	}

	/**
	 * 按钮名唯一性校验（数据库中不存在）
	 */
	@GetMapping("validateNameNoExist")
	public ResponseEntity validateNameNoExist(String name) {
		boolean result  = buttonService.lambdaQuery ().eq ( Button::getName, name ).list ().size () == 0;
		return ResponseEntity.ok ( result );
	}

	/**
	 * 编码唯一性验证（数据库中不存在）
	 */
	@GetMapping("validateCodeNoExist")
	public ResponseEntity validateCodeNoExist(String code) {
		boolean result  = buttonService.lambdaQuery ().eq ( Button::getCode, code ).list ().size () == 0;
		return ResponseEntity.ok ( result );
	}

}
