package com.jeeplus.form.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.Session;
import cn.hutool.db.sql.SqlUtil;
import com.jeeplus.form.constant.TableColumn;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.apache.ddlutils.model.Column;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

/**
 * DML操作工具类
 */
@Slf4j
public class DMLBuilder {
    private String tableName = "";
    private DataSource dataSource;

    /**
     * 构建DML工具
     *
     *  @param dataSource
     * @param tableName
     */
    public DMLBuilder(DataSource dataSource, String tableName) {
        this.dataSource = dataSource;
        this.tableName = tableName;
    }


    /**
     * 插入数据
     *
     * @param tableName
     * @param data
     * @param map
     * @return
     * @throws SQLException
     */
    public String executeInsert(String tableName, Map data, Map map) throws SQLException {

        Entity entity = Entity.create ( tableName );
        //保存表单数据
        String uuid = UUID.randomUUID ( ).toString ( );
        entity.set ( TableColumn.ID, uuid );
        //遍历参数，组装sql语句
        for (Object name : data.keySet ( )) {
            if ( map.get ( name ) != null ) {
                Object value = data.get ( name );
                if ( value instanceof JSONArray ) {
                    value = ((JSONArray) value).toString ( );
                }
                entity.set ( name.toString ( ), value );
            }
        }
        UserDTO user = UserUtils.getCurrentUserDTO ( );
        if ( StrUtil.isNotBlank ( user.getId ( ) ) ) {
            if ( this.getColumns ( ).contains ( TableColumn.CREATE_BY ) && map.get ( TableColumn.CREATE_BY ) == null ) {
                entity.set ( TableColumn.CREATE_BY, user.getId ( ) );
            }
            if ( this.getColumns ( ).contains ( TableColumn.UPDATE_BY ) && map.get ( TableColumn.UPDATE_BY ) == null ) {
                entity.set ( TableColumn.UPDATE_BY, user.getId ( ) );
            }
        }
        if ( this.getColumns ( ).contains ( TableColumn.CREATE_DATE ) && map.get ( TableColumn.CREATE_DATE ) == null ) {
            entity.set ( TableColumn.CREATE_DATE, formatDate ( new Date ( ) ) );
        }
        if ( this.getColumns ( ).contains ( TableColumn.UPDATE_DATE ) && map.get ( TableColumn.UPDATE_DATE ) == null ) {
            entity.set ( TableColumn.UPDATE_DATE, formatDate ( new Date ( ) ) );
        }
        if ( this.getColumns ( ).contains ( TableColumn.DEL_FLAG ) && map.get ( TableColumn.DEL_FLAG ) == null ) {
            entity.set ( TableColumn.DEL_FLAG, 0 );
        }
        Session session = Session.create ( dataSource );
        try {

            session.beginTransaction ( );
            session.insert ( entity );
            session.commit ( );
        } catch (SQLException e) {
            log.error ( "{}", e );
            session.quietRollback ( );
            throw e;
        } finally {
            session.close ();
        }
        return uuid;
    }


    /**
     * 删除数据
     *
     * @param tableName
     * @param ids
     * @throws SQLException
     */
    public void executeDelete(String tableName, String ids) throws SQLException {
        for (String id : ids.split ( "," )) {
            Db.use ( dataSource ).del ( tableName, TableColumn.ID, id );
        }

    }

    /**
     * 根据外键关联字段删除数据
     *
     * @param tableName
     * @param ids
     * @param foreignKey
     * @throws SQLException
     */
    public void executeDeleteByForeignKey(String tableName, String ids, String foreignKey) throws SQLException {
        for (String id : ids.split ( "," )) {
            Db.use ( dataSource ).del ( tableName, foreignKey, id );
        }
    }


    /**
     * 根据外键查询数据
     *
     * @param tableName
     * @param foreignKey
     * @param foreignValue
     * @return
     * @throws SQLException
     */
    public int executeQueryCountByForeignKey(String tableName, String foreignKey, String foreignValue) throws SQLException {
        int count = Db.use ( dataSource ).count ( Entity.create ( tableName ).set ( foreignKey, foreignValue ) );
        return count;
    }

    /**
     * 根据id 查询数据
     *
     * @param tableName
     * @param id
     * @return
     * @throws SQLException
     */
    public Entity executeQueryById(String tableName, String id) throws SQLException {
        Entity entity = Db.use ( dataSource ).get ( Entity.create ( tableName ).set ( TableColumn.ID, id ) );
        return transferEntity ( entity );
    }


    /** 查询列表
     * @param sql
     * @return
     * @throws SQLException
     */
    public List <Entity> executeQueryList(String sql) throws SQLException {
        List <Entity> list = Db.use ( dataSource ).query ( sql );
        return list;
    }

    /** 查询实体
     * @param sql
     * @return
     * @throws SQLException
     */
    public Entity executeQueryOne(String sql) throws SQLException {
        return Db.use ( dataSource ).queryOne ( sql );
    }

    /**
     * 更新表单
     *
     * @param data
     * @return
     */
    public void executeUpdate(String tableName, Map data, Map map) throws SQLException {
        Session session = Session.create ( dataSource );
        Entity entity = Entity.create ( tableName );
        Entity where = Entity.create ( tableName );
        //保存表单数据
        boolean needUpdate = false;
        for (Object name : data.keySet ( )) {
            if ( name.equals ( TableColumn.ID ) || map.get ( name ) == null )
                continue;
            Object value = data.get ( name );
            if ( value instanceof JSONArray ) {
                value = ((JSONArray) value).toString ( );
            }
            entity.set ( name.toString ( ), value );
            needUpdate = true;
        }
        UserDTO user = UserUtils.getCurrentUserDTO ( );
        if ( this.getColumns ( ).contains ( TableColumn.UPDATE_BY ) && StrUtil.isNotBlank ( user.getId ( ) ) && map.get ( TableColumn.UPDATE_BY ) == null ) {
            entity.set ( TableColumn.UPDATE_BY, user.getId ( ) );
        }
        if ( this.getColumns ( ).contains ( TableColumn.UPDATE_DATE ) && map.get ( TableColumn.UPDATE_DATE ) == null ) {
            entity.set ( TableColumn.UPDATE_DATE, formatDate ( new Date ( ) ) );

        }

        where.set ( TableColumn.ID, data.get ( TableColumn.ID ).toString ( ) );
        if ( needUpdate ) {
            try {
                session.beginTransaction ( );
                session.update ( entity, where );
                session.commit ( );
            } catch (SQLException e) {
                log.error ( "{}", e );
                session.quietRollback ( );
                throw e;
            } finally {
                session.close ();
            }
        }
    }

    /**
     * 通过外键更新
     *
     * @param data
     * @return
     */
    public void executeUpdateByForeignKey(String tableName, Map data, Map map, String foreignKey) throws SQLException {
        Session session = Session.create ( dataSource );
        Entity entity = Entity.create ( tableName );
        Entity where = Entity.create ( tableName );
        //保存表单数据
        boolean needUpdate = false;
        for (Object name : data.keySet ( )) {
            if ( name.equals ( TableColumn.ID ) || map.get ( name ) == null )
                continue;
            entity.set ( name.toString ( ), data.get ( name ) );
            needUpdate = true;
        }
        UserDTO user = UserUtils.getCurrentUserDTO ( );
        if ( this.getColumns ( ).contains ( TableColumn.UPDATE_BY ) && StrUtil.isNotBlank ( user.getId ( ) ) && map.get ( TableColumn.UPDATE_BY ) == null ) {
            entity.set ( TableColumn.UPDATE_BY, user.getId ( ) );
        }
        if ( this.getColumns ( ).contains ( TableColumn.UPDATE_DATE ) && map.get ( TableColumn.UPDATE_DATE ) == null ) {
            entity.set ( TableColumn.UPDATE_DATE, formatDate ( new Date ( ) ) );

        }

        where.set ( foreignKey, data.get ( foreignKey ).toString ( ) );
        if ( needUpdate ) {
            try {
                session.beginTransaction ( );
                session.update ( entity, where );
                session.commit ( );
            } catch (SQLException e) {
                session.quietRollback ( );
                log.error ( "{}", e );
                throw e;
            } finally {
                session.close ();
            }
        }
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
     * 获取列
     *
     * @return
     * @throws Exception
     */
    protected HashSet getColumns() throws SQLException {
        HashSet columns = new HashSet ( );
        DDLBuilder ddlBuilder = new DDLBuilder ( dataSource, tableName );
        Column[] dbColumns = ddlBuilder.getTableColumnList ( tableName );
        for (int i = 0; i < dbColumns.length; i++) {
            columns.add ( dbColumns[i].getName ( ) );
        }
        return columns;
    }


    private String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss" );
        //从前端或者自己模拟一个日期格式，转为String即可
        return format.format ( date );
    }

}
