/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.form.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.database.datalink.jdbc.DBPool;
import com.jeeplus.form.mapper.FormMapper;
import com.jeeplus.form.service.dto.ColumnDTO;
import com.jeeplus.form.service.dto.FormDTO;
import com.jeeplus.form.service.dto.RelationDTO;
import com.jeeplus.form.utils.DMLBuilder;
import com.jeeplus.form.utils.FormJsonUtils;
import com.jeeplus.sys.service.AreaService;
import com.jeeplus.sys.service.OfficeService;
import com.jeeplus.sys.service.dto.DataRuleDTO;
import com.jeeplus.sys.utils.UserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 数据表单Service
 *
 * @author 刘高峰
 * @version 2021-09-24
 */
@Service
@Transactional
public class GenerateFormService {
    @Autowired
    private OfficeService officeService;
    @Autowired
    private AreaService areaService;
    @Autowired
    FormMapper formMapper;

    /**
     * 更新表单
     *
     * @param data
     * @return
     */
    public void executeUpdate(FormDTO form, JSONObject data, HashMap map) throws SQLException {
        //保存表单数据
        DataSource dataSource = DBPool.getInstance ().getDsById ( form.getDataSource ().getId () );
        String tableName = form.getTableName ();
        DMLBuilder dmlBuilder = new DMLBuilder ( dataSource, tableName );
        List<RelationDTO> relations = FormJsonUtils.newInstance().getRelations(form.getSource ());
        if(relations.size() == 0) { // 如果是单表
            dmlBuilder.executeUpdate(tableName, data, map);
        }else{//如果是多表
            // 主表
            Map mainMap = new HashMap();
            Map mainData = new HashMap();
            for (Object name : data.keySet()) {
                if (!name.toString().contains("->")) {
                    mainMap.put(name, map.get(name));
                    mainData.put(name, data.get(name));
                }

            }
            dmlBuilder.executeUpdate(tableName, mainData, mainMap);
            // 多表关联
            for (RelationDTO relation : relations) {
                if("table".equals(relation.getType())){
                    continue;
                }
                Map childMap = new HashMap();
                Map dataMap = new HashMap();
                for (Object name : data.keySet()) {
                    if (name.toString().startsWith(relation.getChildTableName() + "->")) {
                        childMap.put(name.toString().substring((relation.getChildTableName() + "->").length()), map.get(name));
                        dataMap.put(name.toString().substring((relation.getChildTableName() + "->").length()), data.get(name));
                    }
                }
                String foreignKey = relation.getForeignKey().split("\\.")[1];
                childMap.put(foreignKey, "input");
                dataMap.put(foreignKey, data.get(relation.getPrimaryKey()));

                int count = dmlBuilder.executeQueryCountByForeignKey(relation.getChildTableName(), foreignKey, dataMap.get(foreignKey).toString());
                if(count == 0){
                    dmlBuilder.executeInsert(relation.getChildTableName(), dataMap, childMap);
                }else {
                    dmlBuilder.executeUpdateByForeignKey(relation.getChildTableName(), dataMap, childMap, foreignKey);
                }
            }



            // 子表
            for (RelationDTO relation : relations) {
                if("table".equals(relation.getType())){
                    String foreignKey = relation.getForeignKey().split("\\.")[1];
                    for (Object name : data.keySet()) {
                        if (name.toString().equals("childTable->"+relation.getChildTableName())) {
                            JSONArray childArray = data.getJSONArray("childTable->" + relation.getChildTableName());
                            List<ColumnDTO> fieldArra = FormJsonUtils.newInstance().getFieldsByTable(form.getSource (), "childTable->"+relation.getChildTableName());
                            Map childMap = new HashMap();
                            for(ColumnDTO column: fieldArra){
                                childMap.put(column.getModel().split("->")[1], column.getType());
                            }
                            // 统一设置外键
                            childMap.put(foreignKey, "input");
                            String foreignValue = data.get(relation.getPrimaryKey()).toString();

                            // 删除所有数据
                            dmlBuilder.executeDeleteByForeignKey(relation.getChildTableName(), foreignValue, foreignKey);

                            // 逐条插入子表
                            for(int i=0; i<childArray.size(); i++){
                                JSONObject columnData = childArray.getJSONObject(i);
                                Map dataMap = new HashMap();
                                dataMap.put(foreignKey, foreignValue);
                                for(ColumnDTO column: fieldArra){
                                    dataMap.put(column.getModel().split("->")[1], columnData.get(column.getModel()));
                                }
                                dmlBuilder.executeInsert(relation.getChildTableName(), dataMap, childMap);
                            }

                        }
                    }
                }
            }

        }
    }

    /**
     * 保存表单
     *
     * @param data
     * @return
     */
    public void executeInsert(FormDTO form, JSONObject data, Map map) throws SQLException {
        //保存表单数据
        DataSource dataSource = DBPool.getInstance ().getDsById ( form.getDataSource ().getId () );
        String tableName = form.getTableName ();
        DMLBuilder dmlBuilder = new DMLBuilder ( dataSource, tableName );

        List<RelationDTO> relations = FormJsonUtils.newInstance().getRelations(form.getSource ());
        if(relations.size() == 0){ // 如果是单表
            dmlBuilder.executeInsert(form.getTableName(), data, map);
        }else { // 如果是多表关联
            // 主表
            Map mainMap = new HashMap();
            Map mainData = new HashMap();
            for (Object name : data.keySet()) {
                if (!name.toString().contains("->")) {
                    mainMap.put(name, map.get(name));
                    mainData.put(name, data.get(name));
                }
            }
            String id = dmlBuilder.executeInsert(form.getTableName(), mainData, mainMap);
            // 多表关联
            for (RelationDTO relation : relations) {
                if("table".equals(relation.getType())){// 排除子表
                    continue;
                }
                Map childMap = new HashMap();
                Map dataMap = new HashMap();
                for (Object name : data.keySet()) {
                    if (name.toString().startsWith(relation.getChildTableName() + "->")) {
                        childMap.put(name.toString().substring((relation.getChildTableName() + "->").length()), map.get(name));
                        dataMap.put(name.toString().substring((relation.getChildTableName() + "->").length()), data.get(name));
                    }
                }
                String foreignKey = relation.getForeignKey().split("\\.")[1];
                if (relation.getPrimaryKey().equals("id")) {
                    childMap.put(foreignKey, "input");
                    dataMap.put(foreignKey, id);
                } else {
                    childMap.put(foreignKey, "input");
                    dataMap.put(foreignKey, data.get(relation.getPrimaryKey()));
                }
                dmlBuilder.executeInsert(relation.getChildTableName(), dataMap, childMap);
            }

            // 子表
            for (RelationDTO relation : relations) {
                if("table".equals(relation.getType())){
                    String foreignKey = relation.getForeignKey().split("\\.")[1];
                    for (Object name : data.keySet()) {
                        if (name.toString().equals("childTable->"+relation.getChildTableName())) {
                            JSONArray childArray = data.getJSONArray("childTable->" + relation.getChildTableName());
                            List<ColumnDTO> fieldArra = FormJsonUtils.newInstance().getFieldsByTable(form.getSource (), "childTable->"+relation.getChildTableName());
                            Map childMap = new HashMap();
                            for(ColumnDTO column: fieldArra){
                                childMap.put(column.getModel().split("->")[1], column.getType());
                            }
                            String foreignValue;
                            // 统一设置外键
                            if (relation.getPrimaryKey().equals("id")) {
                                childMap.put(foreignKey, "input");
                                foreignValue = id;
                            } else {
                                childMap.put(foreignKey, "input");
                                foreignValue = data.get(relation.getPrimaryKey()).toString();
                            }

                            // 逐条插入子表
                            for(int i=0; i<childArray.size(); i++){
                                JSONObject columnData = childArray.getJSONObject(i);
                                Map dataMap = new HashMap();
                                dataMap.put(foreignKey, foreignValue);
                                for(ColumnDTO column: fieldArra){
                                    dataMap.put(column.getModel().split("->")[1], columnData.get(column.getModel()));
                                }
                                dmlBuilder.executeInsert(relation.getChildTableName(), dataMap, childMap);
                            }

                        }
                    }
                }
            }
        }


    }


    /**
     * 删除表单内容
     *
     * @return
     */
    public void executeDelete(FormDTO form, String ids) throws SQLException{
        DataSource dataSource = DBPool.getInstance ().getDsById ( form.getDataSource ().getId () );
        String tableName = form.getTableName ();
        DMLBuilder dmlBuilder = new DMLBuilder ( dataSource, tableName );

        List<RelationDTO> relations = FormJsonUtils.newInstance().getRelations(form.getSource ());
        if(relations.size() == 0){ // 如果是单表
            dmlBuilder.executeDelete(form.getTableName(), ids);
        }else { // 如果是多表关联
            // 子表
            for (RelationDTO relation : relations) {
                String keys = "";
                if(!relation.getPrimaryKey().equalsIgnoreCase("id")){
                    String querySql = "select "+relation.getPrimaryKey()+ " from " + form.getTableName() + " where id in ( ";
                    String idArra = "";
                    for (String id : ids.split(",")) {
                        idArra = idArra + "'" + id + "',";
                    }
                    idArra = idArra.substring(0, idArra.length() - 1);
                    querySql = querySql + idArra + " ) ";


                   List<Entity> list =  dmlBuilder.executeQueryList ( querySql );
                    for (Entity map : list){
                        String key = map.get(relation.getPrimaryKey()).toString();
                        keys = keys + key + ",";
                    }
                    keys = keys.substring(0, keys.length()-1);
                }else{
                    keys = ids;
                }
                dmlBuilder.executeDeleteByForeignKey(relation.getChildTableName(), keys, relation.getForeignKey().split("\\.")[1]);
            }

            // 主表
            dmlBuilder.executeDelete(form.getTableName(), ids);

        }

    }

    /**
     * 查询表单内容
     *
     * @return
     */
    public Map executeQueryById(FormDTO form, String id) throws SQLException {
        DataSource dataSource = DBPool.getInstance ().getDsById ( form.getDataSource ().getId () );
        String tableName = form.getTableName ();
        DMLBuilder dmlBuilder = new DMLBuilder ( dataSource, tableName );

        List<RelationDTO> relations = FormJsonUtils.newInstance().getRelations(form.getSource ());
        if(relations.size() == 0){ // 如果是单表
            return dmlBuilder.executeQueryById(form.getTableName(), id);
        }else { // 如果是多表关联
            String originalSql = "select ";
            List<ColumnDTO> fields = FormJsonUtils.newInstance().getFields(form.getSource ());
            HashMap<String, String> typeMap = new HashMap();
            for (ColumnDTO column : fields) {
                typeMap.put(column.getModel(), column.getType());
            }
            for (ColumnDTO field : fields) {
                if(field.getModel().startsWith("childTable->")){
                    continue;
                }
                if(field.getModel().contains("->")){
                    originalSql = originalSql + field.getModel().replace ("->", ".") + " as '" + field.getModel() + "',";
                }else{
                    originalSql = originalSql + form.getTableName()+ "."+ field.getModel() + " as '" + field.getModel() + "',";
                }
            }
            originalSql = originalSql.substring(0, originalSql.length() - 1);
            originalSql = originalSql + " from " + form.getTableName();
            // 多表关联关系
            for (RelationDTO relation : relations) {
                if("table".equals(relation.getType())){
                    continue;
                }
                originalSql = originalSql + " left join " + relation.getChildTableName() + " on " + form.getTableName() + "." + relation.getPrimaryKey()
                        + " = "  + relation.getForeignKey();
            }
            originalSql = originalSql + " where " + form.getTableName() + ".id = '" + id + "'";
            Map map = dmlBuilder.executeQueryOne ( originalSql );
            map.put("id", id);// 主键存储。
            //处理日期
            for (Object key : map.keySet()) {
                if ("date".equals(typeMap.get(key)) || "time".equals(typeMap.get(key))) {
                    if (map.get(key) != null) {
                        if(map.get (key) instanceof  Timestamp){
                            long time = ((Timestamp) map.get (key)).getTime ()/1000;
                            map.put (key, DateUtil.date ( time ));
                        }
                    }
                }

            }


            // 子表
            for (RelationDTO relation : relations) {
                if("table".equals(relation.getType())){
                    String foreignKey = relation.getForeignKey().split("\\.")[1];

                    String childSql = "select ";
                    List<ColumnDTO> childTableFieldArra = FormJsonUtils.newInstance().getFieldsByTable(form.getSource (), "childTable->"+relation.getChildTableName());
                    for(ColumnDTO column: childTableFieldArra){
                        childSql = childSql + column.getModel().split("->")[1] + " as '" + column.getModel() + "',";
                    }
                    childSql = childSql.substring(0, childSql.length()-1);
                    childSql = childSql + " from "+ relation.getChildTableName() +" where "+foreignKey+" = '"+map.get(relation.getPrimaryKey())+"'";
                    List<Entity> childList = dmlBuilder.executeQueryList (childSql);
                    //处理日期
                    HashMap<String, String> childTableTypeMap = new HashMap();
                    for (ColumnDTO column : childTableFieldArra) {
                        childTableTypeMap.put(column.getModel(), column.getType());
                    }
                    for (Entity childMap : childList) {
                        for (String key : childMap.keySet()) {
                            if ("date".equals(childTableTypeMap.get(key)) || "time".equals(childTableTypeMap.get(key))) {
                                if (childMap.get(key) != null) {
                                    if(childMap.get (key) instanceof  Timestamp){
                                        long time = ((Timestamp) childMap.get (key)).getTime ()/1000;
                                        childMap.put (key, DateUtil.date(time));
                                    }
                                }
                            }

                        }
                    }

                    map.put("childTable->"+relation.getChildTableName()+"",  JSONUtil.toJsonStr ( childList ));


                }
            }
            return map;
        }

    }

    @DS("#form.dataSource.enName")
    public IPage<Entity> executeFindPage(Page page, FormDTO form, String params) {
        String originalSql = "";
            List<RelationDTO> relations = FormJsonUtils.newInstance().getRelations(form.getSource ());
        if (relations.size() == 0) {// 包含子表
            originalSql = "select * from " + form.getTableName() + " ";
        } else {// 如果是多表关联
            originalSql = "select "+form.getTableName()+".id,";
            List<ColumnDTO> fields = FormJsonUtils.newInstance().getFields(form.getSource ());
            for (ColumnDTO field : fields) {
                if(field.getModel().startsWith("childTable->")){//排除子表
                    continue;
                }
                if(field.getModel().contains("->")){
                    originalSql = originalSql + field.getModel().replace ("->", ".") + " as '" + field.getModel() + "',";
                }else{
                    originalSql = originalSql + form.getTableName()+ "." + field.getModel() + " as '" + field.getModel() + "',";
                }
            }
            originalSql = originalSql.substring(0, originalSql.length() - 1);
            originalSql = originalSql + " from " + form.getTableName();
            // 子表

            for (RelationDTO relation : relations) {
                if("table".equals(relation.getType())){// 排除子表
                    continue;
                }
                originalSql = originalSql + " left join " + relation.getChildTableName() + " on " + form.getTableName() + "." + relation.getPrimaryKey()
                        + " = "  + relation.getForeignKey();
            }
        }
        Map<String, String> paramsMap = JSONUtil.toBean(params, Map.class);
        List<ColumnDTO> fieldArra = FormJsonUtils.newInstance().getFields(form.getSource ());
        HashMap<String, String> typeMap = new HashMap();
        for (ColumnDTO column : fieldArra) {
            typeMap.put(column.getModel(), column.getType());
        }
        if (paramsMap.size() > 0) {
            if ( StrUtil.isNotBlank(dataRuleFilter(form))) {
                originalSql = originalSql + " where 1=1 " + dataRuleFilter(form) + "  and   ";
            } else {
                originalSql = originalSql + " where ";
            }
            for (String key : paramsMap.keySet()) {
                if(StrUtil.isNotBlank(String.valueOf(paramsMap.get(key)))){
                    originalSql = originalSql + " " + key + " like " + "'%" + String.valueOf(paramsMap.get(key)) + "%' and   ";
                }
            }
            originalSql = originalSql.substring(0, originalSql.length() - 6);
        } else if (StrUtil.isNotBlank(dataRuleFilter(form))) {
            originalSql = originalSql + " where 1=1 " + dataRuleFilter(form);
        }
        IPage<Entity> result = formMapper.findPage ( page, originalSql);
        IPage<Entity> newResult = new Page <Entity> (result.getCurrent (), result.getSize (), result.getTotal ()  );
        List<Entity> records = Lists.newArrayList ();
        for (Entity map : result.getRecords ()) {
            Entity newEntity = new Entity (  );
            for (String key : map.keySet()) {
                Object newValue = DMLBuilder.getValueStr ( map.get ( key ) );
                String newKey = StringUtils.lowerCase ( key );
                if(newValue == null) {
                    newEntity.put ( newKey, null );
                }else if ("user".equals(typeMap.get(newKey))) {
                    if (UserUtils.get(newValue.toString()) != null) {
                        newEntity.put(newKey, UserUtils.get(newValue.toString ()).getName());
                    }
                }else if ("office".equals(typeMap.get(newKey))) {
                    if (officeService.getById (newValue.toString()) != null) {
                        newEntity.put(newKey, officeService.getById (newValue.toString()).getName());
                    }
                }else if ("area".equals(typeMap.get(newKey))) {
                    if (areaService.getById (newValue.toString()) != null) {
                        newEntity.put(newKey, areaService.getById (newValue.toString()).getName());
                    }
                }else {
                    newEntity.put ( newKey, newValue );
                }
            }
            records.add ( newEntity );
        }
        newResult.setRecords ( records );
        return newResult;
    }


    public static String dataRuleFilter(FormDTO form) {
        if ( UserUtils.getCurrentUserDTO () == null || StrUtil.isBlank(UserUtils.getCurrentUserDTO ().getId ())) {
            return "";
        }
        List<DataRuleDTO> dataRuleList = UserUtils.getDataRuleList();
        // 如果是超级管理员，则不过滤数据
        if (dataRuleList.size() == 0) {
            return "";
        }
        // 数据范围
        StringBuilder sqlString = new StringBuilder();
        for (DataRuleDTO dataRule : dataRuleList) {
            if (form.getTableName().equals(dataRule.getClassName())) {
                sqlString.append(dataRule.getDataScopeSql());
            }
        }

        return sqlString.toString();

    }

}
