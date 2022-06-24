package com.jeeplus.flowable.service.converter.json;

import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.bpmn.model.UserTask;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;

public class FlowBpmnJsonConverter extends BpmnJsonConverter {
    static {
        convertersToBpmnMap.put(STENCIL_SEQUENCE_FLOW, FlowSequenceFlowJsonConverter.class);
        convertersToBpmnMap.put(STENCIL_TASK_USER, FlowUserTaskJsonConverter.class);
        convertersToBpmnMap.put(STENCIL_EVENT_START_NONE, FlowStartEventJsonConverter.class);
        convertersToBpmnMap.put(STENCIL_EVENT_START_TIMER, FlowStartEventJsonConverter.class);
        convertersToBpmnMap.put(STENCIL_EVENT_START_CONDITIONAL, FlowStartEventJsonConverter.class);
        convertersToBpmnMap.put(STENCIL_EVENT_START_ERROR, FlowStartEventJsonConverter.class);
        convertersToBpmnMap.put(STENCIL_EVENT_START_ESCALATION, FlowStartEventJsonConverter.class);
        convertersToBpmnMap.put(STENCIL_EVENT_START_MESSAGE, FlowStartEventJsonConverter.class);
        convertersToBpmnMap.put(STENCIL_EVENT_START_SIGNAL, FlowStartEventJsonConverter.class);

        convertersToJsonMap.put(SequenceFlow.class, FlowSequenceFlowJsonConverter.class);
        convertersToJsonMap.put(UserTask.class, FlowUserTaskJsonConverter.class);
        convertersToJsonMap.put(StartEvent.class, FlowStartEventJsonConverter.class);
    }
}
