/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.test.onetomany.service.dto.TestDataChild22DTO;
import com.jeeplus.test.onetomany.domain.TestDataChild22;
import com.jeeplus.test.onetomany.mapper.TestDataChild22Mapper;

/**
 * 飞机票Service
 * @author liugf
 * @version 2021-10-17
 */
@Service
@Transactional
public class TestDataChild22Service extends ServiceImpl<TestDataChild22Mapper, TestDataChild22> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public TestDataChild22DTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 查询列表
	 * @param testDataMainId
	 * @return
	 */
	public List <TestDataChild22DTO> findList(String testDataMainId) {
		return  baseMapper.findList (testDataMainId);
	}

}
