/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.flowable.utils;

import com.google.common.collect.Sets;
import com.jeeplus.flowable.constant.FlowableConstant;
import com.jeeplus.flowable.model.Flow;
import com.jeeplus.sys.service.dto.RoleDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.editor.constants.ModelDataJsonConstants;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.repository.Model;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntity;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntity;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;

import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流程工具
 * @version 2021-08-03
 */
public class FlowableUtils {


	/**
	 * 获取流程表单URL
	 * @param formKey
	 * @param flow 表单传递参数
	 * @return
	 */
	public static String getFormUrl(String formKey, Flow flow) throws Exception{

		StringBuilder formUrl = new StringBuilder();
		formUrl.append(formKey).append(formUrl.indexOf("?") == -1 ? "?" : "&");
		formUrl.append("act.taskId=").append(flow.getTaskId() != null ? flow.getTaskId() : "");
		formUrl.append("&act.taskName=").append(flow.getTaskName() != null ? URLEncoder.encode(flow.getTaskName (), "UTF-8") : "");
		formUrl.append("&act.taskDefKey=").append(flow.getTaskDefKey() != null ? flow.getTaskDefKey() : "");
		formUrl.append("&act.procInsId=").append(flow.getProcInsId() != null ? flow.getProcInsId() : "");
		formUrl.append("&act.procDefId=").append(flow.getProcDefId() != null ? flow.getProcDefId() : "");
		formUrl.append("&id=").append(flow.getBusinessId() != null ? flow.getBusinessId() : "");

		return formUrl.toString();
	}

	public static UserEntity toFlowableUser(UserDTO user){
		if (user == null){
			return null;
		}
		UserEntity userEntity = new UserEntityImpl();
		userEntity.setId(user.getId());
		userEntity.setFirstName(user.getName());
		userEntity.setLastName( StringUtils.EMPTY);
		userEntity.setPassword(user.getPassword());
		userEntity.setEmail(user.getEmail());
		userEntity.setRevision(1);
		return userEntity;
	}

	public static GroupEntity toFlowableGroup(RoleDTO role){
		if (role == null){
			return null;
		}
		GroupEntity groupEntity = new GroupEntityImpl();
		groupEntity.setId(role.getEnName ());
		groupEntity.setName(role.getName());
//		groupEntity.setType(role.getRoleType());
		groupEntity.setRevision(1);
		return groupEntity;
	}


	public static String toTimeString(long time) {
		TimeUtils t = new TimeUtils(time);
		int day = t.get(TimeUtils.DAY);
		int hour = t.get(TimeUtils.HOUR);
		int minute = t.get(TimeUtils.MINUTE);
		int second = t.get(TimeUtils.SECOND);
		StringBuilder sb = new StringBuilder();
		if (day > 0){
			sb.append(day).append("天");
		}
		if (hour > 0){
			sb.append(hour).append("时");
		}
		if (minute > 0){
			sb.append(minute).append("分");
		}
		if (second > 0){
			sb.append(second).append("秒");
		}
		return sb.toString();
	}

	public static Model buildModel(Model model, String categoryId, String name, String key, String description) {
		Map map = new HashMap();
		map.put("category", categoryId);
		map.put("key", key);
		map.put(ModelDataJsonConstants.MODEL_NAME, name);
		map.put(ModelDataJsonConstants.MODEL_REVISION, 1);
		map.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
		model.setMetaInfo(JSONUtils.valueToString(map));
		model.setKey(key);
		model.setName(name);
		model.setCategory(categoryId);
		model.setKey(key);
		return model;
	}

	public static boolean isReachable(Process process, FlowNode sourceElement, FlowNode targetElement) {
		return isReachable(process, sourceElement, targetElement, Sets.newHashSet());
	}

	public static boolean isReachable(Process process, FlowNode sourceElement, FlowNode targetElement,
									  Set<String> visitedElements) {
		// Special case: start events in an event subprocess might exist as an execution and are most likely be able to
		// reach the target
		// when the target is in the event subprocess, but should be ignored as they are not 'real' runtime executions
		// (but rather waiting for a
		// trigger)
		if (sourceElement instanceof StartEvent && isInEventSubprocess(sourceElement)) {
			return false;
		}
		// No outgoing seq flow: could be the end of eg . the process or an embedded subprocess
		if (sourceElement.getOutgoingFlows().size() == 0) {
			visitedElements.add(sourceElement.getId());
			FlowElementsContainer parentElement = process.findParent(sourceElement);
			if (parentElement instanceof SubProcess) {
				sourceElement = (SubProcess) parentElement;
				// by zjm begin
				// 子流程的结束节点，若目标节点在该子流程中，说明无法到达，返回false
				if (((SubProcess) sourceElement).getFlowElement(targetElement.getId()) != null) {
					return false;
				}
				// by zjm end
			} else {
				return false;
			}
		}
		if (sourceElement.getId().equals(targetElement.getId())) {
			return true;
		}
		// To avoid infinite looping, we must capture every node we visit
		// and check before going further in the graph if we have already
		// visited the node.
		visitedElements.add(sourceElement.getId());
		// by zjm begin
		// 当前节点能够到达子流程，且目标节点在子流程中，说明可以到达，返回true
		if (sourceElement instanceof SubProcess && ((SubProcess) sourceElement).getFlowElement(targetElement.getId()) != null) {
			return true;
		}
		// by zjm end
		List<SequenceFlow> sequenceFlows = sourceElement.getOutgoingFlows();
		if (sequenceFlows != null && sequenceFlows.size() > 0) {
			for (SequenceFlow sequenceFlow : sequenceFlows) {
				String targetRef = sequenceFlow.getTargetRef();
				FlowNode sequenceFlowTarget = (FlowNode) process.getFlowElement(targetRef, true);
				if (sequenceFlowTarget != null && !visitedElements.contains(sequenceFlowTarget.getId())) {
					boolean reachable = isReachable(process, sequenceFlowTarget, targetElement, visitedElements);
					if (reachable) {
						return true;
					}
				}
			}
		}
		return false;
	}

	protected static boolean isInEventSubprocess(FlowNode flowNode) {
		FlowElementsContainer flowElementsContainer = flowNode.getParentContainer();
		while (flowElementsContainer != null) {
			if (flowElementsContainer instanceof EventSubProcess) {
				return true;
			}
			if (flowElementsContainer instanceof FlowElement) {
				flowElementsContainer = ((FlowElement) flowElementsContainer).getParentContainer();
			} else {
				flowElementsContainer = null;
			}
		}
		return false;
	}

	public static String[] getSourceAndTargetRealActivityId(FlowNode sourceFlowElement, FlowNode targetFlowElement) {
		// 实际应操作的当前节点ID
		String sourceRealActivityId = sourceFlowElement.getId();
		// 实际应操作的目标节点ID
		String targetRealActivityId = targetFlowElement.getId();
		List<String> sourceParentProcesss = FlowableUtils.getParentProcessIds(sourceFlowElement);
		List<String> targetParentProcesss = FlowableUtils.getParentProcessIds(targetFlowElement);
		int diffParentLevel = getDiffLevel(sourceParentProcesss, targetParentProcesss);
		if (diffParentLevel != -1) {
			sourceRealActivityId = sourceParentProcesss.size() == diffParentLevel ? sourceRealActivityId :
					sourceParentProcesss.get(diffParentLevel);
			targetRealActivityId = targetParentProcesss.size() == diffParentLevel ? targetRealActivityId :
					targetParentProcesss.get(diffParentLevel);
		}
		return new String[]{sourceRealActivityId, targetRealActivityId};
	}


	public static List<String> getParentProcessIds(FlowNode flowNode) {
		List<String> result = new ArrayList<> ();
		FlowElementsContainer flowElementsContainer = flowNode.getParentContainer();
		while (flowElementsContainer != null) {
			if (flowElementsContainer instanceof SubProcess) {
				SubProcess flowElement = (SubProcess) flowElementsContainer;
				result.add(flowElement.getId());
				flowElementsContainer = flowElement.getParentContainer();
			} else if (flowElementsContainer instanceof Process) {
				Process flowElement = (Process) flowElementsContainer;
				result.add(flowElement.getId());
				flowElementsContainer = null;
			}
		}
		// 第一层Process为第0个
		Collections.reverse(result);
		return result;
	}

	/**
	 * 查询不同层级
	 *
	 * @param sourceList
	 * @param targetList
	 * @return 返回不同的层级，如果其中一个层级较深，则返回层级小的+1，从第0层开始，请注意判断是否会出现下标越界异常；返回 -1 表示在同一层
	 */
	public static Integer getDiffLevel(List<String> sourceList, List<String> targetList) {
		if (sourceList == null || sourceList.isEmpty() || targetList == null || targetList.isEmpty()) {
			throw new FlowableException ("sourceList and targetList cannot be empty");
		}
		if (sourceList.size() == 1 && targetList.size() == 1) {
			// 都在第0层且不相等
			if (!sourceList.get(0).equals(targetList.get(0))) {
				return 0;
			} else {// 都在第0层且相等
				return -1;
			}
		}

		int minSize = sourceList.size() < targetList.size() ? sourceList.size() : targetList.size();
		Integer targetLevel = null;
		for (int i = 0; i < minSize; i++) {
			if (!sourceList.get(i).equals(targetList.get(i))) {
				targetLevel = i;
				break;
			}
		}
		if (targetLevel == null) {
			if (sourceList.size() == targetList.size()) {
				targetLevel = -1;
			} else {
				targetLevel = minSize;
			}
		}
		return targetLevel;
	}

	public static Map<String, Set<String>> getSpecialGatewayElements(FlowElementsContainer container) {
		return getSpecialGatewayElements(container, null);
	}

	public static Map<String, Set<String>> getSpecialGatewayElements(FlowElementsContainer container, Map<String,
			Set<String>> specialGatewayElements) {
		if (specialGatewayElements == null) {
			specialGatewayElements = new HashMap<>(16);
		}
		Collection<FlowElement> flowelements = container.getFlowElements();
		for (FlowElement flowElement : flowelements) {
			boolean isBeginSpecialGateway =
					flowElement.getId().endsWith( FlowableConstant.SPECIAL_GATEWAY_BEGIN_SUFFIX) && (flowElement instanceof ParallelGateway || flowElement instanceof InclusiveGateway || flowElement instanceof ComplexGateway);
			if (isBeginSpecialGateway) {
				String gatewayBeginRealId = flowElement.getId();
				String gatewayId = gatewayBeginRealId.substring(0, gatewayBeginRealId.length() - 6);
				Set<String> gatewayIdContainFlowelements = specialGatewayElements.computeIfAbsent(gatewayId,
						k -> new HashSet<>());
				findElementsBetweenSpecialGateway(flowElement,
						gatewayId + FlowableConstant.SPECIAL_GATEWAY_END_SUFFIX, gatewayIdContainFlowelements);
			} else if (flowElement instanceof SubProcess) {
				getSpecialGatewayElements((SubProcess) flowElement, specialGatewayElements);
			}
		}

		// 外层到里层排序
		Map<String, Set<String>> specialGatewayNodesSort = new LinkedHashMap<>();
		specialGatewayElements.entrySet().stream().sorted((o1, o2) -> o2.getValue().size() - o1.getValue().size()).forEach(entry -> specialGatewayNodesSort.put(entry.getKey(), entry.getValue()));

		return specialGatewayNodesSort;
	}

	public static void findElementsBetweenSpecialGateway(FlowElement specialGatewayBegin, String specialGatewayEndId,
														 Set<String> elements) {
		elements.add(specialGatewayBegin.getId());
		List<SequenceFlow> sequenceFlows = ((FlowNode) specialGatewayBegin).getOutgoingFlows();
		if (sequenceFlows != null && sequenceFlows.size() > 0) {
			for (SequenceFlow sequenceFlow : sequenceFlows) {
				FlowElement targetFlowElement = sequenceFlow.getTargetFlowElement();
				String targetFlowElementId = targetFlowElement.getId();
				elements.add(specialGatewayEndId);
				if (targetFlowElementId.equals(specialGatewayEndId)) {
					continue;
				} else {
					findElementsBetweenSpecialGateway(targetFlowElement, specialGatewayEndId, elements);
				}
			}
		}
	}

	public static Set<String> getParentExecutionIdsByActivityId(List<ExecutionEntity> executions, String activityId) {
		List<ExecutionEntity> activityIdExecutions =
				executions.stream().filter(e -> activityId.equals(e.getActivityId())).collect(Collectors.toList());
		if (activityIdExecutions.isEmpty()) {
			throw new FlowableException("Active execution could not be found with activity id " + activityId);
		}
		// check for a multi instance root execution
		ExecutionEntity miExecution = null;
		boolean isInsideMultiInstance = false;
		for (ExecutionEntity possibleMiExecution : activityIdExecutions) {
			if (possibleMiExecution.isMultiInstanceRoot()) {
				miExecution = possibleMiExecution;
				isInsideMultiInstance = true;
				break;
			}
			if (isExecutionInsideMultiInstance(possibleMiExecution)) {
				isInsideMultiInstance = true;
			}
		}
		Set<String> parentExecutionIds = new HashSet<> ();
		if (isInsideMultiInstance) {
			Stream<ExecutionEntity> executionEntitiesStream = activityIdExecutions.stream();
			if (miExecution != null) {
				executionEntitiesStream = executionEntitiesStream.filter(ExecutionEntity::isMultiInstanceRoot);
			}
			executionEntitiesStream.forEach(childExecution -> {
				parentExecutionIds.add(childExecution.getParentId());
			});
		} else {
			ExecutionEntity execution = activityIdExecutions.iterator().next();
			parentExecutionIds.add(execution.getParentId());
		}
		return parentExecutionIds;
	}

	public static boolean isExecutionInsideMultiInstance(ExecutionEntity execution) {
		return getFlowElementMultiInstanceParentId(execution.getCurrentFlowElement()).isPresent();
	}

	public static Optional<String> getFlowElementMultiInstanceParentId(FlowElement flowElement) {
		FlowElementsContainer parentContainer = flowElement.getParentContainer();
		while (parentContainer instanceof Activity) {
			if (isFlowElementMultiInstance((Activity) parentContainer)) {
				return Optional.of(((Activity) parentContainer).getId());
			}
			parentContainer = ((Activity) parentContainer).getParentContainer();
		}
		return Optional.empty();
	}

	public static boolean isFlowElementMultiInstance(FlowElement flowElement) {
		if (flowElement instanceof Activity) {
			return ((Activity) flowElement).getLoopCharacteristics() != null;
		}
		return false;
	}

	public static String getParentExecutionIdFromParentIds(ExecutionEntity execution, Set<String> parentExecutionIds) {
		ExecutionEntity taskParentExecution = execution.getParent();
		String realParentExecutionId = null;
		while (taskParentExecution != null) {
			if (parentExecutionIds.contains(taskParentExecution.getId())) {
				realParentExecutionId = taskParentExecution.getId();
				break;
			}
			taskParentExecution = taskParentExecution.getParent();
		}
		if (realParentExecutionId == null || realParentExecutionId.length() == 0) {
			throw new FlowableException("Parent execution could not be found with executionId id " + execution.getId());
		}
		return realParentExecutionId;
	}


}
