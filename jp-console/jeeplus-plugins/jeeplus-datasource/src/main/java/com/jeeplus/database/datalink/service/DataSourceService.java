/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.database.datalink.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.database.DataSourceConfig;
import com.jeeplus.database.datalink.domain.DataSource;
import com.jeeplus.database.datalink.mapper.DataSourceMapper;
import com.jeeplus.sys.utils.DictUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

/**
 * 数据库连接Service
 *
 * @author 刘高峰
 * @version 2021-08-06
 */
@Service
@Transactional
public class DataSourceService extends ServiceImpl <DataSourceMapper, DataSource> {

    @Autowired
    private DataSourceConfig dataSourceConfig;

    /**
     * 转换为JDBC连接
     *
     * @param type
     * @param host
     * @param port
     * @param dbName
     * @return
     */
    public String toUrl(String type, String host, String port, String dbName) {
        String template = DictUtils.getDictValue(type, "db_jdbc_url", "mysql");
        if (template != null) {
            template = HtmlUtils.htmlUnescape(template);
            return template.replace("${host}", host).replace("${port}", port + "").replace("${db}", dbName);
        }
        return null;
    }

    /**
     * 获取JDBC驱动
     *
     * @param type
     * @return
     */
    public String getDriver(String type) {

       return DictUtils.getDictValue(type, "db_driver", "mysql");

    }

    /**
     * 根据id获取数据源
     * @param id
     * @return
     */
    public DataSource getById(String id) {
        if ( StringUtils.isBlank ( id ) || dataSourceConfig.primary.equals ( id ) ) {
           return createPrimaryDataSource ();
        } else {
            return super.getById ( id );
        }

    }


    /**
     * 根据英文名获取数据源
     * @param enName
     * @return
     */
    public DataSource getByEnName(String enName) {

        if ( StrUtil.isBlank ( enName ) || dataSourceConfig.primary.equals ( enName ) ) {
            return createPrimaryDataSource ();
        } else {
            return super.lambdaQuery ().eq ( DataSource::getEnName, enName ).one ();
        }

    }

    private DataSource createPrimaryDataSource() {
        DataSource dataSource = new  DataSource (  );
        dataSource.setId ( dataSourceConfig.primary );
        dataSource.setEnName ( dataSourceConfig.primary );
        dataSource.setName ( "本地数据库" );
        return dataSource;
    }

}
