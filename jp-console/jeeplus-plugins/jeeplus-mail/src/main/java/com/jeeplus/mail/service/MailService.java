/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.mail.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.mail.mapper.MailMapper;
import com.jeeplus.mail.domain.Mail;
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
public class MailService extends ServiceImpl <MailMapper, Mail> {


}
