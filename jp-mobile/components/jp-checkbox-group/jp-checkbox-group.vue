<template>
		<checkbox-group @change="CheckboxChange">
			<slot></slot>
		</checkbox-group>
</template>

<script>
	export default {
		props: {
		    value: Array,
			disabled: {
				type: Boolean,
				default: false
			}
		},
		methods:{
			CheckboxChange(evt) {
				this.$emit('input', evt.detail.value)
			},
		}
	}
</script>

<style>

</style>
