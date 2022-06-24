/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.extension.domain.TaskDefExtension;
import com.jeeplus.extension.service.TaskDefExtensionService;
import com.jeeplus.extension.service.dto.TaskDefExtensionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工作流扩展Controller
 * @author 刘高峰
 * @version 2021-09-23
 */
@RestController
@RequestMapping(value = "/extension/taskDefExtension")
public class TaskDefExtensionController  {

	@Autowired
	private TaskDefExtensionService taskDefExtensionService;

	/**
	 * 工作流扩展列表数据
	 */
	@GetMapping("list")
	public ResponseEntity list(TaskDefExtension taskDefExtension, Page <TaskDefExtension> page) throws Exception {
		QueryWrapper <TaskDefExtension> queryWrapper = QueryWrapperGenerator.buildQueryCondition (taskDefExtension, TaskDefExtension.class);
		IPage <TaskDefExtension> result = taskDefExtensionService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}

	/**
	 * 根据Id获取工作流扩展数据
	 */
	@GetMapping("queryById")
	public ResponseEntity queryById(String id) {
		return ResponseEntity.ok ( taskDefExtensionService.getById ( id ) );
	}

	@GetMapping("queryByDefIdAndTaskId")
	public ResponseEntity queryByDefIdAndTaskId(TaskDefExtension taskDefExtension) throws Exception {
		if( StrUtil.isBlank(taskDefExtension.getProcessDefId()) || StrUtil.isBlank(taskDefExtension.getTaskDefId())){
			return ResponseEntity.ok ( null );
		}
		List<TaskDefExtension> list = taskDefExtensionService.lambdaQuery ()
				.eq ( TaskDefExtension::getProcessDefId, taskDefExtension.getProcessDefId () )
				.eq ( TaskDefExtension::getTaskDefId, taskDefExtension.getTaskDefId () )
				.list ();
		if(list.size() > 1){
			throw new Exception("重复的task id定义!");
		}else if(list.size() == 1){
			String id = list.get(0).getId();
			return ResponseEntity.ok ( taskDefExtensionService.getById (id) );
		}else {
			return ResponseEntity.ok ( taskDefExtension );
		}

	}

	/**
	 * 保存工作流扩展
	 */
	@PostMapping("save")
	public ResponseEntity save(@RequestBody List<TaskDefExtensionDTO> taskDefExtensionList){

		for(TaskDefExtensionDTO taskDefExtension: taskDefExtensionList){
			List<TaskDefExtension> list = taskDefExtensionService.lambdaQuery ()
					.eq ( TaskDefExtension::getProcessDefId, taskDefExtension.getProcessDefId () )
					.eq ( TaskDefExtension::getTaskDefId, taskDefExtension.getTaskDefId () ).list ();
			// 删除旧数据
			for(TaskDefExtension defExtension:list){
				taskDefExtensionService.delete (defExtension.getId ()  );
			}
			//保存新数据
			taskDefExtensionService.save (taskDefExtension);//保存
		}
		return ResponseEntity.ok ("保存工作流扩展成功");
	}


	/**
	 * 批量删除工作流扩展
	 */
	@DeleteMapping("delete")
	public ResponseEntity delete(String ids) {
		String idArray[] =ids.split(",");
		taskDefExtensionService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok ("删除工作流扩展成功");
	}

}
