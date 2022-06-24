/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.database.datamodel.service.dto;


import com.google.common.collect.Lists;
import com.jeeplus.core.domain.BaseEntity;
import com.jeeplus.core.query.Query;
import com.jeeplus.database.datalink.domain.DataSource;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 数据模型Entity
 *
 * @author 刘高峰
 * @version 2021-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataSetDTO extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 目标数据库
     */
    @Query(tableColumn = "dataSource.name", javaField = "dataSource.name")
    private DataSource dataSource;
    /**
     * 数据源名称
     */
    @Query(tableColumn = "a.name")
    private String name;
    /**
     * sql语句
     */
    private String sqlCmd;
    /**
     * 关联数据集
     */
    private List<DataMetaDTO> columnList = Lists.newArrayList();
    /**
     * 关联参数
     */
    private List<DataParamDTO> params = Lists.newArrayList();
}
