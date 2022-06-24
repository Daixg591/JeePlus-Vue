/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.ResponseUtil;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.extension.domain.FormDefinition;
import com.jeeplus.extension.domain.FormDefinitionJson;
import com.jeeplus.extension.service.FormDefinitionJsonService;
import com.jeeplus.extension.service.FormDefinitionService;
import com.jeeplus.extension.service.dto.FormDefinitionDTO;
import com.jeeplus.extension.service.mapstruct.FormDefinitionJsonWrapper;
import com.jeeplus.extension.service.mapstruct.FormDefinitionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 流程表单Controller
 * @author 刘高峰
 * @version 2021-02-02
 */
@RestController
@RequestMapping(value = "/extension/formDefinition")
public class FormDefinitionController {

	@Autowired
	private FormDefinitionService formDefinitionService;
	@Autowired
	private FormDefinitionJsonService formDefinitionJsonService;
	@Autowired
	private FormDefinitionJsonWrapper formDefinitionJsonWrapper;
	@Autowired
	private FormDefinitionWrapper formDefinitionWrapper;

	/**
	 * 流程表单列表数据
	 */
	@GetMapping("list")
	public ResponseEntity list(FormDefinitionDTO formDefinitionDTO, Page <FormDefinitionDTO> page) throws Exception {
		QueryWrapper <FormDefinitionDTO> queryWrapper = QueryWrapperGenerator.buildQueryCondition (formDefinitionDTO, FormDefinitionDTO.class);
		IPage <FormDefinitionDTO> result = formDefinitionService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取流程表单数据
	 */
	@GetMapping("queryById")
	public ResponseEntity queryById(String id) {
		return ResponseEntity.ok (formDefinitionService.getById ( id ));
	}

	/**
	 * 根据Id获取流程表单数据
	 */
	@GetMapping("queryByJsonId")
	public ResponseEntity queryByJsonId(String jsonId) {
		FormDefinitionJson formDefinitionJson = formDefinitionJsonService.getById ( jsonId );
		if(formDefinitionJson != null){
			FormDefinitionDTO formDefinitionDTO = formDefinitionService.getById (formDefinitionJson.getFormDefinitionId());
			formDefinitionDTO.setFormDefinitionJson(formDefinitionJsonWrapper.toDTO ( formDefinitionJson ));
			return ResponseEntity.ok ( formDefinitionDTO );
		}else{
			return ResponseEntity.badRequest().body ("流程关联的动态表单版本已经被删除!");
		}

	}
	/**
	 * 保存流程表单
	 */
	@PreAuthorize("hasAnyAuthority('extension:formDefinition:add','extension:formDefinition:edit')")
	@PostMapping("save")
	public ResponseEntity save(@RequestBody FormDefinitionDTO formDefinitionDTO) {
		//新增或编辑表单保存
		FormDefinition formDefinition = formDefinitionWrapper.toEntity ( formDefinitionDTO );
		formDefinitionService.saveOrUpdate (formDefinition);//保存
		return ResponseUtil.newInstance ().add ( "id", formDefinition.getId () ).ok ("保存成功");
	}


	/**
	 * 批量删除流程表单
	 */
	@PreAuthorize ("hasAnyAuthority('extension:formDefinition:del')")
	@DeleteMapping("delete")
	public ResponseEntity delete(String ids) {
		String idArray[] =ids.split(",");
		formDefinitionService.removeByIds ( Lists.newArrayList ( idArray) );
		return ResponseEntity.ok ("删除流程表单成功");
	}




}
