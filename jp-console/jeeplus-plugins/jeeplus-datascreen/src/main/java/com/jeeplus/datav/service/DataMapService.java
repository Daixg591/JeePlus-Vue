/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.datav.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.datav.domain.DataMap;
import com.jeeplus.datav.mapper.DataMapMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 地图Service
 * @author 刘高峰
 * @version 2021-04-11
 */
@Service
@Transactional
public class DataMapService extends ServiceImpl <DataMapMapper, DataMap> {

}
