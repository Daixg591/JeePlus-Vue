package com.jeeplus.flowable.model;

import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * 流程模型模型
 *
 * @author liugaofeng
 * @version 2021-08-02
 */
@Data
public class FlowModel {

    private static final long serialVersionUID = 1L;
    private String id;
    protected String name;
    protected String key;
    protected String description;
    protected Date created;
    protected Date lastUpdated;
    private String createdBy;
    private String lastUpdatedBy;
    protected int version;
    protected String modelEditorJson;
    protected String comment;
    protected Integer modelType;
    protected String tenantId;
    private byte[] thumbnail;
    private Map procDef;    // 流程定义对象
    private Date updateDate;

    public FlowModel() {
        super ( );
        this.created = new Date ( );
    }


}


