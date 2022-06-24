<template>
	<view class="view">
		<cu-custom bgColor="bg-blue" backUrl="/pages/index/index" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content"> 站内信</block>
		</cu-custom>
		<view class="cu-bar search bg-white">
			<view class="search-form round">
				<text class="cuIcon-search"></text>
				<input :adjust-position="false" v-model="curWord" type="text" @input="inputWord" placeholder="搜索" confirm-type="search"></input>
			</view>
			<view class="action">
				<button class="cu-btn bg-blue shadow-blur round"  @tap="searchSubmit">搜索</button>
			</view>
		</view>
		<view class="cu-list menu sm-border menuCard card-menu margin-top">
			<view class="cu-item arrow" @tap="toInbox">
				<view class="content">
					<text class="cuIcon-mail text-yellow"></text>
					<text class="text-grey">收件箱</text>
				</view>
				<view class="action">
					<view class="text-grey ">{{noReadCount}}/{{mailBoxCount}}</view>
				</view>
			</view>
			
			<view class="cu-item arrow" @tap="toOutbox">
				<view class="content">
					<text class="cuIcon-forward text-green"></text>
					<text class="text-grey">已发送</text>
				</view>
				<view class="action">
					<view class="text-grey ">{{mailComposeCount}}</view>
				</view>
			</view>
			<view class="cu-item arrow" @tap="toDraft">
				<button class="cu-btn content" open-type="contact">
					<text class="cuIcon-post text-grey"></text>
					<text class="text-grey">草稿箱</text>
				</button>
				<view class="action">
					<view class="text-grey ">{{mailDraftCount}}</view>
				</view>
			</view>
			<view class="cu-item arrow" @tap="toTrash">
				<button class="cu-btn content" open-type="contact">
					<text class="cuIcon-delete text-red"></text>
					<text class="text-grey">已删除</text>
				</button>
				<view class="action">
					<view class="text-grey ">{{mailTrashCount}}</view>
				</view>
			</view>
		</view>
			<uni-fab
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
	import MescrollMixin from "@/components/mescroll-uni/mescroll-mixins.js";
	import uniFab from '@/components/uni-fab/uni-fab.vue';
	export default {
		mixins: [MescrollMixin], // 使用mixin (在main.js注册全局组件)
		data() {
			return {
				curWord:'',
				mailBoxCount: '',
				mailComposeCount: '',
				mailDraftCount: '',
				mailTrashCount: '',
				noReadCount: ''
			}
		},
		components:{
			uniFab
		},
		async onShow() {
		  let {data} = await this.$http.get('/app/mailBox/queryStatus');
		  this.mailBoxCount = data.mailBoxCount
		  this.mailComposeCount = data.mailComposeCount
		  this.mailDraftCount = data.mailDraftCount
		  this.noReadCount = data.noReadCount
		  this.mailTrashCount = data.mailTrashCount
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
			toAdd (){
				uni.navigateTo({
				  url: '/pages/apps/mail/sendEmailForm'
				})
			},
			toInbox (){
				uni.navigateTo({
				    url: '/pages/apps/mail/inbox'
				});
			},
			toDraft () {
				uni.navigateTo({
				    url: '/pages/apps/mail/draft'
				});
			},
			toOutbox () {
				uni.navigateTo({
				    url: '/pages/apps/mail/outbox'
				});
			},
			toTrash () {
				uni.navigateTo({
				    url: '/pages/apps/mail/trash'
				});
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

</style>