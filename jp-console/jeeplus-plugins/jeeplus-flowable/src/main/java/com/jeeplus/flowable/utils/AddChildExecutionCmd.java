package com.jeeplus.flowable.utils;

import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityManager;
import org.flowable.engine.impl.util.CommandContextUtil;
/*
 * 添加一个流程实例下面的执行实例
 */
public class AddChildExecutionCmd implements Command<Void> {

    private ExecutionEntity parentExecutionEntity;

    public AddChildExecutionCmd(ExecutionEntity parentExecutionEntity) {
        this.parentExecutionEntity = parentExecutionEntity;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        ExecutionEntityManager executionEntityManager = CommandContextUtil.getExecutionEntityManager(commandContext);
        executionEntityManager.createChildExecution(parentExecutionEntity);
        return null;
    }
}
