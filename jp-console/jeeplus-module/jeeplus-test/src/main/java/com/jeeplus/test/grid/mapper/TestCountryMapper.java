/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.grid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.grid.service.dto.TestCountryDTO;
import com.jeeplus.test.grid.domain.TestCountry;

/**
 * 国家MAPPER接口
 * @author 刘高峰
 * @version 2021-10-17
 */
public interface TestCountryMapper extends BaseMapper<TestCountry> {

    /**
     * 根据id获取国家
     * @param id
     * @return
     */
    TestCountryDTO findById(String id);

    /**
     * 获取国家列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <TestCountryDTO> findList(Page <TestCountryDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
