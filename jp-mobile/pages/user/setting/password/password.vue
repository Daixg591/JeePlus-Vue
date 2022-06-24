<template>
	<view>
		<cu-custom bgColor="bg-gradual-blue" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">修改密码</block>
		</cu-custom>
		<form @submit="formSubmit">
			<view class="cu-form-group margin-top">
				<view class="title"> <text class="red-color">*</text> 旧密码</view>
				<input placeholder="请输入原密码" type="password" v-model="inputForm.oldPassword" name="oldPassword"></input>
			</view>
			<view class="cu-form-group">
				<view class="title"><text class="red-color">*</text>新密码</view>
				<input placeholder="请输入新密码" type="password" v-model="inputForm.newPassword" name="newPassword"></input>
			</view>
			<view class="cu-form-group">
				<view class="title"><text class="red-color">*</text>确认新密码</view>
				<input placeholder="请确认新密码" type="password" v-model="inputForm.confirmNewPassword" name="confirmNewPassword"></input>
			</view>
			<view class="padding-xl">
				<button form-type="submit" class="cu-btn block bg-blue margin-tb-sm lg" >提交</button>
			</view>
		</form>
	</view>
</template>

<script>
  var  graceChecker = require("@/common/graceChecker.js");
  export default {
	onShow() {
	  this.$auth.checkLogin()
	},
	data () {
		return {
			loading: false,
			inputForm: {
				oldPassword: '',
				newPassword: '',
				confirmNewPassword: ''
			}
		}
	},

    methods:{
		formSubmit: function(e) {
		    //定义表单规则
		    var rule = [
		        {name:"oldPassword", checkType : "notnull", checkRule:"",  errorMsg:"密码不能为空"},
		        {name:"newPassword", checkType : "notnull", checkRule:"",  errorMsg:"新密码不能为空"},
		        {name:"confirmNewPassword", checkType : "notnull", checkRule:"",  errorMsg:"确认新密码不能为空"},
				{name:"confirmNewPassword", checkType : "same", checkRule: this.inputForm.newPassword,  errorMsg:"两次输入的新密码不一致，请重新输入!"}
		    ];
		    //进行表单检查
		    var formData = e.detail.value;
		    var checkRes = graceChecker.check(formData, rule);
		    if(checkRes){
				uni.showLoading()
				this.$http.post(`/app/sys/user/savePwd`,this.inputForm).then(({data}) => {
					uni.showToast({title:data.msg, icon:"success"});
					uni.navigateTo({
					  url: '/pages/user/person/person'
					})
				}).catch((e)=>{
					
				})
		        
		    }else{
		        uni.showToast({ title: graceChecker.error, icon: "none" });
		    }
		}
    }
  }
</script>

<style>
  .btn-logout {
    margin-top: 100upx;
    width: 80%;
    border-radius: 50upx;
    font-size: 16px;
    color: #fff;
    background: linear-gradient(to right, #365fff, #36bbff);
  }

  .btn-logout-hover {
    background: linear-gradient(to right, #365fdd, #36bbfa);
  }
  .cu-form-group .title {
  	min-width: calc(4em + 40px);
  }
</style>
