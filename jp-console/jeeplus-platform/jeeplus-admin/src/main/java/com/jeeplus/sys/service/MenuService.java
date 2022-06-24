/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.core.service.TreeService;
import com.jeeplus.sys.constant.CacheNames;
import com.jeeplus.sys.constant.CommonConstants;
import com.jeeplus.sys.service.dto.MenuDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.domain.Menu;
import com.jeeplus.sys.mapper.MenuMapper;
import com.jeeplus.sys.service.mapstruct.MenuWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 菜单.
 *
 * @author jeeplus
 * @version 2021-09-05
 */
@Service
@Transactional
public class MenuService extends TreeService<MenuMapper, Menu> {

    @Autowired
    private DataRuleService dataRuleService;
    @Autowired
    private MenuWrapper menuWrapper;

    /**
     * 读取菜单列表
     * @return
     */
    public List<MenuDTO> findAllMenu() {
        return menuWrapper.toDTO (super.lambdaQuery ().orderByAsc (Menu::getSort).list ());
    }

    /**
     * 按id查询菜单
     */

    public Menu getById(String id){
        return super.getById ( id );
    }

    /**
     * 读取用户有权访问的菜单列表
     * @param userDTO
     * @return
     */
    public List<MenuDTO> findByUserId(UserDTO userDTO) {
        MenuDTO m = new MenuDTO ();
        m.setUserId(userDTO.getId());
        return baseMapper.findByUserId(m);
    }

    /**
     * 读取带数据规则的菜单列表
     * @return
     */
    public List<MenuDTO> findAllWithDataRuleList() {
        return baseMapper.findAllWithDataRuleList ();
    }

    /**
     * 保存或更新菜单
     * @param menu
     * @return
     */
    @CacheEvict(cacheNames ={CacheNames.SYS_CACHE_MENU, CacheNames.USER_CACHE_DATA_RULE_LIST, CacheNames.USER_CACHE_TOP_MENU, CacheNames.USER_CACHE_MENU_LIST}, allEntries = true) // 清除用户菜单缓存
    public boolean saveOrUpdate(Menu menu) {
       return  super.saveOrUpdate (menu);
    }

    /**
     * 删除菜单
     * @param id
     */
    @CacheEvict(cacheNames ={CacheNames.SYS_CACHE_MENU, CacheNames.USER_CACHE_DATA_RULE_LIST, CacheNames.USER_CACHE_TOP_MENU, CacheNames.USER_CACHE_MENU_LIST}, allEntries = true)
    public void deleteById(String id) {
        // 解除菜单角色关联
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq ("a.menu_id", id);
        queryWrapper.or ();
        queryWrapper.like ("menu.parent_ids", id);
        List<String> mrList = baseMapper.mrList (queryWrapper);
        for (String mr : mrList) {
            baseMapper.deleteMenuRole(mr);
        }
        // 删除菜单关联的数据权限数据，以及解除角色数据权限关联
        List<String> mdList = baseMapper.mdList (queryWrapper);
        for (String dataRuleId : mdList) {
            dataRuleService.delete (dataRuleId);
        }
        super.removeWithChildrenById (id);
    }


    public List<MenuDTO> getTreeMenu(List<MenuDTO> list, String extId, String isShowHid) {
        Menu menu = super.getById ("1"); //功能菜单
        List rootTree =  formatListToTree (menuWrapper.toDTO (menu), list, extId, isShowHid);
        return rootTree;
    }


    /**
     * 以root为根节点, 将allList从线性列表转为树形列表
     *
     * @param rootDTO 根节点, 为空抛出空指针异常
     * @param allList 所有需要参与构造为树的列表
     * @param extId 需要排除在树之外的节点(子节点一并被排除)
     * @return java.util.List<Menu>
     * @Author 滕鑫源
     * @Date 2020/10/23 17:04
     **/
    public List<MenuDTO> formatListToTree(MenuDTO rootDTO, List<MenuDTO> allList, String extId, String isShowHide) {
        String rootId = rootDTO.getId();
        // 最终的树形态
        List<MenuDTO> trees = Lists.newArrayList();
        // 把需要构造树的所有列表, 根据以父id作为key, 整理为列表
        Map <String, List<MenuDTO>> treeMap = Maps.newHashMap();
        for (MenuDTO menuDTO : allList) {
            List<MenuDTO> menuDTOs = treeMap.get(menuDTO.getParent().getId());
            if (menuDTOs == null) {
                menuDTOs = Lists.newLinkedList();
            }

            // 剔除排除项, 构造treeMap, 转递归为线性操作
            if ( StrUtil.isBlank(extId) ||  (!extId.equals(menuDTO.getId()) && (menuDTO.getParentIds() == null || menuDTO.getParentIds().indexOf("," + extId + ",") == -1))){
                if (isShowHide.equals( CommonConstants.HIDE) && CommonConstants.HIDE.equals ( menuDTO.getIsShow() )) {
                    continue;
                }
                menuDTOs.add(menuDTO);
                treeMap.put(menuDTO.getParent().getId(), menuDTOs);
            }

        }
        // 没有给定的子树, 返回空树
        if (treeMap.get(rootId) == null || treeMap.get(rootId).isEmpty()) {
            return trees;
        }
        // 开始递归格式化
        List<MenuDTO> children = treeMap.get(rootId);
        for (MenuDTO parent : children) {
            formatFillChildren(parent, treeMap);
            trees.add(parent);
        }
        if (StrUtil.equals(rootId, MenuDTO.getRootId ())) {
            return children;
        } else {
            rootDTO.setChildren(trees);
            return Lists.newArrayList(rootDTO);
        }
    }

    /**
     * 从treeMap中取出子节点填入parent, 并递归此操作
     *
     * @param parent
     * @param treeMap
     * @return void
     * @Author 滕鑫源
     * @Date 2020/9/30 16:33
     **/
    private void formatFillChildren(MenuDTO parent, Map<String, List<MenuDTO>> treeMap) {
        List<MenuDTO> children = treeMap.get(parent.getId());
        parent.setChildren(children);
        if (children != null && !children.isEmpty()) {
            for (MenuDTO child : children) {
                formatFillChildren(child, treeMap);
            }
        }
    }


}
