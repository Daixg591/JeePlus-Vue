/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.calendar.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.calendar.domain.MyCalendar;
import com.jeeplus.calendar.service.dto.MyCalendarDTO;
import com.jeeplus.calendar.mapper.MyCalendarMapper;
import com.jeeplus.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 日历Service
 *
 * @author liugf
 * @version 2021-04-19
 */
@Service
@Transactional
public class MyCalendarService extends ServiceImpl <MyCalendarMapper, MyCalendar> {

    public List<MyCalendarDTO> findList(QueryWrapper queryWrapper) {
        queryWrapper.eq ( "a.user_id", UserUtils.getCurrentUserDTO ().getId () );
        return baseMapper.findList(queryWrapper);
    }



}
