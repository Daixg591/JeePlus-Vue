/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.office.service;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.office.domain.DocCategory;
import com.jeeplus.office.mapper.DocCategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文书模板Service
 * @author sunyinhui
 * @version 2021-06-23
 */
@Service
@Transactional
public class DocCategoryService extends TreeService<DocCategoryMapper, DocCategory> {


}
