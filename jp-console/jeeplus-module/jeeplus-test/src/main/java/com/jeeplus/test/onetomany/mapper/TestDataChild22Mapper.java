/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeeplus.test.onetomany.service.dto.TestDataChild22DTO;
import com.jeeplus.test.onetomany.domain.TestDataChild22;

/**
 * 飞机票MAPPER接口
 * @author liugf
 * @version 2021-10-17
 */
public interface TestDataChild22Mapper extends BaseMapper<TestDataChild22> {

    /**
     * 根据id获取飞机票
     * @param id
     * @return
     */
    TestDataChild22DTO findById(String id);

    /**
     * 获取飞机票列表
     *
     * @param TestDataMainFormId
     * @return
     */
    List <TestDataChild22DTO> findList(String TestDataMainFormId);

}
