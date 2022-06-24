package com.jeeplus.flowable.listener;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component("myExecutionListener")
public class MyExecutionListener implements ExecutionListener {

    public void notify(DelegateExecution delegateExecution) {

        String eventName = delegateExecution.getEventName();

        if ("start".equals(eventName)) {
            System.out.println("start=========");
        }else if ("end".equals(eventName)) {
            System.out.println("end=========");
        } else if ("take".equals(eventName)) {//连线监听
            System.out.println("take=========");
        }
    }
}
