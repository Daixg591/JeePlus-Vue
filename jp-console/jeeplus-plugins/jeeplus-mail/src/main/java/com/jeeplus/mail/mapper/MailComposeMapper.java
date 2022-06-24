/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.mail.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.mail.domain.MailCompose;
import com.jeeplus.mail.service.dto.MailComposeDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 发件箱MAPPER接口
 *
 * @author jeeplus
 * @version 2021-06-12
 */
public interface MailComposeMapper extends BaseMapper <MailCompose> {

    MailComposeDTO get(String id);
    /**
     * 获发件箱列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <MailComposeDTO> findList(Page <MailComposeDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}
