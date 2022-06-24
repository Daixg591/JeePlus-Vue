/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.one.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 请假表单Entity
 * @author 刘高峰
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_form_leave")
public class TestFormLeave extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 归属部门
     */
	private String officeId;
	/**
     * 员工
     */
	private String userId;
	/**
     * 归属区域
     */
	private String areaId;
	/**
     * 请假开始日期
     */
	private Date beginDate;
	/**
     * 请假结束日期
     */
	private Date endDate;

}
