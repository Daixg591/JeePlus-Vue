/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.aop.demo.annotation.DemoMode;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.sys.service.dto.DictValueDTO;
import com.jeeplus.sys.domain.DictType;
import com.jeeplus.sys.domain.DictValue;
import com.jeeplus.sys.service.DictTypeService;
import com.jeeplus.sys.service.DictValueService;
import com.jeeplus.sys.service.mapstruct.DictValueWrapper;
import com.jeeplus.sys.utils.DictUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典Controller
 * @author jeeplus
 * @version 2021-05-16
 */

@Api("字典管理")
@RestController
@RequestMapping("/sys/dict")
public class DictController {

	@Autowired
	private DictTypeService dictTypeService;

	@Autowired
	private DictValueService dictValueService;

	@Autowired
	private DictValueWrapper dictValueWrapper;

	/**
	 * 根据字典类型获取字典列表
	 * @param dictTypeId
	 * @return
	 */
	@ApiLog("根据字典类型获取字典列表")
	@PreAuthorize ("hasAuthority('sys:dict:list')")
	@GetMapping("getDictValue")
	public ResponseEntity getDictValue(String dictTypeId) {
		List<DictValueDTO> list = Lists.newArrayList ();
		if(StrUtil.isNotBlank (dictTypeId)) {
			list = dictValueService.lambdaQuery ().eq ( DictValue::getDictTypeId, dictTypeId).orderByAsc (DictValue::getSort).list ().stream ().map (dictValueWrapper::toDTO).collect (Collectors.toList ());
		}
		return ResponseEntity.ok (list);
	}

	/**
	 * 获取字典类型列表
	 * @param dictType
	 * @param page
	 * @return
	 */
	@ApiLog("获取字典类型列表")
	@PreAuthorize ("hasAuthority('sys:dict:list')")
	@GetMapping("type/list")
	public ResponseEntity<IPage<DictType>> data(DictType dictType, Page<DictType> page) throws Exception{
		QueryWrapper<DictType> queryWrapper = QueryWrapperGenerator.buildQueryCondition (dictType, DictType.class);
		IPage<DictType> result = dictTypeService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据id 获取字典类型
	 * @param id
	 * @return
	 */
	@ApiLog("获取字典类型")
	@PreAuthorize ("hasAnyAuthority('sys:dict:view','sys:dict:add','sys:dict:edit')")
	@GetMapping("queryById")
	public ResponseEntity queryById(String id) {
		DictType dictType = dictTypeService.getById (id);
		return ResponseEntity.ok (dictType);
	}

	/**
	 * 根据id获取字典值
	 * @param dictValueId
	 * @return
	 */
	@ApiLog("根据id获取字典值")
	@PreAuthorize ("hasAnyAuthority('sys:dict:view','sys:dict:add','sys:dict:edit')")
	@GetMapping("queryDictValue")
	public ResponseEntity queryDictValue(String dictValueId) {
		DictValue dictValue = dictValueService.getById (dictValueId);
		return ResponseEntity.ok (dictValue);
	}

	/**
	 * 保存字典类型
	 * @param dictType
	 * @return
	 */
	@DemoMode
	@ApiLog("保存字典类型")
	@PreAuthorize ("hasAnyAuthority('sys:dict:add','sys:dict:edit')")
	@PostMapping("save")
	public ResponseEntity save(@RequestBody @Valid DictType dictType) {
		dictTypeService.saveOrUpdate (dictType);
		return ResponseEntity.ok ("保存字典类型'" + dictType.getRemarks () + "'成功！')");
	}

	/**
	 * 保存字典值
	 * @param dictValue
	 * @return
	 */
	@DemoMode
	@ApiLog("保存字典值")
	@PreAuthorize ("hasAnyAuthority('sys:dict:add','sys:dict:edit')")
	@PostMapping("saveDictValue")
	public ResponseEntity saveDictValue(@Valid @RequestBody DictValue dictValue) {
		dictTypeService.saveDictValue(dictValue);
		return ResponseEntity.ok ("保存键值'" + dictValue.getLabel() + "'成功！')");
	}


	/**
	 * 删除字典值
	 * @param ids
	 * @return
	 */
	@DemoMode
	@ApiLog("删除字典值")
	@PreAuthorize ("hasAuthority('sys:dict:edit')")
	@DeleteMapping("deleteDictValue")
	public ResponseEntity deleteDictValue(String ids) {
		dictTypeService.batchDeleteDictValue(ids.split(","));
		return ResponseEntity.ok ("删除键值成功！')");
	}

	/**
	 * 批量删除字典
	 */
	@DemoMode
	@ApiLog("批量删除字典")
	@PreAuthorize ("hasAuthority('sys:dict:del')")
	@DeleteMapping("delete")
	public ResponseEntity delete(String ids) {
		String idArray[] = ids.split ( "," );
		for(String id: idArray){
			dictTypeService.delete ( id );
		}
		return ResponseEntity.ok ( "删除字典成功！" );
	}

	/**
	 * 获取字典集
	 * @return
	 */
	@ApiLog("获取字典集")
	@GetMapping("getDictMap")
	public ResponseEntity getDictMap() {
		return ResponseEntity.ok ( DictUtils.getDictMap ());
	}


}
