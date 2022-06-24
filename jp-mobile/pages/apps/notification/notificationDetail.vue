<template>
	<view>
		<cu-custom bgColor="bg-blue" backUrl="/pages/apps/notification/notification" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content"> 通知公告</block>
		</cu-custom>
		<view class="cu-bar bg-white solid-bottom">
			<view class="grid col-1  margin-top-sm action " style="width: 100%;">
				<view>
					<text class="cuIcon-notification text-blue"></text> 标题：{{notication.title}}
				</view>
				<view class="flex solid-bottom padding justify-between">
					<view class=" text-gray text-sm">
						<text>发布者：{{notication.createBy.name}}，类型：{{$dictUtils.getDictLabel('oa_notify_type', notication.type ,'')}}</text>
					</view>
					<view class=" text-gray text-sm">
						<text>{{notication.createDate}}</text>
					</view>
				</view>
				
			</view>
			
		</view>
		
		<view class="padding bg-white">
			<view class="text-left padding">
				<view v-html="notication.content"></view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				notication: {
					createBy: {
						name: ''
					}
				}
			}
		},
		onLoad: function (option) {
		    this.$http.get('/app/notify/oaNotify/queryById?isSelf=true&id='+option.id).then(({data})=>{
				this.notication = data.oaNotify
			});
		},
		methods: {
			
		}
	}
</script>

<style>

</style>
