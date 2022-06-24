/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.database.datalink.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import com.jeeplus.core.query.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据库连接Entity
 *
 * @author 刘高峰
 * @version 2021-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("plugin_datasource_link")
public class DataSource extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 数据库名称
     */
    @Query
    private String name;
    /**
     * 数据库英文名
     */
    private String enName;
    /**
     * 数据库类型
     */
    private String type;
    /**
     * 数据库驱动
     */
    private String driver;
    /**
     * 主机地址
     */
    private String host;
    /**
     * 端口
     */
    private String port;
    /**
     * 数据库名
     */
    private String databaseName;
    /**
     * 数据库连接
     */
    private String url;
    /**
     * 数据库用户名
     */
    private String username;
    /**
     * 数据库密码
     */
    private String password;
    /**
     * 调用次数
     */
    private String invokes;

}
