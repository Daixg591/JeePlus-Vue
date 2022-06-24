package com.jeeplus.sys.constant.enums;

/**
 * 组织机构类型
 */
public enum OfficeTypeEnum {
    COMPANY ("1", "公司"),
    OFFICE ("2", "部门");

    /**
     * 类型值
     */
    private String value;

    /**
     * 类型标签
     */
    private String label;

    private OfficeTypeEnum(String value, String label) {
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
