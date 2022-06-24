<template>
<view>
<view class="action">
	<button class="cu-btn  shadow round" :disabled="disabled" :style="'color:white;background-color:'+color" @tap="showModal" data-target="DialogModal1">{{color}}</button>
</view>
 <view class="cu-modal" :class="modalName=='DialogModal1'?'show':''">
 	<view class="cu-dialog">
 		<view class="padding-xl">
 			 <color-picker :show='true' :color="color" @pickerColor="pickerColor"></color-picker>
 		</view>
 		<view class="cu-bar bg-white justify-end">
 			<view class="action">
 				<button class="cu-btn line-green text-green" @tap="hideModal">取消</button>
 				<button class="cu-btn bg-green margin-left" @tap="confirm">确定</button>
 
 			</view>
 		</view>
 	</view>
 </view>
 </view>
</template>
<script>
import colorPicker from "./colorPicker.vue";

export default {
  components: { colorPicker },
  props: {
    value: String,
  	disabled: {
  		type: Boolean,
  		default: false
  	}
  },
  data() {
    return {
	  modalName: null,
	  color: null,
	  colorRgb: ''
    };
  },
  watch:{
  	value:{
  		handler (val) {
  			this.color = val
  		},
  		immediate: true,
  		deep: false
  	}
  },
  methods: {
    pickerColor(color) {
      this.color = color.rgb
    },
	showModal(e) {
		this.modalName = e.currentTarget.dataset.target
	},
	hideModal(e) {
		this.modalName = null
	},
	confirm () {
		this.$emit('input', this.color)
		this.modalName = null
	}
  }
};
</script>

<style lang="scss" scoped>
#container {
  width: 100vw;
  height: 100vh;
}
.actions {
  margin-top: 20rpx;
  display: flex;
  justify-content: space-between;
  .iconfont {
    color: #777;
    font-size: 56rpx;
  }
  .icon-yes {
    color: rgb(14, 165, 0)
  }
}

</style>
