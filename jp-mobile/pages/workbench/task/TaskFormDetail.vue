<template>
	<view>
		<cu-custom bgColor="bg-blue" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{title}}</block>
		</cu-custom>
		<!-- 菜单 -->
		<view class="bg-white nav fixed flex text-center" :style="[{top:CustomBar + 'px'}]">
			<view class="cu-item flex-sub" :class="0==tabIndex?'text-blue cur':''" @tap="tabSelect" data-id="0">
				表单信息
			</view>
			<view class="cu-item flex-sub"  v-if="procInsId" :class="2==tabIndex?'text-blue cur':''" @tap="tabSelect" data-id="2">
				流转记录
			</view>
			<view class="cu-item flex-sub" :class="1==tabIndex?'text-blue cur':''" @tap="tabSelect" data-id="1">
				流程图
			</view>
		
		</view>
		<view v-show="0 === tabIndex">
			<scroll-view scroll-y class="page">
			<view class=" bg-white margin-top">
				  <component :formReadOnly="formReadOnly" :class="formReadOnly?'readonly':''"  ref="form" :businessId="businessId" :is="form"></component>
				  <PreviewForm :formData="formData"  v-if="formType !== '2'"  :processDefinitionId="procDefId" :edit="false" ref="form"></PreviewForm>
			</view>

			<view class="cu-tabbar-height"></view>
			</scroll-view>
		</view>
		<view v-show="1 === tabIndex">
			<view class="padding bg-white margin-top">
				<flow-chart ref="chart1" v-if="procInsId" :processInstanceId="procInsId" />
				<flow-chart ref="chart2" v-if="!procInsId" :processDefId="procDefId" />
			</view>
		</view>
		<view v-show="2 === tabIndex">
			<view class="padding bg-white margin-top">
	
				<view class="cu-timeline" :key="index" v-for="(act, index) in historicTaskList">
					<view class="cu-time">{{act.histIns.startTime |formatDate('MM-DD')}}</view>
					<view class="cu-item text-blue">
						<view class="content">
							<view class="cu-capsule radius">
								<view class="cu-tag bg-cyan">{{act.histIns.activityName}}</view>
								<!-- <view class="cu-tag line-cyan">{{act.histIns.activityName}}</view> -->
							</view>
							
							<view class="margin-top">
								审批人 ： {{act.assigneeName}}
							</view>
							<view class="margin-top">
								办理状态 ：<view class="cu-tag bg-blue">{{act.comment.status}}</view>  
							</view>
							<view class="margin-top">
								审批意见 ： {{act.comment.message}}
							</view>
							<view class="margin-top">
								开始时间 : {{act.histIns.startTime |formatDate}}
							</view>
							<view class="margin-top">
								结束时间 : {{act.histIns.endTime |formatDate}}
							</view>
							<view class="margin-top">
								用时 : {{act.durationTime || '0秒'}}
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import userSelect from '@/components/user-select/user-select.vue'
	import PreviewForm from '../form/GenerateFlowableForm'
	import TestActivitiLeaveForm from '@/pages/test/activiti/TestActivitiLeaveForm.vue'
	export default {
		onLoad: function (option) {
		    this.flow = JSON.parse(decodeURIComponent(option.flow));
			this.procDefId = this.flow.procDefId
			this.procDefKey = this.flow.procDefKey
			this.formType = this.flow.formType
			this.formUrl = this.flow.formUrl
			this.taskId = this.flow.taskId
			this.taskDefKey = this.flow.taskDefKey
			this.status = this.flow.status
			this.title = this.flow.formTitle
			this.businessId = this.flow.businessId
			this.procInsId = this.flow.procInsId
			this.formReadOnly = true
		},
		async mounted () {
			  if (this.formType === '2') { //外置表单
				if (this.formUrl === '/404') {
				  this.form = null
				  uni.showToast({ title: '没有关联流程表单!', icon: "none" });
				} else {
					// uniapp 不支持动态组件，所以通过名称匹配决定调用的表单组件
					if(this.formUrl.endsWith('TestActivitiLeaveForm')){ 
						this.form = TestActivitiLeaveForm
					}else{
						uni.showToast({ title: '没有关联流程表单!', icon: "none" });
					}
				}
			  } else { // 动态表单
			  // 读取流程表单
				if (this.formUrl === '/404') {
				   uni.showToast({ title: '没有关联流程表单!', icon: "none" });
				} else {
				  let {data} = await this.$http.get('/app/flowable/form/getMobileForm?formDefinitionJsonId='+this.formUrl);
				 // 初始化动态表单
				  data.formStructure.forEach((item)=>{
					  item.writable = true //挂载 writable，readable,value 属性，是为了触发对这三个属性的监听
					  item.readable = true
					  if(this.isObjectValue(item)){
						  item.value = null
					  }else{
						  item.value =  ''
					  }
					  let input = JSON.parse(JSON.stringify(item))
					  this.formData.push(input)
				  })
			
				    // 读取任务表单配置
				let res = await this.$http.get('/app/flowable/form/getHistoryTaskFormData',{ processInstanceId: this.procInsId, procDefId: this.procDefId, taskDefKey: this.taskDefKey })
				this.setData(res.data.taskFormData, 'audit')
				  
				}
			  }
			// 读取历史任务列表
			  this.$http.get(`/app/flowable/task/historicTaskList?procInsId=${this.procInsId}`).then(({data}) => {
				this.historicTaskList = data.historicTaskList.reverse()
			  })
		},
		components:{
		  userSelect,
		  TestActivitiLeaveForm,
		  PreviewForm
		},
		data() {
			return {
				flow: null,
				tabIndex: 0,
				form: null,
				formType: '',
				formUrl: '',
				taskSelectedTab: 'frist',
				historicTaskList: [],
				procDefId: '',
				procInsId: '',
				formReadOnly: false,
				procDefKey: '',
				taskId: '',
				formData: [],
				taskDefKey: '',
				status: '',
				title: '',
				businessId: ''
			}
		},
		methods:{
			tabSelect (e) {
				  if (this.procInsId) {
					this.$refs.chart1.init()
				  } else {
					this.$refs.chart2.init()
				  }
				this.tabIndex = parseInt(e.currentTarget.dataset.id);
			},
			// 为任务表单赋值
			setData (taskFormData, status) {
				this.formData.forEach((input)=>{
					let item = taskFormData.filter((item)=>{
						if(input.model === item.id){
							return true
						}else{
							return false
						}
					})[0]
					if(item){
						if(this.isObjectValue(input)){
							  if(item.value && typeof item.value=== 'string'){
								   input.value =  JSON.parse(item.value)
							   }else {
								   input.value = item.value
							   }
						   }else{
							   input.value = item.value
						  }
						input.readable = item.readable
						input.writable = false
					}else{
						input.readable = false
					}
				})
			},
			// 判断数据类型是否是非String类型
			isObjectValue (input) {
				if(input.type === 'checkbox' ||
				   input.type === 'slider' ||
				   input.type === 'switch' ||
				   input.type === 'rate' ||
				   input.type === 'imgupload' ||
				   input.type === 'select' && input.options.multiple ||
				   input.type === 'fileupload'){
					   return true
				  }
				  return false
			}
		}
	}
</script>

<style>
	page {
		padding-top: 45px;
	}
	.cu-form-group .title {
		min-width: calc(4em + 40px);
	}

</style>
