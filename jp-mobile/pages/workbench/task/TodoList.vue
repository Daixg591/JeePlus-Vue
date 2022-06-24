<template>
	<view>
		<cu-custom bgColor="bg-blue" backUrl="/pages/index/index" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content"> 待办事项</block>
		</cu-custom>
		<view  :style="[{top:CustomBar + 'px'}]">
		<view class="cu-bar search">
			<view class="search-form bg-white round">
				<text class="cuIcon-search"></text>
				<input type="text"  placeholder="搜索" v-model="curWord" confirm-type="search" @input="inputWord"></input>
			</view>
		</view>
		<mescroll-body ref="mescrollRef" @init="mescrollInit" @down="downCallback" :up="upOption" @up="upCallback">
			<view class="cu-list menu-avatar">
				<view class="cu-item" :class="modalName=='move-box-'+ index?'move-cur':''" v-for="(row, index) in list" :key="index"
				 @touchstart="ListTouchStart" @touchmove="ListTouchMove"  @touchend="ListTouchEnd" :data-target="'move-box-' + index">
			<!-- 
						<view @tap="toDetail(row)" class="cu-avatar round" :style="'background-image:url('+(row.sender.photo?row.sender.photo:'/static/user/flat-avatar.png')+');'">
								
						</view> -->
						<view @tap="todo(row, index)" class="content">
								<view class="text-bold text-grey">
									<view class="ellipsis-description">
										{{row.vars.title}}
									</view>
								</view>
								<view class="text-sm text-grey ellipsis-description">
									当前环节：{{row.task&&row.task.name}}
								</view>
						<!-- 		<view class="  text-sm">
									<view class="ellipsis-description">
										{{row.task&&row.task.name}}
									</view>
								</view> -->
						
						
						</view>
						<view @tap="todo(row, index)" class="action">
							<view class="text-grey text-xs" > {{row.task.createTime | formatDate}}</view>
							<view class="  text-orange text-xs">
								 等待审核
							</view>
						</view>
				
					<view class="move" >
						<view class="bg-blue" @tap="todo(row)">办理</view>
					</view>
				</view>
			</view>
		</mescroll-body>
		</view>
		<!-- 对话框 -->
		<view class="cu-modal" :class="showSelectUserDialog==true?'show':''">
				<view class="cu-dialog">
					<view class="cu-bar bg-white justify-end">
						<view class="content">选择委派用户</view>
						<view class="action" @tap="hideSelectUserDialog">
							<text class="cuIcon-close text-red"></text>
						</view>
					</view>
					<view class="padding-xl">
						<user-select :showCheckBox="false"  :checkOnlyLeaf = "true":showRadio="true" v-model="transferUserId"></user-select>
					</view>
					<view class="cu-bar bg-white justify-end">
						<view class="action">
							<button class="cu-btn line-green text-green" @tap="hideSelectUserDialog">取消</button>
							<button class="cu-btn bg-green margin-left" @tap="selectUsersToTransferTask">确定</button>

						</view>
					</view>
				</view>
			</view>

	</view>
</template>

<script>
	import MescrollMixin from "@/components/mescroll-uni/mescroll-mixins.js";
	import MescrollMoreItemMixin from "@/components/mescroll-uni/mixins/mescroll-more-item.js";
	import pick from 'lodash.pick'
	import userSelect from '@/components/user-select/user-select.vue'
	import uniPopupMessage from '@/components/uni-popup/uni-popup-message.vue'
	import uniPopupDialog from '@/components/uni-popup/uni-popup-dialog.vue'
	import uniPopupShare from '@/components/uni-popup/uni-popup-share.vue'
	export default {
		mixins: [MescrollMixin,MescrollMoreItemMixin], // 使用mixin (在main.js注册全局组件)
		props:{
			i: Number, // 每个tab页的专属下标 (除了支付宝小程序必须在这里定义, 其他平台都可不用写, 因为已在MescrollMoreItemMixin定义)
			index: { // 当前tab的下标 (除了支付宝小程序必须在这里定义, 其他平台都可不用写, 因为已在MescrollMoreItemMixin定义)
				type: Number,
				default(){
					return 0
				}
			},
			tabs: { // 为了请求数据,演示用,可根据自己的项目判断是否要传
				type: Array,
				default(){
					return []
				}
			}
		},
		components:{
			userSelect,
			uniPopupMessage,
			uniPopupShare
		},
		data() {
			return {
				upOption: {
					noMoreSize: 3, //如果列表已无数据,可设置列表的总数量要大于半页才显示无更多数据;避免列表数据过少(比如只有一条数据),显示无更多数据会不好看
					empty: {
						tip: '~ 搜索无结果 ~' // 提示
					}
				},
				CustomBar: this.CustomBar,
				list: [], // 数据列表
				showSelectUserDialog: false,
				transferUserId: '',
				modalName: null,
				currentRow: null,
				curWord:"" //当前搜索关键词
			}
		},
		methods: {
		     claim (row) {
		        this.$http.post('/app/flowable/task/claim', {taskId: row.task.id}).then(({data}) => {
				   uni.showToast({ title: data.msg, icon: "success" });
		           // this.doSearch(this.curWord)
				   row.status = 'todo'
				   row.claimTime = new Date().getTime()
		        })
		      },
		      unclaim (row) {
		        this.$http.post('/app/flowable/task/unclaim', {taskId: row.task.id}).then(({data}) => {
					uni.showToast({ title: data.msg, icon: "success" });
					// this.doSearch(this.curWord)
					row.status = 'claim'
					row.claimTime = undefined
		        })
		      },
		      todo (row, index) {
				if(row.status === 'claim'){
					uni.showToast({ title: '请先签收任务!', icon: "none" });
					this.listTouchDirection = 'left'
					this.modalName = 'move-box-'+index
					return;
				}
		        this.$http.get('/app/flowable/task/getTaskDef', {
		          taskId: row.task.id,
		          taskName: row.task.name,
		          taskDefKey: row.task.taskDefinitionKey,
		          procInsId: row.task.processInstanceId,
		          procDefId: row.task.processDefinitionId,
				  procDefKey: row.task.processDefKey,
		          status: row.status
		        }).then(({data}) => {
					let query = {formTitle: `${row.vars.title}`, title: `审批【${row.task.name || ''}】`, ...pick(data.flow, 'formType', 'formReadOnly', 'formUrl', 'procDefKey', 'taskDefKey', 'procInsId', 'procDefId', 'taskId', 'status', 'title', 'businessId')};
					uni.navigateTo({
					   url: '/pages/workbench/task/TaskForm?flow='+JSON.stringify(query)
					})
		        })
		      },
			// 输入监听
			inputWord(e){
				// this.curWord = e.detail.value // 已使用v-model,无需再次赋值
				// 节流,避免输入过快多次请求
				this.searchTimer && clearTimeout(this.searchTimer)
				this.searchTimer = setTimeout(()=>{
					this.doSearch(this.curWord)
				},300)
			},
			// 搜索
			doSearch(word){
				this.curWord = word
				this.list = []; // 先清空列表,显示加载进度
				this.mescroll.resetUpScroll();
			},
			/*上拉加载的回调: 其中page.num:当前页 从1开始, page.size:每页数据条数,默认10 */
			upCallback(page) {
				//联网加载数据
				this.$http.get('/app/flowable/task/todo', {
					pageNo: page.num,
					pageSize: page.size,
					status: '1',
					title: this.curWord
					
				}).then(({data})=>{
					let curPageData = data.page.list
					this.mescroll.endBySize(curPageData.length,  data.page.count);
					//如果是第一页需手动制空列表
					if(page.num == 1) 
					this.list = [];
					//追加新数据
					this.list=this.list.concat(curPageData);
				}).catch(e=>{
					//联网失败, 结束加载
					this.mescroll.endErr();
				})
				
			},
			
			transferTaskDialog (row) {
				  this.showSelectUserDialog = true
				  this.currentRow = row
			 },
			hideSelectUserDialog () {
				this.showSelectUserDialog = false
			},
		  selectUsersToTransferTask () {
			this.$http.post('/app/flowable/task/delegate', {taskId:  this.currentRow.task.id, userId: this.transferUserId}).then(({data}) => {
			  uni.showToast({
			  	title:data.msg
			  })
			  this.doSearch(this.curWord)
			  this.showSelectUserDialog = false
			})
		  },
			// ListTouch触摸开始
			ListTouchStart(e) {
				this.listTouchStart = e.touches[0].pageX
			},
			
			// ListTouch计算方向
			ListTouchMove(e) {
				this.listTouchDirection = e.touches[0].pageX - this.listTouchStart > -60? 'right' : 'left'
			},
			
			// ListTouch计算滚动
			ListTouchEnd(e) {
				if (this.listTouchDirection == 'left') {
					this.modalName = e.currentTarget.dataset.target
				} else {
					this.modalName = null
				}
				this.listTouchDirection = null
			}
		}
	}
</script>

<style>
	.ellipsis-description {
		  font-size: 12px;
		  line-height: $line-height-base;
		  display: -webkit-box;/*作为弹性伸缩盒子模型显示*/
		  -webkit-line-clamp: 1; /*显示的行数；如果要设置2行加...则设置为2*/
		  overflow: hidden; /*超出的文本隐藏*/
		  text-overflow: ellipsis; /* 溢出用省略号*/
		   -webkit-box-orient: vertical;/*伸缩盒子的子元素排列：从上到下*/
	}
	
	.cu-list.menu-avatar>.cu-item .content {
		position: absolute;
		left: 29px;
		width: calc(100% - 77px);
		line-height: 1.6em;
	}

	.cu-bar .search-form{
		background-color: white;
	}
	.cu-list>.cu-item .move {
	    width: 240px;
	}
	.cu-list>.cu-item.move-cur {
	    -webkit-transform: translateX(-240px);
	    transform: translateX(-240px);
	}
</style>
