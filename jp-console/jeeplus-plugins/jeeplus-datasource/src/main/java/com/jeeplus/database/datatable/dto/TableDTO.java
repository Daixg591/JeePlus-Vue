package com.jeeplus.database.datatable.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ddlutils.model.Column;

@Data
@EqualsAndHashCode(callSuper = false)
public class TableDTO {
    private String dataSourceId;
    private String name;
    private String schema;
    private String catalog;
    private String description;
    Column[] columns = {};
}
