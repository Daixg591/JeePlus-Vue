package com.jeeplus.core.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/*
 * 数据Entity类
 * @author jeeplus
 * @version 2021-05-16
 */
@Data
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 实体主键
     */
    @TableId
    private String id;

    /**
     * 创建日期
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 更新日期
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 逻辑删除标记
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer delFlag;


    /**
     * 默认构造函数
     */

    public BaseEntity () {

    }

    /**
     * 构造函数
     */
    public BaseEntity (String id) {
        this.id = id;
    }



}
