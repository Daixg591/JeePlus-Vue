/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.mail.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.mail.domain.MailTrash;
import com.jeeplus.mail.service.dto.MailTrashDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 邮件MAPPER接口
 * @author 刘高峰
 * @version 2021-08-28
 */
public interface MailTrashMapper extends BaseMapper <MailTrash> {

    MailTrashDTO get(String id);
    /**
     * 获发件箱列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <MailTrashDTO> findList(Page <MailTrashDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}
