/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 火车票Entity
 * @author liugf
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_data_child1")
public class TestDataChild21 extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 出发地
     */
    @TableField("startarea")
	private String startAreaId;
	/**
     * 目的地
     */
    @TableField("endarea")
	private String endAreaId;
	/**
     * 出发时间
     */
	private Date starttime;
	/**
     * 到达时间
     */
	private Date endtime;
	/**
     * 代理价格
     */
	private Double price;
	/**
     * 业务主表ID
     */
	private String testDataMainId;
	/**
     * 备注信息
     */
	private String remarks;

}
