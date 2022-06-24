/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.treetable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.treetable.service.dto.CarDTO;
import com.jeeplus.test.treetable.domain.Car;

/**
 * 车辆MAPPER接口
 * @author lgf
 * @version 2021-10-17
 */
public interface CarMapper extends BaseMapper<Car> {

    /**
     * 根据id获取车辆
     * @param id
     * @return
     */
    CarDTO findById(String id);

    /**
     * 获取车辆列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <CarDTO> findList(Page <CarDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
