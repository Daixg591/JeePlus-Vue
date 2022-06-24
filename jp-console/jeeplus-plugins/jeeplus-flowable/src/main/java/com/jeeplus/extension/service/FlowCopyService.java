/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.extension.domain.FlowCopy;
import com.jeeplus.extension.mapper.FlowCopyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程抄送Service
 * @author 刘高峰
 * @version 2021-09-10
 */
@Service
@Transactional
public class FlowCopyService extends ServiceImpl <FlowCopyMapper, FlowCopy> {

}
