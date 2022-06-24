<template>
	<view>
		<view class="cu-modal bottom-modal" style="min-height: 200upx;" :class="modalName=='bottomModal'?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white">
					<view class="action text-blue" @tap="hideModal">取消</view>
					<view class="action text-green" @tap="selectUsers">确定</view>
				</view>
				<view>
					  <ly-tree :tree-data="data"
						:props="props" 
						node-key="id" 
						:checkOnClickNode ="true"
						:showRadio="showRadio"
						:show-checkbox ="showCheckBox"
						:checkOnlyLeaf = "checkOnlyLeaf"
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
				data: [],
				modalName: null,
				labels: null,
				treeList: []
			};
		},
		props: {
		    limit: Number,
		    value: String,
		    size: String,
		    readonly: {
		      type: Boolean,
		      default: () => { return false }
		    },
			checkOnlyLeaf: {
		      type: Boolean,
		      default: () => { return false }
		    },
			showRadio: {
		      type: Boolean,
		      default: () => { return false }
		    },
			showCheckBox: {
			  type: Boolean,
			  default: () => { return true }
			},
		    disabled: {
		      type: Boolean,
		      default: () => { return false }
		    },
			props: {
				type: Object,
				default: () => {
					return {
						children: 'children',
						label: 'label'
					}
				}
			}
		  },
		mounted() {
			this.$http.get('/app/sys/user/treeData').then(({data})=>{
				this.data = data.treeData
				this.setTreeList(this.data)
				let labelArra = []
				if(this.value){
					let keys = this.value.split(',')
					keys.forEach((id) => {
						this.treeList.forEach((node) => {
						  if (id === node.id) {
							labelArra.push(node.label)
						  }
						})
					 })
					this.labels = labelArra.join(',')
					this.$refs.userTree.setCheckedKeys(keys);
				}
			})
		},
		methods:{
			setTreeList (datas) { // 遍历树  获取id数组
			      for (var i in datas) {
			        this.treeList.push(datas[i])
			        if (datas[i].children) {
			          this.setTreeList(datas[i].children)
			        }
			      }
			    },
			selectUsers () {
				let ids = this.$refs.userTree.getCheckedNodes().filter((item)=>{
					return item.type === 'user'
				}).map((item)=>{
					return item.id
				}).join(",");
				let names = this.$refs.userTree.getCheckedNodes().filter((item)=>{
					return item.type === 'user'
				}).map((item)=>{
					return item.label
				}).join(",");
				this.labels = names
				this.$emit('doSubmit', ids)
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