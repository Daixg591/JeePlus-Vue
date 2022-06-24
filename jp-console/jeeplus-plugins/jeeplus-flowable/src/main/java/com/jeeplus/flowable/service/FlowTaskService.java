/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.flowable.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.flowable.constant.FlowableConstant;
import com.jeeplus.flowable.vo.*;
import com.jeeplus.flowable.common.cmd.BackUserTaskCmd;
import com.jeeplus.flowable.model.Flow;
import com.jeeplus.flowable.model.TaskComment;
import com.jeeplus.flowable.mapper.FlowMapper;
import com.jeeplus.flowable.service.converter.json.FlowModelService;
import com.jeeplus.flowable.utils.FlowableUtils;
import com.jeeplus.flowable.utils.ProcessDefCache;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.Process;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.runtime.ActivityInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.flowable.task.service.impl.persistence.entity.TaskEntityImpl;
import org.flowable.variable.api.history.HistoricVariableInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程定义相关Service
 *
 * @author jeeplus
 * @version 2021-09-03
 */
@Slf4j
@Service
@Transactional
public class FlowTaskService {

    @Autowired
    private FlowMapper flowMapper;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private FlowModelService flowModelService;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private FlowProcessService flowProcessService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取待办任务列表
     *
     * @return
     */
    public Page <ProcessVo> todoList(Page<ProcessVo> page, Flow flow) {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>> ();
        String userId = UserUtils.getCurrentUserDTO ().getId ();//ObjectUtils.toString(UserUtils.getUser().getId());
        // =============== 已经签收或者等待签收的任务  ===============
        TaskQuery todoTaskQuery = taskService.createTaskQuery ().taskCandidateOrAssigned (userId).active ()
                .includeProcessVariables ().orderByTaskCreateTime ().desc ();

        // 设置查询条件
        if ( StrUtil.isNotBlank (flow.getProcDefKey ())) {
            todoTaskQuery.processDefinitionKey (flow.getProcDefKey ());
        }
        if (flow.getBeginDate () != null) {
            todoTaskQuery.taskCreatedAfter (flow.getBeginDate ());
        }
        if (flow.getEndDate () != null) {
            todoTaskQuery.taskCreatedBefore (flow.getEndDate ());
        }
        if (StrUtil.isNotBlank (flow.getTitle ())) {
            todoTaskQuery.processVariableValueLike ( FlowableConstant.TITLE, "%" + flow.getTitle () + "%");
        }


        long total = todoTaskQuery.count ( );
        page.setTotal ( total );

        List <Task> todoList;
        // 查询列表
        if ( page.getSize ( ) == -1 ) {//不分页
            todoList = todoTaskQuery.list ( );
        } else {
            int start = (int) ((page.getCurrent ( ) - 1) * page.getSize ( ));
            int size = (int) (page.getSize ( ));
            todoList = todoTaskQuery.listPage ( start, size );
        }
        List records = Lists.newArrayList ();
        for (Task task : todoList) {
            Process process = SpringUtil.getBean (RepositoryService.class).getBpmnModel (task.getProcessDefinitionId ()).getMainProcess ();
            ProcessVo processVo = new ProcessVo ();
            TaskVo taskVo = new TaskVo (task);
            taskVo.setProcessDefKey (process.getId ());
            processVo.setTask (taskVo);
            processVo.setVars (task.getProcessVariables ());
            processVo.setProcessDefinitionName ( ProcessDefCache.get (task.getProcessDefinitionId ()).getName ());
            processVo.setVersion (ProcessDefCache.get (task.getProcessDefinitionId ()).getVersion ());
            processVo.setStatus ("todo");
            records.add ( processVo );
        }
        page.setRecords ( records );
        return page;
    }

    /**
     * 获取已办任务列表
     *
     * @param page
     * @return
     */
    public Page<HisTaskVo> historicList(Page<HisTaskVo> page, Flow act) {
        String userId = UserUtils.getCurrentUserDTO ().getId ();

        HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery ().taskAssignee (userId).finished ()
                .includeProcessVariables ().orderByHistoricTaskInstanceEndTime ().desc ();

        // 设置查询条件
        if (StrUtil.isNotBlank (act.getProcDefKey ())) {
            histTaskQuery.processDefinitionKey (act.getProcDefKey ());
        }
        if (act.getBeginDate () != null) {
            histTaskQuery.taskCompletedAfter (act.getBeginDate ());
        }
        if (act.getEndDate () != null) {
            histTaskQuery.taskCompletedBefore (act.getEndDate ());
        }
        if (act.getTitle () != null) {
            histTaskQuery.processVariableValueLike (FlowableConstant.TITLE, "%" + act.getTitle () + "%");
        }

        // 查询总数
        page.setTotal (histTaskQuery.count ());
        // 查询列表
        List<HistoricTaskInstance> histList;
        if (page.getSize () == -1) {
            histList = histTaskQuery.list ();
        } else {
            int start = (int) ((page.getCurrent ( ) - 1) * page.getSize ( ));
            int size = (int) (page.getSize ( ));
            histList = histTaskQuery.listPage (start, size);
        }

        List records = Lists.newArrayList ();
        for (HistoricTaskInstance histTask : histList) {
            HisTaskVo hisTaskVo= new HisTaskVo (histTask);
            hisTaskVo.setProcessDefinitionName ( ProcessDefCache.get (histTask.getProcessDefinitionId ()).getName ());
            hisTaskVo.setBack (isBack (histTask));
            List<Task> currentTaskList = taskService.createTaskQuery ().processInstanceId (histTask.getProcessInstanceId ()).list ();
            if(((List) currentTaskList).size () > 0){
                TaskVo currentTaskVo =  new TaskVo (currentTaskList.get (0));
                hisTaskVo.setCurrentTask (currentTaskVo);
            }



            // 获取意见评论内容

            List<TaskComment> commentList = this.getTaskComments (histTask.getId ());
            if (commentList.size () > 0) {
                TaskComment comment = commentList.get (commentList.size ()-1);
                hisTaskVo.setComment (comment.getMessage ());
                hisTaskVo.setLevel (comment.getLevel ());
                hisTaskVo.setType (comment.getType ());
                hisTaskVo.setStatus (comment.getStatus ());

            }
            records.add ( hisTaskVo );
        }
        page.setRecords ( records );
        return page;
    }

    /**
     * 获取流转历史任务列表
     *
     * @param procInsId 流程实例
     */
    public List<Flow> historicTaskList(String procInsId) throws Exception {
        List<Flow> actList = Lists.newArrayList ();
        List<HistoricActivityInstance> list = Lists.newArrayList ();
        List<HistoricActivityInstance> historicActivityInstances2 = historyService.createHistoricActivityInstanceQuery ().processInstanceId (procInsId)
                .orderByHistoricActivityInstanceStartTime ().asc ().orderByHistoricActivityInstanceEndTime ().asc ().list ();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances2) {
            if (historicActivityInstance.getEndTime () != null) {
                list.add (historicActivityInstance);
            }
        }

        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances2) {
            if (historicActivityInstance.getEndTime () == null) {
                list.add (historicActivityInstance);
            }
        }

        for (int i = 0; i < list.size (); i++) {
            HistoricActivityInstance histIns = list.get (i);
            // 只显示开始节点和结束节点，并且执行人不为空的任务
            if (StrUtil.isNotBlank (histIns.getAssignee ())
                    && historyService.createHistoricTaskInstanceQuery ().taskId (histIns.getTaskId ()).count () != 0
                    || BpmnXMLConstants.ELEMENT_TASK_USER.equals (histIns.getActivityType ()) && histIns.getEndTime () == null
                    || BpmnXMLConstants.ELEMENT_EVENT_START.equals (histIns.getActivityType ())
                    || BpmnXMLConstants.ELEMENT_EVENT_END.equals (histIns.getActivityType ())) {
                // 获取流程发起人名称
                Flow e = queryTaskState (histIns);

                actList.add (e);
            }
        }
        return actList;
    }

    /**
     * 获取流程表单（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY）
     *
     * @return
     */
    public String getFormKey(String procDefId, String taskDefKey) {
        String formKey = "";
        if (StrUtil.isNotBlank (procDefId)) {
            if (StrUtil.isNotBlank (taskDefKey)) {
                try {
                    formKey = formService.getTaskFormKey (procDefId, taskDefKey);
                } catch (Exception e) {
                    formKey = "";
                }
            }
            if (StrUtil.isBlank (formKey)) {
                formKey = formService.getStartFormKey (procDefId);
            }
            if (StrUtil.isBlank (formKey)) {
                formKey = "/404";
            }
        }
        log.debug ("getFormKey: {}", formKey);
        return formKey;
    }

    /**
     * 获取正在运行的流程实例对象
     *
     * @param procInsId
     * @return
     */
    public ProcessInstance getProcIns(String procInsId) {
        return runtimeService.createProcessInstanceQuery ().processInstanceId (procInsId).singleResult ();
    }

    /**
     * 获取已经结束流程实例对象
     *
     * @param procInsId
     * @return
     */
    public HistoricProcessInstance getFinishedProcIns(String procInsId) {
        return historyService.createHistoricProcessInstanceQuery ().processInstanceId (procInsId).singleResult ();
    }


    /**
     * 获取我发起的流程申请列表
     *
     * @param user
     * @return
     */
    public Page<ProcessVo> getMyStartedProcIns(UserDTO user, Page<ProcessVo> page, Flow flow) throws Exception {
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery ().startedBy (user.getId ()).includeProcessVariables ().orderByProcessInstanceStartTime ().desc ();
        if (flow.getBeginDate () != null) {
            query.startedAfter (flow.getBeginDate ());
        }
        if (flow.getEndDate () != null) {
            query.startedBefore (flow.getEndDate ());
        }
        if (StrUtil.isNotBlank (flow.getTitle ())) {
            query.variableValueLike (FlowableConstant.TITLE, "%" + flow.getTitle () + "%");
        }

        page.setTotal (query.count ());
        List<HistoricProcessInstance> histList;
        if (page.getSize () == -1) {//不分页
            histList = query.list ();
        } else {
            int start = (int) ((page.getCurrent ( ) - 1) * page.getSize ( ));
            int size = (int) (page.getSize ( ));
            histList = query.involvedUser (user.getId ()).listPage (start, size);
        }
        List records = Lists.newArrayList ();
        for (HistoricProcessInstance historicProcessInstance : histList) {
            ProcessVo processVo =  flowProcessService.queryProcessState (historicProcessInstance.getProcessDefinitionId (), historicProcessInstance.getId ());
            processVo.setEndTime (historicProcessInstance.getEndTime ());
            processVo.setStartTime (historicProcessInstance.getStartTime ());
            processVo.setProcessDefinitionId (historicProcessInstance.getProcessDefinitionId ());
            processVo.setProcessInstanceId (historicProcessInstance.getId ());
            processVo.setVars (historicProcessInstance.getProcessVariables ());
            processVo.setProcessDefinitionName (historicProcessInstance.getProcessDefinitionName ());
            processVo.setVersion ( historicProcessInstance.getProcessDefinitionVersion ());
            records.add (processVo);
        }
        page.setRecords ( records );

        return page;
    }


    /**
     * 启动流程
     *
     * @param procDefKey    流程定义KEY
     * @param businessTable 业务表表名
     * @param businessId    业务表编号
     * @param title         流程标题，显示在待办任务标题
     * @return 流程实例ID
     */
    public String startProcess(String procDefKey, String businessTable, String businessId, String title) {
        Map<String, Object> vars = Maps.newHashMap ();
        return startProcess (procDefKey, businessTable, businessId, title, vars);
    }

    /**
     * 启动流程
     *
     * @param procDefKey    流程定义KEY
     * @param businessTable 业务表表名
     * @param businessId    业务表编号
     * @param title         流程标题，显示在待办任务标题
     * @param vars          流程变量
     * @return 流程实例ID
     */
    @SuppressWarnings("unused")
    public String startProcess(String procDefKey, String businessTable, String businessId, String title, Map<String, Object> vars) {
        //String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId())
        // 设置流程变量
        if (vars == null) {
            vars = Maps.newHashMap ();
        }

        String userId = (String) vars.get (FlowableConstant.INITIATOR);
        if (userId == null) {
            userId = UserUtils.getCurrentUserDTO ().getId ();
        }
        String userName = UserUtils.get (userId).getName ();
        vars.put (FlowableConstant.USERNAME, userName);

        // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId (userId);

        // 设置流程标题
        if (StrUtil.isNotBlank (title)) {
            vars.put (FlowableConstant.TITLE, title);
        }

        // 启动流程
        ProcessInstance procIns = runtimeService.startProcessInstanceByKey (procDefKey, businessTable + ":" + businessId, vars);

        // 更新业务表流程实例ID
        Flow act = new Flow ();
        act.setBusinessTable (businessTable);// 业务表名
        act.setBusinessId (businessId);  // 业务表ID
        act.setProcInsId (procIns.getId ());
        act.setVars (vars);
        flowMapper.updateProcInsIdByBusinessId (act);
        return act.getProcInsId ();
    }


    /**
     * 删除任务
     *
     * @param taskId       任务ID
     * @param deleteReason 删除原因
     */
    public void deleteTask(String taskId, String deleteReason) {
        taskService.deleteTask (taskId, deleteReason);
    }

    /**
     * 签收任务
     *
     * @param taskId 任务ID
     * @param userId 签收用户ID（用户登录名）
     */
    public void claim(String taskId, String userId) {
        taskService.claim (taskId, userId);
    }


    /**
     * 提交任务, 并保存意见
     *
     * @param vars      任务变量
     */
    public void complete(Flow flow, Map<String, Object> vars) {
        // 添加意见
        if (StrUtil.isNotBlank (flow.getProcInsId ())) {
            taskService.addComment (flow.getTaskId (), flow.getProcInsId (),flow.getComment ().getCommentType (), flow.getComment ().getFullMessage ());
        }

        // 设置流程变量
        if (vars == null) {
            vars = Maps.newHashMap ();
        }

        // 设置流程标题
        if (StrUtil.isNotBlank (flow.getTitle ())) {
            vars.put (FlowableConstant.TITLE, flow.getTitle ());
        }


        Task task = taskService.createTaskQuery ().taskId (flow.getTaskId ()).singleResult ();
        // owner不为空说明可能存在委托任务
        if (StrUtil.isNotBlank (task.getOwner ())) {
            DelegationState delegationState = task.getDelegationState ();
            switch (delegationState) {
                case PENDING:
                    taskService.resolveTask (flow.getTaskId ());
                    taskService.complete (flow.getTaskId (), vars);
                    break;

                case RESOLVED:
                    // 委托任务已经完成
                    break;

                default:
                    // 不是委托任务
                    taskService.complete (flow.getTaskId (), vars);
                    break;
            }
        } else if(StrUtil.isBlank (task.getAssignee ()))  { // 未签收任务
            // 签收任务
            taskService.claim (flow.getTaskId (), UserUtils.getCurrentUserDTO ().getId ());
            // 提交任务
            taskService.complete (flow.getTaskId (), vars);
        } else  {
            // 提交任务
            taskService.complete (flow.getTaskId (), vars);
        }
    }


    /**
     * 查询任务节点的状态
     */
    public Flow queryTaskState(HistoricActivityInstance histIns) {
        Flow e = new Flow ();
        e.setHistIns (histIns);
        // 获取流程发起人名称
        if (BpmnXMLConstants.ELEMENT_EVENT_START.equals (histIns.getActivityType ())) {
            List<HistoricProcessInstance> il = historyService.createHistoricProcessInstanceQuery ().processInstanceId (histIns.getProcessInstanceId ()).orderByProcessInstanceStartTime ().asc ().list ();
            if (il.size () > 0) {
                if (StrUtil.isNotBlank (il.get (0).getStartUserId ())) {
                    UserDTO user = UserUtils.get (il.get (0).getStartUserId ());
                    if (user != null) {
                        e.setAssignee (histIns.getAssignee ());
                        e.setAssigneeName (user.getName ());
                    }
                }
            }
            TaskComment taskComment = new TaskComment ();
            taskComment.setStatus (FlowableConstant.START_EVENT_LABEL);
            taskComment.setMessage (FlowableConstant.START_EVENT_COMMENT);
            e.setComment (taskComment);
            return e;
        }
        if (BpmnXMLConstants.ELEMENT_EVENT_END.equals (histIns.getActivityType ())) {
            TaskComment taskComment = new TaskComment ();
            taskComment.setStatus (FlowableConstant.END_EVENT_LABEL);
            taskComment.setMessage (FlowableConstant.END_EVENT_COMMENT);
            e.setAssigneeName (FlowableConstant.SYSTEM_EVENT_COMMENT);
            e.setComment (taskComment);
            return e;
        }
        // 获取任务执行人名称
        if (StrUtil.isNotEmpty (histIns.getAssignee ())) {
            UserDTO user = UserUtils.get (histIns.getAssignee ());
            if (user != null) {
                e.setAssignee (histIns.getAssignee ());
                e.setAssigneeName (user.getName ());
            }
        }
        // 获取意见评论内容
        if (StrUtil.isNotBlank (histIns.getTaskId ())) {
            List<TaskComment> commentList = this.getTaskComments (histIns.getTaskId ());
            HistoricVariableInstanceQuery action = historyService.createHistoricVariableInstanceQuery ().processInstanceId (histIns.getProcessInstanceId ()).taskId (histIns.getTaskId ()).variableName ("_flow_button_name");
            if (commentList.size () > 0) {
                TaskComment comment = commentList.get (commentList.size ()-1);
                e.setComment (comment);
            }else {
                e.setComment (new TaskComment ());
            }
        }
        //等待执行的任务
        if(histIns.getEndTime () == null) {
            TaskComment taskComment = new TaskComment ();
            taskComment.setStatus ( ActionType.WAITING.getStatus ());
            taskComment.setMessage (FlowableConstant.WAITING_EVENT_COMMENT);
            e.setComment (taskComment);
        }
        return e;
    }


    public List<TaskComment> getTaskComments(String taskId){
        return jdbcTemplate.query("select * from ACT_HI_COMMENT where TYPE_ like '"+TaskComment.prefix+"%' and TASK_ID_ = '" + taskId + "' order by TIME_ desc", new RowMapper<TaskComment>() {
            @Override
            public TaskComment mapRow(ResultSet rs, int rowNum) throws SQLException {
                TaskComment taskComment = new TaskComment ();
                taskComment.setCommentType (rs.getString ("TYPE_"));
                taskComment.setFullMessage (new String(rs.getBytes ("FULL_MSG_")));
                return taskComment;
            }
        });
    }


    public Map getDiagram(String processId) {
        Map m = new HashMap ();
        try {
            String processDefId = "";
            ProcessInstance pi = runtimeService.createProcessInstanceQuery ().processInstanceId (processId).singleResult ();
            //流程走完的不显示图
            if (pi == null) {
                processDefId = historyService.createHistoricProcessInstanceQuery ().processInstanceId (processId).singleResult ().getProcessDefinitionId ();
            } else {
                processDefId = pi.getProcessDefinitionId ();
            }
            BpmnModel bpmnModel = repositoryService.getBpmnModel (processDefId);
            List<HistoricActivityInstance> historyProcess = getHistoryProcess (processId);
            Set<String> activityIds = new LinkedHashSet<> ();
            List<String> flows = new ArrayList<> ();
            for (HistoricActivityInstance hi : historyProcess) {
                String activityType = hi.getActivityType ();
                if (activityType.equals (BpmnXMLConstants.ELEMENT_SEQUENCE_FLOW) || activityType.equals (BpmnXMLConstants.ELEMENT_GATEWAY_EXCLUSIVE)) {
                    flows.add (hi.getActivityId ());
                } else  if (StrUtil.isNotBlank (hi.getAssignee ())
                        && historyService.createHistoricTaskInstanceQuery ().taskId (hi.getTaskId ()).count () != 0
                        || BpmnXMLConstants.ELEMENT_TASK_USER.equals (hi.getActivityType ()) && hi.getEndTime () == null
                        || BpmnXMLConstants.ELEMENT_EVENT_START.equals (hi.getActivityType ())
                        || BpmnXMLConstants.ELEMENT_EVENT_END.equals (hi.getActivityType ())) {
                    activityIds.add (hi.getActivityId ());
                }
            }
            List<Task> tasks = taskService.createTaskQuery ().processInstanceId (processId).list ();
            for (Task task : tasks) {
                activityIds.add (task.getTaskDefinitionKey ());
            }
            byte[] bpmnBytes = flowModelService.getBpmnXML (bpmnModel);
            m.put ("bpmnXml", new String (bpmnBytes));
            m.put ("flows", flows);
            m.put ("activityIds", activityIds);
            return m;
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return null;
    }


    /**
     * 任务历史
     *
     * @param processId 部署id
     */
    public List<HistoricActivityInstance> getHistoryProcess(String processId) {
        List<HistoricActivityInstance> list = historyService // 历史相关Service
                .createHistoricActivityInstanceQuery () // 创建历史活动实例查询
                .processInstanceId (processId) // 执行流程实例id
                .finished ().orderByHistoricActivityInstanceEndTime ().asc ()
                .list ();
        return list;
    }



    /**
     * 保存审核意见
     *
     * @param flow
     */
    public void auditSave(Flow flow, Map vars) {
        complete (flow, vars);

    }


    /**
     * 是否可以取回任务
     */
    public boolean isBack(HistoricTaskInstance hisTask) {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery ()
                .processInstanceId (hisTask.getProcessInstanceId ()).singleResult ();
        if (pi != null) {
            if (pi.isSuspended ()) {
                return false;
            } else {
                Task currentTask = taskService.createTaskQuery ().processInstanceId (hisTask.getProcessInstanceId ()).list ().get (0);
                HistoricTaskInstance lastHisTask = historyService.createHistoricTaskInstanceQuery ().processInstanceId (hisTask.getProcessInstanceId ()).finished ()
                        .includeProcessVariables ().orderByHistoricTaskInstanceEndTime ().desc ().list ().get (0);

                if (currentTask.getClaimTime () != null) {//用户已签收
                    return false;
                }
                if (hisTask.getId ().equals (lastHisTask.getId ())) {
                    return true;
                }
                return false;
            }

        } else {
            return false;
        }
    }

    /*
     * 驳回任务
     */
    public void backTask(String backTaskDefKey, String taskId, TaskComment comment) {
        Task task = taskService.createTaskQuery ().taskId (taskId).singleResult ();
        if(StrUtil.isBlank (task.getAssignee ())){
            taskService.claim (taskId, UserUtils.getCurrentUserDTO ().getId ());
        }

        // 退回发起者处理,退回到发起者,默认设置任务执行人为发起者
        ActivityInstance targetRealActivityInstance = runtimeService.createActivityInstanceQuery ().processInstanceId (task.getProcessInstanceId ()).activityId (backTaskDefKey).list ().get (0);
        if (targetRealActivityInstance.getActivityType ().equals (BpmnXMLConstants.ELEMENT_EVENT_START)) {
            flowProcessService.stopProcessInstanceById (task.getProcessInstanceId (), ProcessStatus.REJECT, comment.getMessage ());
        }else {
            taskService.addComment (taskId, task.getProcessInstanceId (), comment.getCommentType (), comment.getFullMessage ());
            managementService.executeCommand (new BackUserTaskCmd (runtimeService,
                    taskId, backTaskDefKey));
        }
    }


    /**
     * 获取可驳回节点
     *
     * @param taskId
     * @return
     */
    public List<Flow> getBackNodes(String taskId) {
        Task taskEntity = taskService.createTaskQuery ().taskId (taskId).singleResult ();
        String processInstanceId = taskEntity.getProcessInstanceId ();
        String currActId = taskEntity.getTaskDefinitionKey ();
        String processDefinitionId = taskEntity.getProcessDefinitionId ();
        Process process = repositoryService.getBpmnModel (processDefinitionId).getMainProcess ();
        FlowNode currentFlowElement = (FlowNode) process.getFlowElement (currActId, true);
        List<ActivityInstance> activitys =
                runtimeService.createActivityInstanceQuery ().processInstanceId (processInstanceId).finished ().orderByActivityInstanceStartTime ().asc ().list ();
        List<String> activityIds =
                activitys.stream ().filter (activity -> activity.getActivityType ().equals (BpmnXMLConstants.ELEMENT_TASK_USER) || activity.getActivityType ().equals (BpmnXMLConstants.ELEMENT_EVENT_START)).filter (activity -> !activity.getActivityId ().equals (currActId)).map (ActivityInstance::getActivityId).distinct ().collect (Collectors.toList ());
        List<Flow> result = new ArrayList<> ();
        for (String activityId : activityIds) {
            FlowNode toBackFlowElement = (FlowNode) process.getFlowElement (activityId, true);
            if (FlowableUtils.isReachable (process, toBackFlowElement, currentFlowElement)) {
                Flow vo = new Flow ();
                vo.setTaskDefKey (activityId);
                vo.setTaskName (toBackFlowElement.getName ());
                vo.setTaskId (activityId);
                result.add (vo);
            }
        }
        return result;
    }

    public void addSignTask(String taskId, List<String> userIds, String comment, Boolean flag) throws Exception {
        TaskEntityImpl taskEntity = (TaskEntityImpl) taskService.createTaskQuery().taskId(taskId).singleResult();
        //1.把当前的节点设置为空
        if (taskEntity != null) {
            //如果是加签再加签
            String parentTaskId = taskEntity.getParentTaskId();
            if (org.apache.commons.lang.StringUtils.isBlank(parentTaskId)) {
                taskEntity.setOwner(UserUtils.getCurrentUserDTO ().getId ());
                taskEntity.setAssignee(null);
                taskEntity.setCountEnabled(true);
                if (flag) {
                    taskEntity.setScopeType(FlowableConstant.AFTER_ADDSIGN);
                } else {
                    taskEntity.setScopeType(FlowableConstant.BEFORE_ADDSIGN);
                }
                //1.2 设置任务为空执行者
                taskService.saveTask(taskEntity);
            }
            //2.添加加签数据
            this.createSignSubTasks(userIds, taskEntity);
            //3.添加审批意见
            String type = flag ? ActionType.ADD_AFTER_MULTI_INSTANCE.toString() : ActionType.ADD_BEFORE_MULTI_INSTANCE.toString();
            taskService.addComment (taskId, taskEntity.getProcessInstanceId (), type, comment);
        } else {
            throw  new Exception ("不存在任务实例，请确认!");
        }
    }

    /**
     * 创建加签子任务
     *
     * @param userIds     加签参数
     * @param taskEntity 父任务
     */
    private void createSignSubTasks(List<String> userIds, TaskEntity taskEntity) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            String parentTaskId = taskEntity.getParentTaskId();
            if (org.apache.commons.lang.StringUtils.isBlank(parentTaskId)) {
                parentTaskId = taskEntity.getId();
            }
            String finalParentTaskId = parentTaskId;
            //1.创建被加签人的任务列表
            userIds.forEach(userCode -> {
                if (StrUtil.isNotBlank(userCode)) {
                    this.createSubTask(taskEntity, finalParentTaskId, userCode);
                }
            });
            String taskId = taskEntity.getId();
            if (org.apache.commons.lang.StringUtils.isBlank(taskEntity.getParentTaskId())) {
                //2.创建加签人的任务并执行完毕
                Task task = this.createSubTask(taskEntity, finalParentTaskId, UserUtils.getCurrentUserDTO ().getId ());
                taskId = task.getId();
            }
            Task taskInfo = taskService.createTaskQuery().taskId(taskId).singleResult();
            if (null != taskInfo) {
                taskService.complete(taskId);
            }
            //如果是候选人，需要删除运行时候选表种的数据。
            long candidateCount = taskService.createTaskQuery().taskId(parentTaskId).taskCandidateUser(UserUtils.getCurrentUserDTO ().getId ()).count();
            if (candidateCount > 0) {
                taskService.deleteCandidateUser(parentTaskId, UserUtils.getCurrentUserDTO ().getId ());
            }
        }
    }

    /**
     * 创建子任务
     *
     * @param ptask    创建子任务
     * @param assignee 子任务的执行人
     * @return
     */
    protected TaskEntity createSubTask(TaskEntity ptask, String ptaskId, String assignee) {
        TaskEntity task = null;
        if (ptask != null) {
            //1.生成子任务
            task = (TaskEntity) taskService.newTask(UUID.randomUUID ().toString ());
            task.setCategory(ptask.getCategory());
            task.setDescription(ptask.getDescription());
            task.setTenantId(ptask.getTenantId());
            task.setAssignee(assignee);
            task.setName(ptask.getName());
            task.setParentTaskId(ptaskId);
            task.setProcessDefinitionId(ptask.getProcessDefinitionId());
            task.setProcessInstanceId(ptask.getProcessInstanceId());
            task.setTaskDefinitionKey(ptask.getTaskDefinitionKey());
            task.setTaskDefinitionId(ptask.getTaskDefinitionId());
            task.setPriority(ptask.getPriority());
            task.setCreateTime(new Date());
            taskService.saveTask(task);
        }
        return task;
    }

}
