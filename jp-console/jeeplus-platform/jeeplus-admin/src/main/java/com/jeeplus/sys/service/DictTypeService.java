/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jeeplus.sys.constant.CacheNames;
import com.jeeplus.sys.service.dto.DictTypeDTO;
import com.jeeplus.sys.service.dto.DictValueDTO;
import com.jeeplus.sys.domain.DictType;
import com.jeeplus.sys.domain.DictValue;
import com.jeeplus.sys.mapper.DictTypeMapper;
import com.jeeplus.sys.service.mapstruct.DictTypeWrapper;
import com.jeeplus.sys.service.mapstruct.DictValueWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据字典Service
 * @author lgf
 * @version 2021-01-16
 */
@Service
@Transactional
public class DictTypeService extends ServiceImpl<DictTypeMapper, DictType> {

	@Autowired
	private DictValueService dictValueService;
	@Autowired
	private DictTypeWrapper dictTypeWrapper;
	@Autowired
	private DictValueWrapper dictValueWrapper;

	@Transactional(readOnly = true)
	public DictTypeDTO get(String id) {
		DictTypeDTO dictTypeDTO = dictTypeWrapper.toDTO (super.getById (id));
		dictTypeDTO.setDictValueDTOList (dictValueService.lambdaQuery ().eq (DictValue::getDictTypeId, id).orderByAsc ( DictValue::getSort ).list ().stream ().map (dictValueWrapper::toDTO).collect(Collectors.toList()));
		return dictTypeDTO;
	}


	@CacheEvict(value = CacheNames.SYS_CACHE_DICT_MAP, allEntries = true)
	public boolean saveOrUpdate(DictType dictType) {
		return super.saveOrUpdate (dictType);
	}

	@CacheEvict(value = CacheNames.SYS_CACHE_DICT_MAP, allEntries = true)
	public void saveDictValue(DictValue dictValue) {
		dictValueService.saveOrUpdate (dictValue);
	}

	@CacheEvict(value = CacheNames.SYS_CACHE_DICT_MAP, allEntries = true)
	public void batchDeleteDictValue(String[] ids) {
		dictValueService.removeByIds (Lists.newArrayList (ids));
	}

	@CacheEvict(value = CacheNames.SYS_CACHE_DICT_MAP, allEntries = true)
	public void delete(String id) {
		super.removeById (id);
		dictValueService.lambdaUpdate ().eq (DictValue::getDictTypeId, id).remove ();
	}

	@Cacheable(CacheNames.SYS_CACHE_DICT_MAP)
	public List<DictTypeDTO> getDict () {
		List<DictTypeDTO> list = dictTypeWrapper.toDTO (super.list ());
		list.forEach (dictTypeDTO -> {
			List <DictValueDTO> dictValueDTOs = dictValueService.lambdaQuery ().eq (DictValue::getDictTypeId, dictTypeDTO.getId ()).orderByAsc ( DictValue::getSort ).list ().stream ().map (dictValueWrapper::toDTO).collect(Collectors.toList());
			dictTypeDTO.setDictValueDTOList (dictValueDTOs);
		});
		return list;
	}

}
