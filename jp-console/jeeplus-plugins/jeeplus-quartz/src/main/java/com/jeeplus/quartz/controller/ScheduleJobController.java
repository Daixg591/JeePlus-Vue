/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.quartz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.quartz.domain.ScheduleJob;
import com.jeeplus.quartz.service.ScheduleJobService;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 定时任务Controller
 *
 * @author lgf
 * @version 2021-02-04
 */
@RestController
@RequestMapping("/quartz/scheduleJob")
public class ScheduleJobController {

    @Autowired
    private ScheduleJobService scheduleJobService;


    /**
     * 定时任务列表数据
     */
    @PreAuthorize("hasAuthority('quartz:scheduleJob:list')")
    @GetMapping("list")
    public ResponseEntity<IPage<ScheduleJob>> data(ScheduleJob scheduleJob, Page <ScheduleJob> page) throws Exception {
        QueryWrapper <ScheduleJob> queryWrapper = QueryWrapperGenerator.buildQueryCondition (scheduleJob, ScheduleJob.class);
        IPage <ScheduleJob> result = scheduleJobService.page (page, queryWrapper);
        return ResponseEntity.ok (result);
    }

    /**
     * 查看，增加，编辑定时任务表单页面
     */
    @GetMapping("queryById")
    public ResponseEntity<ScheduleJob> queryById(String id) {
        ScheduleJob scheduleJob = scheduleJobService.getById ( id );
        return ResponseEntity.ok ( scheduleJob );
    }

    /**
     * 保存定时任务
     */
    @PreAuthorize ("hasAnyAuthority('quartz:scheduleJob:add','quartz:scheduleJob:edit')")
    @PostMapping("save")
    public ResponseEntity save(@Valid @RequestBody ScheduleJob scheduleJob) {

        //验证cron表达式
        if (!CronExpression.isValidExpression(scheduleJob.getCronExpression())) {
            return ResponseEntity.badRequest ().body ("cron表达式不正确！");
        }
        scheduleJobService.saveOrUpdate (scheduleJob);//保存
        return ResponseEntity.ok ("保存定时任务成功!");
    }

    /**
     * 批量删除定时任务
     */
    @PreAuthorize("hasAuthority('quartz:scheduleJob:del')")
    @DeleteMapping("delete")
    public ResponseEntity<String> deleteAll(String ids) {
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            scheduleJobService.delete(scheduleJobService.getById (id));
        }
        return ResponseEntity.ok ("删除定时任务成功");
    }

    /**
     * 暂停任务
     */
    @PreAuthorize("hasAuthority('quartz:scheduleJob:stop')")
    @PostMapping("stop")
    public ResponseEntity stop(String id) {
        scheduleJobService.stopJob(scheduleJobService.getById ( id ));
        return ResponseEntity.ok ("暂停成功!");
    }

    /**
     * 立即运行一次
     */
    @PreAuthorize("hasAuthority('quartz:scheduleJob:startNow')")
    @PostMapping("startNow")
    public ResponseEntity startNow(String id) {
        scheduleJobService.startNowJob(scheduleJobService.getById ( id ));
        return ResponseEntity.ok ("运行成功");
    }

    /**
     * 恢复
     */
    @PreAuthorize("hasAuthority('quartz:scheduleJob:resume')")
    @PostMapping("resume")
    public ResponseEntity resume(String id) {
        scheduleJobService.restartJob(scheduleJobService.getById ( id ));
        return ResponseEntity.ok ("恢复成功");
    }

    /**
     * 验证类任务类是否存在
     */
    @GetMapping("existsClass")
    public ResponseEntity existsClass(String className) {
        Class job = null;
        try {
            job = Class.forName(className);
            return ResponseEntity.ok (job);
        } catch (ClassNotFoundException e1) {
            return ResponseEntity.badRequest ().body ("类不存在");
        }
    }

}
