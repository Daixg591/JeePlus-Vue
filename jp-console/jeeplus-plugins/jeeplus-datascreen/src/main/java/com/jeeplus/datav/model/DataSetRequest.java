package com.jeeplus.datav.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataSetRequest {

    private String db;
    private String sql;
    private String[] field;
    private String[] defaultValue;
}
