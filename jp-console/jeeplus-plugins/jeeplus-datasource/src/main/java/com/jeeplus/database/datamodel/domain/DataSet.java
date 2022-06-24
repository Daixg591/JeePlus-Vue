/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.database.datamodel.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据模型Entity
 *
 * @author 刘高峰
 * @version 2021-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("plugin_datasource_model")
public class DataSet extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 目标数据库
     */
    private String dataSourceId;
    /**
     * 数据源名称
     */
    private String name;
    /**
     * sql语句
     */
    private String sqlCmd;
}
