/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.common.utils.RequestUtils;
import com.jeeplus.sys.constant.CacheNames;
import com.jeeplus.sys.domain.User;
import com.jeeplus.sys.mapper.UserMapper;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.service.mapstruct.UserWrapper;
import com.jeeplus.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 用户管理
 * @author jeeplus
 * @version 2021-09-05
 */
@Service
@Transactional
public class UserService  extends ServiceImpl<UserMapper, User> {

	@Autowired
	private UserWrapper userWrapper;

	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public UserDTO get(String id) {
		QueryWrapper queryWrapper = new QueryWrapper ();
		queryWrapper.eq ("a.id", id);
		return baseMapper.get (queryWrapper);
	}

	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	@Cacheable(cacheNames = CacheNames.USER_CACHE_LOGIN_NAME, key = "#loginName")
	public UserDTO getUserByLoginName(String loginName) {
		QueryWrapper queryWrapper = new QueryWrapper ();
		queryWrapper.eq ("a.login_name", loginName);
		return baseMapper.get (queryWrapper);
	}


	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <UserDTO> findPage(Page <UserDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);


	}

	/**
	 * 查询角色下关联的用户
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <UserDTO> findPageByRole(Page <UserDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); //排除已经删除
		return  baseMapper.findListByRole (page, queryWrapper);


	}

	/**
	 * 查询角色下关联的用户
	 * @param roleId
	 * @return
	 */
	public List <UserDTO> findListByRoleId(String roleId) {
		QueryWrapper queryWrapper = new QueryWrapper (  );
		queryWrapper.eq ("a.del_flag", 0 ); //排除已经删除
		queryWrapper.eq ( "r.id", roleId );

		Page <UserDTO> page = new Page <> (  );
		page.setSize ( -1 );
		return  baseMapper.findListByRole ( page, queryWrapper ).getRecords ();
	}

	/**
	 * 查询岗位下关联的用户
	 * @param postId
	 * @return
	 */
	public List <UserDTO> findListByPostId(String postId) {
		return  baseMapper.findListByPostId ( postId );


	}


	/**
	 * 保存或者更新用户
	 * @param userDTO
	 */
	public void saveOrUpdate(UserDTO userDTO) {
		User user = userWrapper.toEntity (userDTO);
		super.saveOrUpdate ( user );
		// 更新用户与角色关联
		baseMapper.deleteUserRole(userDTO.getId ());
		if (userDTO.getRoleDTOList () != null){
			userDTO.getRoleDTOList ().forEach ( roleDTO -> {
				baseMapper.insertUserRole ( userDTO.getId (), roleDTO.getId () );
			} );
		}else{
			throw new RuntimeException (userDTO.getLoginName() + "没有设置角色！");
		}
		// 更新用户与岗位联系
		baseMapper.deleteUserPost ( userDTO.getId () );
		if(userDTO.getPostDTOList () != null) {
			userDTO.getPostDTOList ().forEach ( postDTO -> {
				baseMapper.insertUserPost ( userDTO.getId (), postDTO.getId () );
			} );
		}
		UserUtils.deleteCache ( userDTO );
	}


	public void deleteUser(UserDTO userDTO) {
		baseMapper.deleteUserRole(userDTO.getId ());
		super.removeById (userDTO.getId ());
		UserUtils.deleteCache ( userDTO );
	}

	public void updateUserLoginInfo(UserDTO userDTO) {
		User user = new User ( userDTO.getId () );
		// 更新本次登录信息
		HttpServletRequest request = RequestUtils.getRequest ();
		if ( request != null ) {
			user.setLoginIp( request.getRemoteHost () );
		}

		user.setLoginDate(new Date());
		super.updateById (user);
	}


}
