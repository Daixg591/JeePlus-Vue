/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.validation.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 测试校验功能Entity
 * @author lgf
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_validation")
public class TestValidation extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 浮点数字
     */
	private Double num;
	/**
     * 整数
     */
	private Integer num2;
	/**
     * 字符串
     */
	private String str;
	/**
     * 邮件
     */
	private String email;
	/**
     * 网址
     */
	private String url;
	/**
     * 日期
     */
	private Date newDate;
	/**
     * 备注信息
     */
	private String remarks;
	/**
     * 浮点数小于等于0
     */
	private String c1;
	/**
     * 身份证号码
     */
	private String c2;
	/**
     * QQ号码
     */
	private String c3;
	/**
     * 手机号码
     */
	private String c4;
	/**
     * 中英文数字下划线
     */
	private String c5;
	/**
     * 合法字符(a-z A-Z 0-9)
     */
	private String c6;
	/**
     * 英语
     */
	private String en;
	/**
     * 汉字
     */
	private String zn;
	/**
     * 汉英字符
     */
	private String enzn;

}
