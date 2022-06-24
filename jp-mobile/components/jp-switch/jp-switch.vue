<template>
	<view>
		<switch @change="SwitchA" :class="value?'checked':''" :disabled="disabled" :checked="value?true:false"></switch>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			};
		},
		props: {
		    value: Boolean,
			disabled: {
				type: Boolean,
				default: false
			}
		},
		methods:{
			SwitchA(e) {
				this.$emit('input', e.detail.value)
			}
		}
	}
</script>

<style>

</style>
