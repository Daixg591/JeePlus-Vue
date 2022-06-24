package com.jeeplus.flowable.service.ext;

import com.jeeplus.flowable.service.ext.iml.FlowGroupQueryImpl;
import com.jeeplus.flowable.service.ext.iml.FlowUserQueryImpl;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.idm.api.*;
import org.flowable.idm.engine.impl.IdmIdentityServiceImpl;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * 扩展flowable用户权限Service
 * @author liugaofeng
 * @version 2021-09-02
 */
public class FlowIdentityServiceImpl extends IdmIdentityServiceImpl {


	@Override
	public UserQuery createUserQuery() {
		// TODO Auto-generated method stub
		return new FlowUserQueryImpl ();
	}

	@Override
	public GroupQuery createGroupQuery() {
		// TODO Auto-generated method stub
		return new FlowGroupQueryImpl ();
	}

	@Override
	public boolean checkPassword(String userId, String password) {
		return executeCheckPassword(userId, password);
	}

	@Override
	public List<Group> getGroupsWithPrivilege(String name) {
		List<Group> groups = new ArrayList<>();
        List<PrivilegeMapping> privilegeMappings = getPrivilegeMappingsByPrivilegeId(name);
        for (PrivilegeMapping privilegeMapping : privilegeMappings) {
            if (privilegeMapping.getGroupId() != null) {
                Group group = new GroupEntityImpl();
                group.setId(privilegeMapping.getGroupId());
                group.setName(privilegeMapping.getGroupId());
                groups.add(group);
            }
        }

        return groups;
	}

	@Override
    public List<User> getUsersWithPrivilege(String name) {
        List<User> users = new ArrayList<>();
        List<PrivilegeMapping> privilegeMappings = getPrivilegeMappingsByPrivilegeId(name);
        for (PrivilegeMapping privilegeMapping : privilegeMappings) {
            if (privilegeMapping.getUserId() != null) {
                User user = new UserEntityImpl();
                user.setId(privilegeMapping.getUserId());
                user.setLastName(privilegeMapping.getUserId());
                users.add(user);
            }
        }

        return users;
    }

	@Override
	public User newUser(String userId) {
		throw new FlowableException("LDAP identity service doesn't support creating a new user");
	}


	@Override
    public void saveUser(User user) {
        throw new FlowableException("LDAP identity service doesn't support saving an user");
    }

    @Override
    public NativeUserQuery createNativeUserQuery() {
        throw new FlowableException("LDAP identity service doesn't support native querying");
    }

    @Override
    public void deleteUser(String userId) {
        throw new FlowableException("LDAP identity service doesn't support deleting an user");
    }

    @Override
    public Group newGroup(String groupId) {
        throw new FlowableException("LDAP identity service doesn't support creating a new group");
    }

    @Override
    public NativeGroupQuery createNativeGroupQuery() {
        throw new FlowableException("LDAP identity service doesn't support native querying");
    }

    @Override
    public void saveGroup(Group group) {
        throw new FlowableException("LDAP identity service doesn't support saving a group");
    }

    @Override
    public void deleteGroup(String groupId) {
        throw new FlowableException("LDAP identity service doesn't support deleting a group");
    }


    protected boolean executeCheckPassword(final String userId, final String password) {

    	return true;
    }

}
