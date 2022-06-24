<template>

		<!-- 待用活动表单 -->
		<active-form :formData="formData" ref="form" >								
		</active-form>
</template>

<script>
	import activeForm from '@/components/generate-form/generate-form.vue';
	var  graceChecker = require("@/common/graceChecker.js");
	export default {	
		components:{
			activeForm
		},
		props: {
		      processDefinitionId: {
		        type: String,
		        required: true
		      },
		      edit: {
		        type: Boolean,
		        default: true
		      },
			formData:{
				type:Array,
				default:function(){
					return []
				}
			}
		},

		methods: {
			// 提交序列化的表单
			getFormData (option){
				var submitData={};
				for(var i=0;i<option.length;i++){
					submitData[option[i].model]=option[i].value;
				}
				return submitData;
			},
			//
			  submitStartFormData (vars, callback) {
				  	// 表单校验
					let errMsg = graceChecker.checkFormData(this.formData);
					if(errMsg){
						uni.showToast({
							title:errMsg,
							icon:'none'
						})
						return
					}
				  	let data = this.getFormData(this.formData)
					this.$http.post(`/app/flowable/form/submitStartFormData`,
						{
						  id: this.id,
						  ...vars,
						  data: JSON.stringify(data)
						}
					).then(({data}) => {
						if (data && data.success) {
						  uni.showToast({
							title: data.msg,
							icon: "none"
						  });
						  callback(data)
						}
					})
			  },
			  submitTaskFormData (vars, procInsId, taskId, assignee, comment, callback) {
				  // 表单校验
				  let errMsg = graceChecker.checkFormData(this.formData);
				  if(errMsg){
				  	uni.showToast({
				  		title:errMsg,
				  		icon:'none'
				  	})
				  	return
				  }
				  let data = this.getFormData(this.formData);
				  data = {...vars, ...data}
				  this.loading = true
				  this.$http.post(`/app/flowable/form/submitTaskFormData`,
					 {
					  id: this.id,
					  procInsId: procInsId,
					  taskId: taskId,
					  assignee: assignee,
					  comment: comment,
					  data: JSON.stringify(data)
				  }).then(({data}) => {
					if (data && data.success) {
					  uni.showToast({
						  title: data.msg
					  })
					  callback(data)
					}
				  })
			},
			

			

		}
	}
</script>

<style lang="scss">
	.form-container-box{
		width: 100vw;
		font-size: 28upx;
		min-height: 100vh;
		position: relative;
		padding-bottom: 100upx;
	}
	.bgfff {
		background: #fff;
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
	}
	  
	.buttonBox {
		width: 100%;			
		height: 84upx;
		color: white;
		display: flex;
		align-items: center;
		justify-content: center;
		background-color: #ff5b01;
		
	}
	.submit-data {
		padding: 20upx;
		margin-top: 20upx;
		word-break: break-word;
	}
	
</style>
