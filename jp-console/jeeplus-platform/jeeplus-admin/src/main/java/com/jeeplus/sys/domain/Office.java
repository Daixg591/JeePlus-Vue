/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机构Entity
 *
 * @author jeeplus
 * @version 2021-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_office")
public class Office extends TreeEntity<Office> {

    private static final long serialVersionUID = 1L;

    /**
     * 归属区域
     */
    private String areaId;

    /**
     * 机构编码
     */
    private String code;

    /**
     * 机构类型（1：公司；2：部门）
     */
    private String type;

    /**
     * 机构等级（1：一级；2：二级；3：三级；4：四级）
     */
    private String grade;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 邮政编码
     */
    private String zipCode;

    /**
     * 负责人
     */
    private String master;

    /**
     * 电话
     */
    private String phone;

    /**
     * 传真
     */
    private String fax;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否可用
     */
    private String useable;

    /**
     * 主负责人
     */
    private String primaryPerson;

    /**
     * 副负责人
     */
    private String deputyPerson;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 构造函数
     */
    public Office () {
        super();
    }

    /**
     * 构造函数
     */
    public Office (String id) {
        super(id);
    }

}
