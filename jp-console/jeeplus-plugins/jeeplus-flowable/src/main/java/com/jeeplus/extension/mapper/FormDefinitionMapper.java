/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.extension.domain.FormDefinition;
import com.jeeplus.extension.service.dto.FormDefinitionDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 流程表单MAPPER接口
 * @author 刘高峰
 * @version 2021-02-02
 */
public interface FormDefinitionMapper extends BaseMapper <FormDefinition> {


    /**
     * 根据Id获取数据
     * @param id
     * @return
     */
    FormDefinitionDTO getById(String id);


    /**
     * 获列表
     * @param queryWrapper
     * @return
     */
    IPage <FormDefinitionDTO> findList(Page <FormDefinitionDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
