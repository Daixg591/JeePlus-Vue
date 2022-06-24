<template>
	<view>
		<form>
			<view class="cu-form-group" v-if="item.readable" :class="index===0?'margin-top':''" v-for="(item,index) in formData" :key="index">
				<view class="title">
					<text class="red-color " v-if="item.options.required">* </text> {{item.name}}
				</view>
				<template v-if="item.type=='input'">				
					<!-- 普通输入框 -->
					<input :placeholder='item.placeholder' :disabled="!item.writable"  v-model="item.value"></input>
				</template>	
				<template v-if="item.type=='textarea' || item.type=='editor'">
					<!-- 多行文本输入框 -->
					<textarea maxlength="-1"  :placeholder='item.placeholder' :disabled="!item.writable"   v-model="item.value"></textarea>
				</template>	
				<template v-if="item.type=='number'">
					<!-- 普通输入框 -->
					<input type="number" :placeholder='item.placeholder' :disabled="!item.writable"   v-model="item.value"></input>
				</template>	
				<template v-if="item.type=='radio'">
					<!-- 单选框 -->
				
					<jp-radio-group  v-if="item.options.remote === 3"  v-model="item.value">
						<label v-for="(option, index) in $dictUtils.getDictList(item.options.dictType)">
							<view>
								{{option.label}}
								<radio class='blue radio' :disabled="!item.writable" :value="option.value"  :class="item.value==option.value?'checked':''" :checked="item.value==option.value?true:false" ></radio>
							</view>
						</label>
					</jp-radio-group>
					<jp-radio-group  v-else-if="item.options.showLabel"  v-model="item.value">
						<label v-for="(option, index) in item.options.options">
							<view>
								{{option.label}}
								<radio class='blue radio' :disabled="!item.writable" :value="option.value"  :class="item.value==option.value?'checked':''" :checked="item.value==option.value?true:false" ></radio>
							</view>
						</label>
					</jp-radio-group>
					<jp-radio-group  v-else  v-model="item.value">
						<label v-for="(option, index) in item.options.options">
							<view>
								{{option.value}}
								<radio class='blue radio' :disabled="!item.writable"  :value="option.value"  :checked="item.value==option.value?true:false" ></radio>
							</view>
						</label>
					</jp-radio-group>
				</template>	
				<template v-if="item.type=='checkbox'">
					<jp-checkbox-group v-if="item.options.remote === 3" v-model="item.value">
						<label v-for="(option, index) in $dictUtils.getDictList(item.options.dictType)">
							<view>
								{{option.label || option.value}}
								<checkbox :disabled="!item.writable"  class="blue" :value="option.value"  :class="(','+item.value+',').indexOf(','+option.value+',')>=0?'checked':''" :checked="(','+item.value+',').indexOf(','+option.value+',')>=0" ></checkbox>
							</view>
						</label>
					</jp-checkbox-group>
					
					<jp-checkbox-group v-else-if="item.options.showLabel" v-model="item.value">
						<label v-for="(option, index) in item.options.options">
							<view>
								{{option.label}}
								<checkbox :disabled="!item.writable"  class="blue" :value="option.value"  :class="(','+item.value+',').indexOf(','+option.value+',')>=0?'checked':''" :checked="(','+item.value+',').indexOf(','+option.value+',')>=0" ></checkbox>
							</view>
						</label>
					</jp-checkbox-group>
					<jp-checkbox-group v-else v-model="item.value">
						<label v-for="(option, index) in item.options.options">
							<view>
								{{option.value}}
								<checkbox  class="blue" :disabled="!item.writable"  :value="option.value"  :class="(','+item.value+',').indexOf(','+option.value+',')>=0?'checked':''" :checked="(','+item.value+',').indexOf(','+option.value+',')>=0" ></checkbox>
							</view>
						</label>
					</jp-checkbox-group>
				</template>	
				<template v-if="item.type=='time'">
					<!-- 时间控件 -->
					<jp-datetime-picker  v-model="item.value" :placeholder='item.placeholder' :disabled="!item.writable" mode="time"></jp-datetime-picker>
				</template>	
				<template v-if="item.type=='date'">
					<!-- 日期控件 -->
					<jp-datetime-picker  v-model="item.value" :placeholder='item.placeholder' :disabled="!item.writable" mode="date"></jp-datetime-picker>
				</template>	
				<template v-if="item.type=='rate'">
					<!-- 评分 -->
					<uni-rate :size="18":disabled="!item.writable" :allowHalf="item.options.allowHalf" v-model="item.value" :max="item.options.max"/>
				</template>	
				<template v-if="item.type=='color'">
					<!-- 颜色选择框 -->
					<jp-color-picker :disabled="!item.writable" v-model="item.value" ></jp-color-picker>
				</template>	
				<template v-if="item.type=='select'">
					<!-- 选择框 -->
					<jp-picker :disabled="!item.writable" v-if="item.options.remote === 3" v-model="item.value" :range="$dictUtils.getDictList(item.options.dictType)">
					</jp-picker>
					<jp-picker :disabled="!item.writable" v-else-if="item.options.showLabel" v-model="item.value" :range="item.options.options">
					</jp-picker>
					<jp-picker :disabled="!item.writable" v-else v-model="item.value" rangeKey="value" :range="item.options.options">
					</jp-picker>
				</template>	
				<template v-if="item.type=='switch'">
					<!-- 开关 -->
					<jp-switch :placeholder='item.placeholder' :disabled="!item.writable"   v-model="item.value"></jp-switch>
				</template>	
				<template v-if="item.type=='slider'">
					<!-- 滑块 -->
					<jp-slider  :placeholder='item.placeholder' :disabled="!item.writable"   v-model="item.value"></jp-slider>
				</template>	
				<template v-if="item.type=='text'">
					<!-- 普通输入框 -->
					<text>{{item.value}}</text>
				</template>	
				<template v-if="item.type=='html'">
					<!-- 普通输入框 -->
					<view v-html="item.value"></view>
				</template>	
				<template v-if="item.type=='imgupload' || item.type=='fileupload'">
					<!-- 图片上传 -->
					<jp-image-upload :disabled="!item.writable"   v-model="item.value"></jp-image-upload>
				</template>	
				<template v-if="item.type=='user'">
					<!-- 用户选择框 -->
					<user-select  :disabled="!item.writable"   v-model="item.value"></user-select>
				</template>	
				<template v-if="item.type=='office'">
					<!-- 机构选择框 -->
					<jp-office-select :disabled="!item.writable"   v-model="item.value"></jp-office-select>
				</template>	
				<template v-if="item.type=='area'">
					<!-- 普通输入框 -->
					<jp-area-select :disabled="!item.writable"  v-model="item.value"></jp-area-select>
				</template>	
				<template v-if="item.type=='dict'">
					<!-- 字典 -->
					<jp-picker :disabled="!item.writable"  v-model="item.value" :range="$dictUtils.getDictList(item.options.dictType)"></jp-picker>
				</template>	
			</view>								
		</form>
	</view>
</template>

<script>
	export default {
		name: 'activeForm',
		
		watch:{
			formData:{
				handler (val) {
					console.log(this.formData)
				},
				immediate: true,
				deep: false
			}
		},
		props: {
			formData:{
				type:Array,
				default:function(){
					return []
				}
			}
		},
		methods: {	

		}
	}
</script>

<style lang="scss">
.uni-list-cell {
    justify-content: flex-start
}
.cu-form-group{

    uni-checkbox-group{
		text-align: right;
	}
	uni-radio-group {
		text-align: right;
	}
	uni-checkbox, uni-radio {
	    position: relative;
	    margin-top: 7px;
		margin-left:7px;
		margin-bottom: 7px;
	}
}
</style>