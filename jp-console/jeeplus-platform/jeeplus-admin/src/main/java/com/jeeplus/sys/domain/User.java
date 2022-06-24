/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import com.jeeplus.core.query.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户Entity
 *
 * @author jeeplus
 * @version 2021-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 公司Id
     */
    private String companyId;

    /**
     * 部门Id
     */
    private String officeId;

    /**
     * 登录名
     */
    @Query
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 工号
     */
    private String no;

    /**
     * 姓名
     */
    @Query
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录日期
     */
    private Date loginDate;

    /**
     * 是否允许登陆
     */
    private String loginFlag;

    /**
     * 头像
     */
    private String photo;

    /**
     * 二维码
     */
    private String qrCode;

    /**
     * 签名
     */
    private String sign;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 超级管理员标志
     */
    private Boolean isAdmin;

    /**
     * 构造函数
     */
    public User (String id) {
      super(id);
    }


}
