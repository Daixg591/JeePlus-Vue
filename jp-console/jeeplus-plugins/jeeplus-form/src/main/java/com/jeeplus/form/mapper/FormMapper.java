/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.form.mapper;

import cn.hutool.db.Entity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.form.domain.Form;
import com.jeeplus.form.service.dto.DataTableColumnDTO;
import com.jeeplus.form.service.dto.DataTableDTO;
import com.jeeplus.form.service.dto.FormDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 数据表单MAPPER接口
 * @author 刘高峰
 * @version 2021-09-24
 */
public interface FormMapper extends BaseMapper <Form> {

    /**
     * 获取对象
     *
     * @param id
     * @return
     */
    FormDTO getById(@Param ( "id" ) String id);

    /**
     * 获取表单列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <FormDTO> findList(Page <FormDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);


    @Select ( "${sqlSegment}" )
    IPage <Entity> findPage(Page <Entity> page, @Param("sqlSegment") String sqlSegment);
}
