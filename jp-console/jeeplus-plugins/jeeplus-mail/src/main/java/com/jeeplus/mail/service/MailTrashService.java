/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.mail.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.mail.domain.MailTrash;
import com.jeeplus.mail.mapper.MailTrashMapper;
import com.jeeplus.mail.service.dto.MailTrashDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 邮件Service
 * @author 刘高峰
 * @version 2021-08-28
 */
@Service
@Transactional(readOnly = true)
public class MailTrashService extends ServiceImpl <MailTrashMapper, MailTrash> {

	public MailTrashDTO get(String id) {
		return baseMapper.get(id);
	}


	public IPage <MailTrashDTO> findPage(Page <MailTrashDTO> page, QueryWrapper <MailTrashDTO> queryWrapper) {
		queryWrapper.eq ( "a.del_flag", 0 ); //排除已删除
		return baseMapper.findList (page, queryWrapper);
	}
}
