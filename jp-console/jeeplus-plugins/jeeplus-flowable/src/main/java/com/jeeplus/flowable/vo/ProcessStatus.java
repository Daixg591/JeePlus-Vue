package com.jeeplus.flowable.vo;

public enum ProcessStatus {
    SUSPENDED("已挂起", 0, "danger"),
    WAITING("等待审核", 1, "primary"),
    AGREE("审核通过", 2, "success"),
    REVOKE ("流程撤回", 3, "warning"),
    REJECT("审核驳回", 4, "danger"),
    STOP("审核终止", 5, "info"),
    DELETED("流程作废", 6, "danger");
    // 成员变量
    private String status;
    private int code;
    private String level;
    // 构造方法
    ProcessStatus(String status, int code, String level) {
        this.code = code;
        this.status = status;
        this.level = level;
    }
    // 普通方法
    public static String getStatus(int code) {
        for (ProcessStatus c : ProcessStatus.values()) {
            if (c.getCode () == code) {
                return c.status;
            }
        }
        return null;
    }

    public static ProcessStatus value(int code) {
        for (ProcessStatus c : ProcessStatus.values()) {
            if (c.getCode () == code) {
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
