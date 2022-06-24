<template>
	<view>
		<form @submit="formSubmit" ref="inputForm" class="cu-list menu">
			<view class="cu-form-group margin-top">
				<view class="title">
					<text class="red-color">* </text> 类型
				</view>
				<jp-picker v-model="inputForm.leaveType"  :disabled="formReadOnly"  :range="$dictUtils.getDictList('oa_leave_type')">
					<view class="picker">
						{{$dictUtils.getDictLabel('oa_leave_type', inputForm.leaveType ,'请选择')}}
					</view>
				</jp-picker>
			</view>
			
			<view class="cu-form-group">
				<view class="title">请假开始时间</view>
				<picker mode="date" :value="inputForm.startTime"  :disabled="formReadOnly" @change="StartTimeChange" >
					<view class="picker">
						{{inputForm.startTime}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group">
				<view class="title">请假结束时间</view>
				<picker mode="date" :value="inputForm.endTime" :disabled="formReadOnly" @change="EndTimeChange">
					<view class="picker">
						{{inputForm.endTime}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group">
				<view class="title">
					<text class="red-color">* </text> 请假事由
				</view>
				<textarea maxlength="2000" v-model="inputForm.reason" :disabled="formReadOnly" name="reason" placeholder="请填写内容"></textarea>
			</view>
			
		</form>
	</view>
</template>

<script>
	var  graceChecker = require("@/common/graceChecker.js");
	import jpPicker from '@/components/jp-picker/jp-picker.vue'
	import moment from 'moment'
	export default {
		data () {
			return {
				loading: false,
				inputForm: {
				  id: '',
				  leaveType: '',
				  startTime: moment(new Date()).format('YYYY-MM-DD'),
				  endTime: moment(new Date()).format('YYYY-MM-DD'),
				  reason: ''
				}
			}
		},
		props: {
		  businessId: {
		    type: String,
		    default: ''
		  },
		  formReadOnly: {
		    type: Boolean,
		    default: false
		  }
		},
		components: {
			jpPicker
		},
		watch: {
		  'businessId': {
		    handler (newVal) {
		      if (this.businessId) {
		        this.init(this.businessId)
		      } else {
		        this.$nextTick(() => {
		          // this.$refs.inputForm.reset()
		        })
		      }
		    },
		    immediate: true,
		    deep: false
		  }
		},
		methods: {
			StartTimeChange (e) {
				this.inputForm.startTime = e.detail.value
			},
			EndTimeChange (e) {
				this.inputForm.endTime = e.detail.value
			},
			init (id) {
			  if (id) {
			      this.$http.get(`/test/activiti/testActivitiLeave/queryById?id=`+id).then(({data}) => {
			        this.inputForm = this.recover(this.inputForm, data.testActivitiLeave)
			      })
			  }
			},
			saveForm (callback) {
				//定义表单规则
				var rule = [
					{name:"leaveType", checkType : "notnull", checkRule:"",  errorMsg:"类型不能为空"},
					{name:"startTime", checkType : "notnull", checkRule:"",  errorMsg:"请假开始时间不能为空"},
					{name:"endTime", checkType : "notnull", checkRule:"",  errorMsg:"请假结束时间不能为空"},
					{name:"reason", checkType : "notnull", checkRule:"",  errorMsg:"请假事由不能为"}
				];
				//进行表单检查
				var formData = this.inputForm;
				var checkRes = graceChecker.check(formData, rule);
				if(checkRes){
					uni.showLoading()
					this.$http.post(`/test/activiti/testActivitiLeave/save`,this.inputForm).then(({data}) => {
						if (data && data.success) {
						  callback(data.businessTable, data.businessId)
						}
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
		min-width: calc(4em + 40px);
	}


</style>
