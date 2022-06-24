<template>
	<view>
		<cu-custom bgColor="bg-blue">
			<block slot="content"> 工作台</block>
		</cu-custom>
		<swiper class="screen-swiper square-dot bg-blue"  :indicator-dots="true" :circular="true"
		 :autoplay="true" interval="2000" duration="500">
			<swiper-item v-for="(item,index) in swiperList"  :key="index">
				<image :src="item.url" mode="aspectFill" v-if="item.type=='image'"></image>
				<video :src="item.url" autoplay loop muted :show-play-btn="false" :controls="false" objectFit="cover" v-if="item.type=='video'"></video>
			</swiper-item>
		</swiper>
		
		<view class="cu-list grid col-4 no-border fixed">
			<view @tap="toTodoList" class="circle-button-box">
				<view class="cuIcon-time text-blue circle-button font-size-35"></view>
				<text>待办事项</text>
			</view>
			<view @tap="toHistoryList" class="circle-button-box">
				<view class="cuIcon-roundcheck  text-blue circle-button font-size-35"></view>
				<text>已办事项</text>
			</view>
			<view @tap="toApplyList" class="circle-button-box">
				<view   class="cuIcon-peoplelist text-blue circle-button font-size-35"></view>
				<text>我发起的</text>
			</view>
			<view @tap="toFlowCopyList" class="circle-button-box">
				<view class="cuIcon-copy  text-blue circle-button font-size-35"></view>
				<text>抄送给我</text>
			</view>
			
			
		</view>
		<scroll-view scroll-y class="page">
			<template v-for="key in processMap.keys()">
				<view class="cu-bar bg-white solid-bottom margin-top">
					<view class="action">
						<text class=" text-orange font-b">{{key}}</text>
					</view>
				</view>
				<view class="cu-list grid col-4 no-border">
				<view class="circle-button-box" @click="start(act)" v-for="(act, index) in processMap.get(key)" :key="index">
					<view class="cuIcon-calendar bg-blue text-white circle-button font-size-35"></view>
					<text class="ellipsis-description">{{act.name}}</text>
				</view>
				</view>
			</template>
			
			<view class="cu-tabbar-height"></view>
		</scroll-view>
			<view class="cu-tabbar-height"></view>
	</view>
</template>

<script>
	import moment from 'moment'
	import {mapState} from 'vuex'
	export default {
		data() {
			return {
				cardCur: 0,
				dataList: [],
				categoryList: [],
				processMap: new Map(),
				swiperList: [{
					id: 0,
					type: 'image',
					url: '/static/swiper/1.svg'
				}, {
					id: 1,
					type: 'image',
					url: '/static/swiper/2.svg'
				}, {
					id: 2,
					type: 'image',
					url: '/static/swiper/3.svg'
				}, {
					id: 3,
					type: 'image',
					url: '/static/swiper/4.svg'
				}, {
					id: 4,
					type: 'image',
					url: '/static/swiper/5.svg'
				}, {
					id: 5,
					type: 'image',
					url: '/static/swiper/6.svg'
				}, {
					id: 6,
					type: 'image',
					url: '/static/swiper/7.svg'
				}],
				dotStyle: false,
				towerStart: 0,
				direction: ''
			};
		},
		computed: mapState({
			 username: (state) => state.user.username
		}),
	    async mounted() {
			
			let res = await this.$http.get('/app/extension/actCategory/treeData')
			let {data} = await this.$http.get('/app/flowable/process/list')
			this.processMap = new Map()
			res.data.treeData.forEach((item)=>{
				this.processMap.set(item.name, [])
			})
			let list = data.page.list
			list.forEach((item)=>{
				if(this.processMap.has(item.category)){
					let list = this.processMap.get(item.category)
					list.push(item)
				}else{
					this.processMap.set(item.category, [item])
				}
			})
			
			for(let [key,value] of this.processMap){
			    console.log(key,value);
			}
			
			
		},
		methods: {
			toApplyList (mail) {
				uni.navigateTo({
				   url: '/pages/workbench/task/ApplyList'
				})
			},
			toTodoList (mail) {
				uni.navigateTo({
				    url: '/pages/workbench/task/TodoList'
				})
			},
			toHistoryList (mail) {
				uni.navigateTo({
				    url: '/pages/workbench/task/HistoryList'
				})
			},
			toFlowCopyList (mail) {
				uni.navigateTo({
				    url: '/pages/workbench/task/FlowCopyList'
				})
			},
			start (row) {
				  // 读取流程表单
				this.$http.get('/app/flowable/task/getTaskDef', {
				  procDefId: row.id,
				  status: 'start'
				}).then(({data}) => {
				  if (data.success) {
					  let processTitle = `${this.username} 在 ${moment(new Date()).format('YYYY-MM-DD HH:mm')} 发起了 [${row.name}]`
					  let query = {procDefId: row.id, procDefKey: row.key, status: 'start', title: `发起流程【${row.name}】`, formType: data.flow.formType, formUrl: data.flow.formUrl, formTitle: processTitle}
					  uni.navigateTo({
					     url: '/pages/workbench/task/TaskForm?flow='+JSON.stringify(query)
					  })
				  }
				})
		  }
		}
	}
</script>

<style>
  .cu-list.card-menu {
      overflow: hidden;
      margin-right: 5px;
      margin-left: 5px;
      border-radius: 7px;
  }
  .cu-list.card-menu.margin-top-20 {
      margin-top: -20px;
  }
  .cu-list.menu>.cu-item .content>uni-view:first-child {
      display: -webkit-box;
      display: -webkit-flex;
      display: flex;
      -webkit-box-align: center;
      /* -webkit-align-items: center; */
      /* align-items: center; */
      display: inline-block;
      margin-right: 5px;
      width: 1.6em;
      text-align: center;
  }
  .font-size-35{
	 font-size: 35px!important;
  }
  .circle-button{
	 width: 44px;
	 height: 44px;
	 border-radius: 18px;
	 padding-top: 4px;
  }
  .circle-button-box{
	  width: 25%;
	  margin-top: 10px;
	  display: -webkit-box;
	  display: -webkit-flex;
	  display: flex;
	  -webkit-box-orient: vertical;
	  -webkit-box-direction: normal;
	  -webkit-flex-direction: column;
	  flex-direction: column;
	  -webkit-box-align: center;
	  -webkit-align-items: center;
	  align-items: center;
	  -webkit-box-pack: center;
	  -webkit-justify-content: center;
	  justify-content: center;
	  box-sizing: border-box;
  }
  .font-b {
	  vertical-align: middle;
	  font-size: 18px;
	  font-weight: 500;
	  color: #3a3a3a;
  }
</style>
