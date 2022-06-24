/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.form.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.aop.demo.annotation.DemoMode;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.database.datalink.jdbc.DBPool;
import com.jeeplus.form.domain.Form;
import com.jeeplus.form.service.FormService;
import com.jeeplus.form.service.dto.ColumnDTO;
import com.jeeplus.form.service.dto.DataTableColumnDTO;
import com.jeeplus.form.service.dto.DataTableDTO;
import com.jeeplus.form.service.dto.FormDTO;
import com.jeeplus.form.service.mapstruct.FormWrapper;
import com.jeeplus.form.utils.DDLBuilder;
import com.jeeplus.form.utils.FormJsonUtils;
import com.jeeplus.sys.constant.CommonConstants;
import com.jeeplus.sys.domain.Menu;
import com.jeeplus.sys.service.DataRuleService;
import com.jeeplus.sys.service.MenuService;
import com.jeeplus.sys.service.dto.DataRuleDTO;
import com.jeeplus.sys.service.dto.MenuDTO;
import com.jeeplus.sys.service.mapstruct.DataRuleWrapper;
import com.jeeplus.sys.service.mapstruct.MenuWrapper;
import org.apache.commons.lang.StringUtils;
import org.apache.ddlutils.platform.oracle.Oracle8Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据表单Controller
 * @author 刘高峰
 * @version 2021-09-24
 */
@RestController
@RequestMapping(value = "/form/make")
public class MakeFormController {

	@Autowired
	private FormService formService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private DataRuleService dataRuleService;
	@Autowired
	private FormWrapper formWrapper;
	/**
	 * 数据表单列表数据
	 */
	@PreAuthorize("hasAuthority('form:make:list')")
	@GetMapping("list")
	public ResponseEntity<IPage <FormDTO>> list(FormDTO formDTO, Page <FormDTO> page) throws Exception {
		QueryWrapper <FormDTO> queryWrapper = QueryWrapperGenerator.buildQueryCondition (formDTO, FormDTO.class);
		IPage <FormDTO> result = formService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取数据表单数据
	 */
	@GetMapping("queryById")
	public ResponseEntity<FormDTO> queryById(String id) {
		return ResponseEntity.ok ( formService.getById ( id ) );
	}

	/**
	 * 保存数据表单
	 */
	@PreAuthorize ("hasAnyAuthority('form:make:add','form:make:edit')")
	@PostMapping("saveFormSource")
	public ResponseEntity save(@RequestBody @Valid FormDTO formDTO) {
		String dataSourceId = formDTO.getDataSource ().getId ();
		DataSource dataSource = DBPool.getInstance().getDsById ( dataSourceId );
		DDLBuilder ddlBuilder = new DDLBuilder (dataSource, formDTO.getTableName());

		List<ColumnDTO> fieldArra = FormJsonUtils.newInstance().getFields(formDTO.getSource ());
		if(formDTO.getAutoCreate().equals("1")){ //自动建表，需要同步表结构
			ddlBuilder.syncTable(fieldArra);
		}
		//新增或编辑表单保存
		if(StrUtil.isBlank(formDTO.getVersion())){
			formDTO.setVersion("1");
		}else{
			formDTO.setVersion(String.valueOf(Integer.valueOf(formDTO.getVersion())+1));
		}
		formService.saveOrUpdate (formWrapper.toEntity (  formDTO ));//保存
		return ResponseEntity.ok ("保存数据表单成功");
	}

	/**
	 * 保存数据表单
	 */
	@PreAuthorize ("hasAnyAuthority('form:make:add','form:make:edit')")
	@PostMapping("saveBasicInfo")
	public ResponseEntity saveBasicInfo(@RequestBody @Valid FormDTO formDTO) {

		String dataSourceId = formDTO.getDataSource ().getId ();
		DataSource dataSource = DBPool.getInstance().getDsById ( dataSourceId );

		// 自动建表
		if(StrUtil.isBlank(formDTO.getId()) && formDTO.getAutoCreate().equals( CommonConstants.YES )){
			DDLBuilder ddlBuilder = new DDLBuilder (dataSource);
			if(ddlBuilder.getDataBaseType ().equals ( Oracle8Platform.DATABASENAME )){
				formDTO.setTableName ( StringUtils.upperCase ( formDTO.getTableName () ) );
			}
			ddlBuilder.setTableName ( formDTO.getTableName () );
			ddlBuilder.createTable();
		}
		//新增表单保存
		if(StrUtil.isBlank(formDTO.getVersion())){
			formDTO.setVersion("1");
		}
		formService.saveOrUpdate (formWrapper.toEntity ( formDTO ));//保存
		return ResponseEntity.ok ("保存数据表单成功");
	}

	/**
	 * 编码唯一性验证（数据库中不存在）
	 */
	@GetMapping("validateKeyNoExist")
	public ResponseEntity validateKeyNoExist(String key) {
		Form form = formService.lambdaQuery ().eq (Form::getCode, key).one ();
		return ResponseEntity.ok ( form == null );
	}

	/**
	 * 表名唯一性验证（数据库中不存在）
	 */
	@GetMapping("validateTableNoExist")
	public ResponseEntity validateTableNoExist(DataTableDTO dataTable) throws SQLException {
		boolean result  = formService.validateTableNoExist(dataTable);
		return ResponseEntity.ok ( result );
	}


	@RequestMapping("getTableList")
	public ResponseEntity getTableList( DataTableDTO dataTable) throws SQLException{


		// 获取物理表列表
		List<DataTableDTO> tableList = formService
				.findTableListFormDb(dataTable);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", tableList);
		map.put("total", tableList.size());
		return ResponseEntity.ok ( map );
	}

	@RequestMapping("getTableColumnList")
	public ResponseEntity getTableColumnList( DataTableDTO dataTable) throws  SQLException{


		// 获取物理表列表
		List<DataTableColumnDTO> tableColumnList = formService
				.findTableColumnList(dataTable);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", tableColumnList);
		map.put("total", tableColumnList.size());
		return ResponseEntity.ok (map);
	}

	/**
	 * 批量删除数据表单
	 */
	@PreAuthorize ("hasAuthority('form:make:del')")
	@DeleteMapping("delete")
	public ResponseEntity delete(String ids) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			FormDTO form = formService.getById (id);
			//需要删除表
			if (StrUtil.isNotBlank(form.getTableName()) && form.getAutoCreate().equals("1")) {
				String dataSourceId = form.getDataSource ().getId ();
				DataSource dataSource = DBPool.getInstance().getDsById ( dataSourceId );
				DDLBuilder formTableBuilder = new DDLBuilder (dataSource, form.getTableName());
				formTableBuilder.dropTable();
			}
			formService.removeById ( form.getId () );

		}
		return ResponseEntity.ok ("删除数据表单成功");
	}
	/*
	 *部署
	 */
	@DemoMode
	@PreAuthorize ("hasAuthority('form:make:deploy')")
	@RequestMapping("createMenu")
	public ResponseEntity createMenu(@RequestParam("formId")String formId, MenuDTO menuDTO) {
		// 获取排序号，最末节点排序号+30
		if (StrUtil.isBlank(menuDTO.getId())) {
			// 获取排序号，最末节点排序号+30
			if (StrUtil.isBlank(menuDTO.getId())) {
				List<MenuDTO> list = Lists.newArrayList();
				List<MenuDTO> sourcelist = menuService.findAllMenu();
				MenuDTO.sortList(list, sourcelist, menuDTO.getParentId(), false);
				if (list.size() > 0) {
					menuDTO.setSort(list.get(list.size() - 1).getSort() + 30);
				}
			}
		}

		FormDTO form = formService.getById (formId);
		Menu topMenu = formService.createMenu(form, MenuWrapper.INSTANCE.toEntity ( menuDTO ) );
		if(menuDTO.getDataRuleDTOList () != null){
			for(DataRuleDTO dataRuleDTO: menuDTO.getDataRuleDTOList()){
				dataRuleDTO.setMenuId(topMenu.getId());
				dataRuleService.save( DataRuleWrapper.INSTANCE.toEntity ( dataRuleDTO ));
			}
		}

		return ResponseEntity.ok ("创建菜单'" + form.getName() + "'成功<br/>");
	}

}
