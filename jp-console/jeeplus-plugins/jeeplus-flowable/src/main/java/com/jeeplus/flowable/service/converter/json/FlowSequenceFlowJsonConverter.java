package com.jeeplus.flowable.service.converter.json;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.jeeplus.extension.domain.NodeSetting;
import com.jeeplus.extension.domain.TaskDefExtension;
import com.jeeplus.extension.service.NodeSettingService;
import com.jeeplus.extension.service.TaskDefExtensionService;
import com.jeeplus.extension.service.dto.FlowConditionDTO;
import org.flowable.bpmn.model.ExtensionAttribute;
import org.flowable.bpmn.model.ExtensionElement;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.editor.language.json.converter.SequenceFlowJsonConverter;

import java.util.List;
import java.util.Map;

public class FlowSequenceFlowJsonConverter extends SequenceFlowJsonConverter {


    @Override
    protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode,
                                               Map<String, JsonNode> shapeMap) {
        String procDefId = getProperty ("process_id", modelNode).asText ();
        String taskDefId = getProperty ("overrideid", elementNode).asText ();
        SequenceFlow flowElement = (SequenceFlow) super.convertJsonToElement (elementNode, modelNode, shapeMap);

         NodeSetting nodeSetting = SpringUtil.getBean(NodeSettingService.class).queryByKey (procDefId, taskDefId, "conditionType");
        if(nodeSetting != null ){
            ExtensionAttribute attribute = new ExtensionAttribute();
            attribute.setName("flowable:conditionType");
            attribute.setValue(nodeSetting.getValue ());
            flowElement.addAttribute(attribute);

        }
        List<TaskDefExtension> list = SpringUtil.getBean (TaskDefExtensionService.class).lambdaQuery ()
                .eq ( TaskDefExtension::getProcessDefId, procDefId ).eq ( TaskDefExtension::getTaskDefId, taskDefId ).list ();
        if (list.size () > 0) {
            TaskDefExtension taskDefExtension = list.get (0);
            List<FlowConditionDTO> flowConditionList = SpringUtil.getBean (TaskDefExtensionService.class).getById (taskDefExtension.getId ()).getFlowConditionList ();
            for (FlowConditionDTO flowCondition : flowConditionList) {
                ExtensionElement condition = new ExtensionElement ();
                condition.setName ("flowable:Condition");

                ExtensionAttribute id = new ExtensionAttribute ();
                id.setName ("id");
                id.setValue (flowCondition.getId ());
                ExtensionAttribute field = new ExtensionAttribute ();
                field.setName ("field");
                field.setValue (flowCondition.getField ());
                ExtensionAttribute compare = new ExtensionAttribute ();
                compare.setName ("compare");
                compare.setValue (flowCondition.getCompare ());
                ExtensionAttribute value = new ExtensionAttribute ();
                value.setName ("value");
                value.setValue (flowCondition.getValue ());

                ExtensionAttribute logic = new ExtensionAttribute ();
                logic.setName ("logic");
                logic.setValue (flowCondition.getLogic ());
                ExtensionAttribute sort = new ExtensionAttribute ();
                sort.setName ("sort");
                sort.setValue (String.valueOf (flowCondition.getSort ()));

                condition.addAttribute (id);
                condition.addAttribute (field);
                condition.addAttribute (compare);
                condition.addAttribute (value);
                condition.addAttribute (logic);
                condition.addAttribute (sort);
                flowElement.addExtensionElement (condition);

            }


        }

        return flowElement;
    }


}
