<template>
	<view>
		<!---消息系统暂未实现-->
			<cu-custom bgColor="bg-blue" :isBack="true">
				<block slot="backText">返回</block>
				<block slot="content">消息中心</block>
			</cu-custom>
			<scroll-view class="VerticalMain" scroll-y scroll-with-animation>
					<view class="cu-bar bg-white solid-bottom margin-top">
						<view class="action">
							<text class="cuIcon-notification text-orange "></text> 消息
						</view>
					</view>
					<view class="cu-list menu-avatar">
						<view class="cu-item" :class="modalName=='move-box-'+ index?'move-cur':''" v-for="(item,index) in messageList" :key="index"
						 @touchstart="ListTouchStart" @touchmove="ListTouchMove" @touchend="ListTouchEnd" :data-target="'move-box-' + index">
							<view class="cu-avatar round lg" :style="[{backgroundImage:'url('+(item.createBy.photo?item.createBy.photo:'/static/user/flat-avatar.png')+')'}]"></view>
							<view class="content">
									<view class="text-bold text-grey">
										<view class="ellipsis-description" v-html="item.title"></view>
									</view>
									<view class=" text-gray text-sm">
										<div class="margin-left-xs ellipsis-description" v-html="item.content"></div>
									</view>
							</view>
							<view class="action" style="120px">
								<view class="text-grey text" >{{item.createDate}}</view>
								<!-- <view class="cu-tag round bg-grey sm">5</view> -->
							</view>
							<view class="move">
								<view class="bg-grey">置顶</view>
								<view class="bg-red">删除</view>
							</view>
						</view>
						<view v-if="messageList.length === 0">
							<view class="padding-lr bg-white">
								<view class="padding">暂无消息!</view>
							</view>
						</view>
					</view>
			</scroll-view>
		<view class="cu-tabbar-height"></view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
				messageList: []
			};
		},
		async mounted () {
			
		},
		methods: {
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
				this.listTouchDirection = e.touches[0].pageX - this.listTouchStart > 0 ? 'right' : 'left'
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
	.cu-list.menu-avatar>.cu-item .action {
	    width: 120px;
	    text-align: center;
	}
	.page {
		height: 100Vh;
		width: 100vw;
	}

	.page.show {
		overflow: hidden;
	}

	.switch-sex::after {
		content: "\e716";
	}

	.switch-sex::before {
		content: "\e7a9";
	}

	.switch-music::after {
		content: "\e66a";
	}

	.switch-music::before {
		content: "\e6db";
	}
/* 	.fixed {
		position: fixed;
		z-index: 99;
	}
	 */
	.VerticalNav.nav {
		width: 200upx;
		white-space: initial;
	}
	
	.VerticalNav.nav .cu-item {
		width: 100%;
		text-align: center;
		background-color: #fff;
		margin: 0;
		border: none;
		height: 50px;
		position: relative;
	}
	
	.VerticalNav.nav .cu-item.cur {
		background-color: #f1f1f1;
	}
	
	.top-warp{
		z-index: 9990;
		position: fixed;
		/* top: --window-top; /* css变量 */
		left: 0;
		width: 100%;
		/* height: 120upx; */
		/* background-color: white; */
	}
	.top-warp .tip{
		font-size: 28upx;
		height: 60upx;
		line-height: 60upx;
		text-align: center;
	}
</style>
