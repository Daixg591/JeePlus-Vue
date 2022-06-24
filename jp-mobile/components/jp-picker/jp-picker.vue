<template>
	<view>
		<picker @change="PickerChange" :value="index" :disabled="disabled" :range-value="rangeValue"  :range-key="rangeKey" :range="range">
			<view class="picker">
				{{label}}
			</view>
		</picker>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				index: -1,
				label: '请选择'
			};
		},
		props: {
		    value: String,
			rangeKey: {
				type: String,
				default: 'label'
			},
			rangeValue: {
				type: String,
				default: 'value'
			},
			range: {
				type: Array,
				default: []
			},
			disabled: {
				type: Boolean,
				default: false
			}
		},
		mounted() {
			
		},
		watch:{
			value: {
				handler (val) {
					if(val) {
						let options = this.range.filter((option)=>{
							return option.value === val
						})
						if(options.length === 0){
							this.label = '请选择'
						} else {
							this.label = options[0][this.rangeKey]
						}
					}
				},
				immediate: true,
				deep: false
			}
		},
		methods:{
			PickerChange(e) {
				this.index = e.detail.value;
				if(this.index !== -1){
					this.label = this.range[this.index][this.rangeKey]
					this.$emit('input', this.range[this.index][this.rangeValue])
				}else{
					this.label = '请选择'
					this.$emit('input', null)
				}
				
			}
		}
	}
</script>

<style>

</style>
