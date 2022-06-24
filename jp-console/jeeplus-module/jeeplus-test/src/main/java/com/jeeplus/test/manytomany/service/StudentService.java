/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.manytomany.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.test.manytomany.domain.Student;
import com.jeeplus.test.manytomany.mapper.StudentMapper;

/**
 * 学生Service
 * @author 刘高峰
 * @version 2021-10-17
 */
@Service
@Transactional
public class StudentService extends ServiceImpl<StudentMapper, Student> {

}
