package com.jeeplus.form.service.dto;

import lombok.Data;

@Data
public class RelationDTO {
    private String primaryKey;
    private String foreignKey;
    private String childTableName;
    private String type;
}
