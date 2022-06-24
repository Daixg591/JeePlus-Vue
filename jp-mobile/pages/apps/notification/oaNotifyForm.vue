<template>
	<view>
		<cu-custom bgColor="bg-blue" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{titile}}</block>
		</cu-custom>
		<form @submit="formSubmit" class="cu-list menu">
			<view class="cu-form-group margin-top">
				<view class="title">
					<text class="red-color ">* </text> 类型  
				</view>
				<jp-picker v-model="inputForm.type" rangeKey="label" rangeValue="value" :range="$dictUtils.getDictList('oa_notify_type')">
					<view class="picker">
						{{$dictUtils.getDictLabel('oa_notify_type', inputForm.type ,'请选择')}}
					</view>
				</jp-picker>
			</view>
			<view class="cu-form-group">
				<view class="title">
					<text class="red-color ">* </text> 标题
				</view>
				<input placeholder="请输入标题" maxlength="200" v-model="inputForm.title" name="title"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">
					<text class="red-color ">* </text> 内容
				</view>
				<textarea maxlength="2000" v-model="inputForm.content" name="content" placeholder="请填写内容"></textarea>
			</view>
			<view class="cu-form-group">
				<view class="title">
					<text class="red-color ">* </text> 状态
				</view>
				<jp-radio-group v-model="inputForm.status">
					<radio class='blue radio status' v-for="item in $dictUtils.getDictList('oa_notify_status')" :value="item.value" :key="item.id" :class="inputForm.status==item.value?'checked':''" :checked="inputForm.status==item.value?true:false" >{{item.label}}</radio>
				</jp-radio-group>
			</view>
			<view class="cu-form-group">
				<view class="title">
					<text class="red-color ">* </text> 选择人员
				</view>
				<user-select v-model="inputForm.oaNotifyRecordIds"></user-select>
			</view>
			
			<view class="padding-xl">
				<button form-type="submit" class="cu-btn block bg-blue margin-tb-sm lg" >提交</button>
			</view>
		</form>
	</view>
</template>

<script>
	import userSelect from '@/components/user-select/user-select.vue'
	import elRadioGroup from '@/components/jp-radio-group/jp-radio-group.vue'
	import elPicker from '@/components/jp-picker/jp-picker.vue'
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
		async onLoad(notify) {
			if(notify&&notify.id){
				this.titile = "编辑通知";
				let {data} = await this.$http.get(`/app/notify/oaNotify/queryById?isSelf=false&id=`+notify.id);
				this.inputForm = this.recover(this.inputForm, data.oaNotify)
			}
		},
		components:{
			userSelect,
			elRadioGroup,
			jpPicker
		},
		data () {
			return {
				loading: false,
				expandOnCheckNode: false, // 是否展开选中的节点
				data: [],
				defaultProps: {
					children: 'children',
					label: 'label'
				},
				index: -1,
				titile: '新建通知',
				modalName: '',
				oaNotifyRecordNames: '',
				imgList: [],
				inputForm: {
				  id: '',
				  type: '',
				  title: '',
				  content: '',
				  files: '',
				  status: '',
				  oaNotifyRecordIds: ''
				}
			}
		},
		methods: {
			formSubmit: function(e) {
				//定义表单规则
				var rule = [
					{name:"type", checkType : "notnull", checkRule:"",  errorMsg:"类型不能为空"},
					{name:"title", checkType : "notnull", checkRule:"",  errorMsg:"标题不能为空"},
					{name:"content", checkType : "notnull", checkRule:"",  errorMsg:"内容不能为空"},
					{name:"status", checkType : "notnull", checkRule:"",  errorMsg:"状态不能为空"},
					{name:"oaNotifyRecordIds", checkType : "notnull", checkRule:"",  errorMsg:"候选人不能为空"}
				];
				//进行表单检查
				var formData = this.inputForm;
				var checkRes = graceChecker.check(formData, rule);
				if(checkRes){
					uni.showLoading()
					this.$http.post(`/app/notify/oaNotify/save`,this.inputForm).then(({data}) => {
						uni.showToast({title:data.msg, icon:"success"});
						uni.navigateTo({
						  url: '/pages/apps/notification/notification?tabIndex=1'
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
	.cu-form-group .title {
		min-width: calc(4em + 30px);
	}


</style>
