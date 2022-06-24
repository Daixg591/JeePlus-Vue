/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.quartz.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.quartz.domain.ScheduleJob;
import com.jeeplus.quartz.mapper.ScheduleJobMapper;
import com.jeeplus.sys.constant.CommonConstants;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 定时任务Service
 *
 * @author lgf
 * @version 2021-02-04
 */
@Service
@Transactional
public class ScheduleJobService extends ServiceImpl <ScheduleJobMapper, ScheduleJob> {


    @Autowired
    private Scheduler scheduler;

    @Transactional
    public boolean saveOrUpdate(ScheduleJob scheduleJob) {
        if ( StrUtil.isNotBlank ( scheduleJob.getId () ) ) {
            ScheduleJob t = super.getById (scheduleJob.getId());
            JobKey key = new JobKey(t.getName(), t.getJobGroup ());
            try {
                scheduler.deleteJob(key);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        this.add(scheduleJob);
        return super.saveOrUpdate (scheduleJob);
    }

    @Transactional
    public void add(ScheduleJob scheduleJob) {
        Class job = null;
        try {
            job = Class.forName(scheduleJob.getClassName());
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        JobDetail jobDetail = JobBuilder.newJob(job).withIdentity(scheduleJob.getName(), scheduleJob.getJobGroup ())
                .build();
        jobDetail.getJobDataMap().put("scheduleJob", scheduleJob);

        // 表达式调度构建器（可判断创建SimpleScheduleBuilder）
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(scheduleJob.getName(), scheduleJob.getJobGroup ()).withSchedule(scheduleBuilder).build();

        //重置启动时间
        ((CronTriggerImpl)trigger).setStartTime(new Date());


        try {
            scheduler.scheduleJob(jobDetail, trigger);
            JobKey key = new JobKey(scheduleJob.getName(), scheduleJob.getJobGroup ());
            if (scheduleJob.getStatus().equals(CommonConstants.NO)) {
                scheduler.pauseJob(key);
            } else {
                scheduler.resumeJob(key);
            }

        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }



    @Transactional(readOnly = false)
    public void delete(ScheduleJob scheduleJob) {

        JobKey jobKey = new JobKey(scheduleJob.getName(), scheduleJob.getJobGroup ());
        try {
            scheduler.pauseJob(jobKey);
            scheduler.deleteJob(jobKey);
            super.removeById ( scheduleJob.getId () );
        } catch (SchedulerException e) {
            e.printStackTrace();
        }


    }



    /**
     * 暂停任务
     */
    @Transactional
    public void stopJob(ScheduleJob scheduleJob) {

        JobKey key = new JobKey(scheduleJob.getName(), scheduleJob.getJobGroup ());
        try {
            scheduler.pauseJob(key);
            scheduleJob.setStatus( CommonConstants.NO );
            super.updateById(scheduleJob);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复任务
     */
    @Transactional
    public void restartJob(ScheduleJob scheduleJob) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getName(), scheduleJob.getJobGroup ());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if(trigger == null) {
                this.add (scheduleJob);
            }
            JobKey key = new JobKey(scheduleJob.getName(), scheduleJob.getJobGroup ());
            scheduler.resumeJob(key);
            super.updateById (scheduleJob);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


    /**
     * 立马执行一次任务
     *
     */
    @Transactional
    public void startNowJob(ScheduleJob scheduleJob) {
        JobKey key = new JobKey(scheduleJob.getName(), scheduleJob.getJobGroup ());
        try {
            scheduler.triggerJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }




}
