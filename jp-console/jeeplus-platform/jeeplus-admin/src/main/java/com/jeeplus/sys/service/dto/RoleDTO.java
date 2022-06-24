/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.jeeplus.core.service.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 角色Entity
 * @author jeeplus
 * @version 2021-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	/**
	 * 角色名称
	 */
	@Length(min = 1, max = 100)
	private String name;

	/**
	 * 英文名称
	 */
	@Length(min = 1, max = 100)
	private String enName;

	/**
	 * 原角色名称
	 */
	private String oldName;

	/**
	 * 原英文名称
	 */
	private String oldEnName;

	/**
	 * 是否是系统数据
	 */
	private String sysData;

	/**
	 * 是否是可用
	 */
	private String useable;

	/**
	 * 根据用户ID查询角色列表
	 */
	@ApiModelProperty(hidden = true)
	private UserDTO userDTO;

	/**
	 * 备注
	 */
	private String remarks;

	/**
	 * 拥有菜单列表
	 */
	@JsonIgnore
	private List<MenuDTO> menuDTOList = Lists.newArrayList();

	/**
	 * 数据范围
	 */
	@JsonIgnore
	private List<DataRuleDTO> dataRuleDTOList = Lists.newArrayList();

	public RoleDTO( ) {
	}

	public RoleDTO(String id) {
		super(id);
	}

	@JsonIgnore
	public List<String> getMenuIdList() {
		List<String> menuIdList = Lists.newArrayList();
		for (MenuDTO menu : menuDTOList) {
			menuIdList.add(menu.getId());
		}
		return menuIdList;
	}

	public void setMenuIdList(List<String> menuIdList) {
		menuDTOList = Lists.newArrayList();
		for (String menuId : menuIdList) {
			MenuDTO menuDTO = new MenuDTO ();
			menuDTO.setId(menuId);
			menuDTOList.add(menuDTO);
		}
	}

	public String getMenuIds() {
		return StringUtils.join(getMenuIdList(), ",");
	}

	public void setMenuIds(String menuIds) {
		menuDTOList = Lists.newArrayList();
		if (menuIds != null){
			String[] ids = StringUtils.split(menuIds, ",");
			setMenuIdList(Lists.newArrayList(ids));
		}
	}


	/**
	 * 获取权限字符串列表
	 */
	public List<String> getPermissions() {
		List<String> permissions = Lists.newArrayList();
		for (MenuDTO menuDTO : menuDTOList) {
			if (menuDTO.getPermission()!=null && !"".equals(menuDTO.getPermission())){
				permissions.add(menuDTO.getPermission());
			}
		}
		return permissions;
	}


	@JsonIgnore
	public List<String> getDataRuleIdList() {
		List<String> dataRuleIdList = Lists.newArrayList();
		for (DataRuleDTO dataRule : dataRuleDTOList) {
			dataRuleIdList.add(dataRule.getId());
		}
		return dataRuleIdList;
	}

	public void setDataRuleIdList(List<String> dataRuleIdList) {
		dataRuleDTOList = Lists.newArrayList();
		for (String dataRuleId : dataRuleIdList) {
			DataRuleDTO dataRuleDTO = new DataRuleDTO ();
			dataRuleDTO.setId(dataRuleId);
			dataRuleDTOList.add(dataRuleDTO);
		}
	}

	public String getDataRuleIds() {
		return StringUtils.join(getDataRuleIdList(), ",");
	}

	public void setDataRuleIds(String dataRuleIds) {
		dataRuleDTOList = Lists.newArrayList();
		if (dataRuleIds != null){
			String[] ids = StringUtils.split(dataRuleIds, ",");
			setDataRuleIdList(Lists.newArrayList(ids));
		}
	}

}
