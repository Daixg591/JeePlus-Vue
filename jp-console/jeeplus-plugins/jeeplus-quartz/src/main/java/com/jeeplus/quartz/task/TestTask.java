package com.jeeplus.quartz.task;

import org.quartz.DisallowConcurrentExecution;

import com.jeeplus.quartz.domain.Task;

@DisallowConcurrentExecution
public class TestTask extends Task {

    @Override
    public void run() {
        System.out.println("这是测试任务TestTask。");

    }

}
