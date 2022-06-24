/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.extension.domain.FlowCopy;
import com.jeeplus.extension.service.dto.FlowCopyDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 流程抄送MAPPER接口
 * @author 刘高峰
 * @version 2021-10-10
 */
public interface FlowCopyMapper extends BaseMapper <FlowCopy> {

    /**
     * 获列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <FlowCopyDTO> findList(Page <FlowCopyDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}
