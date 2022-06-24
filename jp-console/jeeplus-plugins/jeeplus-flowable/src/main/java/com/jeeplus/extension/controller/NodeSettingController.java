/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.extension.domain.NodeSetting;
import com.jeeplus.extension.service.NodeSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 节点设置Controller
 * @author 刘高峰
 * @version 2021-01-11
 */
@RestController
@RequestMapping(value = "/extension/nodeSetting")
public class NodeSettingController {

	@Autowired
	private NodeSettingService nodeSettingService;


	/**
	 * 节点列表数据
	 */
	@GetMapping("list")
	public ResponseEntity list(NodeSetting nodeSetting, Page <NodeSetting> page) throws Exception {
		QueryWrapper <NodeSetting> queryWrapper = QueryWrapperGenerator.buildQueryCondition (nodeSetting, NodeSetting.class);
		IPage <NodeSetting> result = nodeSettingService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}

	/**
	 * 根据Id获取节点数据
	 */
	@GetMapping("queryById")
	public ResponseEntity queryById(String id) {
		return ResponseEntity.ok (nodeSettingService.getById ( id ));
	}

	/**
	 * 根据Id获取节点数据
	 */
	@GetMapping("queryValueByKey")
	public ResponseEntity queryById(String processDefId, String taskDefId, String key) {
		NodeSetting nodeSetting = nodeSettingService.queryByKey (processDefId, taskDefId, key);
		if(nodeSetting == null){
			return ResponseEntity.ok ( "" );
		}else{
			return ResponseEntity.ok (nodeSetting.getValue ());
		}
	}

	/**
	 * 保存节点
	 */
	@PostMapping("save")
	public ResponseEntity save(@RequestBody List<NodeSetting> nodeSettingList) {

		for(NodeSetting nodeSetting: nodeSettingList){
			nodeSettingService.deleteByDefIdAndTaskId(nodeSetting);//删除节点旧的属性
		}

		for(NodeSetting nodeSetting: nodeSettingList){
			nodeSettingService.save (nodeSetting);
		}

		return ResponseEntity.ok ("保存配置成功");
	}

	/**
	 * 批量删除节点
	 */
	@DeleteMapping("delete")
	public ResponseEntity delete(String ids) {
		String idArray[] =ids.split(",");
		nodeSettingService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok ("删除节点成功");
	}

}
