/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.sys.domain.Post;
import com.jeeplus.sys.service.PostService;
import com.jeeplus.sys.service.dto.PostDTO;
import com.jeeplus.sys.service.mapstruct.PostWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 岗位Controller
 * @author 刘高峰
 * @version 2021-08-17
 */

@Api("岗位管理")
@RestController
@RequestMapping(value = "/sys/post")
public class PostController {

	@Autowired
	private PostService postService;

	/**
	 * 获取岗位列表数据
	 * @param post
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ApiLog("查询岗位列表")
	@PreAuthorize("hasAuthority('sys:post:list')")
	@GetMapping("list")
	public ResponseEntity list(Post post, Page<Post> page) throws Exception {
		QueryWrapper<Post> queryWrapper = QueryWrapperGenerator.buildQueryCondition (post, Post.class);
		IPage<Post> result = postService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}

	/**
	 * 查询岗位数据
	 * @param id
	 * @return
	 */
	@ApiLog("查询岗位")
	@PreAuthorize ("hasAnyAuthority('sys:post:view','sys:post:add','sys:post:edit')")
	@GetMapping("queryById")
	public ResponseEntity queryById(String id) {
		Post post = postService.getById ( id );
		return ResponseEntity.ok (post);
	}

	/**
	 * 保存岗位
	 * @param postDTO
	 * @return
	 */
	@ApiLog("保存岗位")
	@PreAuthorize ("hasAnyAuthority('sys:post:add','sys:post:edit')")
	@PostMapping("save")
	public ResponseEntity save(@Valid @RequestBody PostDTO postDTO){
		//新增或编辑表单保存
		Post post = PostWrapper.INSTANCE.toEntity (postDTO);
		postService.saveOrUpdate (post);//保存
		return ResponseEntity.ok ("保存岗位成功");
	}

	/**
	 * 删除岗位
	 * @param ids
	 * @return
	 */
	@ApiLog("删除岗位")
	@PreAuthorize ("hasAuthority('sys:post:del')")
	@DeleteMapping("delete")
	public ResponseEntity delete(String ids) {
		String idArray[] =ids.split(",");
		postService.removeByIds (Lists.newArrayList (idArray));
		return ResponseEntity.ok ("删除岗位成功");
	}

}
