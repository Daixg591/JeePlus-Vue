/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.one.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.one.service.dto.TestFormLeaveDTO;
import com.jeeplus.test.one.domain.TestFormLeave;

/**
 * 请假表单MAPPER接口
 * @author 刘高峰
 * @version 2021-10-17
 */
public interface TestFormLeaveMapper extends BaseMapper<TestFormLeave> {

    /**
     * 根据id获取请假表单
     * @param id
     * @return
     */
    TestFormLeaveDTO findById(String id);

    /**
     * 获取请假表单列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <TestFormLeaveDTO> findList(Page <TestFormLeaveDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
