package com.jeeplus.flowable.utils;

import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityManager;
import org.flowable.task.service.impl.persistence.entity.TaskEntityManager;
import org.flowable.task.service.impl.util.CommandContextUtil;

import java.util.List;

public class DeleteTaskCmd implements Command<Void> {

    private String processInstanceId;

    public DeleteTaskCmd(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    /*
     * 删除任务命令
     */
    @Override
    public Void execute(CommandContext commandContext) {
        TaskEntityManager taskEntityManager = CommandContextUtil.getTaskEntityManager(commandContext);
        ExecutionEntityManager executionEntityManager = org.flowable.engine.impl.util.CommandContextUtil.getExecutionEntityManager(commandContext);
        List<ExecutionEntity> executionEntities = executionEntityManager.findChildExecutionsByProcessInstanceId(processInstanceId);
        executionEntities.forEach(executionEntity -> taskEntityManager.deleteTasksByExecutionId(executionEntity.getId()));
        return null;
    }
}
