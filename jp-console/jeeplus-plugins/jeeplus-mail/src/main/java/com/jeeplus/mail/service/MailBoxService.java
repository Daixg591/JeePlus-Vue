/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.mail.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.mail.domain.MailBox;
import com.jeeplus.mail.mapper.MailBoxMapper;
import com.jeeplus.mail.service.dto.MailBoxDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 收件箱Service
 *
 * @author jeeplus
 * @version 2021-06-12
 */
@Service
@Transactional(readOnly = true)
public class MailBoxService extends ServiceImpl <MailBoxMapper, MailBox> {


    public MailBoxDTO get(String id) {
        return baseMapper.get(id);
    }


    public IPage <MailBoxDTO> findPage(Page <MailBoxDTO> page, QueryWrapper <MailBoxDTO> queryWrapper) {
        queryWrapper.eq ( "a.del_flag", 0 );
        return baseMapper.findList (page, queryWrapper);
    }


}
