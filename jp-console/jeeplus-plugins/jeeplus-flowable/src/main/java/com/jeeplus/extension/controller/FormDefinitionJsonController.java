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
import com.jeeplus.extension.domain.FormDefinitionJson;
import com.jeeplus.extension.service.FormDefinitionJsonService;
import com.jeeplus.sys.constant.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 流程表单Controller
 * @author 刘高峰
 * @version 2021-02-02
 */
@RestController
@RequestMapping(value = "/extension/formDefinitionJson")
public class FormDefinitionJsonController {

	@Autowired
	private FormDefinitionJsonService formDefinitionJsonService;

	/**
	 * 流程表单列表数据
	 */
	@GetMapping("list")
	public ResponseEntity list(FormDefinitionJson formDefinitionJson, Page <FormDefinitionJson> page) throws Exception {
		QueryWrapper <FormDefinitionJson> queryWrapper = QueryWrapperGenerator.buildQueryCondition (formDefinitionJson, FormDefinitionJson.class);
		IPage <FormDefinitionJson> result = formDefinitionJsonService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取流程表单数据
	 */
	@GetMapping("queryById")
	public ResponseEntity queryById(FormDefinitionJson formDefinitionJson) {
		formDefinitionJson = formDefinitionJsonService.getById (formDefinitionJson.getId());

		return ResponseEntity.ok (formDefinitionJson);
	}

	/**
	 * 更新主版本
	 */
	@PutMapping("updatePrimary")
	public ResponseEntity updatePrimary(String id) {
		//设置主版本
		formDefinitionJsonService.updatePrimary ( id );
		return ResponseEntity.ok ("设置主表单成功!");
	}

	/**
	 * 保存流程表单
	 */
	@PostMapping("save")
	public ResponseEntity save(@RequestBody FormDefinitionJson formDefinitionJson) {
		if( StrUtil.isBlank(formDefinitionJson.getId())){//新增
			formDefinitionJson.setVersion(1);
		}else {//更新
			FormDefinitionJson old = formDefinitionJsonService.getById (formDefinitionJson.getId());
			if(CommonConstants.YES.equals(old.getStatus())){//已发布, 如果表单已经发布，不可修改，只能发布为新版本
				formDefinitionJson.setId("");//发布新版本
				formDefinitionJson.setVersion(formDefinitionJsonService.getMaxVersion(formDefinitionJson)+1);
			}

		}
		formDefinitionJsonService.saveOrUpdate (formDefinitionJson);//保存
		formDefinitionJsonService.updatePrimary ( formDefinitionJson.getId () );// 设置为新版本
		return ResponseEntity.ok ("保存流程表单成功");
	}


	/**
	 * 批量删除流程表单
	 */
	@DeleteMapping("delete")
	public ResponseEntity delete(String ids) {
		String idArray[] =ids.split(",");
		formDefinitionJsonService.removeByIds ( Lists.newArrayList ( idArray) );
		return ResponseEntity.ok ("删除流程表单成功");
	}

}
