/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.datav.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.database.datalink.domain.DataSource;
import com.jeeplus.database.datalink.jdbc.DBPool;
import com.jeeplus.database.datalink.service.DataSourceService;
import com.jeeplus.datav.domain.DataScreen;
import com.jeeplus.datav.model.DataSetRequest;
import com.jeeplus.datav.service.DataScreenService;
import com.jeeplus.datav.service.dto.DataScreenDTO;
import com.jeeplus.datav.service.mapstruct.DataScreenWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 大屏Controller
 * @author 刘高峰
 * @version 2021-03-29
 */
@RestController
@RequestMapping(value = "/datav/dataScreen")
public class DataScreenController {

	@Autowired
	private DataScreenService dataScreenService;
	@Autowired
	private DataSourceService dataSourceService;
	@Autowired
	private DataScreenWrapper dataScreenWrapper;

	/**
	 * 大屏设计列表数据
	 */
	@PreAuthorize("hasAuthority('datav:dataScreen:list')")
	@GetMapping("list")
	public ResponseEntity<IPage <DataScreenDTO>> list(DataScreen dataScreen, Page <DataScreen> page) throws Exception {

		QueryWrapper <DataScreen> queryWrapper = QueryWrapperGenerator.buildQueryCondition ( dataScreen, DataScreen.class );
		Page<DataScreen> result = dataScreenService.page ( page, queryWrapper );
		return ResponseEntity.ok ( dataScreenWrapper.toDTO ( result ) );
	}

	/**
	 * 根据Id获取大屏设计数据
	 */
	@PreAuthorize ("hasAnyAuthority('datav:dataScreen:view','datav:dataScreen:add','datav:dataScreen:edit')")
	@GetMapping("queryById")
	public ResponseEntity<DataScreenDTO> queryById(String id) {
		DataScreenDTO dataScreenDTO =  dataScreenWrapper.toDTO (  dataScreenService.getById ( id ) );
		return ResponseEntity.ok ( dataScreenDTO );
	}

	/**
	 * 复制大屏
	 */
	@GetMapping("copy")
	public ResponseEntity<String> copy(String id) {
		DataScreen dataScreen = dataScreenService.getById ( id );
		dataScreen.setId (null);
		dataScreen.setName (dataScreen.getName () + "-copy");
		dataScreenService.save (dataScreen);
		return ResponseEntity.ok ("复制大屏成功!");
	}


	/**
	 * 保存大屏设计
	 */
	@PreAuthorize ("hasAnyAuthority('datav:dataScreen:add','datav:dataScreen:edit')")
	@PostMapping("save")
	public ResponseEntity<String> save(@RequestBody @Valid DataScreen dataScreen) {
		//新增或编辑表单保存
		dataScreenService.saveOrUpdate (dataScreen);
		return ResponseEntity.ok ("保存大屏设计成功");
	}


	/**
	 * 批量删除大屏设计
	 */
	@PreAuthorize("hasAuthority('datav:dataScreen:del')")
	@DeleteMapping("delete")
	public ResponseEntity<String> delete(String ids) {
		String idArray[] =ids.split(",");
		dataScreenService.removeByIds ( Lists.newArrayList ( idArray) );
		return ResponseEntity.ok ("删除大屏设计成功");
	}


	@PostMapping(value = "/sqlQuery")
	public ResponseEntity exec(@RequestBody DataSetRequest dataSetRequest) {
		DataSource dataSource = dataSourceService.getById ( dataSetRequest.getDb () );
		if (dataSource == null) {
			return ResponseEntity.badRequest ().body ( "数据库链接不存在!" );
		}

		String sql = dataSetRequest.getSql ();
		JdbcTemplate jdbcTemplate = DBPool.getInstance().getJdbcTemplateByEnName (dataSource.getEnName());
		if (sql.contains("delete") || sql.contains("update")) {
			return ResponseEntity.badRequest ().body ( "只允许查询操作!" );
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		Map result = new HashMap (  );
		result.put ( "success", true );
		result.put ( "data", list );
		return ResponseEntity.ok ( result );
	}

}
