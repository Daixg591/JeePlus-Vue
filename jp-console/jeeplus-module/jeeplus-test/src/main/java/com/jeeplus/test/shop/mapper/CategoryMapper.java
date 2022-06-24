/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.mapper;

import com.jeeplus.core.domain.TreeMapper;
import java.util.List;
import com.jeeplus.test.shop.service.dto.CategoryDTO;
import com.jeeplus.test.shop.domain.Category;

/**
 * 商品类型MAPPER接口
 * @author liugf
 * @version 2021-10-17
 */
public interface CategoryMapper extends TreeMapper<Category> {

    /**
     * 根据id获取商品类型
     * @param id
     * @return
     */
    CategoryDTO findById(String id);

    /**
     * 获取商品类型列表
     *
     * @return
     */
    List <CategoryDTO> findList();

}
