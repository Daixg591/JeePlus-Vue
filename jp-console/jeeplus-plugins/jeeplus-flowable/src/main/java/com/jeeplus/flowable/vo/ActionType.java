package com.jeeplus.flowable.vo;

public enum ActionType {
    BEGIN("开始", "_flow_start", "primary"),
    END("结束", "_flow_end", "primary"),
    SAVE("暂存", "_flow_save", "primary"),
    AGREE("同意", "_flow_agree", "success"),
    REJECT("驳回", "_flow_reject", "danger"),
    BACK("指定回退", "_flow_back", "danger"),
    ADD_MULTI_INSTANCE("加签", "_flow_add_multi_instance", "primary"),
    DEL_MULTI_INSTANCE("减签", "_flow_add_multi_instance", "primary"),
    TRANSFER("转办", "_flow_transfer", "primary"),
    DELEGATE("委派", "_flow_delegate", "primary"),
    STOP("终止", "_flow_stop", "info"),
    PRINT("打印", "_flow_print", "primary"),
    ADD_BEFORE_MULTI_INSTANCE("前加签", "_flow_before_add_multi_instance", "primary"),
    ADD_AFTER_MULTI_INSTANCE("后加签", "_flow_after_add_multi_instance", "primary"),
    COMMIT("提交", "_flow_commit", "success"),
    Audit("审批", "_flow_audit", "success"),
    WAITING("等待审核", "_flow_waiting", "primary"),
    RECOMMIT("重新提交", "_flow_recommit", "warning"),
    DELETED("作废流程", "_flow_delete", "danger"),
    REVOKE("撤销流程", "_flow_revoke", "warning");
    // 成员变量
    private String status; // 评论描述
    private String type; //编码
    private String level; //描述级别
    // 构造方法
    private ActionType(String status, String type, String level) {
        this.type = type;
        this.status = status;
        this.level = level;
    }
    // 普通方法
    public static String getStatus(String type) {
        for (ActionType c : ActionType.values()) {
            if (c.getType ().equals (type)) {
                return c.status;
            }
        }
        return null;
    }

    // 普通方法
    public static String getLevel(String type) {
        for (ActionType c : ActionType.values()) {
            if (c.getType ().equals (type)) {
                return c.level;
            }
        }
        return "primary"; //如果不存在返回 primary
    }

    public static ActionType value(String type) {
        for (ActionType c : ActionType.values()) {
            if (c.getType ().equals (type)) {
                return c;
            }
        }
        return null;
    }
    // get set 方法
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
