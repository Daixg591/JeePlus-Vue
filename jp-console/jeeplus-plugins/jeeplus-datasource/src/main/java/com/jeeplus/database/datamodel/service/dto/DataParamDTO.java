/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.database.datamodel.service.dto;

import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据模型参数Entity
 *
 * @author 刘高峰
 * @version 2021-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataParamDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 数据集
     */
    private DataSetDTO dataSetDTO;
    /**
     * 字段
     */
    private String field;
    /**
     * 映射关系
     */
    private String defaultValue;
    /**
     * 排序
     */
    private int sort;
}
