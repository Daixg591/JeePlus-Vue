/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.datav.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.datav.domain.DataScreen;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.datav.mapper.DataScreenMapper;

/**
 * 大屏Service
 * @author 刘高峰
 * @version 2021-03-29
 */
@Service
@Transactional
public class DataScreenService extends ServiceImpl <DataScreenMapper, DataScreen> {


}
