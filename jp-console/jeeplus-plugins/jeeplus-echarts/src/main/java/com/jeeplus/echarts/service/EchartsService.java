/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.echarts.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.echarts.domain.Echarts;
import com.jeeplus.echarts.mapper.EchartsMapper;
import com.jeeplus.echarts.service.dto.EchartsDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 图表组件Service
 *
 * @author 刘高峰
 * @version 2021-08-13
 */
@Service
@Transactional
public class EchartsService extends ServiceImpl <EchartsMapper, Echarts> {

    /**
     * 查询角色下关联的用户
     * @param page
     * @param queryWrapper
     * @return
     */
    public IPage <EchartsDTO> findPage(Page <EchartsDTO> page, QueryWrapper queryWrapper) {
        queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
        return  baseMapper.findList (page, queryWrapper);


    }
}
