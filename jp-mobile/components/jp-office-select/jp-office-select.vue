<template>
	<view>
		<view class="action arrow" :disabled="disabled" @tap="showModal" data-target="bottomModal">
			<view class="upicker uni-input ellipsis-description">{{labels?labels:'请选择'}}</view>
		</view>
		<view class="cu-modal bottom-modal" style="min-height: 200upx;" :class="modalName=='bottomModal'?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white">
					<view class="action text-blue" @tap="hideModal">取消</view>
					<view class="action text-green" @tap="selectOffice">确定</view>
				</view>
				<view>
					  <ly-tree :tree-data="data"
						:props="props" 
						node-key="id" 
						:checkOnClickNode ="true"
						:showRadio="showRadio"
						:show-checkbox ="showCheckBox"
						:checkOnlyLeaf = "checkOnlyLeaf"
						ref="officeTree" />
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
				labels: '',
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
		      default: () => { return true }
		    },
			showCheckBox: {
			  type: Boolean,
			  default: () => { return false }
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
						label: 'name'
					}
				}
			}
		  },
		mounted() {
			this.$http.get('/app/sys/office/treeData').then(({data})=>{
				this.data = data.treeData
				this.setTreeList(this.data)
				if(this.value){
					let keys = this.value.split(',')
					let labelArra = []
					keys.forEach((id) => {
						this.treeList.forEach((node) => {
						  if (id === node.id) {
							labelArra.push(node.name)
						  }
						})
					 })
					this.labels = labelArra.join(',')
					this.$refs.officeTree.setCheckedKeys(keys);
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
			selectOffice () {
				let ids = this.$refs.officeTree.getCheckedNodes().map((item)=>{
					return item.id
				}).join(",");
				let names = this.$refs.officeTree.getCheckedNodes().map((item)=>{
					return item.name
				}).join(",");
				this.labels = names
				this.$emit('input', ids)
				this.hideModal()
			},
			showModal(e) {
				if(this.disabled){
					return
				}
				this.modalName = e.currentTarget.dataset.target
			},
			hideModal(e) {
				this.modalName = null
			}
		}
	}
</script>

