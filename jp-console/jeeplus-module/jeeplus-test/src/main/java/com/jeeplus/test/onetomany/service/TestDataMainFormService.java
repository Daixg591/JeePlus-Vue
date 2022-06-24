/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service;

import com.jeeplus.sys.constant.CommonConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.onetomany.service.dto.TestDataMainFormDTO;
import com.jeeplus.test.onetomany.service.dto.TestDataChild23DTO;
import com.jeeplus.test.onetomany.service.dto.TestDataChild21DTO;
import com.jeeplus.test.onetomany.service.dto.TestDataChild22DTO;
import com.jeeplus.test.onetomany.service.mapstruct.TestDataMainFormWrapper;
import com.jeeplus.test.onetomany.service.mapstruct.TestDataChild23Wrapper;
import com.jeeplus.test.onetomany.service.mapstruct.TestDataChild21Wrapper;
import com.jeeplus.test.onetomany.service.mapstruct.TestDataChild22Wrapper;
import com.jeeplus.test.onetomany.domain.TestDataMainForm;
import com.jeeplus.test.onetomany.domain.TestDataChild23;
import com.jeeplus.test.onetomany.domain.TestDataChild21;
import com.jeeplus.test.onetomany.domain.TestDataChild22;
import com.jeeplus.test.onetomany.mapper.TestDataMainFormMapper;

/**
 * 票务代理Service
 * @author liugf
 * @version 2021-10-17
 */
@Service
@Transactional
public class TestDataMainFormService extends ServiceImpl<TestDataMainFormMapper, TestDataMainForm> {
	/**
	* 子表service
	*/
	@Autowired
	private TestDataChild23Service testDataChild23Service;
	/**
	* 子表service
	*/
	@Autowired
	private TestDataChild21Service testDataChild21Service;
	/**
	* 子表service
	*/
	@Autowired
	private TestDataChild22Service testDataChild22Service;

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public TestDataMainFormDTO findById(String id) {
		TestDataMainFormDTO testDataMainFormDTO = baseMapper.findById ( id );
		testDataMainFormDTO.setTestDataChild23DTOList(testDataChild23Service.findList(id));
		testDataMainFormDTO.setTestDataChild21DTOList(testDataChild21Service.findList(id));
		testDataMainFormDTO.setTestDataChild22DTOList(testDataChild22Service.findList(id));
		return testDataMainFormDTO;
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <TestDataMainFormDTO> findPage(Page <TestDataMainFormDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);
	}

	/**
	* 保存或者更新
	* @param  testDataMainFormDTO
	* @return
	*/
	public void saveOrUpdate(TestDataMainFormDTO testDataMainFormDTO) {
		TestDataMainForm testDataMainForm =  TestDataMainFormWrapper.INSTANCE.toEntity ( testDataMainFormDTO );
		super.saveOrUpdate (testDataMainForm);
		for (TestDataChild23DTO testDataChild23DTO : testDataMainFormDTO.getTestDataChild23DTOList ()){
			if ( CommonConstants.DELETED.equals ( testDataChild23DTO.getDelFlag()) ){
				testDataChild23Service.removeById ( testDataChild23DTO.getId () );
			}else{
				TestDataChild23 testDataChild23 = TestDataChild23Wrapper.INSTANCE.toEntity ( testDataChild23DTO );
				testDataChild23.setTestDataMainId ( testDataMainForm.getId () );
				testDataChild23Service.saveOrUpdate ( testDataChild23 );
			}
		}
		for (TestDataChild21DTO testDataChild21DTO : testDataMainFormDTO.getTestDataChild21DTOList ()){
			if ( CommonConstants.DELETED.equals ( testDataChild21DTO.getDelFlag()) ){
				testDataChild21Service.removeById ( testDataChild21DTO.getId () );
			}else{
				TestDataChild21 testDataChild21 = TestDataChild21Wrapper.INSTANCE.toEntity ( testDataChild21DTO );
				testDataChild21.setTestDataMainId ( testDataMainForm.getId () );
				testDataChild21Service.saveOrUpdate ( testDataChild21 );
			}
		}
		for (TestDataChild22DTO testDataChild22DTO : testDataMainFormDTO.getTestDataChild22DTOList ()){
			if ( CommonConstants.DELETED.equals ( testDataChild22DTO.getDelFlag()) ){
				testDataChild22Service.removeById ( testDataChild22DTO.getId () );
			}else{
				TestDataChild22 testDataChild22 = TestDataChild22Wrapper.INSTANCE.toEntity ( testDataChild22DTO );
				testDataChild22.setTestDataMainId ( testDataMainForm.getId () );
				testDataChild22Service.saveOrUpdate ( testDataChild22 );
			}
		}
	}

	/**
	 * 删除
	 * @param  id
	 * @return
	 */
	public void removeById(String id) {
		super.removeById ( id );
		testDataChild23Service.lambdaUpdate ().eq ( TestDataChild23::getTestDataMainId, id ).remove ();
		testDataChild21Service.lambdaUpdate ().eq ( TestDataChild21::getTestDataMainId, id ).remove ();
		testDataChild22Service.lambdaUpdate ().eq ( TestDataChild22::getTestDataMainId, id ).remove ();
	}

}
