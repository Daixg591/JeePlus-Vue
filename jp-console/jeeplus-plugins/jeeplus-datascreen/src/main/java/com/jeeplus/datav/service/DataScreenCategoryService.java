/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.datav.service;

import com.jeeplus.datav.domain.DataScreenCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.datav.mapper.DataScreenCategoryMapper;

/**
 * 大屏分类Service
 * @author 刘高峰
 * @version 2021-03-29
 */
@Service
@Transactional(readOnly = true)
public class DataScreenCategoryService extends TreeService<DataScreenCategoryMapper, DataScreenCategory> {


}
