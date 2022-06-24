/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.manytomany.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.manytomany.service.dto.StudentCourseDTO;
import com.jeeplus.test.manytomany.domain.StudentCourse;

/**
 * 学生课程记录MAPPER接口
 * @author lgf
 * @version 2021-10-17
 */
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {

    /**
     * 根据id获取学生课程记录
     * @param id
     * @return
     */
    StudentCourseDTO findById(String id);

    /**
     * 获取学生课程记录列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <StudentCourseDTO> findList(Page <StudentCourseDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
