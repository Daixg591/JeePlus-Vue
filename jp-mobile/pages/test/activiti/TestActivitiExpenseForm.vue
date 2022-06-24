<template>
	<view>
		<form @submit="formSubmit" ref="inputForm" class="cu-list menu">
			<view class="cu-form-group margin-top">
				<text class="red-color">* </text> 员工
				<user-select v-model="inputForm.user.id"></user-select>
			</view>
			
			<view class="cu-form-group">
				<view class="title">
					<text class="red-color">* </text> 报销金额
				</view>
				<input placeholder="请输入报销金额" maxlength="200" v-model="inputForm.cost" name="cost"></input>
			</view>

			<view class="cu-form-group">
				<view class="title">报销日期</view>
				<picker mode="date" :value="inputForm.d" :disabled="formReadOnly" @change="EndTimeChange">
					<view class="picker">
						{{inputForm.endTime}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group">
				<view class="title">
					<text class="red-color">* </text> 报销金额
				</view>
				<input placeholder="请输入报销金额" maxlength="200" v-model="inputForm.cost" name="cost"></input>
			</view>
			
		</form>
	</view>
</template>

<script>
	var  graceChecker = require("@/common/graceChecker.js");
	import moment from 'moment'
	export default {
		data () {
			return {
				loading: false,
				index: -1,
				picker: this.$dictUtils.getDictList('oa_leave_type'),
				inputForm: {
				  id: '',
				  user: {
					id: ''
				  },
				  cost: '',
				  office: {
					id: ''
				  },
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
			PickerChange(e) {
				this.index = e.detail.value;
				this.inputForm.leaveType = this.picker[this.index].value
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
