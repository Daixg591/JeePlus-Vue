<template>
	<view>
		<cu-custom bgColor="bg-blue" backUrl="/pages/index/index" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">我发起的</block>
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
						<view @click="toDetail(row)" class="content">
								<view class="text-bold text-grey">
									<view class="ellipsis-description">
										{{row.vars.title}}
									</view>
								</view>
								<view class="text-sm text-grey ellipsis-description">
									{{row.taskName}}
								</view>
						</view>
						<view @click="toDetail(row)" class="action">
							<view class="text-grey text-xs" > {{row.startTime  | formatDate}}</view>
							<view :class=" `text-${row.level} text-xs`">
								 {{row.status}}
							</view>
				
						</view>	  
						<view class="move" >
							<view v-if="row.code === 1" class="bg-cyan" @click="urge(row)">催办</view>
							<view v-if="row.code === 1" class="bg-orange" @click="callback(row)">撤销</view>
							<view class="bg-blue" @click="toDetail(row)">历史</view>
							
						</view>
				</view>
		
			</view>
		</mescroll-body>
		</view>
	</view>
</template>

<script>
	import MescrollMixin from "@/components/mescroll-uni/mescroll-mixins.js";
	import MescrollMoreItemMixin from "@/components/mescroll-uni/mixins/mescroll-more-item.js";
	import pick from 'lodash.pick'
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
				modalName: null,
				curWord:"" //当前搜索关键词
			}
		},
		methods: {
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
				this.$http.get('/app/flowable/task/myApplyed', {
					pageNo: page.num,
					pageSize: page.size,
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
				  // 撤销申请
			  callback (row) {
				  uni.showModal({
				      title: '提示',
				      content: '确定要撤销该流程吗？',
				      success: (res)=>{
				          if (res.confirm) {
				              this.$http.delete('/app/flowable/process/deleteProcIns?ids='+row.processInstanceId+'&reason=用户撤销').then(({data}) => {
								if (data && data.success) {
								  uni.showToast({
									title:data.msg
								  })
								  this.doSearch(this.curWord)
								}
				              })
				          } else if (res.cancel) {
				             uni.hideLoading()
				          }
				      },
					  fail() {
					  	
					  }
				  });				
			  },
			  urge (row) {
				uni.showToast({
					title: '催办成功!'
				})
			  },

			toDetail (row) {
				this.$http.get('/app/flowable/task/getTaskDef', {
				  procInsId: row.processInstanceId,
				  procDefId: row.processDefinitionId
				}).then(({data}) => {
				  if (data.success) {
					let query = {readOnly: true, title: row.vars.title, formTitle: row.vars.title, ...pick(data.flow, 'formType', 'formUrl', 'procDefKey', 'taskDefKey', 'procInsId', 'procDefId', 'taskId', 'status', 'title', 'businessId')}
					uni.navigateTo({
					   url: '/pages/workbench/task/TaskFormDetail?flow='+JSON.stringify(query)
					})
				  }
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
