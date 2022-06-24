/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jeeplus.core.service.dto.TreeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 菜单Entity
 * @author jeeplus
 * @version 2021-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuDTO extends TreeDTO <MenuDTO> {

	private static final long serialVersionUID = 1L;

	/**
	 * 链接
	 */
	@Size(min = 0, max = 2000)
	private String href;

	/**
	 * 目标（ _iframe、_self）
	 */
	@Size(min = 0, max = 20)
	private String target;

	/**
	 * 图标
	 */
	@Size(min = 0, max = 100)
	private String icon;

	/**
	 * 是否在菜单中显示（1：显示；0：不显示）
	 */
	@Size(min = 1, max = 1)
	private String isShow;

	/**
	 * 按钮类型
	 */
	private String type;

	/**
	 * 权限标识
	 */
	@Size(min = 0, max = 200)
	private String permission;

	/**
	 * 数据权限
	 */
	private List<DataRuleDTO> dataRuleDTOList;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 是否固定在标签页
	 */
	private  String affix;

	/**
	 * 备注
	 */
	private String remarks;

	/**
	 * 排序
	 * @param list
	 * @param sourcelist
	 * @param parentId
	 * @param cascade
	 */
	@JsonIgnore
	public static void sortList(List<MenuDTO> list, List<MenuDTO> sourcelist, String parentId, boolean cascade){
		for (int i=0; i<sourcelist.size(); i++){
			MenuDTO e = sourcelist.get(i);
			if (e.getParent()!=null && e.getParent().getId()!=null
					&& e.getParent().getId().equals(parentId)){
				list.add(e);
				if (cascade){
					// 判断是否还有子节点, 有则继续获取子节点
					for (int j=0; j<sourcelist.size(); j++){
						MenuDTO child = sourcelist.get(j);
						if (child.getParent()!=null && child.getParent().getId()!=null
								&& child.getParent().getId().equals(e.getId())){
							sortList(list, sourcelist, e.getId(), true);
							break;
						}
					}
				}
			}
		}
	}



}
