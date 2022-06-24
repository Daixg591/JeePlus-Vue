<template>
	<view>
		<cu-custom bgColor="bg-blue" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content"> {{mailCompose.mail.title}}</block>
		</cu-custom>
		<view class="cu-bar bg-white solid-bottom">
			<view class="mail grid col-1  margin-top-sm action " >
				<view class=" text-gray text-sm">
					<text class="title padding-right-xs" >发件人：</text> 自己
				</view>
				<view class=" text-gray text-sm">
					<text class="title  padding-right-xs" >收件人：</text>{{mailCompose.receiverNames}}
				</view>
				<view class=" text-gray text-sm">
					<text class="title  padding-right-xs" >时间：</text>{{mailCompose.sendtime}}
				</view>			
			</view>
		</view>

		<view class="padding bg-white">
			<view class="text-left padding">
				<view v-html="mailCompose.mail.content"></view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				mailCompose: {
					mail: {
						title: ''
					},
					sender: {
						name: ''
					}
				}
			}
		},
		onLoad: function (option) {
		    this.$http.get('/app/mailCompose/queryById?id='+option.id).then(({data})=>{
				this.mailCompose = data.mailCompose
			});
		},
		methods: {
			
		}
	}
</script>

<style>
  .mail .title {
  	min-width: calc(4em + 0px);
	text-align: right; 
	display: inline-block
  }
</style>
