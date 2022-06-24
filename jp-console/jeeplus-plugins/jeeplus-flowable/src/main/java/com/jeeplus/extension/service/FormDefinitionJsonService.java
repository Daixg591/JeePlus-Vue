/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.extension.domain.FormDefinitionJson;
import com.jeeplus.extension.mapper.FormDefinitionJsonMapper;
import com.jeeplus.sys.constant.CommonConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程表单Service
 * @author 刘高峰
 * @version 2021-02-02
 */
@Service
@Transactional
public class FormDefinitionJsonService extends ServiceImpl <FormDefinitionJsonMapper, FormDefinitionJson> {


	public Integer getMaxVersion(FormDefinitionJson formDefinitionJson) {
		return lambdaQuery ().eq ( FormDefinitionJson::getFormDefinitionId, formDefinitionJson.getFormDefinitionId () ).orderByDesc ( FormDefinitionJson::getVersion ).last ( "limit 1" ).one ().getVersion ();
	}

	public void deleteByFormDefinitionId(FormDefinitionJson formDefinitionJson) {
		lambdaUpdate ().eq ( FormDefinitionJson::getFormDefinitionId, formDefinitionJson.getFormDefinitionId () ).remove ();
	}

	public void updatePrimary(String id) {
		// 获取当前数据
		FormDefinitionJson formDefinitionJson = getById ( id );
		// 其余版本设置为非主版本
		lambdaUpdate ().eq ( FormDefinitionJson::getFormDefinitionId, formDefinitionJson.getFormDefinitionId () ).ne ( FormDefinitionJson::getId, id ).set ( FormDefinitionJson::getIsPrimary, CommonConstants.NO ).update ();
		//指定版本设置为主版本
		lambdaUpdate ().eq ( FormDefinitionJson::getId, id ).set ( FormDefinitionJson::getIsPrimary, CommonConstants.YES ).update ();
	}

}
