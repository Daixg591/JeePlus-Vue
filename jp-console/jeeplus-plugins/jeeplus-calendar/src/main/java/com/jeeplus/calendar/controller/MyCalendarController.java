/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.calendar.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.calendar.domain.MyCalendar;
import com.jeeplus.calendar.service.dto.MyCalendarDTO;
import com.jeeplus.calendar.service.mapstruct.MyCalendarWrapper;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.calendar.service.MyCalendarService;
import com.jeeplus.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 日历Controller
 *
 * @author liugf
 * @version 2021-04-19
 */
@RestController
@RequestMapping("/myCalendar")
public class MyCalendarController {

    @Autowired
    private MyCalendarService myCalendarService;

    @Autowired
    private MyCalendarWrapper myCalendarWrapper;

    /**
     * 查询日历
     * @param id
     * @return
     */
    @ApiLog("查询日历")
    @GetMapping("queryById")
    public ResponseEntity queryById(String id) {
        return ResponseEntity.ok ( myCalendarService.getById ( id ) );
    }

    /**
     * 查询日历列表
     * @param myCalendarDTO
     * @return
     * @throws Exception
     */
    @ApiLog("查询日历列表")
    @GetMapping("findList")
    protected ResponseEntity<List<MyCalendarDTO>> findList(MyCalendarDTO myCalendarDTO) throws Exception {
        QueryWrapper <MyCalendarDTO> queryWrapper = QueryWrapperGenerator.buildQueryCondition ( myCalendarDTO, MyCalendarDTO.class );
        List <MyCalendarDTO> list = myCalendarService.findList ( queryWrapper );
        return ResponseEntity.ok ( list );
    }

    /**
     * 保存日历
     * @param myCalendarDTO
     * @return
     */
    @ApiLog("保存日历")
    @PostMapping("save")
    public ResponseEntity save(@Valid MyCalendarDTO myCalendarDTO) {
        myCalendarDTO.setUserDTO ( UserUtils.getCurrentUserDTO ( ) );
        myCalendarService.saveOrUpdate ( myCalendarWrapper.toEntity ( myCalendarDTO ) );
        return ResponseEntity.ok ( "保存成功!" );
    }

    /**
     * 删除日历
     * @param id
     * @return
     */
    @ApiLog("删除日历")
    @DeleteMapping("del")
    public ResponseEntity del(String id) {
        myCalendarService.removeById ( id );
        return ResponseEntity.ok ( "删除成功!" );

    }

    /**
     * 缩放日历
     * @param id
     * @param request
     * @return
     */
    @ApiLog("缩放日历")
    @PostMapping("resize")
    public ResponseEntity resize(String id, HttpServletRequest request) {
        Integer daydiff = Integer.parseInt ( request.getParameter ( "daydiff" ) ) * 24 * 60 * 60 * 1000;
        Integer minudiff = Integer.parseInt ( request.getParameter ( "minudiff" ) ) * 1000;
        MyCalendar myCalendar = myCalendarService.getById ( id );
        Date start = myCalendar.getStart ( );
        long lstart = start.getTime ( );
        Date end = myCalendar.getEnd ( );
        long lend = end.getTime ( );
        Integer difftime = daydiff + minudiff;
        if ( end == null ) {
            myCalendar.setEnd ( DateUtil.date ( lstart + difftime ) );

        } else {
            myCalendar.setEnd ( DateUtil.date ( lend + difftime ) );
        }
        myCalendar.setUserId ( UserUtils.getCurrentUserDTO ().getId () );
        myCalendarService.updateById ( myCalendar );
        return ResponseEntity.ok ( "保存成功！" );
    }

    /**
     * 拖拽日历
     * @param id
     * @param request
     * @return
     */
    @ApiLog("拖拽日历")
    @PostMapping("drag")
    public ResponseEntity drag(String id, HttpServletRequest request) {
        Integer daydiff = Integer.parseInt ( request.getParameter ( "daydiff" ) ) * 24 * 60 * 60 * 1000;
        Integer minudiff = Integer.parseInt ( request.getParameter ( "minudiff" ) ) * 1000;
        MyCalendar myCalendar = myCalendarService.getById ( id );
        Date start = myCalendar.getStart ( );
        long lstart = start.getTime ( );
        Date end = myCalendar.getEnd ( );
        long lend = end.getTime ( );
        Integer difftime = daydiff + minudiff;
        myCalendar.setStart ( DateUtil.date ( lstart + difftime ) );
        if ( end != null ) {
            myCalendar.setEnd ( DateUtil.date ( lend + difftime ) );
        }
        myCalendar.setUserId ( UserUtils.getCurrentUserDTO ().getId () );
        myCalendarService.updateById ( myCalendar );
        return ResponseEntity.ok ( "保存成功" );
    }

}
