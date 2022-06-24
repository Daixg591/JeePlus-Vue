<template>
	<view>
		<cu-custom bgColor="bg-blue" backUrl="/pages/index/index?id=apps" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content"> 通知公告</block>
		</cu-custom>
		<!-- 菜单 -->
		<view class="bg-white nav fixed flex text-center" :style="[{top:CustomBar + 'px'}]">
			<view class="cu-item flex-sub" :class="0==tabIndex?'text-blue cur':''" @tap="tabSelect" data-id="0">
				我的通知
			</view>
			<view class="cu-item flex-sub" v-if="$auth.hasPermission('notify:oaNotify:list')" :class="1==tabIndex?'text-blue cur':''" @tap="tabSelect" data-id="1">
				通告管理
			</view>
		</view>

		<!-- 子组件 (i: 每个tab页的专属下标;  index: 当前tab的下标) -->
		<!-- 如果每个子组件布局不一样, 可拆开写 (注意ref只能为 "mescrollItem下标" 的格式, 另外 :i="下标" :index="tabIndex"也是固定写法) : -->>
		<my-notify-list  ref="mescrollItem0" :i="0" :index="tabIndex"></my-notify-list>
		<oa-notify-list  ref="mescrollItem1" :i="1" :index="tabIndex"></oa-notify-list>

	</view>
</template>

<script>
	import myNotifyList from "./myNotifyList.vue"
	import oaNotifyList from "./oaNotifyList.vue"
	import MescrollMoreMixin from "@/components/mescroll-uni/mixins/mescroll-more.js";
	import * as $auth from "@/common/auth"
	export default {
		mixins: [MescrollMoreMixin], // 多个mescroll-body写在子组件时, 则使用mescroll-more.js补充子组件的页面生命周期
		components: {
			myNotifyList,
			oaNotifyList
		},
		onLoad(tab) {		
			if(tab&&tab.tabIndex){
				this.tabIndex = parseInt(tab.tabIndex)
			}
		},
		data() {
			return {
				tabIndex: 0 // 当前tab下标,必须与mescroll-more.js对应,所以tabIndex是固定变量,不可以改为其他的名字
			}
		},
		methods:{
			tabSelect(e) {
				this.tabIndex = parseInt(e.currentTarget.dataset.id);
			}
		}
	}
</script>

<style>
	.top-warp{
		z-index: 9990;
		position: fixed;
		top: --window-top; /* css变量 */
		left: 0;
		width: 100%;
		height: 120upx;
		background-color: white;
	}
	.top-warp .tip{
		font-size: 28upx;
		height: 60upx;
		line-height: 60upx;
		text-align: center;
	}
</style>
