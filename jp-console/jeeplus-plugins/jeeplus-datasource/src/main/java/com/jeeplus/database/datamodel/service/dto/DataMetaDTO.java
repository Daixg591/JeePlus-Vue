/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.database.datamodel.service.dto;


import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据资源Entity
 *
 * @author 刘高峰
 * @version 2021-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataMetaDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 字段名
     */
    private String name;
    /**
     * 字段描述
     */
    private String label;
    /**
     * 字段类型
     */
    private String type;
    /**
     * 是否需要分析该字段
     */
    private Boolean isNeed;
    /**
     * 数据模型
     */
    private DataSetDTO dataSetDTO;
    /**
     *  排序
     */
    private int sort;
}
