/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.notify.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.notify.domain.Notify;
import com.jeeplus.notify.service.dto.NotifyDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 通知通告MAPPER接口
 *
 * @author jeeplus
 * @version 2021-05-16
 */
public interface NotifyMapper extends BaseMapper <Notify> {

    /**
     * 获取通知
     */
    NotifyDTO getById(String id);


    /**
     * 获取通知数目
     *
     * @return
     */
    Long findCount(@Param ( "currentUserId" ) String currentUserId, @Param ( "isSelf" ) boolean isSelf, @Param("readFlag") String readFlag);

    /**
     * 获取列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <NotifyDTO> findList(Page <NotifyDTO> page,
                               @Param ( "currentUserId" ) String currentUserId,
                               @Param ( "isSelf" ) boolean isSelf,
                               @Param("readFlag") String readFlag,
                               @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
