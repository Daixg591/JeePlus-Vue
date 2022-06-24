<template name="apps">
	<view>
		<cu-custom bgColor="bg-blue">
			<block slot="content"> 应用</block>
		</cu-custom>
		<scroll-view scroll-y class="page my-app">
			<view class="grid col-2 padding-sm">
				<view class="padding-sm">
					<navigator hover-class="none" url="/pages/apps/notification/notification" >
						<view class="padding radius text-center shadow-blur bg-blue">
							<view class="cuIcon-notice text-white circle-button font-size-35"></view>
							<view class="margin-top-sm text-Abc">通告</view>
						</view>
					</navigator>
				</view>
				<view class="padding-sm">
					<navigator hover-class="none" url="/pages/apps/mail/mail" >
						<view class="padding radius text-center shadow-blur bg-blue">
							<view class="cuIcon-mail text-white circle-button font-size-35"></view>
							<view class="margin-top-sm text-Abc">站内信</view>
						</view>
					</navigator>
				</view>
			</view>
			<view class="grid col-3 padding-sm" style="margin-top: -7px;">
				<view class="padding-sm">
					<navigator hover-class="none" url="/pages/samples/basics/home" >
						<view class="padding radius text-center shadow-blur bg-white">
							<text class="lg font-size-35 text-blue cuIcon-form"></text>
							<view class="margin-top-sm text-Abc">元素</view>
						</view>
					</navigator>
				</view>
				<view class="padding-sm">
					<navigator hover-class="none" url="/pages/samples/component/home" >
						<view class="padding radius text-center shadow-blur bg-white">
							<text class="lg font-size-35 text-blue cuIcon-list"></text>
							<view class="margin-top-sm text-Abc">组件</view>
						</view>
					</navigator>
				</view>
				<view class="padding-sm">
					<navigator hover-class="none" url="/pages/samples/plugin/home" >
						<view class="padding radius text-center shadow-blur bg-white">
							<text class="lg font-size-35 text-blue cuIcon-similar"></text>
							<view class="margin-top-sm text-Abc">扩展</view>
						</view>
					</navigator>
				</view>
				<view class="padding-sm">
					<navigator hover-class="none" url="/pages/samples/ucharts/ucharts" >
						<view class="padding radius text-center shadow-blur bg-white">
							<text class="lg font-size-35 text-blue cuIcon-rank"></text>
							<view class="margin-top-sm text-Abc">图表</view>
						</view>
					</navigator>
				</view>
				<view class="padding-sm">
					<view class="padding radius text-center shadow-blur bg-white">
						<text class="lg font-size-35 text-blue cuIcon-calendar"></text>
						<view class="margin-top-sm text-Abc">日历</view>
					</view>
				</view>
				<view class="padding-sm">
					<view class="padding radius text-center shadow-blur bg-white">
						<text class="lg font-size-35 text-blue cuIcon-phone"></text>
						<view class="margin-top-sm text-Abc">电话</view>
					</view>
				</view>
				<view class="padding-sm">
					<view class="padding radius text-center shadow-blur bg-white">
						<text class="lg font-size-35 text-blue cuIcon-activity"></text>
						<view class="margin-top-sm text-Abc">工作</view>
					</view>
				</view>
				<view class="padding-sm">
					<view class="padding radius text-center shadow-blur bg-white">
						<text class="lg font-size-35 text-blue cuIcon-hot"></text>
						<view class="margin-top-sm text-Abc">热点</view>
					</view>
				</view>
				<view class="padding-sm">
					<view class="padding radius text-center shadow-blur bg-white">
						<text class="lg font-size-35 text-blue cuIcon-safe"></text>
						<view class="margin-top-sm text-Abc">安全</view>
					</view>
				</view>

			</view>
			<view class="cu-tabbar-height"></view>
		</scroll-view>

	</view>
</template>

<script>
	
	export default {
		name: "apps",
		data() {
			return {
			};
		},
		onShow() {
			console.log("success")
		}
	}
</script>

<style>
	.page {
		height: 100vh;
	}
	.font-size-35{
		 font-size: 35px!important;
	}
	.my-app .padding-sm {
	    padding: 6px;
	}
	.grid .padding-sm .bg-white{
		    box-shadow: 0 1px 4px #f9f9f9, 1px 1px 40px rgba(0,0,0,.06);
	}
</style>
