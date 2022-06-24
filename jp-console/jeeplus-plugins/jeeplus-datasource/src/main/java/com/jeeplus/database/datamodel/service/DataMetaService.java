/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.database.datamodel.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.database.datamodel.domain.DataMeta;
import com.jeeplus.database.datamodel.mapper.DataMetaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据资源Service
 *
 * @author 刘高峰
 * @version 2021-08-07
 */
@Service
@Transactional
public class DataMetaService extends ServiceImpl <DataMetaMapper, DataMeta> {




}
