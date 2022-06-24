/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.validation.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 测试校验功能DTO
 * @author lgf
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestValidationDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 浮点数字
     */
	@NotNull(message="浮点数字不能为空")
    @Query(type = QueryType.EQ)
	private Double num;
	/**
     * 整数
     */
	@NotNull(message="整数不能为空")
    @Query(type = QueryType.EQ)
	private Integer num2;
	/**
     * 字符串
     */
	@NotNull(message="字符串不能为空")
	@Size(min=5, max=65, message="字符串长度必须介于 5 和 65 之间")
	private String str;
	/**
     * 邮件
     */
	@NotNull(message="邮件不能为空")
	@Size(min=10, max=60, message="邮件长度必须介于 10 和 60 之间")
	private String email;
	/**
     * 网址
     */
	@NotNull(message="网址不能为空")
	@Size(min=10, max=30, message="网址长度必须介于 10 和 30 之间")
	private String url;
	/**
     * 日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="日期不能为空")
	private Date newDate;
	/**
     * 备注信息
     */
	@NotNull(message="备注信息不能为空")
	private String remarks;
	/**
     * 浮点数小于等于0
     */
	@NotNull(message="浮点数小于等于0不能为空")
	private String c1;
	/**
     * 身份证号码
     */
	@NotNull(message="身份证号码不能为空")
	private String c2;
	/**
     * QQ号码
     */
	@NotNull(message="QQ号码不能为空")
	private String c3;
	/**
     * 手机号码
     */
	@NotNull(message="手机号码不能为空")
	private String c4;
	/**
     * 中英文数字下划线
     */
	@NotNull(message="中英文数字下划线不能为空")
	private String c5;
	/**
     * 合法字符(a-z A-Z 0-9)
     */
	@NotNull(message="合法字符(a-z A-Z 0-9)不能为空")
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
