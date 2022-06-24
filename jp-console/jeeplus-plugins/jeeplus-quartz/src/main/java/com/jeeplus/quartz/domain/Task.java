package com.jeeplus.quartz.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * 定时任务工作类
 *
 * @author ty
 * @date 2021年06月13日
 */
@Slf4j
@DisallowConcurrentExecution
public abstract class Task implements Job {

    /**
     * 系统通知bean
     *
     * @return
     */

    public void execute(JobExecutionContext context) throws JobExecutionException {
        Object scheduleJob = context.getMergedJobDataMap().get("scheduleJob");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        run();
        log.info ("任务名称 = [" + scheduleJob.toString() + "]" + " 在 " + dateFormat.format(new Date()) + " 时运行");
    }

    public abstract void run();
}
