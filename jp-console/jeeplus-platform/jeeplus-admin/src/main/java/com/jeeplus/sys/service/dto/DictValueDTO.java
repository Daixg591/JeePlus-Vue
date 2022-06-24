/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service.dto;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据字典Entity
 *
 * @author lgf
 * @version 2021-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictValueDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 标签页
     */
    @Excel(name = "标签名")
    private String label;

    /**
     * 键值
     */
    @Excel(name = "键值")
    private String value;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private String sort;

    /**
     * 字典类型
     */
    private DictTypeDTO dictTypeDTO;

    /**
     * 备注
     */
    private String remarks;
    /**
     * 构造函数
     *
     * @param dictTypeDTO
     */
    public DictValueDTO(DictTypeDTO dictTypeDTO) {
        this.dictTypeDTO = dictTypeDTO;
    }

}
