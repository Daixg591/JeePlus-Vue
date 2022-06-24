
const tokenKey = 'token'
const refreshTokenKey = 'refreshToken'
const usernameKey = 'WMS-username'
const userInfoKey = 'WMS-userinfo'
const permissionsKey = 'WMS-permission'
import store from '@/store'

// 获取token值
function getUserToken(){
	return uni.getStorageSync(tokenKey);
}

function setUserToken(token){
	uni.setStorageSync(tokenKey,token);
}

function removeUserToken(){
	uni.removeStorageSync(tokenKey);
}

// 获取refresh token值
function getRefreshToken(){
	return uni.getStorageSync(refreshTokenKey);
}

function setRefreshToken(token){
	uni.setStorageSync(refreshTokenKey,token);
}

function removeRefreshToken(){
	uni.removeStorageSync(refreshTokenKey);
}

// 获取用户名
function getUsername(){
	return uni.getStorageSync(usernameKey);
}

function setUsername(username){
	uni.setStorageSync(usernameKey,username);
}

function removeUsername(){
	uni.removeStorageSync(usernameKey);
}

// 获取用户信息
function getUserInfo(){
	return uni.getStorageSync(userInfoKey);
}

function setUserInfo(userinfo){
	uni.setStorageSync(userInfoKey,userinfo);
}

function removeUserInfo(){
	uni.removeStorageSync(userInfoKey);
}

// 获取用户权限
function getPermissions(){
	return uni.getStorageSync(permissionsKey);
}

function setPermissions(permissions){
	uni.setStorageSync(permissionsKey,permissions);
}

function removePermissions(){
	uni.removeStorageSync(permissionsKey);
}
function hasPermission (key) {
  return uni.getStorageSync(permissionsKey).indexOf(key) !== -1 || false
}

function checkLogin () {
	if (!store.state.user.token) {
		uni.showModal({
			title: '未登录',
			content: '您未登录，需要登录后才能继续',
			/**
			 * 如果需要强制登录，不显示取消按钮
			 */
			showCancel: !store.state.user.forcedLogin,
			success: (res) => {
				if (res.confirm) {
					/**
					 * 如果需要强制登录，使用reLaunch方式
					 */
					if (store.state.user.forcedLogin) {
						uni.reLaunch({
							url: '/pages/login/login'
						});
					} else {
						uni.navigateTo({
							url: '/pages/login/login'
						});
					}
				}
			}
		});
	}
}

export {
	getUserToken,
	setUserToken,
	removeUserToken,
	
	getRefreshToken,
	setRefreshToken,
	removeRefreshToken,
	
	getUsername,
	setUsername,
	removeUsername,
	
	getUserInfo,
	setUserInfo,
	removeUserInfo,
	
	getPermissions,
	setPermissions,
	removePermissions,
	hasPermission,
	checkLogin
}