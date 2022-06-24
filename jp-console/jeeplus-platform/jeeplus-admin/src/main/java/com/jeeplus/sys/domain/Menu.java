/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单Entity
 *
 * @author jeeplus
 * @version 2021-05-15
 */

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_menu")
public class Menu extends TreeEntity<Menu> {

    private static final long serialVersionUID = 1L;

    /**
     * 链接
     */
    private String href;

    /**
     * 目标（_frame、_self）
     */
    private String target;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否在菜单中显示（1：显示；0：不显示）
     */
    private String isShow;

    /**
     * 按钮类型
     */
    @TableField("menu_type")
    private String type;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 是否固定在标签栏
     */
    private String affix;

    /**
     * 备注
     */
    private String remarks;
}
