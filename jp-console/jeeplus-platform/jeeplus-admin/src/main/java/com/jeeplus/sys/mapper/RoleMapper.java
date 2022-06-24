/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeeplus.sys.domain.Role;

import java.util.List;

/**
 * 角色MAPPER接口
 *
 * @author jeeplus
 * @version 2021-09-05
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询关联的菜单Id
     */
    List<String> getMenuIdListByRoleId(String roleId);

    /**
     * 查询关联的权限Id
     */
    List<String> getDataRuleIdListByRoleId(String roleId);

    /**
     * 查询角色的所有无下属菜单ID
     *
     * @param id
     * @return
     */
    List<String> queryAllNotChildrenMenuId(String id);

    /**
     * 维护角色与菜单权限关系
     *
     * @param id
     * @return
     */
    int deleteRoleMenu(String id);
    int insertRoleMenu(String roleId, String menuId);

    /**
     * 维护角色与数据权限关系
     *
     * @param id
     * @return
     */
    int deleteRoleDataRule(String id);
    int insertRoleDataRule(String roleId, String dataRuleId);

}
