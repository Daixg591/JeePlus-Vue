/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.validation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.test.validation.domain.TestValidation;
import com.jeeplus.test.validation.mapper.TestValidationMapper;

/**
 * 测试校验功能Service
 * @author lgf
 * @version 2021-10-17
 */
@Service
@Transactional
public class TestValidationService extends ServiceImpl<TestValidationMapper, TestValidation> {

}
