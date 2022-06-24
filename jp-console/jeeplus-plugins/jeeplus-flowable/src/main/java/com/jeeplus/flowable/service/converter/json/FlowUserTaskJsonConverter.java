package com.jeeplus.flowable.service.converter.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.jeeplus.extension.domain.FlowAssignee;
import com.jeeplus.extension.domain.FlowButton;
import org.flowable.bpmn.model.*;
import org.flowable.editor.language.json.converter.UserTaskJsonConverter;

import java.util.List;
import java.util.Map;

public class FlowUserTaskJsonConverter extends UserTaskJsonConverter {



    @Override
    protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode,
                                               Map<String, JsonNode> shapeMap) {
        UserTask flowElement = (UserTask) super.convertJsonToElement (elementNode, modelNode, shapeMap);

        if (getPropertyValueAsString ("formReadOnly", elementNode) != null) {
            ExtensionAttribute attribute = new ExtensionAttribute ();
            attribute.setName ("flowable:formReadOnly");
            attribute.setValue (getPropertyValueAsString ("formReadOnly", elementNode));
            flowElement.addAttribute (attribute);
        }

        JsonNode buttons = getProperty ("button", elementNode);
        if (buttons != null) {
            for (int i = 0; i < buttons.size (); i++) {

                ExtensionElement button = new ExtensionElement ();
                button.setName ("flowable:Button");

                ExtensionAttribute code = new ExtensionAttribute ();
                code.setName ("code");
                code.setValue (getValueAsString ("code", buttons.get (i)));
                ExtensionAttribute name = new ExtensionAttribute ();
                name.setName ("name");
                name.setValue (getValueAsString ("name", buttons.get (i)));
                ExtensionAttribute next = new ExtensionAttribute ();
                next.setName ("next");
                next.setValue (getValueAsString ("next", buttons.get (i)));

                ExtensionAttribute isHide = new ExtensionAttribute ();
                isHide.setName ("isHide");
                isHide.setValue (getValueAsString ("isHide", buttons.get (i)));
                ExtensionAttribute sort = new ExtensionAttribute ();
                sort.setName ("sort");
                sort.setValue (getValueAsString ("sort", buttons.get (i)));

                button.addAttribute (code);
                button.addAttribute (name);
                button.addAttribute (next);
                button.addAttribute (isHide);
                button.addAttribute (sort);

                flowElement.addExtensionElement (button);

            }

        }

        JsonNode assignees = getProperty ("assignee", elementNode);
        if (assignees != null) {
            for (int i = 0; i < assignees.size (); i++) {
                ExtensionElement assignee = new ExtensionElement ();
                assignee.setName ("flowable:Assignee");
                ExtensionAttribute type = new ExtensionAttribute ();
                type.setName ("type");
                type.setValue (getValueAsString ("type", assignees.get (i)));
                ExtensionAttribute value = new ExtensionAttribute ();
                value.setName ("value");
                value.setValue (getValueAsString ("value", assignees.get (i)));
                ExtensionAttribute condition = new ExtensionAttribute ();
                condition.setName ("condition");
                condition.setValue (getValueAsString ("condition", assignees.get (i)));

                ExtensionAttribute operationType = new ExtensionAttribute ();
                operationType.setName ("operationType");
                operationType.setValue (getValueAsString ("operationType", assignees.get (i)));
                ExtensionAttribute sort = new ExtensionAttribute ();
                sort.setName ("sort");
                sort.setValue (getValueAsString ("sort", assignees.get (i)));

                assignee.addAttribute (type);
                assignee.addAttribute (value);
                assignee.addAttribute (condition);
                assignee.addAttribute (operationType);
                assignee.addAttribute (sort);

                flowElement.addExtensionElement (assignee);

            }
        }


        if (getPropertyValueAsString ("formType", elementNode) != null) {
            ExtensionAttribute attribute2 = new ExtensionAttribute ();
            attribute2.setName ("flowable:formType");
            attribute2.setValue (getPropertyValueAsString ("formType", elementNode));
            flowElement.addAttribute (attribute2);
        }

        return flowElement;
    }

    //将Element转为Json
    @Override
    protected void convertElementToJson(ObjectNode propertiesNode, BaseElement baseElement) {
        super.convertElementToJson (propertiesNode, baseElement);
        if (baseElement instanceof UserTask) {
            //读取自定义扩展属性
            if (baseElement.getAttributes ().get ("formType") != null && baseElement.getAttributes ().get ("formType").size () > 0) {
                propertiesNode.put ("formType", baseElement.getAttributes ().get ("formType").get (0).getValue ());
            }
            if (baseElement.getAttributes ().get ("formReadOnly") != null && baseElement.getAttributes ().get ("formReadOnly").size () > 0) {
                propertiesNode.put ("formReadOnly", baseElement.getAttributes ().get ("formReadOnly").get (0).getValue ());
            }


            List<ExtensionElement> buttons = Lists.newArrayList ();
            List<ExtensionElement> assignees = Lists.newArrayList ();
            baseElement.getExtensionElements ().forEach ((s, elements) -> elements.forEach (extensionElement -> {
                if ("button".equals (extensionElement.getName ())) {
                    buttons.add (extensionElement);
                }
                if ("assignee".equals (extensionElement.getName ())) {
                    assignees.add (extensionElement);
                }
            }));
            this.addButtonProperties (buttons, propertiesNode);
            this.addAssigneeProperties (assignees, propertiesNode);


        }

    }


    protected void addAssigneeProperties(List<ExtensionElement> elements, ObjectNode propertiesNode) {
        if (elements.size () == 0)
            return;

        ArrayNode propertiesArrayNode = objectMapper.createArrayNode ();
        List<FlowAssignee> flowAssignees = Lists.newArrayList ();
        elements.forEach (extensionElement -> {
            FlowAssignee flowAssignee = new FlowAssignee ();
            flowAssignee.setType (extensionElement.getAttributes ().get ("type").get (0).getValue ());
            flowAssignee.setValue (extensionElement.getAttributes ().get ("value").get (0).getValue ());
            flowAssignee.setCondition (extensionElement.getAttributes ().get ("condition").get (0).getValue ());
            flowAssignee.setOperationType (extensionElement.getAttributes ().get ("operationType").get (0).getValue ());
            flowAssignee.setSort (Integer.valueOf (extensionElement.getAttributes ().get ("sort").get (0).getValue ()));
            flowAssignees.add (flowAssignee);
        });
        for (FlowAssignee flowAssignee : flowAssignees) {
            ObjectNode propertyItemNode = objectMapper.createObjectNode ();
            propertyItemNode.put ("type", flowAssignee.getType ());
            propertyItemNode.put ("value", flowAssignee.getValue ());
            propertyItemNode.put ("condition", flowAssignee.getCondition ());
            propertyItemNode.put ("operationType", flowAssignee.getOperationType ());
            propertyItemNode.put ("sort", flowAssignee.getSort ());

            propertiesArrayNode.add (propertyItemNode);
        }

        propertiesNode.set ("assignee", propertiesArrayNode);
    }

    protected void addButtonProperties(List<ExtensionElement> elements, ObjectNode propertiesNode) {
        if (elements.size () == 0)
            return;

        ArrayNode propertiesArrayNode = objectMapper.createArrayNode ();
        List<FlowButton> flowButtons = Lists.newArrayList ();
        elements.forEach (extensionElement -> {
            FlowButton flowButton = new FlowButton ();
            flowButton.setName (extensionElement.getAttributes ().get ("name").get (0).getValue ());
            flowButton.setCode (extensionElement.getAttributes ().get ("code").get (0).getValue ());
            flowButton.setIsHide (extensionElement.getAttributes ().get ("isHide").get (0).getValue ());
            flowButton.setSort (Integer.valueOf (extensionElement.getAttributes ().get ("sort").get (0).getValue ()));
            flowButtons.add (flowButton);
        });
        for (FlowButton flowButton : flowButtons) {
            ObjectNode propertyItemNode = objectMapper.createObjectNode ();
            propertyItemNode.put ("name", flowButton.getName ());
            propertyItemNode.put ("code", flowButton.getCode ());
            propertyItemNode.put ("isHide", flowButton.getIsHide ());
            propertyItemNode.put ("sort", flowButton.getSort ());

            propertiesArrayNode.add (propertyItemNode);
        }

        propertiesNode.set ("button", propertiesArrayNode);
    }


}
