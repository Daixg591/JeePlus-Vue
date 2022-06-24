package com.jeeplus.database.datatable.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class JTableVO {
    private String name;
    private String schema;
    private String catalog;
    private String description;
}
