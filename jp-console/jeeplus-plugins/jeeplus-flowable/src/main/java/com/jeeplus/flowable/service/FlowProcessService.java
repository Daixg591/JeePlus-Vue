/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.flowable.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.jeeplus.flowable.constant.FlowableConstant;
import com.jeeplus.flowable.model.TaskComment;
import com.jeeplus.flowable.service.converter.json.FlowModelService;
import com.jeeplus.flowable.vo.ActionType;
import com.jeeplus.flowable.vo.ProcessStatus;
import com.jeeplus.flowable.vo.ProcessVo;
import com.jeeplus.flowable.vo.TaskVo;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.Process;
import org.flowable.editor.constants.ModelDataJsonConstants;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceQuery;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.task.api.Task;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

/**
 * 流程定义相关Service
 *
 * @author jeeplus
 * @version 2021-09-03
 */
@Slf4j
@Service
@Transactional
public class FlowProcessService {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private FlowModelService flowModelService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FlowableBpmnModelService flowableBpmnModelService;

    /**
     * 根据key获取流程
     */
    public ProcessDefinition getProcessDefinitionByKey(String key) {

        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .latestVersion().orderByProcessDefinitionKey().asc();
        processDefinitionQuery.processDefinitionKey(key);
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.list();
        if(processDefinitionList.size() > 0){
            return  processDefinitionList.get(0);
        }else {
            return null;
        }
    }

    public boolean isAuth(UserDTO user, String processDefId) {
        List<IdentityLink> identityLinks = repositoryService.getIdentityLinksForProcessDefinition(processDefId);
        if(identityLinks.size() == 0){
            return true;
        }
        for (IdentityLink identityLink : identityLinks ) {
            if(user.getId().equals(identityLink.getUserId())){
                return true;
            }
            if((","+user.getRoleIds()+",").contains(","+identityLink.getGroupId()+",")) {
                return true;
            }
        }
        return false;
    }
    /**
     * 流程定义列表
     */
    public Page <Map> processList(Page<Map> page, String category) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().active ()
                .latestVersion().orderByProcessDefinitionKey().asc();
        if ( StrUtil.isNotEmpty(category)) {
            processDefinitionQuery.processDefinitionCategory(category);
        }

        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.list();
        List records = Lists.newArrayList ();
        for (ProcessDefinition processDefinition : processDefinitionList) {
            if(this.isAuth( UserUtils.getCurrentUserDTO (), processDefinition.getId())){
                String deploymentId = processDefinition.getDeploymentId();
                Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
                Map pMap = new HashMap<>();
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
                records.add ( pMap );
            }
        }
        page.setRecords ( records );
        page.setTotal ( page.getRecords ().size () );
        return page;
    }

    /**
     * 运行中的流程
     */
    public Page<ProcessVo> runningList(Page<ProcessInstance> page, String procInsId, String procDefKey) throws Exception {
        List<ProcessVo> result = new ArrayList<ProcessVo>();
        Page<ProcessVo> resultPage = new Page<>();
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery().includeProcessVariables();

        if (StrUtil.isNotBlank(procInsId)) {
            processInstanceQuery.processInstanceId(procInsId);
        }

        if (StrUtil.isNotBlank(procDefKey)) {
            processInstanceQuery.processDefinitionKey(procDefKey);
        }

        page.setTotal (processInstanceQuery.count());
        resultPage.setTotal (processInstanceQuery.count());

        List<ProcessInstance> runningList;
        if (page.getSize () == -1) {//不分页
            runningList = processInstanceQuery.list();
        } else {
            int start = (int) ((page.getCurrent () - 1) * page.getSize ());
            int size = (int) (page.getSize ());
            runningList = processInstanceQuery.listPage(start, size);
        }

        for (ProcessInstance p : runningList) {
            ProcessVo processVo  = queryProcessState(p.getProcessDefinitionId (), p.getId());
            processVo.setProcessInstanceId (p.getProcessInstanceId());
            processVo.setProcessDefinitionId (p.getProcessDefinitionId());
            processVo.setProcessDefinitionName (p.getProcessDefinitionName ());
            processVo.setActivityId (p.getActivityId ());
            processVo.setVars (p.getProcessVariables());
            result.add(processVo);
        }
        resultPage.setRecords (result);
        return resultPage;
    }


    /**
     * 已结束的流程
     */
    public Page<ProcessVo> historyList(Page<ProcessVo> page, String procInsId, String procDefKey) throws Exception {

        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().includeProcessVariables().finished()
                .orderByProcessInstanceEndTime().desc();

        if (StrUtil.isNotBlank(procInsId)) {
            query.processInstanceId(procInsId);
        }

        if (StrUtil.isNotBlank(procDefKey)) {
            query.processDefinitionKey(procDefKey);
        }

        page.setTotal ( query.count () );

        List<HistoricProcessInstance> list ;
        if (page.getSize () == -1) {
            list = query.list();
        } else {
            int start = (int) ((page.getCurrent () - 1) * page.getSize ());
            int size = (int) ( page.getSize ());
            list = query.listPage(start, size);
        }
        List<ProcessVo> mapList = Lists.newArrayList();
        for(HistoricProcessInstance instance:list){
            ProcessVo processVo = queryProcessState(instance.getProcessDefinitionId (), instance.getId ());
            processVo.setVars (instance.getProcessVariables());
            processVo.setProcessDefinitionName (instance.getProcessDefinitionName());
            processVo.setStartTime (instance.getStartTime());
            processVo.setEndTime (instance.getEndTime());
            processVo.setProcessInstanceId (instance.getId());
            processVo.setProcessDefinitionId (instance.getProcessDefinitionId());
            processVo.setDeleteReason ( instance.getDeleteReason());
            mapList.add(processVo);
        }
        page.setRecords (mapList);
        return page;
    }

    /**
     * 读取资源，通过部署ID
     */
    public InputStream resourceRead(String procDefId, String proInsId, String resType) throws Exception {

        if (StrUtil.isBlank(procDefId)) {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(proInsId).singleResult();
            procDefId = processInstance.getProcessDefinitionId();
        }
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();

        String resourceName = "";
        if (resType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (resType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
        }

        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        return resourceAsStream;
    }

    /**
     * 部署流程 - 保存
     *
     * @param file
     * @return
     */
    public ResponseEntity deploy(String exportDir, String category, MultipartFile file) {

        String message = "";

        String fileName = file.getOriginalFilename();

        try {
            InputStream fileInputStream = file.getInputStream();
            Deployment deployment = null;
            String extension = FilenameUtils.getExtension(fileName);
            if (extension.equals("zip") || extension.equals("bar")) {
                ZipInputStream zip = new ZipInputStream(fileInputStream);
                deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
            } else if (extension.equals("png")) {
                deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
            } else if (fileName.indexOf("bpmn20.xml") != -1) {
                deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
            } else if (extension.equals("bpmn")) { // bpmn扩展名特殊处理，转换为bpmn20.xml
                String baseName = FilenameUtils.getBaseName(fileName);
                deployment = repositoryService.createDeployment().addInputStream(baseName + ".bpmn20.xml", fileInputStream).deploy();
            } else {
                return ResponseEntity.badRequest ().body ( "不支持的文件类型：" + extension );
            }

            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();

            // 设置流程分类
            for (ProcessDefinition processDefinition : list) {
//					ActUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir);
                repositoryService.setProcessDefinitionCategory(processDefinition.getId(), category);
                message += "部署成功，流程ID=" + processDefinition.getId() + "<br/>";
            }

            if (list.size() == 0) {
                return ResponseEntity.badRequest ().body ( "部署失败，没有流程。" );
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest ().body ( "部署失败:"+e.getMessage() );
        }
        return ResponseEntity.ok ( message );
    }

    /**
     * 设置流程分类
     */
    public void updateCategory(String procDefId, String category) {
        repositoryService.setProcessDefinitionCategory(procDefId, category);
    }

    /**
     * 挂起、激活流程实例
     */
    @Transactional(readOnly = false)
    public String updateState(String state, String procDefId) {
        if (state.equals("active")) {
            repositoryService.activateProcessDefinitionById(procDefId, true, null);
            return "已激活ID为[" + procDefId + "]的流程定义。";
        } else if (state.equals("suspend")) {
            repositoryService.suspendProcessDefinitionById(procDefId, true, null);
            return "已挂起ID为[" + procDefId + "]的流程定义。";
        }
        return "无操作";
    }

    /**
     * 将部署的流程转换为模型
     *
     * @param procDefId
     * @throws UnsupportedEncodingException
     * @throws XMLStreamException
     */
    public Model convertToModel(String procDefId) throws UnsupportedEncodingException, XMLStreamException {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                processDefinition.getResourceName());
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
        XMLStreamReader xtr = xif.createXMLStreamReader(in);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

        BpmnJsonConverter converter = new BpmnJsonConverter();
        ObjectNode modelNode = converter.convertToJson(bpmnModel);
        Model modelData = repositoryService.newModel();
        modelData.setKey(processDefinition.getKey());
        modelData.setName(processDefinition.getResourceName());
        modelData.setCategory(processDefinition.getCategory());//.getDeploymentId());
        modelData.setDeploymentId(processDefinition.getDeploymentId());
        modelData.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(modelData.getKey()).count()+1)));

        ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
        modelObjectNode.put(org.flowable.editor.constants.ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
        modelObjectNode.put(org.flowable.editor.constants.ModelDataJsonConstants.MODEL_REVISION, modelData.getVersion());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
        modelData.setMetaInfo(modelObjectNode.toString());

        this.modelService.saveModel(procDefId, modelData.getName(), modelData.getKey(), processDefinition.getDescription(), modelObjectNode.toString(), true, modelData.getVersion().toString(), SecurityUtils.getCurrentUserObject());

        return modelData;
    }

    /**
     * 导出图片文件到硬盘
     */
    public List<String> exportDiagrams(String exportDir) throws IOException {
        List<String> files = new ArrayList<String>();
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();

        for (ProcessDefinition processDefinition : list) {
            String diagramResourceName = processDefinition.getDiagramResourceName();
            String key = processDefinition.getKey();
            int version = processDefinition.getVersion();
            String diagramPath = "";

            InputStream resourceAsStream = repositoryService.getResourceAsStream(
                    processDefinition.getDeploymentId(), diagramResourceName);
            byte[] b = new byte[resourceAsStream.available()];

            @SuppressWarnings("unused")
            int len = -1;
            resourceAsStream.read(b, 0, b.length);

            // create file if not exist
            String diagramDir = exportDir + "/" + key + "/" + version;
            File diagramDirFile = new File(diagramDir);
            if (!diagramDirFile.exists()) {
                diagramDirFile.mkdirs();
            }
            diagramPath = diagramDir + "/" + diagramResourceName;
            File file = new File(diagramPath);

            // 文件存在退出
            if (file.exists()) {
                // 文件大小相同时直接返回否则重新创建文件(可能损坏)
                log.debug("diagram exist, ignore... : {}", diagramPath);

                files.add(diagramPath);
            } else {
                file.createNewFile();
                log.debug("export diagram to : {}", diagramPath);

                // wirte bytes to file
                FileUtils.writeByteArrayToFile(file, b, true);

                files.add(diagramPath);
            }

        }

        return files;
    }

    /**
     * 删除部署的流程，级联删除流程实例
     *
     * @param deploymentId 流程部署ID
     */
    @Transactional(readOnly = false)
    public void deleteDeployment(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
    }

    /**
     * 删除运行中的流程实例
     *
     * @param procInsId    流程实例ID
     * @param deleteReason 删除原因，可为空
     */
    @Transactional(readOnly = false)
    public void deleteProcIns(String procInsId, String deleteReason) {
        this.stopProcessInstanceById (procInsId, ProcessStatus.DELETED, deleteReason);
    }


    /**
     * 终止流程实例
     *
     * @param procInsId    流程实例ID
     * @param processStatus    流程状态
     */
    @Transactional(readOnly = false)
    public void stopProcessInstanceById(String procInsId, ProcessStatus processStatus, String comment) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
        if (processInstance != null) {
           Task currentTask = taskService.createTaskQuery ().processInstanceId (procInsId).list ().get (0);
            //1、添加审批记录
            TaskComment taskComment = new TaskComment ();
            if(processStatus == ProcessStatus.REVOKE){
                taskComment.setType ( ActionType.REVOKE.getType ());
                taskComment.setStatus (ActionType.REVOKE.getStatus ());
                taskComment.setMessage (comment);
            }else if(processStatus == ProcessStatus.STOP){
                taskComment.setType (ActionType.STOP.getType ());
                taskComment.setStatus (ActionType.STOP.getStatus ());
                taskComment.setMessage (comment);
            }else if(processStatus == ProcessStatus.REJECT){
                taskComment.setType (ActionType.REJECT.getType ());
                taskComment.setStatus (ActionType.REJECT.getStatus ());
                taskComment.setMessage (comment);
            }else if(processStatus == ProcessStatus.DELETED){
                taskComment.setType (ActionType.DELETED.getType ());
                taskComment.setStatus (ActionType.DELETED.getStatus ());
                taskComment.setMessage (comment);
            }



            taskService.addComment (currentTask.getId (),procInsId, taskComment.getCommentType (), taskComment.getFullMessage ());
            if(StrUtil.isBlank (currentTask.getAssignee ()))  { // 未签收任务
                taskService.claim (currentTask.getId (), UserUtils.getCurrentUserDTO ().getId ());
            }
            runtimeService.setVariable(procInsId, FlowableConstant.PROCESS_STATUS_CODE, processStatus.getCode ());
            List<EndEvent> endNodes = flowableBpmnModelService.findEndFlowElement(processInstance.getProcessDefinitionId());
            String endId = endNodes.get(0).getId();
            //2、执行终止
            List<Execution> executions = runtimeService.createExecutionQuery().parentId(procInsId).list();
            List<String> executionIds = new ArrayList<>();
            executions.forEach(execution -> executionIds.add(execution.getId()));
            this.moveExecutionsToSingleActivityId(executionIds, endId);


        }
    }

    /**
     * 撤销流程实例
     *
     * @param procInsId    流程实例ID
     */
    @Transactional(readOnly = false)
    public void callBackProcessInstanceById(String procInsId) {

        this.stopProcessInstanceById (procInsId, ProcessStatus.REVOKE, ProcessStatus.REVOKE.getStatus ());


    }


    /**
     * 执行跳转
     */
    protected void moveExecutionsToSingleActivityId(List<String> executionIds, String activityId) {
        runtimeService.createChangeActivityStateBuilder()
                .moveExecutionsToSingleActivityId(executionIds, activityId)
                .changeState();
    }
    /**
     * 删除历史流程
     */
    @Transactional(readOnly = false)
    public void delHistoryProcInsById(String procInsId) {
        historyService.deleteHistoricProcessInstance(procInsId);
    }

    /**
     * 获取流程定义
     */
    public ProcessDefinition getProcessDefinition(String proDefId) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .latestVersion().orderByProcessDefinitionKey().asc();
        if (StrUtil.isNotBlank(proDefId)) {
            processDefinitionQuery.processDefinitionId(proDefId);
        }
        ProcessDefinition p = processDefinitionQuery.singleResult();
        return p;
    }


    /**
     * 读取流程文件
     */
    public Map getImageStream(String processDefId){
            Map m = new HashMap();
            try {
                BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefId);
                byte[] bpmnBytes = flowModelService.getBpmnXML(bpmnModel);
                m.put("bpmnXml", new String(bpmnBytes));
                return m;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }



    /**
     * 查询流程实例状态
     *
     * @return
     * @throws Exception
     */
    public ProcessVo queryProcessState( String processDefinitionId, String processInstanceId) throws Exception {
        ProcessVo processVo = new ProcessVo ();
        // 通过流程实例ID查询流程实例
        ProcessInstance pi = runtimeService.createProcessInstanceQuery ()
                .processInstanceId (processInstanceId).singleResult ();
        if (pi != null) {
            if (pi.isSuspended ()) {
                //挂起实例
                processVo.setProcessStatus (ProcessStatus.SUSPENDED);
                return processVo;
            } else {
                //执行实例
                processVo.setProcessStatus (ProcessStatus.WAITING);
                Task currentTask = taskService.createTaskQuery ().processInstanceId (processInstanceId).list ().get (0);
                processVo.setTask (new TaskVo (currentTask));
                processVo.setTaskName (currentTask.getName ());
                return processVo;
            }

        } else {
            HistoricProcessInstance pi2 = historyService.createHistoricProcessInstanceQuery ().processInstanceId (processInstanceId).singleResult ();
//            String endActivityId = pi2.getEndActi vityId ();
            List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery ().processInstanceId (processInstanceId).finished ().orderByHistoricActivityInstanceEndTime ().desc ().list ().stream ()
                    .filter (activity -> activity.getActivityType ().equals (BpmnXMLConstants.ELEMENT_TASK_USER)
                            || activity.getActivityType ().equals (BpmnXMLConstants.ELEMENT_EVENT_END)
                            || activity.getActivityType ().equals (BpmnXMLConstants.ELEMENT_EVENT_START)).distinct ().collect (Collectors.toList ());
            Process process = repositoryService.getBpmnModel (processDefinitionId).getMainProcess ();
            String taskName = "";
            if(historicActivityInstanceList.size () != 0) {
              HistoricActivityInstance  historicActivityInstance =  historicActivityInstanceList.get (0);
              FlowNode flowElement = (FlowNode) process.getFlowElement (historicActivityInstance.getActivityId (), true);
              taskName = flowElement.getName ();
            }

            if (pi2 != null) {

                HistoricVariableInstance processStatusInstance = historyService.createHistoricVariableInstanceQuery ().processInstanceId (processInstanceId).variableName (FlowableConstant.PROCESS_STATUS_CODE).singleResult ();

                if (pi2.getDeleteReason () == null && processStatusInstance == null) {
                    processVo.setProcessStatus (ProcessStatus.AGREE);
                    processVo.setTaskName (taskName);
                    return processVo;
                } else if (pi2.getDeleteReason () != null) {
                    processVo.setProcessStatus (ProcessStatus.STOP);
                    processVo.setTaskName (taskName);
                    processVo.setDeleteReason (pi2.getDeleteReason ());
                    return processVo;
                } else {
                    int code =Integer.valueOf (processStatusInstance.getValue ().toString ());
                    ProcessStatus processStatus = ProcessStatus.value (code);
                    processVo.setProcessStatus (processStatus);
                    processVo.setTaskName (taskName);
                    return processVo;
                }

            } else {
                processVo.setProcessStatus (ProcessStatus.DELETED);
                processVo.setTaskName (taskName);
                processVo.setDeleteReason (pi2.getDeleteReason ());
                return processVo;
            }

        }
    }

}
