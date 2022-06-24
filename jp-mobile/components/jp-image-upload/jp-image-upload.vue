<template>
		<view class="margin-top grid col-4 grid-square flex-sub">
			<view class="bg-img"  v-for="(item,index) in imgList" :key="index" @tap="ViewImage" :data-url="item.url">
			 <image :src="item.url" mode="aspectFill"></image>
				<view class="cu-tag bg-red" v-if="!disabled" @tap.stop="DelImg" :data-index="index">
					<text class='cuIcon-close'></text>
				</view>
			</view>
			<view class="solids" v-if="!disabled" @tap="ChooseImage">
				<text class='cuIcon-cameraadd'></text>
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
		watch:{
			value:{
				handler (val) {
					this.imgList = val
				},
				immediate: true,
				deep: false
			}
		},
		props: {
		    value: {
				type: Array,
				default: function () {
					return []
				}
			},
			disabled: {
				type: Boolean,
				default: false
			}
		},
		methods: {
			ChooseImage() {
				uni.chooseImage({
					count: 4, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album'], //从相册选择
					success: (res) => {
						this.upload(res.tempFilePaths[0])
					}
				});
			},
			ViewImage(e) {
				let urls = this.imgList.map((img)=>{
					return img.url
				})
				uni.previewImage({
					urls: urls,
					current: e.currentTarget.dataset.url
				});
			},
			DelImg(e) {
				uni.showModal({
					title: '提示',
					content: '确定要删除图片吗？',
					cancelText: '取消',
					confirmText: '确定',
					success: res => {
						if (res.confirm) {
							this.imgList.splice(e.currentTarget.dataset.index, 1)
							console.log(this.imgList)
							this.$emit('input', this.imgList)
						}
					}
				})
			},
			upload(img) {
				this.$http.upload('/app/sys/file/webupload/upload?uploadPath=/app/formbuilder',img).then((res)=>{
					this.imgList.push(JSON.parse(res))
					this.$emit('input', this.imgList)
				})
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
  uni-image>div, uni-image>img {
      width: 100upx !important;
      height: 100upx !important;
  }
</style>