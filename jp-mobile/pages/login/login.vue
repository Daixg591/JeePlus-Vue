<template>
	<view class="zai-box">
		<image src="../../static/img/login.png" mode='aspectFit' class="zai-logo"></image>
		<view class="zai-title">Jeeplus</view>
			<form>
				<view class="cu-form-group">
					<view class="title">用户名</view>
					<input placeholder="请输入账号" v-model="account" name="input"></input>
				</view>
				<view class="cu-form-group">
					<view class="title">密码</view>
					<input placeholder="请输入密码" type="password" displayable  v-model="password" name="input"></input>
				</view>
			</form>
			<view class="zai-label">忘记密码？</view>
			<button class="bg-gradual-blue round" @click="bindLogin">立即登录</button>
	</view>
</template>

<script>
	import * as $auth from "@/common/auth.js"
	import {mapActions} from 'vuex'

	export default {
		data() {
			return {
				account: 'admin',
				password: 'admin'
			}
		},
		methods: {
			...mapActions(['refreshUserInfo']),
		  // 登录
		  bindLogin() {
				/**
				 * 客户端对账号信息进行一些必要的校验。
				 * 实际开发中，根据业务需要进行处理，这里仅做示例。
				 */
				if (this.account.length < 1) {
					uni.showToast({
						icon: 'none',
						title: '账号不能为空'
					});
					return;
				}
				if (this.password.length < 1) {
					uni.showToast({
						icon: 'none',
						title: '密码不能为空'
					});
					return;
				}
				/**
				 * 下面简单模拟下服务端的处理
				 * 检测用户账号密码是否在已注册的用户列表中
				 * 实际开发中，使用 uni.request 将账号信息发送至服务端，客户端在回调函数中获取结果信息。
				 */
				const data = {
					userName: this.account,
					password: this.password
				};
				this.$http.post('/app/sys/login',data).then(({data})=>{
					if(data.success){
						this.$store.commit('SET_TOKEN',data.token);
						this.refreshUserInfo();
						uni.reLaunch({
							url: '../index/index',
						});
					
					}else{
						uni.showToast({
							icon:"none",
							title:"登录失败："+data.msg
						})
					}
				})
			}
		}
	}
</script>

<style>

	.zai-box{
		padding: 0 50upx;
		position: relative;
		
	}
	.zai-logo{
		width: 100%;
		width: 100%;
		margin-top: 100upx;
		margin-bottom: 100upx;
		padding: 20px;
		height: 310upx;
	}
	.zai-title{
		position: absolute;
		top: 0;
		margin-top: 100upx;
		margin-bottom: 100upx;
		line-height: 340upx;
		font-size: 68upx;
		color: #fff;
		text-align: center;
		width: 100%;
		margin-left: -50upx;
	}
	.zai-form{
		margin-top: 300upx;
	}
	.zai-input{
		background: #e2f5fc;
		margin-top: 30upx;
		border-radius: 100upx;
		padding: 20upx 40upx;
		font-size: 36upx;
	}
	.input-placeholder, .zai-input{
		color: #94afce;
	}
	.zai-label{
		padding: 60upx 0;
		text-align: center;
		font-size: 30upx;
		color: #a7b6d0;
	}
	.zai-btn{
		background: #ff65a3;
		color: #fff;
		border: 0;
		border-radius: 100upx;
		font-size: 36upx;
	}
	.zai-btn:after{
		border: 0;
	}
	/*按钮点击效果*/
	.zai-btn.button-hover{
		transform: translate(1upx, 1upx);
	}
	.cu-form-group .title {
		min-width: calc(4em + 15px);
	}
</style>
