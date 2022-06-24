/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.database.datamodel.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.database.datalink.jdbc.DBPool;
import com.jeeplus.database.datamodel.domain.DataMeta;
import com.jeeplus.database.datamodel.domain.DataParam;
import com.jeeplus.database.datamodel.domain.DataSet;
import com.jeeplus.database.datamodel.mapper.DataSetMapper;
import com.jeeplus.database.datamodel.service.dto.DataMetaDTO;
import com.jeeplus.database.datamodel.service.dto.DataParamDTO;
import com.jeeplus.database.datamodel.service.dto.DataSetDTO;
import com.jeeplus.database.datamodel.service.mapstruct.DataMetaWrapper;
import com.jeeplus.database.datamodel.service.mapstruct.DataParamWrapper;
import com.jeeplus.database.datamodel.service.mapstruct.DataSetWrapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 数据模型Service
 *
 * @author 刘高峰
 * @version 2021-08-07
 */
@Service
@Transactional
public class DataSetService extends ServiceImpl <DataSetMapper, DataSet> {
    @Autowired
    private DataParamService dataParamService;
    @Autowired
    private DataMetaService dataMetaService;


    public DataSetDTO detail(String id) {
        DataSetDTO dataSetDTO = baseMapper.get(id);
        if (dataSetDTO == null) {
            return null;
        };
        dataSetDTO.setColumnList( DataMetaWrapper.INSTANCE.toDTO (dataMetaService.lambdaQuery().eq (DataMeta::getDataSetId, id).list ()));
        dataSetDTO.setParams (DataParamWrapper.INSTANCE.toDTO (dataParamService.lambdaQuery ( ).eq ( DataParam::getDataSetId, id ).list ( )));
        return dataSetDTO;
    }


    /**
     * 自定义分页检索
     * @param page
     * @param queryWrapper
     * @return
     */
    public IPage <DataSetDTO> findPage(Page <DataSetDTO> page, QueryWrapper queryWrapper) {
        queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
        return  baseMapper.findList (page, queryWrapper);


    }

    /**
     * 保存更新
     * @param dataSetDTO
     */
    public void saveOrUpdate(DataSetDTO dataSetDTO) {
        DataSet dataSet = DataSetWrapper.INSTANCE.toEntity (dataSetDTO);
        if( StrUtil.isNotBlank ( dataSet.getId () ) ){
            dataMetaService.lambdaUpdate ().eq ( DataMeta::getDataSetId, dataSet.getId () ).remove ();
            dataParamService.lambdaUpdate ().eq ( DataParam::getDataSetId, dataSet.getId () ).remove ();
        }
        super.saveOrUpdate ( dataSet);
        for (DataMetaDTO dataMetaDTO : dataSetDTO.getColumnList()) {
            DataMeta dataMeta = DataMetaWrapper.INSTANCE.toEntity ( dataMetaDTO );
            dataMeta.setDataSetId ( dataSet.getId () );
            dataMetaService.saveOrUpdate (dataMeta );

        }
        for (DataParamDTO paramsDTO : dataSetDTO.getParams()) {
            DataParam dataParam = DataParamWrapper.INSTANCE.toEntity ( paramsDTO );
            dataParam.setDataSetId ( dataSet.getId () );
            dataParamService.saveOrUpdate (dataParam );
        }
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id) {
        super.removeById ( id );
        dataParamService.lambdaUpdate ().eq ( DataParam::getDataSetId, id ).remove ();
        dataMetaService.lambdaUpdate ().eq ( DataMeta::getDataSetId, id ).remove ();
    }

    public List<Map<String, Object>> queryForListById(String id, HttpServletRequest request) throws Exception {
        Map paramsMap = dataParamService.getParamsForMap(id);
        if(request != null){
            Enumeration <String> names = request.getParameterNames();
            while(names.hasMoreElements()){
                String param = names.nextElement().toString();
                paramsMap.put(param, request.getParameter(param));
            }
        }
        String sql = mergeSql(baseMapper.get ( id ).getSqlCmd (), paramsMap);
        return DBPool.getInstance().getJdbcTemplateByDsId (baseMapper.get (id).getDataSource ().getId ()).queryForList(sql);

    }

    public String mergeSql(String sql, String[] field, String[] value) {
        //转换成MAP
        Map<String, String> paramsMap = new HashMap<>();
        if (field != null) {
            for (int i = 0; i < field.length; i++) {
                paramsMap.put(field[i], value[i]);
            }
        }
        return mergeSql(sql, paramsMap);
    }

    public String mergeSql(String sql, Map<String, String> paramsMap) {
        sql = HtmlUtil.unescape (sql);
        for (String key : paramsMap.keySet()) {
            sql = sql.replace("{#"+key+"#}", paramsMap.get(key));
        }
        return sql;
    }


    public JSONArray toJSON(List<Map<String, Object>> list) {
        JSONArray data = new JSONArray();
        for (Map<String, Object> map : list) {
            JSONObject obj = new JSONObject();
            for (String key : map.keySet()) {
                Object value = map.get(key);
                if (value != null)
                    obj.element(key, value.toString());
                else
                    obj.element(key, "");
            }

            data.add(obj);
        }
        return data;
    }

    public String toXML(List<Map<String, Object>> data) {
        StringBuffer xml = new StringBuffer();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<list>\n");
        for (Map<String, Object> map : data) {
            xml.append("  <data>\n");
            Iterator<String> iter = map.keySet().iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                Object val = map.get(key);

                xml.append("    <prop key=\"" + key + "\">\n      <![CDATA[" + val + "]]>\n    </prop>\n");
            }
            xml.append("  </data>\n");
        }
        xml.append("</list>\n");
        return xml.toString();
    }

    public String toHTML(List<Map<String, Object>> data) {
        StringBuffer html = new StringBuffer("<table class='table table-bordered table-condensed'>");
        StringBuffer head = new StringBuffer("<thead><tr>");
        StringBuffer body = new StringBuffer("<tbody><tr>");

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> map = data.get(i);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (i == 0) {
                    head.append("<th>").append(entry.getKey()).append("</th>");
                }
                body.append("<td>").append(entry.getValue()).append("</td>");
            }
            body.append("</tr><tr>");
        }

        head.append("</tr></thead>");
        body.append("</tr></tbody>");
        html.append(head).append(body).append("</table>");
        return html.toString();
    }

}
