/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.extension.domain.FormDefinition;
import com.jeeplus.extension.domain.FormDefinitionJson;
import com.jeeplus.extension.mapper.FormDefinitionMapper;
import com.jeeplus.extension.service.dto.FormDefinitionDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程表单Service
 * @author 刘高峰
 * @version 2021-02-02
 */
@Service
@Transactional
public class FormDefinitionService extends ServiceImpl <FormDefinitionMapper, FormDefinition> {

	@Autowired
	private FormDefinitionJsonService formDefinitionJsonService;


	public void delete(FormDefinition formDefinition) {

		baseMapper.deleteById ( formDefinition.getId () );
		formDefinitionJsonService.lambdaUpdate ().eq ( FormDefinitionJson::getFormDefinitionId, formDefinition.getId () ).remove ();
	}

	public FormDefinitionDTO getById(String id){
		return baseMapper.getById ( id );
	}

	public IPage <FormDefinitionDTO> findPage (Page <FormDefinitionDTO> page, QueryWrapper<FormDefinitionDTO> queryWrapper) {
		queryWrapper.eq ( "a.del_flag", 0 ); //排除已删除
		queryWrapper.and (wrapper -> wrapper.eq("c.is_primary", null).or ().eq ( "c.is_primary", "1" ));// 添加主版本过滤条件
		return baseMapper.findList (page, queryWrapper  );
	}

}
