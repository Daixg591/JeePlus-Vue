/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service.dto;

import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 岗位Entity
 *
 * @author 刘高峰
 * @version 2021-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PostDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 岗位名称
     */
    @NotEmpty
    private String name;

    /**
     * 岗位编码
     */
    @NotEmpty
    private String code;

    /**
     * 岗位类型
     */
    @NotEmpty
    private String type;

    /**
     * 岗位状态
     */
    private String status;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 岗位排序
     */
    private Integer sort;        // 岗位排序

    public PostDTO() {
        super();
    }

    public PostDTO(String id) {
        super(id);
    }

}
