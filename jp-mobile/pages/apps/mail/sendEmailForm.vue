<template>
	<view>
		<cu-custom bgColor="bg-blue"  :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<form @submit="formSubmit" class="cu-list menu">
			<view class="cu-form-group">
				<view class="title">
					<text class="red-color">* </text> 发送到
				</view>
				<user-select v-model="inputForm.receiverIds"></user-select>
			</view>
			<view class="cu-form-group">
				<view class="title">
					<text class="red-color">* </text> 标题
				</view>
				<input placeholder="请输入标题" maxlength="200" v-model="inputForm.mail.title" name="title"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">
					<text class="red-color">* </text> 内容
				</view>
				<textarea maxlength="2000"  v-model="inputForm.mail.content" name="content" placeholder="请填写内容"></textarea>
			</view>
			<view class="bottom-wrap  flex">
				<view class="flex-sub">
					<button class="buttonBox  bg-gray" @click="saveDraft"> 存为草稿</button>
				</view>
				<view class="flex-sub">
					<button class="buttonBox block bg-blue " @click="sendEmail">发送邮件</button>
				</view>
				
			</view>
		</form>
	</view>
</template>

<script>
	import userSelect from '@/components/user-select/user-select.vue'
	var  graceChecker = require("@/common/graceChecker.js");
	export default {
		onShow() {
		  this.$auth.checkLogin()
		  this.$http.get('/app/sys/user/treeData').then(({data})=>{
			  this.data = data.treeData
		  }).catch((e)=>{
			  throw e
		  })
		},
		async onLoad(mail) {
			if(mail&&mail.id){
				this.titile = "编辑邮件";
				let {data} = await this.$http.get(`/app/mailCompose/queryById?id=`+mail.id);
				this.inputForm = this.recover(this.inputForm, data.mailCompose);
			}
		},
		components:{
			userSelect
		},
		data () {
			return {
				data: [],
				defaultProps: {
					children: 'children',
					label: 'label'
				},
				title: '发送邮件',
				modalName: null,
				inputForm: {
				  id: '',
				  status: '',
				  receiverIds: '',
				  mail: {
					title: '',
					overview: '',
					content: ''
				  }
				}
			}
		},
		methods: {
			PickerChange(e) {
				this.index = e.detail.value
			},
			radioChange (evt) {
				this.inputForm.status = evt.detail.value
			},
			saveDraft () {
				this.inputForm.status = '0'
				this.formSubmit()
			},
			sendEmail () {
				this.inputForm.status = '1'
				this.formSubmit()
			},
			selectUsers () {
				let ids = this.$refs.userTree.getCheckedNodes().filter((item)=>{
					return item.type === 'user'
				}).map((item)=>{
					return item.id
				}).join(",");
				let names = this.$refs.userTree.getCheckedNodes().filter((item)=>{
					return item.type === 'user'
				}).map((item)=>{
					return item.label
				}).join(",");
				this.inputForm.receiverIds = ids
				this.receiverNames = names
				this.hideModal()
			},
			showModal(e) {
				this.modalName = e.currentTarget.dataset.target
			},
			hideModal(e) {
				this.modalName = null
			},
			formSubmit: function(e) {
				//定义表单规则
				var rule = [
					{name:"receiverIds", checkType : "notnull", checkRule:"",  errorMsg:"收件人不能为空"},
					{name:"mail.title", checkType : "notnull", checkRule:"",  errorMsg:"标题不能为空"},
					{name:"mail.content", checkType : "notnull", checkRule:"",  errorMsg:"内容不能为空"}
				];
				//进行表单检查
				var formData = this.inputForm;
				var checkRes = graceChecker.check(formData, rule);
				if(checkRes){
					uni.showLoading()
					this.$http.post(`/app/mailCompose/save`,this.inputForm).then(({data}) => {
						uni.showToast({title:data.msg, icon:"success"});
						uni.navigateTo({
						  url: '/pages/apps/mail/inbox'
						})
					}).catch((e)=>{
						console.log(e)
					})
					
				}else{
					uni.showToast({ title: graceChecker.error, icon: "none" });
				}
			}
		}
	}
</script>

<style>
	.cu-form-group .title {
		min-width: calc(4em + 30px);
	}
	.cu-form-group.arrow:before {
	    position: absolute;
	    /* top: 0; */
	    right: 17px;
	    /* bottom: 0; */
	    display: block;
	    margin: auto;
	    width: 17px;
	    height: 17px;
	    color: #8799a3;
	    content: "\e6a3";
	    text-align: center;
	    font-size: 20px;
	    font-family: cuIcon;
	    line-height: 17px;
	}

	.ellipsis-description {
		  display: -webkit-box;/*作为弹性伸缩盒子模型显示*/
		  -webkit-line-clamp: 1; /*显示的行数；如果要设置2行加...则设置为2*/
		  overflow: hidden; /*超出的文本隐藏*/
		  text-overflow: ellipsis; /* 溢出用省略号*/
		   -webkit-box-orient: vertical;/*伸缩盒子的子元素排列：从上到下*/
	}
  .cu-form-group .title {
  	min-width: calc(4em + 40px);
  }
</style>
