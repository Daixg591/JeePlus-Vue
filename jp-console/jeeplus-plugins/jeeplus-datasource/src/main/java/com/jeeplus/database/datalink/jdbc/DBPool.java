package com.jeeplus.database.datalink.jdbc;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.jeeplus.database.DataSourceConfig;
import com.jeeplus.database.datalink.service.DataSourceService;
import com.jeeplus.database.datalink.service.dto.DataSourceDTO;
import com.jeeplus.database.datalink.service.mapstruct.DataSourceWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 数据库池
 * 存放外部数据库链接
 */
@Slf4j
@Component
@Order(1)
public class DBPool {


    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private DataSourceConfig dataSourceConfig;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private DefaultDataSourceCreator dataSourceCreator;

    public static DBPool getInstance() {
        return SpringUtil.getBean ( DBPool.class );
    }

    /**
     * 测试配置文件是否可用
     *
     * @param driver   数据库驱动
     * @param url      数据库链接
     * @param username 数据库用户
     * @param password 数据库密码
     * @return
     */
    public boolean test(String driver, String url, String username, String password) {

        try {
            BasicDataSource basicDataSource = new BasicDataSource ( );
            basicDataSource.setDriverClassName ( driver );
            basicDataSource.setUrl ( url );
            basicDataSource.setUsername ( username );
            basicDataSource.setPassword ( password );
            JdbcTemplate jdbcTemplate = new JdbcTemplate ( basicDataSource );
            if ( driver.toLowerCase ( ).contains ( "oracle" ) ) {
                jdbcTemplate.queryForList ( "select 1 from dual" );
            } else {
                jdbcTemplate.queryForList ( "select 1" );
            }

        } catch (Exception e) {
            log.error ( "{}", e );
            return false;
        }

        return true;
    }


    /**
     * 添加数据源
     *
     * @param dto
     * @return
     */
    public DataSource addDs(DataSourceDTO dto) {
       if(this.test ( dto.getDriverClassName (), dto.getUrl (), dto.getUsername (), dto.getPassword () )) { //数据库链接是否可用
           DataSourceProperty dataSourceProperty = new DataSourceProperty ( );
           BeanUtils.copyProperties ( dto, dataSourceProperty );
           DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
           DataSource dataSource = dataSourceCreator.createDataSource ( dataSourceProperty );
           ds.addDataSource ( dto.getPollName ( ), dataSource );
           return dataSource;
       }else{
           log.error ("加载数据库链接【" + dto.getPollName () + "】连接失败");
           return null;
       }
    }

    /**
     * 删除数据源
     *
     * @param enName
     * @return
     */
    public String remove(String enName) {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        ds.removeDataSource ( enName );
        return "删除成功";
    }

    /**
     * 获取全部数据源
     * @return
     */
    public Map <String, DataSource> getAllDs() {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        return ds.getCurrentDataSources ();
    }


    /**
     * 获取数据源
     *
     * @param enName
     * @return
     */
    public DataSource getDsByEnName(String enName) {
        if ( StrUtil.isBlank ( enName ) ) {
            enName = dataSourceConfig.primary;
        }
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        DataSource dataSource = ds.getCurrentDataSources ( ).get ( enName );
        if ( dataSource == null ) {
            dataSource = reloadDataSource ( enName);
        }
        return dataSource;
    }

    /**
     * 获取数据源
     *
     * @param dataSourceId
     * @return
     */
    public DataSource getDsById(String dataSourceId) {
        String enName = dataSourceService.getById ( dataSourceId ).getEnName ( );
        return getDsByEnName ( enName );
    }

    /**
     * 获取jdbcTemplate
     *
     * @param enName
     * @return
     */
    public JdbcTemplate getJdbcTemplateByEnName(String enName) {
        DataSource dataSource = getDsByEnName ( enName );
        if(dataSource == null){
            return null;
        }
        JdbcTemplate jdbcTemplate = new JdbcTemplate ( dataSource );
        return jdbcTemplate;
    }

    /**
     * 获取jdbcTemplate
     *
     * @param dataSourceId
     * @return
     */
    public JdbcTemplate getJdbcTemplateByDsId(String dataSourceId) {
        String enName = dataSourceService.getById ( dataSourceId ).getEnName ( );
        return getJdbcTemplateByEnName ( enName );
    }


    /**
     * 重新加载数据源
     *
     * @param enName
     * @return
     */
    public DataSource reloadDataSource(String enName) {
        if ( dataSourceService.getByEnName ( enName ) == null ) {
            return null;
        }
        return addDs ( DataSourceWrapper.INSTANCE.toDTO ( dataSourceService.getByEnName ( enName ) ) );
    }


}
