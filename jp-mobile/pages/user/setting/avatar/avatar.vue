<template>
	<view class=" bg-white">
		 <cu-custom bgColor="bg-gradual-blue" :isBack="true"><block slot="backText">返回</block><block slot="content">上传头像</block></cu-custom>

		<view class="cu-form-group">
			
			<view class="grid text-center  col-4 grid-square flex-sub">
				<view class="bg-img" v-for="(item,index) in imgList" :key="index" @tap="ViewImage" :data-url="imgList[index]">
				 <image :src="imgList[index]" mode="aspectFill"></image>
				</view>
				<view class="solids" @tap="ChooseImage">
					<text class='cuIcon-cameraadd'></text>
				</view>
			</view>
		</view>
		<view class="cu-bar btn-group " style="margin-top: 100upx;">
			<button class=" btn-logout cu-btn  shadow-blur round lg" @click="upload">退出登录</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				imgList: []
			}
		},
		methods: {
			ChooseImage() {
				uni.chooseImage({
					count: 4, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album'], //从相册选择
					success: (res) => {
						if (this.imgList.length != 0) {
							this.imgList = this.imgList.concat(res.tempFilePaths)
						} else {
							this.imgList = res.tempFilePaths
						}
					}
				});
			},
			ViewImage(e) {
				uni.previewImage({
					urls: this.imgList,
					current: e.currentTarget.dataset.url
				});
			},
			upload() {
				this.$http.upload('/app/sys/user/imageUpload',this.imgList[0])
			}
		}
	}
</script>

<style>
  .btn-logout {
    margin-top: 100upx;
    width: 80%;
    border-radius: 50upx;
    font-size: 16px;
    color: #fff;
    background: linear-gradient(to right, #365fff, #36bbff);
  }

  .btn-logout-hover {
    background: linear-gradient(to right, #365fdd, #36bbfa);
  }
</style>