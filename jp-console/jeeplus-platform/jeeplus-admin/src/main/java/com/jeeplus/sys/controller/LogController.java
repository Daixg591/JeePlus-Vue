/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.sys.service.dto.LogDTO;
import com.jeeplus.sys.service.LogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 日志Controller
 *
 * @author jeeplus
 * @version 2021-6-2
 */

@Api("日志管理")
@RestController
@RequestMapping("/sys/log")
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * 获取日志列表
     * @param logDTO
     * @param page
     * @return
     * @throws Exception
     */
    @ApiLog("日志列表")
    @PreAuthorize("hasAuthority('sys:log:list')")
    @GetMapping("list")
    public ResponseEntity data( LogDTO logDTO,  Page <LogDTO> page) throws Exception {
        QueryWrapper <LogDTO> queryWrapper = QueryWrapperGenerator.buildQueryCondition ( logDTO, LogDTO.class );
        IPage <LogDTO> result = logService.findPage ( page, queryWrapper );
        return ResponseEntity.ok ( result );
    }

    /**
     * 我的日志
     * @param logDTO
     * @param page
     * @return
     * @throws Exception
     */
    @ApiLog("我的日志")
    @GetMapping("data/mine")
    public ResponseEntity mine(LogDTO logDTO, Page <LogDTO> page) throws Exception {
        QueryWrapper <LogDTO> queryWrapper = QueryWrapperGenerator.buildQueryCondition ( logDTO, LogDTO.class );
        IPage <LogDTO> result = logService.findPage ( page, queryWrapper );
        return ResponseEntity.ok ( result );
    }


    /**
     * 删除日志
     * @param ids
     * @return
     */
    @ApiLog("删除日志")
    @PreAuthorize ("hasAuthority('sys:log:del')")
    @DeleteMapping("delete")
    public ResponseEntity delete(String ids) {
        logService.removeByIds ( Lists.newArrayList ( ids.split ( "," ) ) );
        return ResponseEntity.ok ( "删除日志成功！" );
    }

    /**
     * 清空日志
     * @return
     */
    @ApiLog("清空日志")
    @PreAuthorize ("hasAuthority('sys:log:del')")
    @DeleteMapping("empty")
    public ResponseEntity empty() {
        logService.remove ( new QueryWrapper <> ( ) );
        return ResponseEntity.ok ( "清空日志成功!" );
    }
}
