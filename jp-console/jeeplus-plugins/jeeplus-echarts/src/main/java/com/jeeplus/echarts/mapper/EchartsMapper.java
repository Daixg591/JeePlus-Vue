/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.echarts.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.echarts.domain.Echarts;
import com.jeeplus.echarts.service.dto.EchartsDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 图表组件MAPPER接口
 *
 * @author 刘高峰
 * @version 2021-08-13
 */
public interface EchartsMapper extends BaseMapper <Echarts> {

    /**
     * 获取图表列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <EchartsDTO> findList(Page <EchartsDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
