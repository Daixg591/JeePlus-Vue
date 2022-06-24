/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.test.onetomany.service.dto.TestDataChild23DTO;
import com.jeeplus.test.onetomany.domain.TestDataChild23;
import com.jeeplus.test.onetomany.mapper.TestDataChild23Mapper;

/**
 * 汽车票Service
 * @author liugf
 * @version 2021-10-17
 */
@Service
@Transactional
public class TestDataChild23Service extends ServiceImpl<TestDataChild23Mapper, TestDataChild23> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public TestDataChild23DTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 查询列表
	 * @param testDataMainId
	 * @return
	 */
	public List <TestDataChild23DTO> findList(String testDataMainId) {
		return  baseMapper.findList (testDataMainId);
	}

}
