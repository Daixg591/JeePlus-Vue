/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.tree.mapper;

import com.jeeplus.core.domain.TreeMapper;
import java.util.List;
import com.jeeplus.test.tree.service.dto.TestTreeDTO;
import com.jeeplus.test.tree.domain.TestTree;

/**
 * 组织机构MAPPER接口
 * @author lgf
 * @version 2021-10-17
 */
public interface TestTreeMapper extends TreeMapper<TestTree> {

    /**
     * 根据id获取组织机构
     * @param id
     * @return
     */
    TestTreeDTO findById(String id);

    /**
     * 获取组织机构列表
     *
     * @return
     */
    List <TestTreeDTO> findList();

}
