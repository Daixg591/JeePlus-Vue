package com.jeeplus.flowable.service.ext.iml;

import cn.hutool.extra.spring.SpringUtil;
import com.jeeplus.flowable.utils.FlowableUtils;
import com.jeeplus.sys.service.UserService;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.UserUtils;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.UserQueryImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 扩展Flowable用户Service
 * @author liugaofeng
 * @version 2021-09-02
 */
public class FlowUserQueryImpl extends UserQueryImpl {

	private static final long serialVersionUID = 1L;

	private UserService systemService;
	public UserService getSystemService() {
		if (systemService == null){
			systemService = SpringUtil.getBean(UserService.class);
		}
		return systemService;
	}

	@Override
    public long executeCount(CommandContext commandContext) {
        return executeQuery().size();
    }

    @Override
    public List<User> executeList(CommandContext commandContext) {
        return executeQuery();
    }

    protected List<User> executeQuery() {
        if (getId() != null) {
            List<User> result = new ArrayList<>();
            result.add(findById(getId()));
            return result;

        } else if (getIdIgnoreCase() != null) {
            List<User> result = new ArrayList<>();
            result.add(findById(getIdIgnoreCase()));
            return result;

        }
        else {
            return executeAllUserQuery();
        }
    }


    protected List<User> executeNameQuery(String name) {
        String fullName = name.replaceAll("%", "");
        return executeUsersQuery(fullName);
    }

    protected List<User> executeAllUserQuery() {
        return executeUsersQuery("");
    }

    protected UserEntity findById(String userId) {

    	UserDTO user = UserUtils.getByLoginName (userId);
    	return FlowableUtils.toFlowableUser(user);
    }

    protected List<User> executeUsersQuery(String fullName) {


    	return new ArrayList<>();
    }
}
