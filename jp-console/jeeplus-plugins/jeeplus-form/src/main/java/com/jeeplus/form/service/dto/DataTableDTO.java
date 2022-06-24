/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.form.service.dto;

import com.google.common.collect.Lists;
import com.jeeplus.core.service.dto.BaseDTO;
import com.jeeplus.database.datalink.domain.DataSource;
import lombok.Data;

import java.util.List;

/**
 * 业务表Entity
 *
 * @author jeeplus
 * @version 2021-09-15
 */
@Data
public class DataTableDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String comments;

    /**
     * 表列
     */
    private List<DataTableColumnDTO> columnList = Lists.newArrayList();

    /**
     * 数据源
     */
    private DataSource dataSource;

    /**
     * 获取列名和说明
     *
     * @return
     */
    public String getNameAndComments() {
        return getName() + (comments == null ? "" : "  :  " + comments);
    }


}


