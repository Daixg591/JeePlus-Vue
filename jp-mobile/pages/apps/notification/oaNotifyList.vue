<template>
	<view v-show="i === index">
	<view  :style="[{'margin-top':  CustomBar - 10 + 'px'}]">
		<view class="cu-bar search">
			<view class="search-form bg-white round">
				<text class="cuIcon-search"></text>
				<input type="text"  placeholder="搜索" v-model="curWord" confirm-type="search" @input="inputWord"></input>
			</view>
		</view>
		<mescroll-body ref="mescrollRef" @init="mescrollInit" @down="downCallback" :up="upOption" @up="upCallback">
			<view class="cu-list menu-avatar">
				<view class="cu-item" :class="modalName=='move-box-'+ index?'move-cur':''" v-for="(notify, index) in list" :key="index"
				 @touchstart="ListTouchStart" @touchmove="ListTouchMove"  @touchend="ListTouchEnd" :data-target="'move-box-' + index">
			
						<view @click="toDetail(notify)" class="cu-avatar cuIcon-notification bg-blue round">
								
						</view>
						<view @click="toDetail(notify)" class="content">
								<view class="text-bold text-grey">
									<view class="ellipsis-description">
										<text v-if="notify.readFlag === '0'" class='cuIcon-title text-blue'></text>
										 标题: {{notify.title}}
									</view>
								</view>
							
								<view class=" text-gray text-sm">
									<view class="ellipsis-description">
										发布者：{{notify.createBy.name}}，内容：{{notify.content}}
									</view>
								</view>
						
						</view>
						<view @click="toDetail(notify)" class="action">
							<view class="text-grey text-xs" >{{notify.createDate}}</view>
							<view :class="notify.status === '1'?'bg-green':'bg-red'" class="cu-tag round  sm">
							{{$dictUtils.getDictLabel('oa_notify_status', notify.status ,'')}}</view>
						</view>
				
					<view class="move" >
						<view class="bg-red" @click="del(notify.id)">删除</view>
					</view>
				</view>
			</view>
		</mescroll-body>
	</view>
		<uni-fab
		v-if="$auth.hasPermission('notify:oaNotify:add')"
			:pattern=" {
						color: '#7A7E83',
						backgroundColor: '#fff',
						selectedColor: '#007AFF',
						buttonColor: '#007AFF'
					}"
			horizontal="right"
			vertical="bottom"
			@fabClick="toAdd"
		></uni-fab>
	</view>
</template>

<script>
	import uniFab from '@/components/uni-fab/uni-fab.vue';
	import MescrollMixin from "@/components/mescroll-uni/mescroll-mixins.js";
	import MescrollMoreItemMixin from "@/components/mescroll-uni/mixins/mescroll-more-item.js";
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
			uniFab
		},
		data() {
			return {
				upOption: {
					// auto: false, //是否在初始化后,自动执行上拉回调callback; 默认true
					// page: {
					// 	num: 0, // 当前页码,默认0,回调之前会加1,即callback(page)会从1开始
					// 	size: 10 // 每页数据的数量
					// }
					noMoreSize: 3, //如果列表已无数据,可设置列表的总数量要大于半页才显示无更多数据;避免列表数据过少(比如只有一条数据),显示无更多数据会不好看
					empty: {
						tip: '~ 搜索无结果 ~' // 提示
					}
				},
				CustomBar: this.CustomBar,
				list: [], // 数据列表
				modalName: null,
				TabCur: 1,
				
				curWord:"" //当前搜索关键词
			}
		},
		methods: {
			// 跳转到详细页面
			toDetail (notify) {
				if(notify.status === '1'){
					uni.navigateTo({
					  url: '/pages/apps/notification/notificationDetail?id='+notify.id
					})
				}else{
					this.toEdit(notify)
				}
				
			},
			toEdit (notify) {
				uni.navigateTo({
				  url: '/pages/apps/notification/oaNotifyForm?id='+notify.id
				})
			},
			toAdd (){
				uni.navigateTo({
				  url: '/pages/apps/notification/oaNotifyForm'
				})
			},
			tabSelect(e) {
				this.TabCur = e.currentTarget.dataset.id;
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
			},
			TabSelect(e) {
					this.tabCur = e.currentTarget.dataset.id;
					this.mainCur = e.currentTarget.dataset.id;
					this.verticalNavTop = (e.currentTarget.dataset.id - 1) * 50
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
				
				this.$http.get('/app/notify/oaNotify/list', {
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
			del (id) {
				this.$http.delete('/app/notify/oaNotify/delete?id='+id).then(({data})=>{
					uni.showToast({
						title: data.msg,
						icon:"success"
					})
					this.doSearch(this.curWord)
				})
			},
			VerticalMain(e) {
					// #ifdef MP-ALIPAY
					   return false  //支付宝小程序暂时不支持双向联动 
					// #endif
					let that = this;
					let tabHeight = 0;
					if (this.load) {
						for (let i = 0; i < this.list.length; i++) {
							let view = uni.createSelectorQuery().select("#main-" + this.list[i].id);
							view.fields({
								size: true
							}, data => {
								this.list[i].top = tabHeight;
								tabHeight = tabHeight + data.height;
								this.list[i].bottom = tabHeight;
							}).exec();
						}
						this.load = false
					}
					let scrollTop = e.detail.scrollTop + 10;
					for (let i = 0; i < this.list.length; i++) {
						if (scrollTop > this.list[i].top && scrollTop < this.list[i].bottom) {
							this.verticalNavTop = (this.list[i].id - 1) * 50
							this.tabCur = this.list[i].id
							console.log(scrollTop)
							return false
						}
					}
			},
			InputFocus(e) {
				this.InputBottom = e.detail.height
			},
			InputBlur(e) {
				this.InputBottom = 0
			},
			showModal(e) {
				this.modalName = e.currentTarget.dataset.target
			},
			hideModal(e) {
				this.modalName = null
			},
			Gridchange(e) {
				this.gridCol = e.detail.value
			},
			Gridswitch(e) {
				this.gridBorder = e.detail.value
			},
			MenuBorder(e) {
				this.menuBorder = e.detail.value
			},
			MenuArrow(e) {
				this.menuArrow = e.detail.value
			},
			MenuCard(e) {
				this.menuCard = e.detail.value
			},
			SwitchSex(e) {
				this.skin = e.detail.value
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
	
	/*关键词搜索*/
	.item{
		padding: 20rpx;
	}
	.tip{
		font-size: 30rpx;
		vertical-align: middle;
	}
	.hot-word{
		font-size: 24rpx;
		margin-left: 30rpx;
		padding: 6rpx 40rpx;
		border: 2rpx solid #FF6990;
		border-radius: 100rpx;
		vertical-align: middle;
		color: #FF6990;
	}
	.word-input{
		display: inline-block;
		width: 60%;
		height: 50rpx;
		line-height: 50rpx;
		font-size: 24rpx;
		margin-left: 30rpx;
		border: 2rpx solid #18B4FE;
		border-radius: 60rpx;
		text-align: center;
		background-color: #fff;
		vertical-align: middle;
	}
	.top-warp{
		z-index: 9990;
		position: fixed;
		/* top: --window-top; /* css变量 */ 
		left: 0;
		width: 100%;
		height: 120upx;
		background-color: white;
	}
	.cu-bar .search-form{
		background-color: white;
	}
</style>
