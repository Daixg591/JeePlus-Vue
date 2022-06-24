/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.form.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jeeplus.database.DataSourceConfig;
import com.jeeplus.database.datalink.domain.DataSource;
import com.jeeplus.database.datalink.jdbc.DBPool;
import com.jeeplus.form.domain.Form;
import com.jeeplus.form.mapper.FormMapper;
import com.jeeplus.form.service.dto.DataTableColumnDTO;
import com.jeeplus.form.service.dto.DataTableDTO;
import com.jeeplus.form.service.dto.FormDTO;
import com.jeeplus.form.utils.DDLBuilder;
import com.jeeplus.sys.domain.Menu;
import com.jeeplus.sys.service.MenuService;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;


/**
 * 数据表单Service
 * @author 刘高峰
 * @version 2021-09-24
 */
@Service
@Transactional
public class FormService extends ServiceImpl <FormMapper, Form> {

	@Autowired
	private MenuService menuService;
	@Autowired
	private DataSourceConfig dataSourceConfig;

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <FormDTO> findPage(Page <FormDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);


	}

	public FormDTO getById(String id) {
		FormDTO formDTO = baseMapper.getById ( id );
		if(formDTO.getDataSource () == null) {
			DataSource dataSource = new DataSource ();
			dataSource.setId ( dataSourceConfig.primary );
			dataSource.setName ( "默认数据库" );
			dataSource.setEnName ( dataSourceConfig.primary );
			formDTO.setDataSource ( dataSource );
		}
		return  formDTO;
	}

	/**
	 * 获取物理数据表列表
	 *
	 * @param dataTable
	 * @return
	 */
	public List<DataTableDTO> findTableListFormDb(DataTableDTO dataTable) throws SQLException {

		javax.sql.DataSource dataSource = DBPool.getInstance ().getDsByEnName ( dataTable.getDataSource ().getEnName () );
		DDLBuilder ddlBuilder = new DDLBuilder (dataSource, dataTable.getName ()  );
		Table[] tables = ddlBuilder.getTableList ();

		List<DataTableDTO> tableDTOList = Lists.newArrayList ( );
		for(Table table: tables){
			DataTableDTO dataTableDTO = new DataTableDTO ();
			dataTableDTO.setName ( table.getName () );
			dataTableDTO.setComments ( table.getDescription () );
			tableDTOList.add ( dataTableDTO );
		}

		return tableDTOList;
	}

	public boolean validateTableNoExist(DataTableDTO dataTable) throws SQLException{

		javax.sql.DataSource dataSource = DBPool.getInstance ().getDsByEnName ( dataTable.getDataSource ().getEnName () );
		DDLBuilder ddlBuilder = new DDLBuilder (dataSource, dataTable.getName ()  );

		Table table = ddlBuilder.getTable ( );
		return table == null;
	}

	public List<DataTableColumnDTO> findTableColumnList(DataTableDTO dataTable) throws SQLException {
		javax.sql.DataSource dataSource = DBPool.getInstance ().getDsByEnName ( dataTable.getDataSource ().getEnName ());
		DDLBuilder ddlBuilder = new DDLBuilder (dataSource, dataTable.getName ()  );

	    Column[] columns = ddlBuilder.getTableColumnList ( dataTable.getName () );
		List<DataTableColumnDTO> list = Lists.newArrayList ();
		for(Column column: columns){
			DataTableColumnDTO columnDTO = new DataTableColumnDTO ();
			columnDTO.setName ( column.getName () );
			columnDTO.setComments ( column.getDescription () );
			columnDTO.setJdbcType ( column.getType () );
			columnDTO.setDataTable ( dataTable );
			list.add ( columnDTO);
		}

		return list;
	}

	public Menu createMenu(FormDTO form, Menu topMenu) {
		String permissionPrefix = "form:"+form.getTableName();
		String url = "/form/GenerateList?id="+form.getId();
		topMenu.setHref(url);
		topMenu.setIsShow("1");
		topMenu.setType("1");
		topMenu.setPermission(permissionPrefix + ":list");
		menuService.save (topMenu);

		Menu addMenu = new Menu();
		addMenu.setName("新建");
		addMenu.setIsShow("0");
		addMenu.setType("2");
		addMenu.setSort(30);
		addMenu.setPermission(permissionPrefix + ":add");
		addMenu.setParentId (topMenu.getId ());
		menuService.save (addMenu);

		Menu delMenu = new Menu();
		delMenu.setName("删除");
		delMenu.setIsShow("0");
		delMenu.setType("2");
		delMenu.setSort(60);
		delMenu.setPermission(permissionPrefix + ":del");
		delMenu.setParentId(topMenu.getId ());
		menuService.save (delMenu);

		Menu editMenu = new Menu();
		editMenu.setName("修改");
		editMenu.setIsShow("0");
		editMenu.setType("2");
		editMenu.setSort(90);
		editMenu.setPermission(permissionPrefix + ":edit");
		editMenu.setParentId(topMenu.getId ());
		menuService.save (editMenu);

		Menu viewMenu = new Menu();
		viewMenu.setName("查看");
		viewMenu.setIsShow("0");
		viewMenu.setType("2");
		viewMenu.setSort(120);
		viewMenu.setPermission(permissionPrefix + ":view");
		viewMenu.setParentId (topMenu.getId ());
		menuService.save(viewMenu);

		Menu importMenu = new Menu();
		importMenu.setName("导入");
		importMenu.setIsShow("0");
		importMenu.setType("2");
		importMenu.setSort(150);
		importMenu.setPermission(permissionPrefix + ":import");
		importMenu.setParentId (topMenu.getId ());
		menuService.save (importMenu);

		Menu exportMenu = new Menu();
		exportMenu.setName("导出");
		exportMenu.setIsShow("0");
		exportMenu.setType("2");
		exportMenu.setSort(180);
		exportMenu.setPermission(permissionPrefix + ":export");
		exportMenu.setParentId (topMenu.getId ());
		menuService.save(exportMenu);
		return topMenu;

	}


}
