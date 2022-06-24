/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.tree.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.jeeplus.core.service.TreeDTOService;
import com.jeeplus.test.tree.service.dto.TestTreeDTO;
import com.jeeplus.test.tree.domain.TestTree;
import com.jeeplus.test.tree.mapper.TestTreeMapper;

/**
 * 组织机构Service
 * @author lgf
 * @version 2021-10-17
 */
@Service
@Transactional
public class TestTreeService extends TreeDTOService<TestTreeMapper, TestTree, TestTreeDTO> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public TestTreeDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	public List <TestTreeDTO> listDTO() {
		return  baseMapper.findList ();
	}

	public boolean saveOrUpdate(TestTree testTree) {
		return super.saveOrUpdate (testTree);
	}

	public boolean removeWithChildrenById(String id) {
		return super.removeWithChildrenById (id);
	}

}
