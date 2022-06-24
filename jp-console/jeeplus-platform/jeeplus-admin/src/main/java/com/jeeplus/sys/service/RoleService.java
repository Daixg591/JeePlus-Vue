/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jeeplus.sys.constant.CacheNames;
import com.jeeplus.sys.service.dto.RoleDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.domain.Role;
import com.jeeplus.sys.mapper.RoleMapper;
import com.jeeplus.sys.service.mapstruct.RoleWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色管理类
 *
 * @author jeeplus
 * @version 2021-06-05
 */
@Service
@Transactional
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleWrapper roleWrapper;

    /**
     * 根据id查询角色，包含已授权的菜单和数据权限
     * @param id
     * @return
     */
    public RoleDTO get(String id) {
        RoleDTO roleDTO = roleWrapper.toDTO(super.getById (id));
        roleDTO.setDataRuleIdList (baseMapper.getDataRuleIdListByRoleId (id));
        roleDTO.setMenuIdList (baseMapper.getMenuIdListByRoleId (id));
        return roleDTO;
    }

    /**
     * 根据name查询角色
     * @param name
     * @return
     */
    public RoleDTO getRoleByName(String name) {
        return roleWrapper.toDTO (lambdaQuery ().eq (Role::getName, name).one ());
    }

    /**
     * 根据enname查询角色
     * @param enName
     * @return
     */
    public RoleDTO getRoleByEnName(String enName) {
        return roleWrapper.toDTO (lambdaQuery ().eq (Role::getEnName, enName).one ());
    }

    /**
     * 查询角色的所有无下属菜单ID
     * @param id
     * @return
     */
    public List<String> queryAllNotChildrenMenuId(String id){
        if(StrUtil.isNotEmpty(id)){
            return baseMapper.queryAllNotChildrenMenuId(id);
        }
        return Lists.newArrayList();
    }

    /**
     * 保存或者更新角色
     * @param roleDTO
     */
    @CacheEvict(cacheNames ={ CacheNames.USER_CACHE_ROLE_LIST, CacheNames.USER_CACHE_DATA_RULE_LIST, CacheNames.USER_CACHE_TOP_MENU, CacheNames.USER_CACHE_MENU_LIST}, allEntries = true)
    public void saveOrUpdate(RoleDTO roleDTO) {
        Role role = roleWrapper.toEntity (roleDTO);
        super.saveOrUpdate (role);
        // 更新角色与菜单关联
        baseMapper.deleteRoleMenu(role.getId ());
        roleDTO.getMenuDTOList ().forEach ( menuDTO -> {
            baseMapper.insertRoleMenu ( roleDTO.getId (), menuDTO.getId () );
        } );
        // 更新角色与数据权限关联
        baseMapper.deleteRoleDataRule(role.getId ());
        roleDTO.getDataRuleDTOList ().forEach ( dataRuleDTO -> {
            baseMapper.insertRoleDataRule ( roleDTO.getId (), dataRuleDTO.getId () );
        } );
    }

    /**
     * 清除角色缓存
     * @param id
     */
    @CacheEvict(cacheNames ={ CacheNames.USER_CACHE_ROLE_LIST, CacheNames.USER_CACHE_DATA_RULE_LIST, CacheNames.USER_CACHE_TOP_MENU, CacheNames.USER_CACHE_MENU_LIST}, allEntries = true)
    public void deleteRole(String id) {
        baseMapper.deleteRoleMenu(id);
        baseMapper.deleteRoleDataRule(id);
        super.removeById (id);
    }

    /**
     * 把用户从角色中移出
     * @param roleDTO
     * @param userDTO
     * @return
     */
    public Boolean removeUserFromRole(RoleDTO roleDTO, UserDTO userDTO) {
        List<RoleDTO> roleDTOList = userDTO.getRoleDTOList ();
        for (RoleDTO e : roleDTOList) {
            if (e.getId().equals(roleDTO.getId())) {
                roleDTOList.remove(e);
                userService.saveOrUpdate (userDTO);
                return true;
            }
        }
        return false;
    }

    /**
     * 将用户添加到角色中
     * @param roleDTO
     * @param userDTO
     * @return
     */
    public UserDTO addUserToRole(RoleDTO roleDTO, UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        List<String> roleIds = userDTO.getRoleIdList ();
        if (roleIds.contains(roleDTO.getId())) {
            return null;
        }
        userDTO.getRoleDTOList ().add(roleDTO);
        userService.saveOrUpdate (userDTO);
        return userDTO;
    }


}
