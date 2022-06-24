/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.google.common.collect.Lists;
import com.jeeplus.common.redis.RedisUtils;
import com.jeeplus.security.util.SecurityUtils;
import com.jeeplus.sys.constant.CacheNames;
import com.jeeplus.sys.service.DataRuleService;
import com.jeeplus.sys.service.MenuService;
import com.jeeplus.sys.service.UserService;
import com.jeeplus.sys.service.dto.DataRuleDTO;
import com.jeeplus.sys.service.dto.MenuDTO;
import com.jeeplus.sys.service.dto.RoleDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.service.mapstruct.MenuWrapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户工具类
 * @author jeeplus
 * @version 2021-06-05
 */
public class UserUtils {



	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static UserDTO get(String id){
		UserDTO userDTO = (UserDTO) RedisUtils.getInstance ().get ( CacheNames.USER_CACHE_USER_ID, id );

		if(userDTO == null){
			userDTO = SpringUtil.getBean( UserService.class).get (id);
		}
		return userDTO;
	}

	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return 取不到返回null
	 */
	@InterceptorIgnore
	public static UserDTO getByLoginName(String loginName){
		UserDTO userDTO = SpringUtil.getBean(UserService.class).getUserByLoginName ( loginName );
		return userDTO;
	}

	/**
	 * 清除当前用户缓存
	 */
	public static void deleteCache(UserDTO userDTO){
		RedisUtils.getInstance ().delete ( CacheNames.USER_CACHE_USER_ID, userDTO.getId ());
		RedisUtils.getInstance ().delete  ( CacheNames.USER_CACHE_DATA_RULE_LIST, userDTO.getId ());
		RedisUtils.getInstance ().delete  ( CacheNames.USER_CACHE_MENU_LIST, userDTO.getId ());
		RedisUtils.getInstance ().delete  ( CacheNames.USER_CACHE_ROLE_LIST, userDTO.getId ());
		RedisUtils.getInstance ().delete  ( CacheNames.USER_CACHE_TOP_MENU, userDTO.getId ());
		RedisUtils.getInstance ().delete  ( CacheNames.USER_CACHE_LOGIN_NAME, userDTO.getLoginName ());
		RedisUtils.getInstance ().delete  ( CacheNames.USER_CACHE_LOGIN_NAME, userDTO.getOldLoginName () );
	}


	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static UserDTO getCurrentUserDTO(){
		String username = SecurityUtils.getLoginName ();
		if (StrUtil.isNotEmpty ( username )){
			return getByLoginName( username);
		}
		return new UserDTO ();
	}

	/**
	 * 获取当前用户角色列表
	 * @return
	 */
	public static List<RoleDTO> getRoleDTOList(){
		return getCurrentUserDTO ().getRoleDTOList ();
	}

	/**
	 * 获取当前用户授权菜单， admin用户不受角色权限控制可以获取全部菜单
	 * @return
	 */
	public static List<MenuDTO> getMenuDTOList(){
		UserDTO user = getCurrentUserDTO ();
		return getMenuDTOListByUser ( user );
	}

	public static List<MenuDTO> getMenuDTOListByUser(UserDTO user){
		List<MenuDTO> menuDTOList = (List<MenuDTO>)RedisUtils.getInstance ().get ( CacheNames.USER_CACHE_MENU_LIST, user.getId () );
		if (menuDTOList == null){

			if (user.getIsAdmin ()){
				menuDTOList = SpringUtil.getBean( MenuService.class).findAllMenu ();
			}else{
				menuDTOList = SpringUtil.getBean(MenuService.class).findByUserId(user);
			}
			RedisUtils.getInstance ().set ( CacheNames.USER_CACHE_MENU_LIST, user.getId (), menuDTOList );
		}
		return menuDTOList;
	}
	/**
	 * 获取当前用户授权数据权限， admin用户不受数据权限限制可以访问全部数据
	 * @return
	 */
	public static List<DataRuleDTO> getDataRuleList(){
		UserDTO userDTO = getCurrentUserDTO ();
		if (userDTO.getIsAdmin ()) {
			return Lists.newArrayList ();
		} else {
			return SpringUtil.getBean( DataRuleService.class).findByUserId(userDTO);
		}
	}

	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	public static MenuDTO getTopMenuDTO() {
		MenuDTO topMenuDTO = (MenuDTO) RedisUtils.getInstance ().get ( CacheNames.USER_CACHE_TOP_MENU, getCurrentUserDTO ().getId () );
		if (topMenuDTO == null) {
			topMenuDTO = MenuWrapper.INSTANCE.toDTO (SpringUtil.getBean(MenuService.class).getById ("1"));
			RedisUtils.getInstance ().set ( CacheNames.USER_CACHE_TOP_MENU, getCurrentUserDTO ().getId (), topMenuDTO );
		}
		return  topMenuDTO;
	}


	/**
	 * 获取用户权限
	 * @return
	 */
	public static Set <String> getPermissionsByLoginName(String loginName) {
		UserDTO userDTO = UserUtils.getByLoginName(loginName);
		Set<String> info = new HashSet <> (  );
		if (userDTO != null) {

			List<MenuDTO> list = UserUtils.getMenuDTOList();
			for (MenuDTO menuDTO : list) {
				if ( StrUtil.isNotBlank(menuDTO.getPermission())) {
					for (String permission : StrUtil.split(menuDTO.getPermission(), ",")) {
						info.add (permission);
					}
				}
			}
		}
		return info;
	}
	public static Set <String> getPermissions() {
		String loginName = SecurityUtils.getLoginName ();
		return getPermissionsByLoginName ( loginName );
	}




}
