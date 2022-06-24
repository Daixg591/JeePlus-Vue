package com.jeeplus.flowable.vo;

import lombok.Data;
import org.flowable.task.api.TaskInfo;

import java.util.Date;

/**
 * 当前活动任务节点
 */
@Data
public class TaskVo {
    private String id;
    private String name;
    private String assignee;
    private String executionId;
    private String taskDefinitionKey;
    private Date createTime;
    private String processDefinitionId;
    private String processInstanceId;
    private String processDefKey;


    public TaskVo(TaskInfo task){
        this.id = task.getId ();
        this.name = task.getName ();
        this.assignee = task.getAssignee ();
        this.executionId = task.getExecutionId ();
        this.taskDefinitionKey = task.getTaskDefinitionKey ();
        this.createTime = task.getCreateTime ();
        this.processDefinitionId = task.getProcessDefinitionId ();
        this.processInstanceId = task.getProcessInstanceId ();
    }
}
