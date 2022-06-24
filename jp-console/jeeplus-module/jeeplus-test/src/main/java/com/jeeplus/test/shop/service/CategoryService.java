/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.jeeplus.core.service.TreeDTOService;
import com.jeeplus.test.shop.service.dto.CategoryDTO;
import com.jeeplus.test.shop.domain.Category;
import com.jeeplus.test.shop.mapper.CategoryMapper;

/**
 * 商品类型Service
 * @author liugf
 * @version 2021-10-17
 */
@Service
@Transactional
public class CategoryService extends TreeDTOService<CategoryMapper, Category, CategoryDTO> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public CategoryDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	public List <CategoryDTO> listDTO() {
		return  baseMapper.findList ();
	}

	public boolean saveOrUpdate(Category category) {
		return super.saveOrUpdate (category);
	}

	public boolean removeWithChildrenById(String id) {
		return super.removeWithChildrenById (id);
	}

}
