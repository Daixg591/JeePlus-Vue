import $http from "../../common/request"
import * as $auth from "../../common/auth"
import * as $dictUtils from "../../common/dictUtils"

const user = {
	state:{
		forcedLogin: false,
		hasLogin: false,
		dictList: $dictUtils.getDictList(),
		token:$auth.getUserToken() || null,
		username:$auth.getUsername() || null,
		avatar: $auth.getUserInfo().photo || '/static/user/flat-avatar.png',
		userInfo: $auth.getUserInfo() || null, // 登录成功返回的个人信息
		permissions:$auth.getPermissions() || null
	},
	mutations:{
		// 登录的用户信息
		SET_USERINFO(state,userInfo){
			state.userInfo = userInfo;
			state.avatar = userInfo.photo
			$auth.setUserInfo(userInfo);
		},
		
		SET_USER_AVATAR(state,photo){
			state.avatar = photo;
		},

		SET_TOKEN: (state,token) => {
			state.token = token;
			$auth.setUserToken(token);
		},
		SET_USERNAME: (state,name) => {
			state.username = name;
			$auth.setUsername(name);
		},
		SET_DICTLIST: (state,dictList) => {
			state.dictList = dictList;
		    $dictUtils.setDictList(dictList);
		},
		SET_PERMISSIONS: (state,permissionArr) => {
			state.permissions = permissionArr
			$auth.setPermissions(permissionArr);
		},
		logout(state) {
			state.token = null;
			state.userInfo = null
			$auth.removeUserInfo()
			$auth.removeUserToken()
		}
	},
	actions: {
		refreshUserInfo({commit}) {
			$http.get('/app/sys/user/info').then(({data}) => {
			  if (data.success) {
				commit('SET_USERINFO', data.user)
				commit('SET_USERNAME', data.user.name)
			  }
			})
			
			$http.get('/sys/user/getMenus').then(({data}) => {
			  if (data.success) {
				commit('SET_PERMISSIONS', data.permissions || [])
				commit('SET_DICTLIST', data.dictList || [])
			  }
			})
		}
	}
}

export default user