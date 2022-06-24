<template>
	<view>
		<addressbook v-if="PageCur=='addressbook'"></addressbook>
		<message v-if="PageCur=='message'"></message>
		<workbench v-if="PageCur=='workbench'"></workbench>
		<apps v-if="PageCur=='apps'"></apps>
		<person v-if="PageCur=='my'"></person>
		<view class="cu-bar tabbar bg-white shadow foot">
			<view class="action" @click="NavChange" data-cur="addressbook">
				<view :class="PageCur=='addressbook'?'text-blue':'text-gray'">
					<!-- <text class="lg" :class="PageCur=='addressbook'?'cuIcon-addressbook':'cuIcon-addressbook'"></text> -->
					<Am-FontAwesome  :class="PageCur=='addressbook'?'fas fa-address-book':'far fa-address-book'"  class="cuIcon-calendar"  ></Am-FontAwesome>
					<text>通讯录</text>
				</view>
			</view>
			<view class="action" @click="NavChange" data-cur="message">
				<view :class="PageCur=='message'?'text-blue':'text-gray'">
					<text class="lg" :class="PageCur=='message'?'cuIcon-messagefill':'cuIcon-message'">
						<text class='cu-tag badge'>0</text>
					</text>
					<text>消息</text>
				</view>
			</view>
			
<!-- 			<view class="action" @click="NavChange" data-cur="workbench">
				<view :class="PageCur=='workbench'?'text-green':'text-gray'">
					<text class="lg" :class="PageCur=='workbench'?'cuIcon-choicenessfill':'cuIcon-choiceness'"></text>
					<text>工作区</text>
				</view>
			</view> -->
			<view class="action text-gray add-action"  @click="NavChange" data-cur="workbench">
				<button class="cu-btn  shadow" :class="PageCur=='workbench'?'cuIcon-homefill bg-blue':'cuIcon-home bg-grey'"></button>
				工作台
			</view>
			
			<view class="action" @click="NavChange" data-cur="apps">
				<view :class="PageCur=='apps'?'text-blue':'text-gray'">
					<text class="lg" :class="PageCur=='apps'?'cuIcon-circlefill':'cuIcon-circle'"></text>
					<text>应用</text>
				</view>
			</view>
			
			<view class="action" @click="NavChange" data-cur="my">
				<view :class="PageCur=='my'?'text-blue':'text-gray'">
					<text class="lg" :class="PageCur=='my'?'cuIcon-profilefill':'cuIcon-profile'"></text>
					<text>我的</text>
				</view>
			</view>
	
		</view>
	</view>
</template>

<script>
	import addressbook from '@/pages/addressbook/addressbook.vue'
	import person from '@/pages/user/person/person.vue'
	import message from '@/pages/message/message.vue'
	import workbench from '@/pages/workbench/workbench.vue'
	import apps from '@/pages/apps/apps.vue'
	export default {
		onShow(option) {
			this.$auth.checkLogin()
			
		},
		components:{
			person,
			message,
			workbench,
			apps,
			addressbook
		},
		data() {
			return {
				PageCur: 'workbench'
			}
		},
		methods: {
			NavChange: function(e) {
				this.PageCur = e.currentTarget.dataset.cur
			}
		}
	}
</script>

<style>
.cu-tag.badge {
	right: 4px;
}
</style>
