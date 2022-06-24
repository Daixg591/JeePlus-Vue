<template>
	 <view class="uni-padding-wrap uni-common-mt">
		 <slider value="0" :disabled="disabled" @change="sliderChange" step="5" />
	</view>
</template>

<script>
	export default {
		data() {
			return {
				valueStr: ''+this.value
			};
		},
		props: {
		    value: Number,
			disabled: {
				type: Boolean,
				default: false
			}
		},
		methods:{
			 sliderChange(e) {
				this.$emit('input', e.detail.value)
			}
		}
	}
</script>

<style>

</style>
