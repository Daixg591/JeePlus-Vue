/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.extension.domain.Button;
import com.jeeplus.extension.mapper.ButtonMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 常用按钮Service
 * @author 刘高峰
 * @version 2021-09-07
 */
@Service
@Transactional
public class ButtonService extends ServiceImpl <ButtonMapper, Button> {


}
