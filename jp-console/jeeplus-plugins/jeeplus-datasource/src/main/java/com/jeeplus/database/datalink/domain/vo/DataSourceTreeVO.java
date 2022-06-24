package com.jeeplus.database.datalink.domain.vo;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataSourceTreeVO {
    private String id;
    private String label;
    private String parentId;
    private DataSourceTreeVO parent;
    private String enName;
    private String type;
    private String dbType;
    private boolean disabled = false;
    private List<DataSourceTreeVO> children = Lists.newArrayList();

    public DataSourceTreeVO () {

    }
    public DataSourceTreeVO(String id, String label, String parentId, String enName, String type){
        this.id = id;
        this.label = label;
        this.parentId = parentId;
        this.enName = enName;
        this.type = type;
    }

    public DataSourceTreeVO(String id, String label, String parentId, String enName, String type, String dbType
    ){
        this.id = id;
        this.label = label;
        this.parentId = parentId;
        this.enName = enName;
        this.type = type;
        this.dbType = dbType;
    }

    public DataSourceTreeVO(String id, String label, String parentId, String enName, String type, boolean disabled){
        this.id = id;
        this.label = label;
        this.parentId = parentId;
        this.enName = enName;
        this.type = type;
        this.disabled = disabled;
    }

}
