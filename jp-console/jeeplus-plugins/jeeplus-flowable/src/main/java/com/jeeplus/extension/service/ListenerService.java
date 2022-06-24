/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.extension.domain.Listener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.extension.mapper.ListenerMapper;

/**
 * 监听器Service
 * @author 刘高峰
 * @version 2021-09-14
 */
@Service
@Transactional
public class ListenerService extends ServiceImpl <ListenerMapper, Listener> {


}
