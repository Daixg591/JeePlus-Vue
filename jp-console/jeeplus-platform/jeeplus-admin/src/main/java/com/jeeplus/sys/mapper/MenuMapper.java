/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jeeplus.core.domain.TreeMapper;
import com.jeeplus.sys.service.dto.MenuDTO;
import com.jeeplus.sys.domain.Menu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜单MAPPER接口
 *
 * @author jeeplus
 * @version 2021-05-16
 */
public interface MenuMapper extends TreeMapper<Menu> {

    List<MenuDTO> findByUserId(MenuDTO menuDTO);

    void deleteMenuRole(String menuId);

    List<MenuDTO> findAllWithDataRuleList();

    List<String> mrList(@Param(Constants.WRAPPER) Wrapper wrapper);

    List<String> mdList(@Param(Constants.WRAPPER) Wrapper wrapper);

}
