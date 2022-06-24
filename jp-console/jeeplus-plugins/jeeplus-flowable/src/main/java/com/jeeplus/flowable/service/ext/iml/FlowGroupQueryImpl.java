package com.jeeplus.flowable.service.ext.iml;

import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import com.jeeplus.flowable.utils.FlowableUtils;
import com.jeeplus.sys.domain.User;
import com.jeeplus.sys.service.UserService;
import com.jeeplus.sys.service.dto.RoleDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.idm.api.Group;
import org.flowable.idm.engine.impl.GroupQueryImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 扩展Flowable组Service
 * @author liugoafeng
 * @version 2021-09-02
 */
public class FlowGroupQueryImpl extends GroupQueryImpl {

	private static final long serialVersionUID = 1L;

	private UserService userService;

	public UserService getSystemService() {
		if (userService == null){
			userService = SpringUtil.getBean(UserService.class);
		}
		return userService;
	}

	@Override
    public long executeCount(CommandContext commandContext) {
        return executeQuery().size();
    }

    @Override
    public List<Group> executeList(CommandContext commandContext) {
        return executeQuery();
    }

    protected List<Group> executeQuery() {
        if (getUserId() != null) {
            return findGroupsByUser(getUserId());
        } else {
            return findAllGroups();
        }
    }


    protected List<Group> findGroupsByUser(String userId) {
    	List<Group> list = Lists.newArrayList();
		UserDTO user = getSystemService().getUserByLoginName(userId);
		if (user != null && user.getRoleDTOList () != null){
			for (RoleDTO role : user.getRoleDTOList ()){
				list.add(FlowableUtils.toFlowableGroup(role));
			}
		}

    	return list;
    }

    protected List<Group> findAllGroups() {
    	return new ArrayList<>();
    }
}
