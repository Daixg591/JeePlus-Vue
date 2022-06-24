/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.database.datamodel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.common.utils.ResponseUtil;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.database.datalink.jdbc.DBPool;
import com.jeeplus.database.datamodel.domain.DataMeta;
import com.jeeplus.database.datamodel.domain.DataParam;
import com.jeeplus.database.datamodel.domain.DataSet;
import com.jeeplus.database.datamodel.service.DataMetaService;
import com.jeeplus.database.datamodel.service.DataParamService;
import com.jeeplus.database.datamodel.service.DataSetService;
import com.jeeplus.database.datamodel.service.dto.DataSetDTO;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * 数据模型Controller
 *
 * @author 刘高峰
 * @version 2021-08-07
 */
@RestController
@RequestMapping(value = "/database/datamodel/dataSet")
public class DataSetController {

    @Autowired
    private DataSetService dataSetService;
    @Autowired
    private DataMetaService dataMetaService;
    @Autowired
    private DataParamService dataParamService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 数据模型列表数据
     */
    @PreAuthorize("hasAuthority('database:datamodel:dataSet:list')")
    @GetMapping(value = "list")
    public ResponseEntity data(DataSetDTO dataSetDTO, Page <DataSetDTO> page) throws Exception {
        QueryWrapper <DataSetDTO> queryWrapper = QueryWrapperGenerator.buildQueryCondition ( dataSetDTO, DataSetDTO.class );
        IPage <DataSetDTO> result = dataSetService.findPage ( page, queryWrapper );
        return ResponseEntity.ok ( result );
    }



    /**
     * 查看，增加，编辑数据模型表单页面
     * params:
     * mode: add, edit, view, 代表三种模式的页面
     */
    @PreAuthorize ("hasAnyAuthority('database:datamodel:dataSet:view','database:datamodel:dataSet:add','database:datamodel:dataSet:edit')")
    @GetMapping(value = "queryById")
    public ResponseEntity queryById(String id) {
        DataSetDTO dataSetDTO = dataSetService.detail(id);
        return ResponseEntity.ok ( dataSetDTO );
    }


    /**
     * 保存数据模型
     */
    @PreAuthorize ("hasAnyAuthority('database:datamodel:dataSet:add','database:datamodel:dataSet:edit')")
    @PostMapping(value = "save")
    public ResponseEntity save(@RequestBody @Valid DataSetDTO dataSetDTO){

        //新增或编辑表单保存
        dataSetService.saveOrUpdate (dataSetDTO);//保存
        return ResponseEntity.ok ("保存数据模型成功");
    }


    /**
     * 批量删除数据模型
     */
    @PreAuthorize ("hasAuthority('database:datamodel:dataSet:del')")
    @DeleteMapping(value = "delete")
    public ResponseEntity delete(@RequestParam(value = "ids")String ids) {
        String idArray[] = ids.split(",");
        StringBuffer msg = new StringBuffer();
        for (String id : idArray) {
            List <Map<String, Object>>  list  = jdbcTemplate.queryForList("select name from plugin_echarts where model_id = '" + id +"'");
            if(list.size() > 0 ){
                String name = "";
                for(Map<String, Object> map : list){
                    name = name +" ["+map.get("name").toString()+"]";
                }
                msg.append("数据模型 ["+dataSetService.getById (id).getName()+"] 已被图表"+name+" 使用，无法删除。请先删除图表，再删除该数据模型。<br/>");
            }else {
                dataMetaService.lambdaUpdate ().eq ( DataMeta::getDataSetId, id ).remove ();
                dataParamService.lambdaUpdate ().eq ( DataParam::getDataSetId, id ).remove ();
                msg.append("删除数据模型 ["+dataSetService.getById (id).getName()+"] 成功。<br/>");
                dataSetService.delete ( id );
            }
        }
        return ResponseEntity.ok ( msg.toString () );
    }


    /**
     * 执行sql 获取表结构
     */

    @GetMapping(value = "getMeta")
    public ResponseEntity query(@RequestParam(value = "dataSourceId", required = false)String dataSourceId, @RequestParam(value = "sql", required = false)String sql, @RequestParam(value = "field[]", required = false)String[] field, @RequestParam(value = "defaultValue[]", required = false)String[] defaultValue) throws IOException, SQLException {
        List columnList = new ArrayList();
        JdbcTemplate jdbcTemplate = DBPool.getInstance().getJdbcTemplateByDsId ( dataSourceId );
        if (jdbcTemplate == null) {
            return ResponseEntity.badRequest ().body ( "数据库链接不存在!" );
        }
        sql = dataSetService.mergeSql(sql, field, defaultValue);
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        int count = rs.getMetaData().getColumnCount();
        for (int i = 1; i <= count; i++) {
            Map map = new HashMap();
            map.put("name", rs.getMetaData().getColumnName(i));
            map.put("type", rs.getMetaData().getColumnTypeName(i));
            map.put("label", rs.getMetaData().getColumnLabel(i));
            columnList.add(map);
        }

        return ResponseEntity.ok (columnList);
    }

    /**
     * 执行sql,预览数据
     *
     * @param sql
     * @return
     */
    @GetMapping(value = "/exec")
    public ResponseEntity exec(@RequestParam(value = "dataSourceId", required = false)String dataSourceId, @RequestParam(value = "sql", required = false)String sql, @RequestParam(value = "field[]", required = false)String[] field, @RequestParam(value = "defaultValue[]", required = false)String[] defaultValue) {
        try {
            JdbcTemplate jdbcTemplate = DBPool.getInstance().getJdbcTemplateByDsId ( dataSourceId );
            if (jdbcTemplate == null) {
                return ResponseEntity.badRequest ().body ( "数据库链接不存在!" );
            }

            if (sql.contains("delete") || sql.contains("update")) {
                return ResponseEntity.badRequest ().body ( "只允许查询操作!" );
            };
            sql = dataSetService.mergeSql(sql, field, defaultValue);

            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
            //数据格式转换
            JSONArray data = dataSetService.toJSON(list);

            //返回结果
            return ResponseUtil.newInstance ().add ("html", dataSetService.toHTML(data)).add ( "json", list ).add ( "xml", dataSetService.toXML(data) ).ok ();
        } catch (Exception e) {
            return ResponseEntity.badRequest ().body ( e.getCause().getLocalizedMessage() );
        }
    }

    /**
     * 执行sql,预览数据
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getData/{id}/{type}")
    public ResponseEntity getData(@PathVariable("id") String id, @PathVariable("type") String type,  HttpServletRequest request) {
        Enumeration<String> names = request.getParameterNames();
        DataSet dataSet = dataSetService.getById (id);
        try {
            JdbcTemplate jdbcTemplate = DBPool.getInstance().getJdbcTemplateByEnName ( dataSet.getDataSourceId () );
            if (jdbcTemplate == null) {
                return ResponseEntity.badRequest ().body ( "数据库链接不存在!" );
            }
            Map paramsMap = dataParamService.getParamsForMap(id);
            while(names.hasMoreElements()){
                String param = names.nextElement().toString();
                paramsMap.put(param, request.getParameter(param));
            }

            String sql = dataSetService.mergeSql(dataSet.getSqlCmd(), paramsMap);
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
            //数据格式转换
            JSONArray data = dataSetService.toJSON(list);

            if("html".equals(type)){
                return ResponseEntity.ok ( dataSetService.toHTML(data) );
            }else if("xml".equals(type)){
                return ResponseEntity.ok (  dataSetService.toXML(data) );
            }else {
               return ResponseEntity.ok ( list );
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest ().body ( e.getCause().getLocalizedMessage() );
        }
    }




}
