/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service.dto;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据权限Entity
 *
 * @author lgf
 * @version 2021-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataRuleDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 所属菜单
     */
    private String menuId;

    /**
     * 数据规则名称
     */
    private String name;

    /**
     * 实体类名
     */
    private String className;

    /**
     * 规则字段
     */
    private String field;

    /**
     * 规则条件
     */
    private String express;

    /**
     * 规则值
     */
    private String value;

    /**
     * 自定义sql
     */
    private String sqlSegment;

    /**
     * 备注
     */
    private String remarks;


    public String getDataScopeSql() {
        StringBuffer sqlBuffer = new StringBuffer ();
        if (StrUtil.isNotBlank (field) && StrUtil.isNotBlank (value)) {
            sqlBuffer.append (" AND " + field + " " + HtmlUtil.unescape (express) + " " + value + " ");
        }
        if (StrUtil.isNotBlank (sqlSegment)) {
            sqlBuffer.append (" AND " + HtmlUtil.unescape (sqlSegment) + " ");
        }

        return sqlBuffer.toString ();
    }
}
