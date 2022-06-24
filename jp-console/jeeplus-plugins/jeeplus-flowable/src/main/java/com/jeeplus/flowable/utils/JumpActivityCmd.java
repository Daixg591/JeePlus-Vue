package com.jeeplus.flowable.utils;

import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.FlowableEngineAgenda;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityManager;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.impl.util.ProcessDefinitionUtil;

import java.util.List;

/*
 * 执行跳转
 */
public class JumpActivityCmd implements Command<Void> {

    private String target;
    private String processInstanceId;

    public JumpActivityCmd(String processInstanceId, String target) {
        this.processInstanceId = processInstanceId;
        this.target = target;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        ExecutionEntityManager executionEntityManager = CommandContextUtil.getExecutionEntityManager (commandContext);
        List<ExecutionEntity> executionEntities = executionEntityManager.findChildExecutionsByParentExecutionId (processInstanceId);
        Process process = ProcessDefinitionUtil.getProcess (executionEntities.get (0).getProcessDefinitionId ());
        FlowNode targetFlowElement = (FlowNode) process.getFlowElement (target);
        FlowableEngineAgenda agenda = CommandContextUtil.getAgenda ();
        executionEntities.forEach (execution -> {
            execution.setCurrentFlowElement (targetFlowElement);
            agenda.planContinueProcessInCompensation (execution);
        });
        return null;
    }
}
