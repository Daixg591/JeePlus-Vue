/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.extension.domain.Listener;
import com.jeeplus.extension.service.ListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 监听器Controller
 * @author 刘高峰
 * @version 2021-10-14
 */
@RestController
@RequestMapping("/extension/listener")
public class ListenerController {

	@Autowired
	private ListenerService listenerService;

	/**
	 * 	监听器列表数据
	 */
	@GetMapping("list")
	public ResponseEntity list(Listener listener, Page <Listener> page) throws Exception {
		QueryWrapper <Listener> queryWrapper = QueryWrapperGenerator.buildQueryCondition (listener, Listener.class);
		IPage <Listener> result = listenerService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据id查询数据
	 * @param id
	 * @return
	 */
	@GetMapping("queryById")
	public ResponseEntity queryById(String id) {
		return ResponseEntity.ok (listenerService.getById ( id )  );
	}

	/**
	 * 保存监听器
	 */
	@PostMapping("save")
	public ResponseEntity save(@Valid @RequestBody Listener listener){
		//新增或编辑表单保存
		listenerService.saveOrUpdate (listener);//保存
		return ResponseEntity.ok ("保存监听器成功");
	}


	/**
	 * 批量删除监听器
	 */
	@DeleteMapping("delete")
	public ResponseEntity delete(String ids) {
		String idArray[] =ids.split(",");
		listenerService.removeByIds ( Lists.newArrayList ( idArray) );
		return ResponseEntity.ok ("删除监听器成功");
	}

}
