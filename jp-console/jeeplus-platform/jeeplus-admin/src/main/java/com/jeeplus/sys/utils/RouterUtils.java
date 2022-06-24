package com.jeeplus.sys.utils;

import com.google.common.collect.Lists;
import com.jeeplus.sys.service.dto.MenuDTO;
import com.jeeplus.sys.service.mapstruct.MenuWrapper;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 获取用户可访问的路由
 */
public class RouterUtils {


    public static List<MenuDTO> getRoutersByMenu() {
        MenuDTO menuDTO = UserUtils.getTopMenuDTO ();
        menuDTO = getChildOfTree (menuDTO, 0, UserUtils.getMenuDTOList ());
        return menuDTO.getChildren ();
    }

    private static MenuDTO getChildOfTree(MenuDTO menuItem, int level, List<MenuDTO> menuList) {

//        MenuDTO menuItem =  MenuWrapper.INSTANCE.copyDTO ( menuItem1 );
        menuItem.setChildren (Lists.newArrayList ());
        for (MenuDTO child : menuList) {
            if (child.getParentId ().equals (menuItem.getId ())) {
                menuItem.getChildren ().add (getChildOfTree (child, level + 1, menuList));
            }
        }


        return menuItem;
    }

}
