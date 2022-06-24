/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.sys.domain.DictValue;
import com.jeeplus.sys.mapper.DictValueMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据字典Service
 * @author lgf
 * @version 2021-01-16
 */
@Service
@Transactional
public class DictValueService extends ServiceImpl<DictValueMapper, DictValue> {

}

