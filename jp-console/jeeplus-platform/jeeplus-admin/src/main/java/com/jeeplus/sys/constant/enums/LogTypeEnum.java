package com.jeeplus.sys.constant.enums;

/**
 * 日志类型
 */
public enum LogTypeEnum {
    LOGIN ("1", "登录日志"),
    ACCESS ("2", "访问日志"),
    EXCEPTION ("3", "异常日志");

    /**
     *  类型值
     */
    private String value;

    /**
     * 类型标签
     */
    private String label;

    private LogTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
