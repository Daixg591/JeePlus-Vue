<template>
		<picker :mode="mode" :value="value" :disabled="disabled" :start="start" :end="end" @change="TimeChange">
			<view class="picker">
				{{label || '请选择'}}
			</view>
		</picker>
</template>

<script>
	export default {
		data () {
			return {
				label: ''
			}
		},
		props: {
		    value: {
				type: String,
				default: '2020-09-12'
			},
			start: String,
			end: String,
			mode: String,
			disabled: {
				type: Boolean,
				default: false
			}
		},
		watch:{
			value:{
				handler (val) {
					this.label = val
				},
				immediate: true,
				deep: false
			}
		},
		methods:{
			TimeChange(e) {
				this.label = e.detail.value
				this.$emit('input', e.detail.value)
			}
		}
	}
</script>

<style>

</style>
