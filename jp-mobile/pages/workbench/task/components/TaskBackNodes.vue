<template>
	<view>
		<view class="cu-modal bottom-modal" style="min-height: 200upx;" :class="modalName=='bottomModal'?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white">
					<view class="action text-blue" @tap="hideModal">取消</view>
					<view class="action text-green" @tap="doConfirm">确定</view>
				</view>
				<view>
					  <ly-tree :tree-data="backNodes"
						:props="props" 
						node-key="taskDefKey" 
						:checkOnClickNode ="true"
						:showRadio="true"
						:show-checkbox ="false"
						ref="userTree" />
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				modalName: null,
				backNodes: []
			};
		},
		props: {
			props: {
				type: Object,
				default: () => {
					return {
						children: 'children',
						label: 'taskName'
					}
				}
			}
		  },
		mounted() {

		},
		methods:{
			init (taskId) {
			  this.showModal()
			  this.$http.post('/flowable/task/backNodes', {taskId: taskId}).then(({data}) => {
				this.backNodes = data.backNodes
			  })
			},
			doConfirm () {
				let taskDefKey = this.$refs.userTree.getCheckedNodes().map((item)=>{
					return item.taskDefKey
				}).join(",");
			  this.$emit('getBackTaskDefKey', taskDefKey)
			  this.hideModal()
			},
			showModal() {
				this.modalName = "bottomModal"
			},
			hideModal() {
				this.modalName = null
			}
		}
	}
</script>