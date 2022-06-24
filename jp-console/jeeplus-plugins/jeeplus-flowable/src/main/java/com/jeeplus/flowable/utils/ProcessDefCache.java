/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.flowable.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.jeeplus.common.redis.RedisUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;

/**
 * 流程定义缓存
 ** @author liugaofefng
 * @version 2021-09-05
 */
public class ProcessDefCache {

	private static final String ACT_CACHE = "actCache";
	private static final String ACT_CACHE_PD_ID_ = "pd_id_";

	/**
	 * 获得流程定义对象
	 * @param procDefId
	 * @return
	 */
	public static ProcessDefinition get(String procDefId) {
		ProcessDefinition pd = (ProcessDefinition) RedisUtils.getInstance ().get(ACT_CACHE, ACT_CACHE_PD_ID_ + procDefId);
		if (pd == null) {
			RepositoryService repositoryService = SpringUtil.getBean(RepositoryService.class);
//			pd = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(pd);
			pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
			if (pd != null) {
				RedisUtils.getInstance ().set (ACT_CACHE, ACT_CACHE_PD_ID_ + procDefId, pd);
			}
		}
		return pd;
	}


}
