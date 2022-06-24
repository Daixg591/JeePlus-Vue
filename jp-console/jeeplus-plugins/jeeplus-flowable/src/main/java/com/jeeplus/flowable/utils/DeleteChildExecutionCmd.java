package com.jeeplus.flowable.utils;

import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityManager;
import org.flowable.engine.impl.util.CommandContextUtil;

/*
 * 删除执行实例
 */
public class DeleteChildExecutionCmd implements Command<Void> {

    private ExecutionEntity child;

    public DeleteChildExecutionCmd(ExecutionEntity child) {
        this.child = child;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        ExecutionEntityManager executionEntityManager = CommandContextUtil.getExecutionEntityManager(commandContext);
        executionEntityManager.delete(child,true);
        return null;
    }
}
