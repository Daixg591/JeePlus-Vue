/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.extension.mapper.FlowConditionMapper;
import com.jeeplus.extension.domain.FlowCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程抄送Service
 * @author 刘高峰
 * @version 2021-10-10
 */
@Service
@Transactional
public class FlowConditionService extends ServiceImpl <FlowConditionMapper, FlowCondition> {


}
