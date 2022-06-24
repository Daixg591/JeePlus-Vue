/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeeplus.test.onetomany.service.dto.TestDataChild23DTO;
import com.jeeplus.test.onetomany.domain.TestDataChild23;

/**
 * 汽车票MAPPER接口
 * @author liugf
 * @version 2021-10-17
 */
public interface TestDataChild23Mapper extends BaseMapper<TestDataChild23> {

    /**
     * 根据id获取汽车票
     * @param id
     * @return
     */
    TestDataChild23DTO findById(String id);

    /**
     * 获取汽车票列表
     *
     * @param TestDataMainFormId
     * @return
     */
    List <TestDataChild23DTO> findList(String TestDataMainFormId);

}
