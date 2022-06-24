/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service.dto;

import com.jeeplus.sys.service.dto.AreaDTO;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 火车票Entity
 * @author liugf
 * @version 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestDataChild21DTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 出发地
     */
	@NotNull(message="出发地不能为空")
	private AreaDTO startArea;
	/**
     * 目的地
     */
	@NotNull(message="目的地不能为空")
	private AreaDTO endArea;
	/**
     * 出发时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="出发时间不能为空")
	private Date starttime;
	/**
     * 到达时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="到达时间不能为空")
	private Date endtime;
	/**
     * 代理价格
     */
	@NotNull(message="代理价格不能为空")
	private Double price;
	/**
     * 业务主表ID
     */
	@NotNull(message="业务主表ID不能为空")
	private TestDataMainFormDTO testDataMainDTO;
	/**
     * 备注信息
     */
	private String remarks;

}
