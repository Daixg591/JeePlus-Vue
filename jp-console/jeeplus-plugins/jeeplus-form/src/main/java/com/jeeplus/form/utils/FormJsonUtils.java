package com.jeeplus.form.utils;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.jeeplus.form.constant.TableColumn;
import com.jeeplus.form.service.dto.ColumnDTO;
import com.jeeplus.form.service.dto.RelationDTO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

public class FormJsonUtils {
    static String  json1="{\"list\":[{\"type\":\"input\",\"icon\":\"icon iconfont icon-input\",\"dsId\":\"master\",\"tableName\":\"test_data_child2\",\"primaryKey\":\"\",\"foreignKey\":\"\",\"options\":{\"width\":\"100%\",\"defaultValue\":\"\",\"required\":true,\"requiredMessage\":\"\",\"dataType\":\"\",\"dataTypeCheck\":false,\"dataTypeMessage\":\"\",\"pattern\":\"\",\"patternCheck\":false,\"patternMessage\":\"\",\"placeholder\":\"\",\"customClass\":\"\",\"disabled\":false,\"labelWidth\":100,\"isLabelWidth\":false,\"hidden\":false,\"dataBind\":true,\"showPassword\":false,\"isShow\":true,\"isSort\":true,\"isSearch\":true,\"remoteFunc\":\"func_1596987346402\",\"remoteOption\":\"option_1596987346402\"},\"name\":\"单行文本\",\"key\":\"1596987346402\",\"model\":\"startarea\",\"enName\":\"master\",\"rules\":[{\"required\":true,\"message\":\"必须填写\"}]},{\"type\":\"input\",\"icon\":\"icon iconfont icon-input\",\"dsId\":\"master\",\"tableName\":\"test_data_child3\",\"primaryKey\":\"id\",\"foreignKey\":\"test_data_child3->test_data_main\",\"options\":{\"width\":\"100%\",\"defaultValue\":\"\",\"required\":true,\"requiredMessage\":\"\",\"dataType\":\"\",\"dataTypeCheck\":false,\"dataTypeMessage\":\"\",\"pattern\":\"\",\"patternCheck\":false,\"patternMessage\":\"\",\"placeholder\":\"\",\"customClass\":\"\",\"disabled\":false,\"labelWidth\":100,\"isLabelWidth\":false,\"hidden\":false,\"dataBind\":true,\"showPassword\":false,\"isShow\":true,\"isSort\":true,\"isSearch\":true,\"remoteFunc\":\"func_1596987349724\",\"remoteOption\":\"option_1596987349724\"},\"name\":\"单行文本\",\"key\":\"1596987349724\",\"model\":\"test_data_child3->endarea\",\"enName\":\"master\",\"rules\":[{\"required\":true,\"message\":\"必须填写\"}]},{\"type\":\"table\",\"icon\":\"icon iconfont icon-table\",\"dsId\":\"master\",\"tableName\":\"test_data_child1\",\"primaryKey\":\"id\",\"foreignKey\":\"test_data_child1->test_data_main_id\",\"options\":{\"defaultValue\":[],\"customClass\":\"\",\"labelWidth\":100,\"isLabelWidth\":false,\"hidden\":false,\"dataBind\":true,\"isShow\":false,\"disabled\":false,\"required\":false,\"remoteFunc\":\"func_1596979281176\",\"remoteOption\":\"option_1596979281176\"},\"tableColumns\":[{\"type\":\"input\",\"icon\":\"icon iconfont icon-input\",\"dsId\":\"\",\"tableName\":\"\",\"primaryKey\":\"\",\"foreignKey\":\"\",\"options\":{\"width\":\"200px\",\"defaultValue\":\"\",\"required\":true,\"requiredMessage\":\"\",\"dataType\":\"\",\"dataTypeCheck\":false,\"dataTypeMessage\":\"\",\"pattern\":\"\",\"patternCheck\":false,\"patternMessage\":\"\",\"placeholder\":\"\",\"customClass\":\"\",\"disabled\":false,\"labelWidth\":100,\"isLabelWidth\":false,\"hidden\":false,\"dataBind\":true,\"showPassword\":false,\"isShow\":true,\"isSort\":true,\"isSearch\":true,\"remoteFunc\":\"func_1596979288622\",\"remoteOption\":\"option_1596979288622\",\"tableColumn\":true},\"name\":\"单行文本\",\"novalid\":{},\"key\":\"1596979288622\",\"model\":\"test_data_child1->remarks\",\"rules\":[{\"required\":true,\"message\":\"必须填写\"}]}],\"name\":\"子表单\",\"key\":\"1596979281176\",\"model\":\"childTable->test_data_child1\",\"enName\":\"master\",\"rules\":[]}],\"config\":{\"labelWidth\":100,\"labelPosition\":\"right\",\"size\":\"small\",\"customClass\":\"\",\"customJs\":\"\",\"eventType\":\"1\",\"ui\":\"element\",\"layout\":\"horizontal\",\"labelCol\":3,\"actions\":[{\"name\":\"工具栏\",\"link\":\"http://www.baidu.com\",\"paramKey\":\"\",\"paramValue\":\"\",\"position\":[\"1\",\"2\"],\"auth\":\"xxx\"},{\"name\":\"操作列\",\"link\":\"http://www.baidu.com\",\"paramKey\":\"\",\"paramValue\":\"\",\"position\":[\"2\"]}],\"width\":\"100%\"}}";
    static String  json = "{\"list\":[{\"type\":\"user\",\"icon\":\"icon fa fa-user\",\"dsId\":\"master\",\"tableName\":\"test_data_main\",\"primaryKey\":\"\",\"foreignKey\":\"\",\"options\":{\"defaultType\":\"String\",\"customClass\":\"\",\"width\":\"\",\"limit\":1,\"labelWidth\":100,\"isLabelWidth\":false,\"hidden\":false,\"dataBind\":true,\"required\":false,\"disabled\":false,\"isShow\":true,\"isSort\":true,\"isSearch\":false,\"remoteFunc\":\"func_1595779266518\",\"remoteOption\":\"option_1595779266518\"},\"name\":\"用户\",\"key\":\"1595779266518\",\"model\":\"user_id\",\"enName\":\"master\",\"rules\":[]},{\"type\":\"input\",\"icon\":\"icon iconfont icon-input\",\"dsId\":\"master\",\"tableName\":\"test_data_main\",\"primaryKey\":\"\",\"foreignKey\":\"\",\"options\":{\"width\":\"100%\",\"defaultValue\":\"\",\"required\":false,\"requiredMessage\":\"\",\"dataType\":\"\",\"dataTypeCheck\":false,\"dataTypeMessage\":\"\",\"pattern\":\"\",\"patternCheck\":false,\"patternMessage\":\"\",\"placeholder\":\"\",\"customClass\":\"\",\"disabled\":false,\"labelWidth\":100,\"isLabelWidth\":false,\"hidden\":false,\"dataBind\":true,\"showPassword\":false,\"isShow\":true,\"isSort\":true,\"isSearch\":true,\"remoteFunc\":\"func_1596027400606\",\"remoteOption\":\"option_1596027400606\"},\"name\":\"单行文本\",\"key\":\"1596027400606\",\"model\":\"sex\",\"enName\":\"master\",\"rules\":[]},{\"type\":\"table\",\"icon\":\"icon iconfont icon-table\",\"dsId\":\"master\",\"tableName\":\"test_data_child3\",\"primaryKey\":\"id\",\"foreignKey\":\"test_data_child3.test_data_main\",\"options\":{\"defaultValue\":[],\"customClass\":\"\",\"labelWidth\":100,\"isLabelWidth\":false,\"hidden\":false,\"dataBind\":true,\"isShow\":true,\"disabled\":false,\"required\":false,\"remoteFunc\":\"func_1596027464626\",\"remoteOption\":\"option_1596027464626\"},\"tableColumns\":[{\"type\":\"input\",\"icon\":\"icon iconfont icon-input\",\"dsId\":\"\",\"tableName\":\"\",\"primaryKey\":\"\",\"foreignKey\":\"\",\"options\":{\"width\":\"200px\",\"defaultValue\":\"\",\"required\":false,\"requiredMessage\":\"\",\"dataType\":\"\",\"dataTypeCheck\":false,\"dataTypeMessage\":\"\",\"pattern\":\"\",\"patternCheck\":false,\"patternMessage\":\"\",\"placeholder\":\"\",\"customClass\":\"\",\"disabled\":false,\"labelWidth\":100,\"isLabelWidth\":false,\"hidden\":false,\"dataBind\":true,\"showPassword\":false,\"isShow\":true,\"isSort\":true,\"isSearch\":true,\"remoteFunc\":\"func_1596027496943\",\"remoteOption\":\"option_1596027496943\",\"tableColumn\":true},\"name\":\"单行文本\",\"novalid\":{},\"key\":\"1596027496943\",\"model\":\"test_data_child3.endarea\",\"rules\":[]},{\"type\":\"input\",\"icon\":\"icon iconfont icon-input\",\"dsId\":\"\",\"tableName\":\"\",\"primaryKey\":\"\",\"foreignKey\":\"\",\"options\":{\"width\":\"200px\",\"defaultValue\":\"\",\"required\":false,\"requiredMessage\":\"\",\"dataType\":\"\",\"dataTypeCheck\":false,\"dataTypeMessage\":\"\",\"pattern\":\"\",\"patternCheck\":false,\"patternMessage\":\"\",\"placeholder\":\"\",\"customClass\":\"\",\"disabled\":false,\"labelWidth\":100,\"isLabelWidth\":false,\"hidden\":false,\"dataBind\":true,\"showPassword\":false,\"isShow\":true,\"isSort\":true,\"isSearch\":true,\"remoteFunc\":\"func_1596027504332\",\"remoteOption\":\"option_1596027504332\",\"tableColumn\":true},\"name\":\"单行文本\",\"novalid\":{},\"key\":\"1596027504332\",\"model\":\"test_data_child3.price\",\"rules\":[]}],\"name\":\"子表单\",\"key\":\"1596027464626\",\"model\":\"childTable.test_data_child3\",\"enName\":\"master\",\"rules\":[]}],\"config\":{\"labelWidth\":100,\"labelPosition\":\"right\",\"size\":\"small\",\"customClass\":\"\"}}";
    List<ColumnDTO> fields = Lists.newArrayList();
    List<JSONObject> appFields = Lists.newArrayList();
    List<RelationDTO> relations = Lists.newArrayList();

    void generateModel(JSONArray genList) {
        for (int i = 0; i < genList.size(); i++) {
            JSONObject orderedMap = (JSONObject) genList.get(i);
            if ("grid".equals(orderedMap.get("type"))) {
                JSONArray columns = (JSONArray) orderedMap.get("columns");
                for (int j = 0; j < columns.size(); j++) {
                    JSONObject item = ((JSONObject) columns.get(j));
                    generateModel(item.getJSONArray("list"));
                }
            } else if ("tabs".equals(orderedMap.get("type"))) {
                JSONArray columns = (JSONArray) orderedMap.get("tabs");
                for (int j = 0; j < columns.size(); j++) {
                    JSONObject item = ((JSONObject) columns.get(j));
                    generateModel(item.getJSONArray("list"));
                }
            } else if ("report".equals(orderedMap.get("type"))) {
                JSONArray rows = (JSONArray) orderedMap.get("rows");
                for (int j = 0; j < rows.size(); j++) {
                    JSONObject rowItem = ((JSONObject) rows.get(j));
                    JSONArray columns = (JSONArray) rowItem.get("columns");
                    columns.forEach (item->{
                        generateModel( ((JSONObject) item).getJSONArray("list"));
                    });
                }
            } else {

                if (orderedMap.getJSONObject("options").get("dataBind") !=null && orderedMap.getJSONObject("options").getBoolean("dataBind")) {
                        ColumnDTO gzColumn = new ColumnDTO ();
                        gzColumn.setModel( orderedMap.getString("model"));
                        gzColumn.setName( orderedMap.getString("name"));
                        gzColumn.setType(orderedMap.getString("type"));
                        this.fields.add(gzColumn);
                }


            }
        }
    }

    void generateModelForApp(JSONArray genList) {
        for (int i = 0; i < genList.size(); i++) {
            JSONObject orderedMap = (JSONObject) genList.get(i);
            if ("grid".equals(orderedMap.get("type"))) {
                JSONArray columns = (JSONArray) orderedMap.get("columns");
                for (int j = 0; j < columns.size(); j++) {
                    JSONObject item = ((JSONObject) columns.get(j));
                    generateModelForApp(item.getJSONArray("list"));
                }
            } else if ("tabs".equals(orderedMap.get("type"))) {
                JSONArray columns = (JSONArray) orderedMap.get("tabs");
                for (int j = 0; j < columns.size(); j++) {
                    JSONObject item = ((JSONObject) columns.get(j));
                    generateModelForApp(item.getJSONArray("list"));
                }
            } else if ("report".equals(orderedMap.get("type"))) {
                JSONArray rows = (JSONArray) orderedMap.get("rows");
                for (int j = 0; j < rows.size(); j++) {
                    JSONObject rowItem = ((JSONObject) rows.get(j));
                    JSONArray columns = (JSONArray) rowItem.get("columns");
                    columns.forEach (item->{
                        generateModelForApp ( ((JSONObject) item).getJSONArray("list"));
                    });
                }
            } else {

                if (orderedMap.getJSONObject("options").get("dataBind") !=null && orderedMap.getJSONObject("options").getBoolean("dataBind")) {
                    this.appFields.add(orderedMap);
                }


            }
        }
    }
    void generateTable(JSONArray genList, String model) {
        for (int i = 0; i < genList.size(); i++) {
            JSONObject orderedMap = (JSONObject) genList.get(i);
            if ("grid".equals(orderedMap.get("type"))) {
                JSONArray columns = (JSONArray) orderedMap.get("columns");
                for (int j = 0; j < columns.size(); j++) {
                    JSONObject item = ((JSONObject) columns.get(j));
                    generateTable(item.getJSONArray("list"), model);
                }
            } else if ("tabs".equals(orderedMap.get("type"))) {
                JSONArray columns = (JSONArray) orderedMap.get("tabs");
                for (int j = 0; j < columns.size(); j++) {
                    JSONObject item = ((JSONObject) columns.get(j));
                    generateTable(item.getJSONArray("list"), model);
                }
            } else if ("report".equals(orderedMap.get("type"))) {
                JSONArray rows = (JSONArray) orderedMap.get("rows");
                for (int j = 0; j < rows.size(); j++) {
                    JSONObject rowItem = ((JSONObject) rows.get(j));
                    JSONArray columns = (JSONArray) rowItem.get("columns");
                    columns.forEach (item->{
                        generateTable ( ((JSONObject) item).getJSONArray("list"), model);
                    });
                }
            } else if ("table".equals(orderedMap.get("type"))) {
                if(model.equals(orderedMap.getString("model"))){
                    JSONArray columns = orderedMap.getJSONArray("tableColumns");
                    for (int j = 0; j < columns.size(); j++) {
                        JSONObject item = ((JSONObject) columns.get(j));
                        if ((item.getJSONObject("options")).getBoolean("dataBind")) {
                            ColumnDTO gzColumn = new ColumnDTO ();
                            gzColumn.setModel( item.getString("model"));
                            gzColumn.setName( item.getString("name"));
                            gzColumn.setType(item.getString("type"));
                            this.fields.add(gzColumn);
                        }
                    }
                }
            }
        }
    }

    void generateRelation(JSONArray genList) {
        for (int i = 0; i < genList.size(); i++) {
            JSONObject orderedMap = (JSONObject) genList.get(i);
            if ("grid".equals(orderedMap.get("type"))) {
                JSONArray columns = (JSONArray) orderedMap.get("columns");
                for (int j = 0; j < columns.size(); j++) {
                    JSONObject item = ((JSONObject) columns.get(j));
                    generateRelation(item.getJSONArray("list"));
                }
            } else if ("tabs".equals(orderedMap.get("type"))) {
                JSONArray columns = (JSONArray) orderedMap.get("tabs");
                for (int j = 0; j < columns.size(); j++) {
                    JSONObject item = ((JSONObject) columns.get(j));
                    generateRelation(item.getJSONArray("list"));
                }
            } else if ("report".equals(orderedMap.get("type"))) {
                JSONArray rows = (JSONArray) orderedMap.get("rows");
                for (int j = 0; j < rows.size(); j++) {
                    JSONObject rowItem = ((JSONObject) rows.get(j));
                    JSONArray columns = (JSONArray) rowItem.get("columns");
                    columns.forEach (item->{
                        generateRelation ( ((JSONObject) item).getJSONArray("list"));
                    });
                }
            } else {

                if (orderedMap.getJSONObject("options").get("dataBind") !=null  && orderedMap.getJSONObject("options").getBoolean("dataBind")
                && orderedMap.getString("model").contains("->") ) {
                    if(!this.containRelation(orderedMap.getString("tableName"))){
                        RelationDTO relation = new RelationDTO ();
                        relation.setChildTableName(orderedMap.getString("tableName"));
                        relation.setForeignKey(orderedMap.getString("foreignKey").replace ("->", "."));
                        relation.setPrimaryKey(orderedMap.getString("primaryKey"));
                        relation.setType(orderedMap.getString("type"));
                        this.relations.add(relation);
                    }
                }


            }
        }
    }

    boolean containRelation(String tableName){
        for(RelationDTO relation: this.relations){
            if(tableName.equals(relation.getChildTableName())){
                return true;
            }
        }
        return false;
    }

    void generateConfig(JSONArray genList) {
        for (int i = 0; i < genList.size(); i++) {
            JSONObject orderedMap = (JSONObject) genList.get(i);
            RelationDTO relation = new RelationDTO ();
            relation.setChildTableName(orderedMap.getString("name"));
            relation.setForeignKey(orderedMap.getString("foreignKey"));
            relation.setPrimaryKey(orderedMap.getString("primaryKey"));
            this.relations.add(relation);
        }


    }
    public static FormJsonUtils newInstance(){
        return new FormJsonUtils();
    }

    public List<ColumnDTO>  getFields(String json){
        JSONArray list = JSONObject.fromObject(json).getJSONArray("list");
        generateModel(list);
        return fields;
    }

    public List<JSONObject>  getFieldsForApp(String json){
        JSONArray list = JSONObject.fromObject(json).getJSONArray("list");
        generateModelForApp(list);
        return appFields;
    }

    public List<ColumnDTO>  getFieldsByTable(String json, String model){
        JSONArray list = JSONObject.fromObject(json).getJSONArray("list");
        generateTable(list, model);
        return fields;
    }


    public List getRelations(String json){
        JSONArray list = JSONObject.fromObject(json).getJSONArray("list");
        generateRelation(list);
        return relations;
    }

    public boolean isBasicField(String field) {
        if ( !field.equalsIgnoreCase ( TableColumn.ID )
                && !field.equalsIgnoreCase ( TableColumn.CREATE_BY )
                && !field.equalsIgnoreCase ( TableColumn.CREATE_DATE )
                && !field.equalsIgnoreCase ( TableColumn.UPDATE_BY )
                && !field.equalsIgnoreCase ( TableColumn.UPDATE_DATE )
                && !field.equalsIgnoreCase ( TableColumn.DEL_FLAG ) ) {
            return false;
        } else {
            return true;
        }
    }


    public static void main(String[]args){
        System.out.println( JSONUtil.toJsonStr (  FormJsonUtils.newInstance().getFieldsByTable(json1, "childTable->test_data_child3" ) ));
        System.out.println ( JSONUtil.toJsonStr (  FormJsonUtils.newInstance().getFieldsForApp (json1) ));
    }
}

