package com.jeeplus.flowable.common.factory;


import com.jeeplus.flowable.common.handler.ExtUserTaskActivityBehavior;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;

public class MyActivityBehaviorFactory extends DefaultActivityBehaviorFactory {
    @Override
    public ExtUserTaskActivityBehavior createUserTaskActivityBehavior(UserTask userTask) {
        return new ExtUserTaskActivityBehavior(userTask);
    }
}
