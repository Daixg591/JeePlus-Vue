/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service;


import com.jeeplus.extension.domain.FormCategory;
import com.jeeplus.extension.mapper.FormCategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;

/**
 * 流程分类Service
 * @author 刘高峰
 * @version 2021-02-02
 */
@Service
@Transactional
public class FormCategoryService extends TreeService<FormCategoryMapper, FormCategory> {

}
