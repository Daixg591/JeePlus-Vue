/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.mail.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.mail.domain.MailCompose;
import com.jeeplus.mail.mapper.MailComposeMapper;
import com.jeeplus.mail.service.dto.MailComposeDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 发件箱Service
 *
 * @author jeeplus
 * @version 2021-06-12
 */
@Service
@Transactional(readOnly = true)
public class MailComposeService extends ServiceImpl <MailComposeMapper, MailCompose> {

    public MailComposeDTO get(String id) {
        return baseMapper.get(id);
    }


    public IPage <MailComposeDTO> findPage(Page <MailComposeDTO> page, QueryWrapper <MailComposeDTO> queryWrapper) {
        queryWrapper.eq ( "a.del_flag", 0 );
        return baseMapper.findList (page, queryWrapper);
    }


}
