/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jeeplus.flowable.service.converter.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.flowable.bpmn.model.BaseElement;
import org.flowable.bpmn.model.ExtensionAttribute;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.editor.language.json.converter.StartEventJsonConverter;

import java.util.Map;

/**
 * @author liugaofeng
 */
public class FlowStartEventJsonConverter extends StartEventJsonConverter{
    @Override
    protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
        StartEvent startEvent = (StartEvent)super.convertJsonToElement(elementNode, modelNode, shapeMap);
        if (getPropertyValueAsString ("formType", elementNode) != null) {
            ExtensionAttribute attribute2 = new ExtensionAttribute ();
            attribute2.setName ("flowable:formType");
            attribute2.setValue (getPropertyValueAsString ("formType", elementNode));
            startEvent.addAttribute (attribute2);
        }
        return startEvent;
    }


    //将Element转为Json
    @Override
    protected void convertElementToJson(ObjectNode propertiesNode, BaseElement baseElement) {
        super.convertElementToJson (propertiesNode, baseElement);
        if (baseElement instanceof StartEvent) {
            //读取自定义扩展属性
            if (baseElement.getAttributes ().get ("formType") != null && baseElement.getAttributes ().get ("formType").size () > 0) {
                propertiesNode.put ("formType", baseElement.getAttributes ().get ("formType").get (0).getValue ());
            }
            if (baseElement.getAttributes ().get ("formReadOnly") != null && baseElement.getAttributes ().get ("formReadOnly").size () > 0) {
                propertiesNode.put ("formReadOnly", baseElement.getAttributes ().get ("formReadOnly").get (0).getValue ());
            }
        }

    }



}
