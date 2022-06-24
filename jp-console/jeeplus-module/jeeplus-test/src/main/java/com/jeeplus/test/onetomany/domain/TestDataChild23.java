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
 * 汽车票Entity
 * @author liugf
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_data_child3")
public class TestDataChild23 extends BaseEntity {

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
    @TableField("starttime")
	private Date startTime;
	/**
     * 到达时间
     */
    @TableField("endtime")
	private Date endTime;
	/**
     * 代理价格
     */
	private Double price;
	/**
     * 外键
     */
    @TableField("test_data_main")
	private String testDataMainId;
	/**
     * 备注信息
     */
	private String remarks;

}
