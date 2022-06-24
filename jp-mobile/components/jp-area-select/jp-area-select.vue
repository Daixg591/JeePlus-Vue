<template>
	<view>
		<view class="action arrow"  @tap="showModal" data-target="bottomModal">
			<view class="uni-input upicker  ellipsis-description" >{{labels?labels:'请选择'}}</view>
		</view>
		<view class="cu-modal bottom-modal" style="min-height: 200upx;" :class="modalName=='bottomModal'?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white">
					<view class="action text-blue" @tap="hideModal">取消</view>
					<view class="action text-green" @tap="selectArea">确定</view>
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
			this.$http.get('/app/sys/area/treeData').then(({data})=>{
				this.data = data.treeData
				this.setTreeList(this.data)
				let labelArra = []
				if(this.value){
					this.treeList.forEach((area) => {
					  if (this.value === area.id) {
						labelArra.push(area.name)
					  }
					})
					this.labels = labelArra.join(',')
				}
			}).catch((e)=>{
				 throw e
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
			selectArea () {
				let ids = this.$refs.userTree.getCheckedNodes().map((item)=>{
					return item.id
				}).join(",");
				let names = this.$refs.userTree.getCheckedNodes().map((item)=>{
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
