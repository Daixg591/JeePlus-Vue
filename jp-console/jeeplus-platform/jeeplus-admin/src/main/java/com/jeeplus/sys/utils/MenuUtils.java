package com.jeeplus.sys.utils;

import com.google.common.collect.Lists;
import com.jeeplus.sys.constant.CommonConstants;
import com.jeeplus.sys.service.dto.MenuDTO;
import com.jeeplus.sys.constant.enums.MenuTypeEnum;

import java.util.List;

/**
 * 获取用户可见的菜单
 */
public class MenuUtils {

    public static List<MenuDTO> getMenus() {
        MenuDTO menuDTO = UserUtils.getTopMenuDTO ();
        getChildOfTree(menuDTO, 0, UserUtils.getMenuDTOList ());
        return menuDTO.getChildren();
    }

    private static MenuDTO getChildOfTree(MenuDTO menuItem, int level, List<MenuDTO> menuList) {


        if (menuItem.getIsShow().equals( CommonConstants.SHOW )) {// 如果是父节点且显示
            menuItem.setChildren(Lists.newArrayList());
            for (MenuDTO child : menuList) {
                if (! MenuTypeEnum.BUTTON.getValue ().equals ( child.getType()) && child.getParentId().equals(menuItem.getId()) && child.getIsShow().equals(CommonConstants.SHOW)) {
                    menuItem.getChildren().add(getChildOfTree(child, level + 1, menuList));
                }
            }

        }

        return menuItem;
    }


}
