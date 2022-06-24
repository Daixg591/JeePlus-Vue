package com.jeeplus.flowable.common.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.jeeplus.extension.domain.FlowAssignee;
import com.jeeplus.extension.domain.TaskDefExtension;
import com.jeeplus.extension.service.TaskDefExtensionService;
import com.jeeplus.extension.service.dto.FlowAssigneeDTO;
import com.jeeplus.sys.domain.User;
import com.jeeplus.sys.service.UserService;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.UserUtils;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MutiInstanceHandler {

    /**
     * 获得当前节点的处理者列表
     * @param execution 当前执行实例
     * @return 处理者列表
     */
    public List<String> getList(DelegateExecution execution) {
        String taskDefId = execution.getCurrentFlowElement ().getId ();
        Process process = SpringUtil.getBean (RepositoryService.class).getBpmnModel (execution.getProcessDefinitionId ()).getMainProcess ();
        List<TaskDefExtension> list = SpringUtil.getBean (TaskDefExtensionService.class).lambdaQuery ()
                .eq ( TaskDefExtension::getProcessDefId, process.getId () )
                .eq ( TaskDefExtension::getTaskDefId, taskDefId )
                .list ();
        HashSet<String> candidateUserIds = new HashSet<> ();
        if (list.size () > 0) {
            TaskDefExtension taskDefExtension = list.get (0);
            List<FlowAssigneeDTO> assigneeList = SpringUtil.getBean (TaskDefExtensionService.class).getById (taskDefExtension.getId ()).getFlowAssigneeList ();
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
                        HistoricTaskInstance lastHisTask = SpringUtil.getBean (HistoryService.class).createHistoricTaskInstanceQuery ().processInstanceId (execution.getProcessInstanceId ()).finished ()
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

        return new ArrayList<> (candidateUserIds);

    }

    /**
     * 获取会签是否结束
     * @param execution 当前执行实例
     * @return 是否结束
     */
    public boolean getComplete(DelegateExecution execution) {
        Integer nrOfCompletedInstances = (Integer) execution.getVariable("nrOfCompletedInstances");
        Integer nrOfInstances = (Integer) execution.getVariable("nrOfInstances");
        int agreeCount = 0, rejectCount = 0, abstainCount = 0;
        Map<String, Object> vars = execution.getVariables();
        for (String key : vars.keySet()) {
            //会签投票以SIGN_VOTE+TaskId标识
            //获得会签投票的统计结果
//            if (key.contains(FlowConst.SIGN_VOTE) && !key.equals(FlowConst.SIGN_VOTE_RESULT)) {
//                Integer value = (Integer) vars.get(key);
//                //统计同意、驳回、弃权票数
//                //省略代码若干......
//            }
        }
        //以下为一段简单的规则，可以按情况实现自己的会签规则
        if (!nrOfCompletedInstances.equals(nrOfInstances)) {
            //必须等所有的办理人都投票
            return false;
        } else {
            //会签全部完成时,使用默认规则结束
            if (rejectCount > 0) {
                //有反对票，则最终的会签结果为不通过
                //移除SIGN_VOTE+TaskId为标识的参数
//                removeSignVars(execution);
                //增加会签结果参数，以便之后流转使用
//                execution.setVariable(FlowConst.SIGN_VOTE_RESULT, false);
                //会签结束
                return true;
            } else {
                //没有反对票时，则最终的会签结果为通过
//                removeSignVars(execution);
//                execution.setVariable(FlowConst.SIGN_VOTE_RESULT, true);
                return true;
            }
        }
    }





}
