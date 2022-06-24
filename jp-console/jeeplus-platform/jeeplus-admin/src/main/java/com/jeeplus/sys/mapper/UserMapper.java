/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户MAPPER接口
 *
 * @author jeeplus
 * @version 2021-05-16
 */
public interface UserMapper extends BaseMapper<User> {


    /**
     * 根据条件查询用户
     *
     * @param queryWrapper
     * @return
     */
    @InterceptorIgnore
    UserDTO get(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);

    /**
     * 获取用户列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <UserDTO> findList(Page <UserDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

    /**
     * 按角色获取用户列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <UserDTO> findListByRole(Page <UserDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);


    /**
     * 按岗位获取用户列表
     *
     * @param postId
     * @return
     */
    List <UserDTO> findListByPostId(String postId);



    /**
     * 删除用户角色关联数据
     *
     * @param userId
     * @return
     */
    void deleteUserRole(String userId);

    /**
     * 插入用户角色关联数据
     * @param userId
     * @param roleId
     * @return
     */
    void insertUserRole(String userId, String roleId);

    /**
     * 删除用户岗位关联数据
     *
     * @param userId
     * @return
     */
    void deleteUserPost(String userId);

    /**
     * 插入用户角色关联数据
     * @param userId
     * @param postId
     * @return
     */
    void insertUserPost(String userId, String postId);

}
