package com.jeeplus.quartz.task;

import org.quartz.DisallowConcurrentExecution;

import com.jeeplus.quartz.domain.Task;

@DisallowConcurrentExecution
public class TestTask1 extends Task {

    @Override
    public void run() {
        System.out.println("这是另一个测试任务TestTask1。");

    }

}
