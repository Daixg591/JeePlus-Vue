/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.extension.domain.FlowCopy;
import com.jeeplus.extension.service.FlowCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 流程抄送Controller
 * @author 刘高峰
 * @version 2021-10-10
 */
@RestController
@RequestMapping("/extension/flowCopy")
public class FlowCopyController {

	@Autowired
	private FlowCopyService flowCopyService;

	/**
	 * 列表数据
	 * @param flowCopy
	 * @param page
	 * @return
	 */
	@GetMapping("list")
	public ResponseEntity list(FlowCopy flowCopy, Page <FlowCopy> page) throws Exception {
		QueryWrapper <FlowCopy> queryWrapper = QueryWrapperGenerator.buildQueryCondition (flowCopy, FlowCopy.class);
		IPage <FlowCopy> result = flowCopyService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}

	/**
	 * 根据id查询数据
	 * @param id
	 * @return
	 */
	@GetMapping("queryById")
	public ResponseEntity queryById(String id) {
		return ResponseEntity.ok ( flowCopyService.getById ( id ) );
	}

	/**
	 * 保存流程抄送
	 */
	@PostMapping("save")
	public ResponseEntity save(@RequestParam("userIds") String userIds, FlowCopy flowCopy) {

		for(String userId: userIds.split(",")){
			flowCopy.setId(null);
			flowCopy.setUserId(userId);
			flowCopyService.save(flowCopy);//保存
		}
		return ResponseEntity.ok ("保存流程抄送成功");
	}


	/**
	 * 批量删除流程抄送
	 */
	@DeleteMapping("delete")
	public ResponseEntity delete(String ids) {
		String idArray[] =ids.split(",");
		flowCopyService.removeByIds ( Lists.newArrayList ( idArray) );
		return ResponseEntity.ok ("删除流程抄送成功");
	}

}
