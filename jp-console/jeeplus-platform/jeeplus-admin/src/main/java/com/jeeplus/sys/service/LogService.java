/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.sys.service.dto.LogDTO;
import com.jeeplus.sys.domain.Log;
import com.jeeplus.sys.mapper.LogMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 日志Service
 * @author jeeplus
 * @version 2021-05-16
 */
@Service
@Transactional
public class LogService extends ServiceImpl<LogMapper, Log> {


    /**
     * 自定义分页检索
     * @param page
     * @param queryWrapper
     * @return
     */
    public IPage <LogDTO> findPage(Page <LogDTO> page, QueryWrapper queryWrapper) {
        queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
        return  baseMapper.findList (page, queryWrapper);


    }
}
