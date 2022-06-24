/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.sys.constant.CacheNames;
import com.jeeplus.sys.domain.Area;
import com.jeeplus.sys.mapper.AreaMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 区域Service
 * @author jeeplus
 * @version 2021-05-16
 */
@Service
@Transactional
public class AreaService extends TreeService<AreaMapper, Area> {

	@Cacheable(cacheNames = CacheNames.SYS_CACHE_AREA_LIST)
	public List<Area> findAll(){
		return super.list ();
	}

	@CacheEvict(cacheNames = CacheNames.SYS_CACHE_AREA_LIST, allEntries = true)
	public boolean saveOrUpdate(Area area) {
		return super.saveOrUpdate (area);
	}

	@CacheEvict(cacheNames = CacheNames.SYS_CACHE_AREA_LIST, allEntries = true)
	public boolean removeWithChildrenById(String id) {
		return super.removeWithChildrenById (id);
	}

}
