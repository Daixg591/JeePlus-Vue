/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.mail.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.mail.domain.MailBox;
import com.jeeplus.mail.service.dto.MailBoxDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 收件箱MAPPER接口
 *
 * @author jeeplus
 * @version 2021-06-12
 */
public interface MailBoxMapper extends BaseMapper <MailBox> {


    public MailBoxDTO get(String id);
    /**
     * 获发件箱列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <MailBoxDTO> findList(Page <MailBoxDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
