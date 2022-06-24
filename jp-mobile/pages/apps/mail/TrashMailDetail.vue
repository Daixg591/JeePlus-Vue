<template>
	<view>
		<cu-custom bgColor="bg-blue" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content"> {{mailBox.mail.title}}</block>
		</cu-custom>
		<view class="cu-bar bg-white solid-bottom">
			<view class="mail grid col-1  margin-top-sm action " >
				<view class=" text-gray text-sm">
					<text class="title padding-right-xs" >发件人：</text>{{mailBox.sender.name}}
				</view>
				<view class=" text-gray text-sm">
					<text class="title  padding-right-xs" >收件人：</text>{{mailBox.receiverNames}}
				</view>
				<view class=" text-gray text-sm">
					<text class="title  padding-right-xs" >时间：</text>{{mailBox.sendtime}}
				</view>			
			</view>
		</view>

		<view class="padding bg-white">
			<view class="text-left padding">
				<view v-html="mailBox.mail.content"></view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				mailBox: {
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
		    this.$http.get('/app/mail/mailTrash/queryById?id='+option.id).then(({data})=>{
				this.mailBox = data.mailTrash
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
