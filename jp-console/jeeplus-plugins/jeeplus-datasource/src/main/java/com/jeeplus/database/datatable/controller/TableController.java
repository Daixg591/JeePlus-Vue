package com.jeeplus.database.datatable.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.sql.SqlUtil;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.google.common.collect.Lists;
import com.jeeplus.aop.demo.annotation.DemoMode;
import com.jeeplus.common.utils.ResponseUtil;
import com.jeeplus.database.DataSourceConfig;
import com.jeeplus.database.datalink.jdbc.DBPool;
import com.jeeplus.database.datalink.service.DataSourceService;
import com.jeeplus.database.datatable.constants.FieldTypes;
import com.jeeplus.database.datatable.dto.TableDTO;
import org.apache.commons.lang3.Validate;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据模型Controller
 *
 * @author 刘高峰
 * @version 2021-08-07
 */
@RestController
@RequestMapping("/database/table")
public class TableController {

    @Autowired
    DataSourceService dataSourceService;

    @Autowired
    DynamicDataSourceProperties dynamicDataSourceProperties;

    @Autowired
    DataSourceConfig dataSourceConfig;


    /**
     * 获取全部表
     * @param dataSourceId
     * @return
     */
    @GetMapping("list")
    public ResponseEntity data(@RequestParam("dataSourceId") String dataSourceId)  {
        DataSource dataSource = DBPool.getInstance ().getDsById ( dataSourceId );
        assertDataSourceNotNull (dataSource);
        Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
        try {
            Table[] tables  = platform.getTablesWithoutColumn ();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("rows", tables);
            map.put("total", tables.length);
            return ResponseEntity.ok ( map );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取创建表的相关信息
     *
     * @return
     */
    @GetMapping("/create")
    public ResponseEntity queryCreate(@RequestParam("dataSourceId") String dataSourceId) throws Exception {
        DataSource dataSource = DBPool.getInstance ().getDsById ( dataSourceId );
        assertDataSourceNotNull (dataSource);
        Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
        Connection conn = platform.getDataSource().getConnection();
        Table table = new Table ();
        table.setCatalog ( conn.getCatalog () );
        table.setSchema ( conn.getSchema () );
        conn.close();
        return ResponseUtil.newInstance ().add ("table", table).add("columnTypes", Lists.newArrayList (FieldTypes.class.getDeclaredFields ())).ok ();
    }


    /**
     * 创建表
     * @param tableDTO
     * @return
     */
    @DemoMode
    @PostMapping("/create/do")
    public ResponseEntity doCreate(@RequestBody TableDTO tableDTO) {
        try {
            DataSource dataSource = DBPool.getInstance ().getDsById ( tableDTO.getDataSourceId () );
            assertDataSourceNotNull (dataSource);
            Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
            Table eqTable = platform.getTable (tableDTO.getName());
            if (ObjectUtil.isNotNull(eqTable)) {
                return ResponseEntity.badRequest ().body ("表 '" + tableDTO.getName() + "' 已存在！");
            }
            Table table = new Table ();
            table.setName ( tableDTO.getName () );
            table.setSchema ( tableDTO.getSchema () );
            table.setCatalog ( tableDTO.getCatalog () );
            table.setDescription ( tableDTO.getDescription () );
            for (Column column: tableDTO.getColumns ()){
                table.addColumn ( column );
            }
            platform.createTable ( table, false, false );
            return ResponseEntity.ok ("创建成功!");
        } catch (Exception e) {
            return ResponseEntity.badRequest ().body (e.getMessage());
        }
    }


    /**
     * 获取修改表的信息
     *
     * @return
     */
    @GetMapping("/alter")
    public ResponseEntity queryAlter(@RequestParam("dataSourceId") String dataSourceId, @RequestParam("name") String name) throws Exception {
        DataSource dataSource = DBPool.getInstance ().getDsById ( dataSourceId );
        assertDataSourceNotNull (dataSource);
        Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
        Table table = platform.getTable (name);
        return ResponseUtil.newInstance ().add ("table", table).add("columnTypes", Lists.newArrayList ( FieldTypes.class.getDeclaredFields ())).ok ();

    }

    /**
     * 修改表
     * @param tableDTO
     * @return
     * @throws SQLException
     */
    @DemoMode
    @PostMapping("/alter/do")
    public ResponseEntity doAlter(@RequestBody TableDTO tableDTO) throws SQLException {
            DataSource dataSource = DBPool.getInstance ().getDsById ( tableDTO.getDataSourceId () );
            assertDataSourceNotNull (dataSource);
            Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
            Table table = new Table ();
            table.setName ( tableDTO.getName () );
            table.setSchema ( tableDTO.getSchema () );
            table.setCatalog ( tableDTO.getCatalog () );
            table.setDescription ( tableDTO.getDescription () );
            for(Column column: tableDTO.getColumns ()){
                table.addColumn ( column );
            }
            Table eqTable = platform.getTable ( table.getName () );
            platform.alterTable ( eqTable, table, false );
            return ResponseEntity.ok ("修改成功");
    }

    /**
     * 删除表
     * @param dataSourceId
     * @param tableName
     * @return
     * @throws SQLException
     */
    @DemoMode
    @DeleteMapping("/drop")
    public ResponseEntity doDrop(@RequestParam("dataSourceId") String dataSourceId, String tableName) throws SQLException {
        DataSource dataSource = DBPool.getInstance ().getDsById ( dataSourceId );
        assertDataSourceNotNull (dataSource);
        Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
        platform.dropTable ( tableName );
        return ResponseEntity.ok ("删除成功");
    }


    @GetMapping("/executeSql/{name}")
    public ResponseEntity executeSql(@RequestParam("dataSourceId") String dataSourceId, @PathVariable("name") String name) throws Exception {
        DataSource dataSource =  DBPool.getInstance().getDsById (dataSourceId);
        assertDataSourceNotNull (dataSource);
        List<Entity> result = Db.use (dataSource ).findAll ( name ).stream ().map ( this::transferEntity ).collect( Collectors.toList());

        Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
        Column[] columns = platform.getTableColumnList ( name );
        return ResponseUtil.newInstance().add ("list", result).add("columns", columns).ok ();
    }

    /**
     * 格式化entity
     * @param entity
     * @return
     */
    public Entity transferEntity(Entity entity){
        for (String key : entity.keySet()) {
            Object newValue = getValueStr ( entity.get ( key ) );
            entity.put ( key, newValue );
        }
        return entity;
    }

    /**
     * 值转换
     * @param obj
     * @return
     */
    public static Object getValueStr(Object obj) {
        if(obj == null){
            return null;
        }else if ( obj instanceof Clob ) {
            return SqlUtil.clobToStr ( (Clob) obj );
        } else if ( obj instanceof Blob ) {
            return SqlUtil.blobToStr ( (Blob) obj, CharsetUtil.CHARSET_UTF_8 );
        } else if ( obj instanceof RowId ) {
            RowId rowId = (RowId) obj;
            return StrUtil.str ( rowId.getBytes ( ), CharsetUtil.CHARSET_UTF_8 );
        } else if ( obj instanceof Timestamp ) {
            long time = ((Timestamp) obj).getTime ( ) / 1000;
            return DateUtil.date ( time );

        } else if ( obj instanceof String ) {
            return obj.toString ( );
        } else {
            return obj;
        }
    }

    /**
     * 检查dataSource不为空.
     */
    private static void assertDataSourceNotNull(DataSource dataSource) {
        Validate.validState(dataSource != null, "数据库链接不存在!");
    }

}
