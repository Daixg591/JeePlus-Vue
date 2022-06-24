/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.mail.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.mail.service.MailTrashService;
import com.jeeplus.mail.service.dto.MailTrashDTO;
import com.jeeplus.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 垃圾箱Controller
 * @author 刘高峰
 * @version 2021-08-28
 */
@RestController
@RequestMapping(value = "/mail/trash")
public class MailTrashController {

	@Autowired
	private MailTrashService mailTrashService;

	/**
	 * 垃圾邮件列表数据
	 */
	@GetMapping("list")
	public ResponseEntity<IPage <MailTrashDTO>> list(MailTrashDTO mailTrashDTO, Page <MailTrashDTO> page) throws Exception {

		QueryWrapper <MailTrashDTO> queryWrapper = QueryWrapperGenerator.buildQueryCondition (mailTrashDTO, MailTrashDTO.class);
		queryWrapper.eq ( "a.create_by", UserUtils.getCurrentUserDTO ().getId () );
		IPage <MailTrashDTO> result = mailTrashService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}

	/**
	 * 根据Id获取邮件数据
	 */
	@GetMapping("queryById")
	public ResponseEntity<MailTrashDTO> queryById(String id) {
		MailTrashDTO mailTrash = mailTrashService.get ( id );
		return ResponseEntity.ok ( mailTrash );
	}

	/**
	 * 批量删除垃圾邮件
	 */
	@DeleteMapping("delete")
	public ResponseEntity delete(String ids) {
		String idArray[] =ids.split(",");
		mailTrashService.removeByIds ( Lists.newArrayList ( idArray) );
		return ResponseEntity.ok ("删除邮件成功");
	}

}
