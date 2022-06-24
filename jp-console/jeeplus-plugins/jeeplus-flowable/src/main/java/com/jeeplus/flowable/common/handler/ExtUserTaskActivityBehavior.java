package com.jeeplus.flowable.common.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import com.jeeplus.extension.domain.TaskDefExtension;
import com.jeeplus.extension.service.TaskDefExtensionService;
import com.jeeplus.extension.service.dto.FlowAssigneeDTO;
import com.jeeplus.flowable.utils.FlowableUtils;
import com.jeeplus.sys.domain.User;
import com.jeeplus.sys.service.UserService;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.impl.el.ExpressionManager;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.service.TaskService;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

@Slf4j
public class ExtUserTaskActivityBehavior extends UserTaskActivityBehavior {

    private static final long serialVersionUID = 7711531472879418236L;

    public ExtUserTaskActivityBehavior(UserTask userTask) {
        super(userTask);
    }


    /**
     * 分配办理人员
     */
    @Override
    protected void handleAssignments(TaskService taskService, String assignee, String owner, List<String> candidateUsers, List<String> candidateGroups, TaskEntity task, ExpressionManager expressionManager, DelegateExecution execution) {

        Process process = SpringUtil.getBean (RepositoryService.class).getBpmnModel (task.getProcessDefinitionId ()).getMainProcess ();
        FlowElement flowElement = process.getFlowElement(task.getTaskDefinitionKey());
        Boolean isMultiInstance = FlowableUtils.isFlowElementMultiInstance (flowElement);
        if(isMultiInstance){
            super.handleAssignments (taskService, assignee, owner, candidateUsers, candidateGroups, task, expressionManager, execution);
            return;
        }
        List<TaskDefExtension> list = SpringUtil.getBean (TaskDefExtensionService.class)
                .lambdaQuery ()
                .eq ( TaskDefExtension::getProcessDefId, process.getId () )
                .eq ( TaskDefExtension::getTaskDefId, task.getTaskDefinitionKey () ).list ();
        HashSet<String> candidateUserIds = new HashSet<> ();
        if (list.size () > 0) {
            TaskDefExtension taskDefExtension = list.get (0);
            List<FlowAssigneeDTO> assigneeList =  SpringUtil.getBean(TaskDefExtensionService.class).getById (taskDefExtension.getId()).getFlowAssigneeList ();
            for (FlowAssigneeDTO flowAssignee : assigneeList) {
                switch (flowAssignee.getType ()) {
                    case "user":
                        candidateUserIds.addAll (Arrays.asList (flowAssignee.getValue ().split (",")));
                        break;
                    case "post":
                        if( StrUtil.isNotBlank (flowAssignee.getValue ())){
                            String postId = flowAssignee.getValue ();
                            List<UserDTO> userList = SpringUtil.getBean ( UserService.class).findListByPostId (postId  );
                            candidateUserIds.addAll ( CollectionUtils.extractToList (userList, "id"));
                        }

                        break;
                    case "company":
                        if(StrUtil.isNotBlank (flowAssignee.getValue ())){
                            String companyId = flowAssignee.getValue ();
                            List<User> userList = SpringUtil.getBean (UserService.class).lambdaQuery ().eq ( User::getCompanyId, companyId ).list ();
                            candidateUserIds.addAll (CollectionUtils.extractToList (userList, "id"));
                        }

                        break;
                    case "depart":
                        if(StrUtil.isNotBlank (flowAssignee.getValue ())){
                            String officeId = flowAssignee.getValue ();
                            List<User> userList = SpringUtil.getBean (UserService.class).lambdaQuery ().eq ( User::getOfficeId, officeId ).list ();
                            candidateUserIds.addAll (CollectionUtils.extractToList (userList, "id"));
                        }

                        break;
                    case "role":
                        if(StrUtil.isNotBlank (flowAssignee.getValue ())){
                            String[] roleIds = flowAssignee.getValue ().split (",");
                            for(String roleId: roleIds){
                                List<UserDTO> userList = SpringUtil.getBean (UserService.class).findListByRoleId ( roleId );
                                candidateUserIds.addAll (CollectionUtils.extractToList (userList, "id"));
                            }
                        }
                        break;
                    case "applyUserId":
                        candidateUserIds.add ("${applyUserId}");
                        break;
                    case "previousExecutor":
                        HistoricTaskInstance lastHisTask = SpringUtil.getBean (HistoryService.class).createHistoricTaskInstanceQuery ().processInstanceId (task.getProcessInstanceId ()).finished ()
                                .includeProcessVariables ().orderByHistoricTaskInstanceEndTime ().desc ().list ().get (0);

                        candidateUserIds.add(lastHisTask.getAssignee ());
                        break;
                    case "currentUserId":
                        candidateUserIds.add( UserUtils.getCurrentUserDTO ().getId ());
                        break;
                    case "sql":
                        Map userMap = SpringUtil.getBean (JdbcTemplate.class).queryForMap (flowAssignee.getValue ());
                        candidateUserIds.add(userMap.get ("id").toString ());
                        break;
                    case "custom":
                        //根据你的自定义标记，请自行实现
                        break;
                }
            }
        }
        List<String> candidateIds = new ArrayList<> (candidateUserIds);
        //此处可以根据业务逻辑自定义
        if (candidateIds.size () == 0) {
            super.handleAssignments (taskService, null, owner, Lists.newArrayList (), Lists.newArrayList (), task, expressionManager, execution);
        } else if (candidateIds.size () == 1) {
            super.handleAssignments (taskService, candidateIds.get (0), owner, Lists.newArrayList (), Lists.newArrayList (), task, expressionManager, execution);
        } else if (candidateIds.size () > 1) {
            super.handleAssignments (taskService, null, owner, candidateIds, null, task, expressionManager, execution);
        }


    }

}

