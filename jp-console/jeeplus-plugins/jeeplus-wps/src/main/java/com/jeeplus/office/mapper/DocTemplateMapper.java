/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.office.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.office.domain.DocTemplate;
import com.jeeplus.office.service.dto.DocTemplateDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 文档模板MAPPER接口
 * @version 2021-07-23
 */
public interface DocTemplateMapper extends BaseMapper <DocTemplate> {


    /**
     * 获取用户列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <DocTemplateDTO> findList(Page <DocTemplateDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}
