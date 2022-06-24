/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.LogDTO;
import com.jeeplus.sys.domain.Log;
import org.apache.ibatis.annotations.Param;

/**
 * 日志MAPPER接口
 *
 * @author jeeplus
 * @version 2021-05-16
 */
public interface LogMapper extends BaseMapper<Log> {

    /**
     * 获日志列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <LogDTO> findList(Page <LogDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}
