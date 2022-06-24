/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.onetomany.service.dto.TestDataMainFormDTO;
import com.jeeplus.test.onetomany.domain.TestDataMainForm;

/**
 * 票务代理MAPPER接口
 * @author liugf
 * @version 2021-10-17
 */
public interface TestDataMainFormMapper extends BaseMapper<TestDataMainForm> {

    /**
     * 根据id获取票务代理
     * @param id
     * @return
     */
    TestDataMainFormDTO findById(String id);

    /**
     * 获取票务代理列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <TestDataMainFormDTO> findList(Page <TestDataMainFormDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
