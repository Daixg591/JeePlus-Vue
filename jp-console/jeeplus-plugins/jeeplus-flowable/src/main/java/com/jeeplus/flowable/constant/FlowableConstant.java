package com.jeeplus.flowable.constant;

public interface FlowableConstant {
    /**
     * 约定的发起者节点id 前缀
     */
    public final static String INITIATOR = "applyUserId";// 流程发起人变量
    public final static String USERNAME = "userName"; //流程执行人
    public final static String TITLE = "title"; //流程标题
    public final static String SPECIAL_GATEWAY_BEGIN_SUFFIX = "_begin";
    public final static String SPECIAL_GATEWAY_END_SUFFIX = "_end";
    public final static String START_EVENT_LABEL = "开始";
    public final static String START_EVENT_COMMENT = "发起流程";
    public final static String END_EVENT_LABEL = "结束";
    public final static String END_EVENT_COMMENT= "结束流程";
    public final static String WAITING_EVENT_COMMENT= "等待审核";
    public final static String SYSTEM_EVENT_COMMENT= "系统执行";
    public final static String FLOW_ACTION = "_FLOW_ACTION_";
    public final static String PROCESS_STATUS_CODE = "_process_status_code"; //流程状态码
    public final static String PROCESS_STATUS_COMMENT= "_process_status_comment"; //流程状态描述
    public static final String AFTER_ADDSIGN = "after";    //后加签
    public static final String BEFORE_ADDSIGN = "before";    //前加签

}
