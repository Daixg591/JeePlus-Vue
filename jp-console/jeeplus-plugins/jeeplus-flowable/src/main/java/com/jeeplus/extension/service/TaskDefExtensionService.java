/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.extension.domain.FlowAssignee;
import com.jeeplus.extension.domain.FlowButton;
import com.jeeplus.extension.domain.FlowCondition;
import com.jeeplus.extension.domain.TaskDefExtension;
import com.jeeplus.extension.mapper.TaskDefExtensionMapper;
import com.jeeplus.extension.service.dto.FlowAssigneeDTO;
import com.jeeplus.extension.service.dto.FlowButtonDTO;
import com.jeeplus.extension.service.dto.FlowConditionDTO;
import com.jeeplus.extension.service.dto.TaskDefExtensionDTO;
import com.jeeplus.extension.service.mapstruct.FlowAssigneeWrapper;
import com.jeeplus.extension.service.mapstruct.FlowButtonWrapper;
import com.jeeplus.extension.service.mapstruct.FlowConditionWrapper;
import com.jeeplus.extension.service.mapstruct.TaskDefExtensionWrapper;
import com.jeeplus.sys.constant.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 工作流扩展Service
 * @author 刘高峰
 * @version 2021-09-23
 */
@Service
@Transactional
public class TaskDefExtensionService extends ServiceImpl <TaskDefExtensionMapper, TaskDefExtension> {

	@Autowired
	private FlowAssigneeService flowAssigneeService;
	@Autowired
	private FlowButtonService flowButtonService;
	@Autowired
	private FlowConditionService flowConditionService;
	@Autowired
	private TaskDefExtensionWrapper taskDefExtensionWrapper;

	public TaskDefExtensionDTO getById(String id) {
		TaskDefExtensionDTO taskDefExtension = taskDefExtensionWrapper.toDTO ( baseMapper.selectById ( id ) );
		taskDefExtension.setFlowAssigneeList(flowAssigneeService.lambdaQuery ().eq ( FlowAssignee::getTaskDefId, id ).list ().stream ().map ( FlowAssigneeWrapper.INSTANCE::toDTO ).collect( Collectors.toList()) );
		taskDefExtension.setFlowButtonList(flowButtonService.lambdaQuery ().eq ( FlowButton::getTaskDefId, id ).list ().stream ().map ( FlowButtonWrapper.INSTANCE::toDTO ).collect( Collectors.toList()) );
		taskDefExtension.setFlowConditionList(flowConditionService.lambdaQuery ().eq ( FlowCondition::getTaskDefId, id ).list ().stream ().map ( FlowConditionWrapper.INSTANCE::toDTO ).collect( Collectors.toList()) );
		return taskDefExtension;
	}


	public void save(TaskDefExtensionDTO taskDefExtensionDTO) {
		TaskDefExtension taskDefExtension = taskDefExtensionWrapper.toEntity ( taskDefExtensionDTO );
		super.saveOrUpdate (taskDefExtension);
		for (FlowAssigneeDTO flowAssigneeDTO : taskDefExtensionDTO.getFlowAssigneeList()){
			if (flowAssigneeDTO.getId() == null){
				continue;
			}

			if ( CommonConstants.DELETED.equals (flowAssigneeDTO.getDelFlag())){
				flowAssigneeService.removeById (flowAssigneeDTO.getId ());
			}else{
				FlowAssignee flowAssignee =	FlowAssigneeWrapper.INSTANCE.toEntity ( flowAssigneeDTO );
				flowAssignee.setTaskDefId ( taskDefExtension.getId () );
				flowAssigneeService.saveOrUpdate (flowAssignee);
			}
		}
		for (FlowButtonDTO flowButtonDTO : taskDefExtensionDTO.getFlowButtonList()){
			if (flowButtonDTO.getId() == null){
				continue;
			}
			if (CommonConstants.DELETED.equals (flowButtonDTO.getDelFlag())){
				flowButtonService.removeById ( flowButtonDTO.getId () );

			}else{
				FlowButton flowButton = FlowButtonWrapper.INSTANCE.toEntity ( flowButtonDTO );
				flowButton.setTaskDefId ( taskDefExtension.getId () );
				flowButtonService.saveOrUpdate ( flowButton );
			}
		}
		for (FlowConditionDTO flowConditionDTO : taskDefExtensionDTO.getFlowConditionList()){
			if (flowConditionDTO.getId() == null){
				continue;
			}
			if (CommonConstants.DELETED.equals (flowConditionDTO.getDelFlag())){
				flowConditionService.removeById ( flowConditionDTO.getId () );
			}else{
				FlowCondition flowCondition = FlowConditionWrapper.INSTANCE.toEntity ( flowConditionDTO );
				flowCondition.setTaskDefId ( taskDefExtension.getId () );
				flowConditionService.save ( flowCondition );
			}
		}
	}

	public void delete(String id) {
		removeById ( id );
		flowAssigneeService.lambdaUpdate ().eq ( FlowAssignee::getTaskDefId, id ).remove ();
		flowButtonService.lambdaUpdate ().eq ( FlowButton::getTaskDefId, id ).remove ();
		flowConditionService.lambdaUpdate ().eq ( FlowCondition::getTaskDefId, id ).remove ();
	}

	public void deleteByProcessDefId(String processDefId) {
		List<TaskDefExtension> list = lambdaQuery ().eq ( TaskDefExtension::getProcessDefId, processDefId ).list ();
		for(TaskDefExtension taskDefExtension: list){
			String id = taskDefExtension.getId ();
			delete ( id );
		}

	}

}
