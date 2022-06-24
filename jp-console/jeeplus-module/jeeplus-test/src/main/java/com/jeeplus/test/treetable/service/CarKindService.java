/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.treetable.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeeplus.core.service.TreeService;
import com.jeeplus.test.treetable.domain.CarKind;
import com.jeeplus.test.treetable.mapper.CarKindMapper;

/**
 * 车系Service
 * @author lgf
 * @version 2021-10-17
 */
@Service
@Transactional
public class CarKindService extends TreeService<CarKindMapper, CarKind> {

	public boolean saveOrUpdate(CarKind carKind) {
		return super.saveOrUpdate (carKind);
	}

	public boolean removeWithChildrenById(String id) {
		return super.removeWithChildrenById (id);
	}

}
