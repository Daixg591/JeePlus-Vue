<template>
	<view>
		<radio-group @change="radioChange">
			<slot></slot>
		</radio-group>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			};
		},
		props: {
		    value: String,
			disabled: {
				type: Boolean,
				default: false
			}
		},
		methods:{
			radioChange (evt) {
				this.$emit('input', evt.detail.value)
			}
		}
	}
</script>

<style>

</style>
