/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置Entity
 *
 * @author 刘高峰
 * @version 2021-10-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_config")
public class SysConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /*
        邮箱配置信息
     */
    private String smtp;        // 邮箱服务器地址
    private String port;        // 邮箱服务器端口
    private String mailName;        // 系统邮箱地址
    private String mailPassword;        // 系统邮箱密码
    /*
        阿里大鱼配置信息
     */
    private String accessKeyId;// 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找);
    private String accessKeySecret; // 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    private String signature; //必填:短信签名-可在短信控制台中找到
    private String templateCode;//必填:短信模板-可在短信控制台中找到-->
    /*
       外观配置
     */
    private String defaultTheme;//默认主题
    private String defaultLayout;
    private String productName;//产品名称
    private String logo;//产品logo;


    /**
     * 首页配置
     */
    private String homeUrl;

    /*
      登录配置
     */
    private String multiAccountLogin;//允许多登录1，不允许0
    private String singleLoginType; //后登陆踢出先登录1，已经登陆禁止再登陆2.

    /**
     * 备注
     */
    private String remarks;

}
