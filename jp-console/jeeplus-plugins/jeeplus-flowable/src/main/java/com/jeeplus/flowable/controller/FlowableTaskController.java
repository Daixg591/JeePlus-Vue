/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.flowable.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.extension.domain.NodeSetting;
import com.jeeplus.extension.service.NodeSettingService;
import com.jeeplus.flowable.model.Flow;
import com.jeeplus.flowable.model.TaskComment;
import com.jeeplus.flowable.service.FlowTaskService;
import com.jeeplus.flowable.vo.HisTaskVo;
import com.jeeplus.flowable.vo.ProcessVo;
import com.jeeplus.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 流程个人任务相关Controller
 *
 * @author jeeplus
 * @version 2021-09-03
 */
@RestController
@RequestMapping("/flowable/task")
public class FlowableTaskController {

    @Autowired
    private FlowTaskService flowTaskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RuntimeService runtimeService;


    @Autowired
    private TaskService taskService;

    @Autowired
    private NodeSettingService nodeSettingService;



    @GetMapping("todo")
    public ResponseEntity todoListData(Page <ProcessVo> page, Flow flow) {
        page = flowTaskService.todoList(page, flow);
        return ResponseEntity.ok (page);
    }

    /**
     * 获取已办任务
     *
     * @return
     */
    @GetMapping("historic")
    public ResponseEntity historicListData(Page<HisTaskVo> page, Flow flow) {
        page = flowTaskService.historicList(page, flow);
        return ResponseEntity.ok ( page );
    }

    /**
     * 获取我的申请列表
     *
     * @return
     */
    @GetMapping("myApplyed")
    public ResponseEntity myApplyedListData( Page<ProcessVo> page, Flow flow) throws Exception {
        page = flowTaskService.getMyStartedProcIns( UserUtils.getCurrentUserDTO (), page, flow);
        return ResponseEntity.ok ( page );
    }


    /**
     * 获取任务流转历史列表
     *
     */
    @GetMapping("historicTaskList")
    public ResponseEntity historicTaskList(Flow flow) throws Exception {
        List<Flow> historicTaskList = flowTaskService.historicTaskList (flow.getProcInsId());
        return ResponseEntity.ok ( historicTaskList );
    }


    /**
     * 获取流程表单
     */
    @GetMapping("getTaskDef")
    public ResponseEntity getTaskDef(Flow flow) {
        // 获取流程XML上的表单KEY
        String formKey = flowTaskService.getFormKey(flow.getProcDefId(), flow.getTaskDefKey());

        NodeSetting typeNode = nodeSettingService.queryByKey (flow.getProcDefKey(), flow.getTaskDefKey(), "formType");
        NodeSetting ReadOnlyNode = nodeSettingService.queryByKey (flow.getProcDefKey(), flow.getTaskDefKey(), "formReadOnly");
        String formType = "1";
        boolean formReadOnly = false;
        if(typeNode != null){
           formType = typeNode.getValue ();
           formReadOnly = "true".equals(ReadOnlyNode.getValue ());
        }else{
           if(StringUtils.isBlank(formKey)){
               formType = "1";
           }else if(formKey.indexOf("/")>=0 || formKey.length() != 32){
               formType = "2";
           }
        }

        // 获取流程实例对象
        if (flow.getProcInsId() != null) {
            if (flowTaskService.getProcIns(flow.getProcInsId()) != null) {
                flow.setProcIns(flowTaskService.getProcIns(flow.getProcInsId()));
            } else {
                flow.setFinishedProcIns(flowTaskService.getFinishedProcIns(flow.getProcInsId()));
            }
        }

        flow.setFormUrl(formKey);
        flow.setFormReadOnly(formReadOnly);
        flow.setFormType(formType);
        return ResponseEntity.ok (flow);
    }



    /**
     * 启动流程
     */
    @PostMapping("start")
    public ResponseEntity start(@RequestBody  Flow flow)  {
        String procInsId = flowTaskService.startProcess(flow.getProcDefKey(), flow.getBusinessTable(), flow.getBusinessId(), flow.getTitle());

        //指定下一步处理人
        if(StringUtils.isNotBlank(flow.getAssignee ())){
            Task task = taskService.createTaskQuery().processInstanceId(procInsId).active().singleResult();
            if(task != null){
                taskService.setAssignee(task.getId(), flow.getAssignee ());
            }
        }
        return ResponseEntity.ok (procInsId);
    }

    /**
     * 签收任务
     */
    @PostMapping("claim")
    public ResponseEntity claim(@RequestBody Flow flow) {
        String userId = UserUtils.getCurrentUserDTO ().getId();//ObjectUtils.toString(UserUtils.getUser().getId());
        flowTaskService.claim(flow.getTaskId(), userId);
        return ResponseEntity.ok ("签收成功!");
    }

    /**
     * 完成任务
     */
    @PostMapping( value = "complete")
    public ResponseEntity complete(@RequestBody Flow flow) {
        flowTaskService.complete(flow, flow.getVars().getVariableMap());
        return ResponseEntity.ok ("完成任务!");
    }

    /**
     * 读取流程历史数据，用于渲染流程图
     */
    @GetMapping("getFlowChart")
    public Map getFlowChart(String processInstanceId) throws Exception {
        return flowTaskService.getDiagram(processInstanceId);
    }

    /**
     * 删除任务
     *
     * @param taskId 流程实例ID
     * @param reason 删除原因
     */
    @DeleteMapping("deleteTask")
    public ResponseEntity deleteTask(String taskId, String reason) {
        if (StringUtils.isBlank(reason)) {
            return ResponseEntity.badRequest ().body ("请填写删除原因");
        } else {
            flowTaskService.deleteTask(taskId, reason);
            return ResponseEntity.ok ("删除任务成功，任务ID=" + taskId);
        }
    }

    /**
     * 加签
     */
    @PostMapping("addSignTask")
    public ResponseEntity addSignTask(String taskId, String userIds, String comment, Boolean flag) throws Exception {
        flowTaskService.addSignTask (taskId, Arrays.asList (userIds.split (",")), comment, flag);
        return ResponseEntity.ok ("加签成功!");
    };


    /**
     * 审批
     *
     * @param flow
     */
    @PostMapping("audit")
    public ResponseEntity auditTask(HttpServletRequest request, Flow flow) {
        Map<String, Object> vars = Maps.newHashMap();
        Map<String, String[]> map = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            if(entry.getKey().startsWith("vars.")){
                String key = entry.getKey().substring(5);
                String value = entry.getValue()[0];
                if("true".equals(value) || "false".equals(value)){
                    vars.put(key, Boolean.valueOf(value).booleanValue());
                }else{
                    vars.put(key, value);
                }
            }
        }

        flowTaskService.auditSave(flow, vars);
        //指定下一步处理人
        if(StringUtils.isNotBlank(flow.getAssignee ())){
           Task task = taskService.createTaskQuery().processInstanceId(flow.getProcInsId()).active().singleResult();
           if(task != null){
               taskService.setAssignee(task.getId(), flow.getAssignee ());
           }
        }
        return ResponseEntity.ok ("procInsId");
    }

    /**
     * 取回已经执行的任务，只有在下一任务节点未执行或者未签收时才能取回
     */
    @PutMapping("callback")
    public ResponseEntity callback(@Param("preTaskId") String preTaskId,
                             @Param("currentTaskId") String currentTaskId,
                             @Param("processInstanceId") String processInstanceId,
                             @Param("preTaskDefKey") String preTaskDefKey,
                             @Param("currentTaskDefKey") String currentTaskDefKey) {
        try {
            // 取得流程实例
            ProcessInstance instance = runtimeService
                    .createProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();
            if (instance == null) {
                return ResponseEntity.badRequest ().body ("流程已经结束");
            }

            //在已办任务列表中清除该任务信息
            historyService.deleteHistoricTaskInstance(preTaskId);

            List currTasks = Lists.newArrayList();
            currTasks.add(currentTaskDefKey);
            //回退到上一节点
            runtimeService.createChangeActivityStateBuilder()
                    .processInstanceId(instance.getId())
                    .moveActivityIdsToSingleActivityId(currTasks, preTaskDefKey).changeState();
            historyService.deleteHistoricTaskInstance(currentTaskId);
            return ResponseEntity.ok ("取回成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest ().body ("流程取回失败，未知错误.");
        }
    }



    /**
     * 委托任务
     *
     * @param taskId 任务ID
     */
    @PutMapping("delegate")
    public ResponseEntity delegate(String taskId, String userId) {
        if (StringUtils.isBlank(taskId) || StringUtils.isBlank(userId)) {
            return ResponseEntity.badRequest ().body ("参数异常");
        }
        taskService.setOwner (taskId, UserUtils.getCurrentUserDTO ().getId ());// 委托人为任务的拥有者
        taskService.delegateTask(taskId, userId);
        return ResponseEntity.ok ("委托成功");
    }

    /**
     * 取消签收任务
     *
     * @param taskId 任务ID
     */
    @PutMapping("unclaim")
    public ResponseEntity unclaim(String taskId) {
        taskService.unclaim(taskId);
        return ResponseEntity.ok ("取消签收成功");
    }

    /**
     * 转派任务
     *
     * @param taskId   任务ID
     * @param userId 接收人
     */
    @PutMapping("transfer")
    public ResponseEntity transferTask(String taskId, String userId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(taskId)) {
            return ResponseEntity.badRequest ().body ("转派失败, 参数异常");
        }
        // 设置当前流程任务办理人
        Authentication.setAuthenticatedUserId(UserUtils.getCurrentUserDTO ().getId());
        taskService.setAssignee(taskId, userId);
        return ResponseEntity.ok ("转派成功!");
    }

    /**
     * 获取可退回任务节点
     * @param taskId
     * @return
     */
    @PutMapping(value = "/backNodes")
    public ResponseEntity backNodes(@RequestParam String taskId) {
        List<Flow> nodes = flowTaskService.getBackNodes(taskId);
        return ResponseEntity.ok (nodes);
    }

    /**
     * 驳回任务到指定节点
     */
    @PutMapping(value = "/back")
    public ResponseEntity back(String backTaskDefKey, String taskId, TaskComment comment) {
        flowTaskService.backTask(backTaskDefKey, taskId, comment);
        return ResponseEntity.ok ("驳回成功!");
    }


}
