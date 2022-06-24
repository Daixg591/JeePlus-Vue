/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.form.service.dto;

import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 业务表字段
 *
 * @author jeeplus
 * @version 2021-09-15
 */
@Data
public class DataTableColumnDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 归属表
     */
    private DataTableDTO dataTable;

    /**
     * 列名
     */
    private String name;

    /**
     * 描述
     */
    private String comments;

    /**
     * JDBC类型
     */
    private String jdbcType;

    public String getName() {
        return StringUtils.lowerCase(name);
    }

    /**
     * 获取列名和说明
     *
     * @return
     */
    public String getNameAndComments() {
        return getName() + (comments == null ? "" : "  :  " + comments);
    }

}


