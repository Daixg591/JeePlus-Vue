package com.jeeplus.flowable.vo;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ProcessVo {
    private String processInstanceId; // 流程实例ID
    private String processDefinitionId; // 流程定义ID
    private String processDefinitionName; // 流程名称
    private String activityId;
    private int version; // 流程版本
    private Map vars; // 流程变量
    private Date startTime; // 流程开始时间
    private Date endTime; //流程结束时间
    private String taskName; //流程当前节点名称
    private String deleteReason; //流程作废原因
    private HisTaskVo hisTask; // 历史流程节点
    private TaskVo task; //流程当前节点


    private int code; // 流程状态码
    private String status; //流程状态
    private String level; //状态级别，用于控制在前台显示的颜色

    public String getId(){ // 流程实例id 作为列表的id
        return processInstanceId;
    }

    public void setProcessStatus(ProcessStatus processStatus){
        this.code = processStatus.getCode ();
        this.status = processStatus.getStatus ();
        this.level = processStatus.getLevel ();
    }
}
