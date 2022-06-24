/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.echarts.service.dto;


import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.service.dto.BaseDTO;
import com.jeeplus.database.datamodel.service.dto.DataSetDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图表组件Entity
 *
 * @author 刘高峰
 * @version 2021-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EchartsDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 组件名称
     */
    private String name;
    /**
     * 关联数据模型
     */
    private DataSetDTO dataSet;
    /**
     * echarts定义结构体
     */
    private String option;
    /**
     * 图表类型
     */
    private String type;
    /**
     * x轴
     */
    private String xnames;
    /**
     * y轴
     */
    private String ynames;
}
