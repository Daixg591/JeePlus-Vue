/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.echarts.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import com.jeeplus.core.query.Query;
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
@TableName("plugin_echarts")
public class Echarts extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 组件名称
     */
    @Query
    private String name;
    /**
     * 关联数据模型
     */
    private String dataSetId;
    /**
     * echarts定义结构体
     */
    private String optionContent;
    /**
     * 图表类型
     */
    @TableField("t_type")
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
