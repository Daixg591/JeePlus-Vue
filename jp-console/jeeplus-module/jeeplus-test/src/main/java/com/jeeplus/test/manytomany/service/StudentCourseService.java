/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.manytomany.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.manytomany.service.dto.StudentCourseDTO;
import com.jeeplus.test.manytomany.domain.StudentCourse;
import com.jeeplus.test.manytomany.mapper.StudentCourseMapper;

/**
 * 学生课程记录Service
 * @author lgf
 * @version 2021-10-17
 */
@Service
@Transactional
public class StudentCourseService extends ServiceImpl<StudentCourseMapper, StudentCourse> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public StudentCourseDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <StudentCourseDTO> findPage(Page <StudentCourseDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);
	}

}
