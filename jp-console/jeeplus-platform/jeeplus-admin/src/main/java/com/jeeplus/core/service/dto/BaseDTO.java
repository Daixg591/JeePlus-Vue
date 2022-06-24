package com.jeeplus.core.service.dto;


import com.jeeplus.config.swagger.IgnoreSwaggerParameter;
import com.jeeplus.sys.service.dto.UserDTO;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiIgnore
public abstract class BaseDTO <T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 实体主键
     */
    protected String id;

    /**
     * 创建日期
     */
    @IgnoreSwaggerParameter
    protected Date createDate;

    /**
     * 创建人
     */
    @IgnoreSwaggerParameter
    protected UserDTO createBy;

    /**
     * 更新日期
     */
    @IgnoreSwaggerParameter
    protected Date updateDate;

    /**
     * 更新人
     */
    @IgnoreSwaggerParameter
    protected UserDTO updateBy;

    /**
     * 逻辑删除标记
     */
    @IgnoreSwaggerParameter
    protected Integer delFlag;

    /**
     * 构造函数
     */
    public BaseDTO () {

    }

    /**
     * 构造函数
     * @param id
     */
    public BaseDTO (String id) {
        this.id = id;
    }



}

