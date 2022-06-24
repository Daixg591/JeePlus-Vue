package com.jeeplus.flowable.vo;

import lombok.Data;
import org.flowable.task.api.history.HistoricTaskInstance;

import java.util.Date;
import java.util.Map;

/*
    历史任务节点
 */
@Data
public class HisTaskVo {
    private String id;
    private String name;
    private String assignee;
    private String executionId;
    private String taskDefinitionKey;
    private Date createTime;
    private Date endTime;
    private String processDefinitionId;
    private String processInstanceId;
    private String processDefinitionName; // 流程名称
    private boolean isBack; // 流程是否可以撤回到该节点
    private String code; //任务办理状态：1，2
    private String comment; //任务评论
    private String type; // 操作类型编码
    private String status; // 任务办理描述： 同意，驳回
    private String level; // 文字颜色
    private Map vars;

    private TaskVo currentTask; // 当前流程节点

    public HisTaskVo(HistoricTaskInstance task){
        this.id = task.getId ();
        this.name = task.getName ();
        this.assignee = task.getAssignee ();
        this.executionId = task.getExecutionId ();
        this.taskDefinitionKey = task.getTaskDefinitionKey ();
        this.createTime = task.getCreateTime ();
        this.endTime = task.getEndTime ();
        this.executionId = task.getExecutionId ();
        this.processDefinitionId = task.getProcessDefinitionId ();
        this.vars = task.getProcessVariables ();
        this.processInstanceId = task.getProcessInstanceId ();
    }

}
