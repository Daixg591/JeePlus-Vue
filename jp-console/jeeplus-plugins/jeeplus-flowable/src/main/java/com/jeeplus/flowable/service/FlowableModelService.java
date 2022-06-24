package com.jeeplus.flowable.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.jeeplus.extension.service.NodeSettingService;
import com.jeeplus.extension.service.TaskDefExtensionService;
import com.jeeplus.flowable.model.FlowModel;
import com.jeeplus.flowable.mapper.FlowModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.ui.common.model.ResultListDataRepresentation;
import org.flowable.ui.common.service.exception.BadRequestException;
import org.flowable.ui.common.util.XmlUtil;
import org.flowable.ui.modeler.model.ModelRepresentation;
import org.flowable.ui.modeler.service.FlowableModelQueryService;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 流出模型Service
 * @author liuruijun
 * @version 2021-08-02
 */
@Slf4j
@Service
@Transactional
public class FlowableModelService extends ServiceImpl <FlowModelMapper, FlowModel> {

    @Autowired
    FlowProcessService flowProcessService;
    @Autowired
	private RepositoryService repositoryService;
    @Autowired
    private ModelService flowModelService;
    @Autowired
    private NodeSettingService nodeSettingService;
    @Autowired
    private TaskDefExtensionService taskDefExtensionService;
    @Autowired
    protected FlowableModelQueryService modelQueryService;
    protected BpmnXMLConverter bpmnXmlConverter = new BpmnXMLConverter();
    protected BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();




    /**
     * 流程模型列表
     */
    public  Page<FlowModel> getModels(Page<FlowModel> page, @RequestParam(required = false) String filter, @RequestParam(required = false) String sort, @RequestParam(required = false) Integer modelType, HttpServletRequest request) {
        ResultListDataRepresentation modelQuery = this.modelQueryService.getModels(filter, sort, modelType, request);
        page.setTotal (modelQuery.getTotal());
        List<FlowModel> flowModelList = Lists.newArrayList();
        List list = modelQuery.getData();
        for(Object o : list){
            FlowModel flowModel = new FlowModel();
            ModelRepresentation m = (ModelRepresentation)o;
            flowModel.setName(m.getName());
            flowModel.setId(m.getId());
            flowModel.setComment(m.getComment());
            flowModel.setKey(m.getKey());
            flowModel.setCreatedBy(m.getCreatedBy());
            flowModel.setUpdateDate(m.getLastUpdated());
            flowModel.setLastUpdated(m.getLastUpdated());
            flowModelList.add(flowModel);
            ProcessDefinition processDefinition = flowProcessService.getProcessDefinitionByKey(m.getKey());
            Map pMap = new HashMap<>();
            if(processDefinition != null){
                String deploymentId = processDefinition.getDeploymentId();
                Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
                pMap.put("id", processDefinition.getId());
                pMap.put("category", processDefinition.getCategory());
                pMap.put("key", processDefinition.getKey());
                pMap.put("name", processDefinition.getName());
                pMap.put("version", "V:" + processDefinition.getVersion());
                pMap.put("resourceName", processDefinition.getResourceName());
                pMap.put("diagramResourceName", processDefinition.getDiagramResourceName());
                pMap.put("deploymentId", processDefinition.getDeploymentId());
                pMap.put("suspended", processDefinition.isSuspended());
                pMap.put("deploymentTime", deployment.getDeploymentTime());
            }
            flowModel.setProcDef(pMap);
        }
        page.setRecords (flowModelList);
        return page;
    }


        public String changeXmlToJson(String xml) {
                try {
                    XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
                    InputStreamReader xmlIn = new InputStreamReader(new ByteArrayInputStream(xml.getBytes("UTF-8")), "UTF-8");
                    XMLStreamReader xtr = xif.createXMLStreamReader(xmlIn);
                    BpmnModel bpmnModel = this.bpmnXmlConverter.convertToBpmnModel(xtr);
                    if (CollectionUtils.isEmpty(bpmnModel.getProcesses())) {
                        throw new BadRequestException("No process found in definition " );
                    } else {
                        if (bpmnModel.getLocationMap().size() == 0) {
                            BpmnAutoLayout bpmnLayout = new BpmnAutoLayout(bpmnModel);
                            bpmnLayout.execute();
                        }

                        ObjectNode modelNode = this.bpmnJsonConverter.convertToJson(bpmnModel);
                        return modelNode.toString();
                    }
                } catch (BadRequestException var14) {
                    throw var14;
                } catch (Exception var15) {
                    throw new BadRequestException("Import failed for "  + ", error message " + var15.getMessage());
                }
        }

    /*
     * 根据Model部署流程
     */
    @Transactional(readOnly = false)
    public String deploy(String id, String category) {
        String message = "";
        try {
            org.flowable.ui.modeler.domain.Model model = flowModelService.getModel(id);
            byte[] bpmnBytes = flowModelService.getBpmnXML(model);

            String processName = model.getName();
            if (!StringUtils.endsWith(processName, ".bpmn20.xml")) {
                processName += ".bpmn20.xml";
            }

            Deployment deployment = repositoryService.createDeployment()
                    .addBytes(processName, bpmnBytes)
                    .name(model.getName())
                    .key(model.getKey())
                    .deploy();

            log.debug("流程部署--------deploy：" + deployment );

            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                    .deploymentId(deployment.getId()).list();

            // 设置流程分类
            for (ProcessDefinition processDefinition : list) {
                if (StringUtils.isNotBlank(category)) {
                    repositoryService.setProcessDefinitionCategory(processDefinition.getId(), category);
                }
                message += "部署成功，流程ID=" + processDefinition.getId() ;
            }

            if (list.size() == 0) {
                message = "部署失败，没有流程。";
            }

        } catch (Exception e) {
            throw new FlowableException("设计模型图不正确，检查模型正确性，模型ID=" + id, e);
        }
        return message;
    }

    /**
     * 导出model的xml文件
     *
     * @throws IOException
     */
    public String export(String id, HttpServletResponse response) {
        try {
//            Model modelData = repositoryService.getModel(id);
            org.flowable.ui.modeler.domain.Model modelData = flowModelService.getModel(id);
            byte[] bpmnBytes = flowModelService.getBpmnXML(modelData);
            return new String(bpmnBytes);
        } catch (Exception e) {
            throw new FlowableException("导出model的xml文件失败，模型ID=" + id, e);
        }

    }

    /**
     * 更新Model分类
     */
    @Transactional(readOnly = false)
    public void updateCategory(String id, String category) {
        Model modelData = repositoryService.getModel(id);
        modelData.setCategory(category);
        repositoryService.saveModel(modelData);
    }

    /**
     * 删除模型
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public void delete(String id) {
        org.flowable.ui.modeler.domain.Model model = flowModelService.getModel(id);
        try {
            this.deleteDeployment(model.getKey());
            this.flowModelService.deleteModel(model.getId());
            taskDefExtensionService.deleteByProcessDefId(model.getKey());
            nodeSettingService.deleteByProcessDefId(model.getKey());
        } catch (Exception var4) {
            log.error("Error while deleting: ", var4);
            throw new BadRequestException("Model cannot be deleted: " + id);
        }
    }

    /**
     * 级联删除流程定义
     *
     * @param key
     * @return
     */
    @Transactional(readOnly = false)
    public void deleteDeployment(String key) {
        ProcessDefinition processDefinition = flowProcessService.getProcessDefinitionByKey(key);
        if(processDefinition == null){
            return;
        }else {
            try {
                repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);

            } catch (Exception var4) {
                log.error("Error while deleting: ", var4);
                throw new BadRequestException("Process cannot be deleted: " + processDefinition.getDeploymentId());
            }
            this.deleteDeployment(key);
        }


    }

}
