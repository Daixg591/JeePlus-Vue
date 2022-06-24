/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.sys.domain.Post;
import com.jeeplus.sys.domain.User;
import com.jeeplus.sys.mapper.PostMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 岗位Service
 * @author 刘高峰
 * @version 2021-08-17
 */
@Service
@Transactional
public class PostService extends ServiceImpl<PostMapper, Post> {

}
