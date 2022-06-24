/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.mapper;


import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeeplus.test.onetomany.service.dto.TestDataChild21DTO;
import com.jeeplus.test.onetomany.domain.TestDataChild21;

/**
 * 火车票MAPPER接口
 * @author liugf
 * @version 2021-10-17
 */
public interface TestDataChild21Mapper extends BaseMapper<TestDataChild21> {

    /**
     * 根据id获取火车票
     * @param id
     * @return
     */
    TestDataChild21DTO findById(String id);

    /**
     * 获取火车票列表
     *
     * @param TestDataMainFormId
     * @return
     */
    List <TestDataChild21DTO> findList(String TestDataMainFormId);

}
