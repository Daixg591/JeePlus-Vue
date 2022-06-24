package com.jeeplus.sys.constant.enums;

/**
 * 菜单类型
 *
 * @author jeeplus
 * @version 2021-5-15
 */
public enum MenuTypeEnum {
    FOLDER ("1", "目录"),
    MENU ("2", "菜单"),
    BUTTON ("3", "按钮"),
    ROUTER("4", "路由");

    /**
     * 类型值
     */
    private String value;

    /**
     * 类型标签
     */
    private String label;

    MenuTypeEnum(String value, String label) {
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
