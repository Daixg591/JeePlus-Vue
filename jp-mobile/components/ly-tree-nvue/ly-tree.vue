<template>
	<view>
		<view v-if="showLoading" class="ly-tree_loading">
			<image class="ly-tree_loading-icon" src="data:image/gif;base64,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"></image>
			<text class="ly-tree_loading-text">正在加载</text>
		</view>
		
		<template v-else>
			<text v-if="isEmpty" class="ly-tree_empty">{{emptyText}}</text>
			<view class="ly-tree" role="tree" name="LyTreeExpand">
				<ly-tree-node v-for="nodeId in childNodesId" 
					:nodeId="nodeId" 
					:render-after-expand="renderAfterExpand"
					:show-checkbox="showCheckbox" 
					:show-radio="showRadio" 
					:check-only-leaf="checkOnlyLeaf"
					:key="getNodeKey(nodeId)" 
					:indent="indent" 
					:icon-class="iconClass" />
			</view>
		</template>
	</view>
</template>

<script>
	import TreeStore from './model/tree-store.js';
	import {getNodeKey} from './tool/util.js';
	import LyTreeNode from './ly-tree-node.vue';

	export default {
		name: 'LyTree',
		
		componentName: 'LyTree',
		
		components: {
			LyTreeNode
		},
		
		data() {
			return {
				elId: `ly_${Math.ceil(Math.random() * 10e5).toString(36)}`,
				store: {
					ready: false
				},
				currentNode: null,
				childNodesId: []
			};
		},
		
		provide() {
		    return {
		       tree: this
		    }
		},
		
		props: {
			// 展示数据
			treeData: Array,
			
			// 自主控制loading加载，避免数据还没获取到的空档出现“暂无数据”字样
			ready: {
				type: Boolean,
				default: true
			},
			
			// 内容为空的时候展示的文本
			emptyText: {
				type: String,
				default: '暂无数据'
			},
			
			// 是否在第一次展开某个树节点后才渲染其子节点
			renderAfterExpand: {
				type: Boolean,
				default: true
			},
			
			// 每个树节点用来作为唯一标识的属性，整棵树应该是唯一的
			nodeKey: String,
			
			// 在显示复选框的情况下，是否严格的遵循父子不互相关联的做法，默认为 false
			checkStrictly: Boolean,
			
			// 是否默认展开所有节点
			defaultExpandAll: Boolean,
			
			// 切换全部展开、全部折叠
			toggleExpendAll: Boolean,
			
			// 是否在点击节点的时候展开或者收缩节点， 默认值为 true，如果为 false，则只有点箭头图标的时候才会展开或者收缩节点
			expandOnClickNode: {
				type: Boolean,
				default: true
			},
			
			// 选中的时候展开节点
			expandOnCheckNode: {
				type: Boolean,
				default: true
			},
			
			// 是否在点击节点的时候选中节点，默认值为 false，即只有在点击复选框时才会选中节点
			checkOnClickNode: Boolean,
			checkDescendants: {
				type: Boolean,
				default: false
			},
			
			// 展开子节点的时候是否自动展开父节点
			autoExpandParent: {
				type: Boolean,
				default: true
			},
			
			// 默认勾选的节点的 key 的数组
			defaultCheckedKeys: Array,
			
			// 默认展开的节点的 key 的数组
			defaultExpandedKeys: Array,
			
			// 是否展开当前节点的父节点
			expandCurrentNodeParent: Boolean,
			
			// 当前选中的节点
			currentNodeKey: [String, Number],
			
			// 是否最后一层叶子节点才显示单选/多选框
			checkOnlyLeaf: {
				type: Boolean,
				default: false
			},
			
			// 节点是否可被选择
			showCheckbox: {
				type: Boolean,
				default: false
			},
			
			// 节点单选
			showRadio: {
				type: Boolean,
				default: false
			},
			
			// 配置选项
			props: {
				type: [Object, Function],
				default () {
					return {
						children: 'children', // 指定子树为节点对象的某个属性值
						label: 'label', // 指定节点标签为节点对象的某个属性值
						disabled: 'disabled' //	指定节点选择框是否禁用为节点对象的某个属性值
					};
				}
			},
			
			// 是否懒加载子节点，需与 load 方法结合使用
			lazy: {
				type: Boolean,
				default: false
			},
			
			// 是否高亮当前选中节点，默认值是 false
			highlightCurrent: Boolean,
			
			// 加载子树数据的方法，仅当 lazy 属性为true 时生效
			load: Function,
			
			// 对树节点进行筛选时执行的方法，返回 true 表示这个节点可以显示，返回 false 则表示这个节点会被隐藏
			filterNodeMethod: Function,
			
			// 搜索时是否展示匹配项的所有子节点
			childVisibleForFilterNode: {
				type: Boolean,
				default: false
			},
			
			// 是否每次只打开一个同级树节点展开
			accordion: Boolean,
			
			// 相邻级节点间的水平缩进，单位为像素
			indent: {
				type: Number,
				default: 18
			},
			
			// 自定义树节点的展开图标
			iconClass: String,
			
			// 是否显示节点图标，如果配置为true,需要配置props中对应的图标属性名称
			showNodeIcon: {
				type: Boolean,
				default: false
			},
			
			// 如果数据量较大，建议不要在node节点中添加parent属性，会造成性能损耗
			isInjectParentInNode: {
				type: Boolean,
				default: false
			}
		},
		
		computed: {
			isEmpty() {
				if (this.store.root) {
					const childNodes = this.store.root.getChildNodes(this.childNodesId);
					return !childNodes || childNodes.length === 0 || childNodes.every(({visible}) => !visible);
				}
				
				return true;
			},
			showLoading() {
				return !(this.store.ready && this.ready);
			}
		},
		
		watch: {
			toggleExpendAll(newVal) {
				this.store.toggleExpendAll(newVal);
			},
			defaultCheckedKeys(newVal) {
				this.store.setDefaultCheckedKey(newVal);
			},
			defaultExpandedKeys(newVal) {
				this.store.defaultExpandedKeys = newVal;
				this.store.setDefaultExpandedKeys(newVal);
			},
			treeData(newVal) {
				this.store.setData(newVal);
			},
			checkStrictly(newVal) {
				this.store.checkStrictly = newVal || this.checkOnlyLeaf;
			},
			'store.root.childNodesId'(newVal) {
				this.childNodesId = newVal;
			},
			childNodesId(){
				this.$nextTick(() => {
					this.$emit('ly-tree-render-completed');
				});
			}
		},
		
		methods: {
			/*
			 * @description 对树节点进行筛选操作
			 * @method filter
			 * @param {all} value 在 filter-node-method 中作为第一个参数
			 * @param {Object} data 搜索指定节点的节点数据，不传代表搜索所有节点，假如要搜索A节点下面的数据，那么nodeData代表treeData中A节点的数据
			*/
			filter(value, data) {
				if (!this.filterNodeMethod) throw new Error('[Tree] filterNodeMethod is required when filter');
				this.store.filter(value, data);
			},
			
			/*
			 * @description 获取节点的唯一标识符
			 * @method getNodeKey
			 * @param {String, Number} nodeId
			 * @return {String, Number} 匹配到的数据中的某一项数据
			*/
			getNodeKey(nodeId) {
				let node = this.store.root.getChildNodes([nodeId])[0];
				return getNodeKey(this.nodeKey, node.data);
			},
			
		   /*
		    * @description 获取节点路径
		    * @method getNodePath
		    * @param {Object} data 节点数据
		    * @return {Array} 路径数组
		   */
			getNodePath(data) {
				return this.store.getNodePath(data);
			},
			
			/*
			 * @description 若节点可被选择（即 show-checkbox 为 true），则返回目前被选中的节点所组成的数组
			 * @method getCheckedNodes
			 * @param {Boolean} leafOnly 是否只是叶子节点，默认false
			 * @param {Boolean} includeHalfChecked 是否包含半选节点，默认false
			 * @return {Array} 目前被选中的节点所组成的数组
			*/
			getCheckedNodes(leafOnly, includeHalfChecked) {
				return this.store.getCheckedNodes(leafOnly, includeHalfChecked);
			},
			
			/*
			 * @description 若节点可被选择（即 show-checkbox 为 true），则返回目前被选中的节点的 key 所组成的数组
			 * @method getCheckedKeys
			 * @param {Boolean} leafOnly 是否只是叶子节点，默认false,若为 true 则仅返回被选中的叶子节点的 keys
			 * @param {Boolean} includeHalfChecked 是否返回indeterminate为true的节点，默认false
			 * @return {Array} 目前被选中的节点所组成的数组
			*/
			getCheckedKeys(leafOnly, includeHalfChecked) {
				return this.store.getCheckedKeys(leafOnly, includeHalfChecked);
			},
			
			/*
			 * @description 获取当前被选中节点的 data，若没有节点被选中则返回 null
			 * @method getCurrentNode
			 * @return {Object} 当前被选中节点的 data，若没有节点被选中则返回 null
			*/
			getCurrentNode() {
				const currentNode = this.store.getCurrentNode();
				return currentNode ? currentNode.data : null;
			},
			
			/*
			 * @description 获取当前被选中节点的 key，若没有节点被选中则返回 null
			 * @method getCurrentKey
			 * @return {all} 当前被选中节点的 key， 若没有节点被选中则返回 null
			*/
			getCurrentKey() {
				const currentNode = this.getCurrentNode();
				return currentNode ? currentNode[this.nodeKey] : null;
			},
			
			/*
			 * @description 设置全选/取消全选
			 * @method setCheckAll
			 * @param {Boolean} isCheckAll 选中状态,默认为true
			*/
			setCheckAll(isCheckAll = true) {
				if (this.showRadio) throw new Error('You set the "show-radio" property, so you cannot select all nodes');
				
				if (!this.showCheckbox) console.warn('You have not set the property "show-checkbox". Please check your settings');
				
				this.store.setCheckAll(isCheckAll);
			},
			
			/*
			 * @description 设置目前勾选的节点
			 * @method setCheckedNodes
			 * @param {Array} nodes 接收勾选节点数据的数组
			 * @param {Boolean} leafOnly 是否只是叶子节点, 若为 true 则仅设置叶子节点的选中状态，默认值为 false
			*/
			setCheckedNodes(nodes, leafOnly) {
				this.store.setCheckedNodes(nodes, leafOnly);
			},
			
			/*
			 * @description 通过 keys 设置目前勾选的节点
			 * @method setCheckedKeys
			 * @param {Array} keys 勾选节点的 key 的数组 
			 * @param {Boolean} leafOnly 是否只是叶子节点, 若为 true 则仅设置叶子节点的选中状态，默认值为 false
			*/
			setCheckedKeys(keys, leafOnly) {
				if (!this.nodeKey) throw new Error('[Tree] nodeKey is required in setCheckedKeys');
				this.store.setCheckedKeys(keys, leafOnly);
			},
			
			/*
			 * @description 通过 key / data 设置某个节点的勾选状态
			 * @method setChecked
			 * @param {all} data 勾选节点的 key 或者 data 
			 * @param {Boolean} checked 节点是否选中
			 * @param {Boolean} deep 是否设置子节点 ，默认为 false
			*/
			setChecked(data, checked, deep) {
				this.store.setChecked(data, checked, deep);
			},
			
			/*
			 * @description 若节点可被选择（即 show-checkbox 为 true），则返回目前半选中的节点所组成的数组
			 * @method getHalfCheckedNodes
			 * @return {Array} 目前半选中的节点所组成的数组
			*/
			getHalfCheckedNodes() {
				return this.store.getHalfCheckedNodes();
			},
			
			/*
			 * @description 若节点可被选择（即 show-checkbox 为 true），则返回目前半选中的节点的 key 所组成的数组
			 * @method getHalfCheckedKeys
			 * @return {Array} 目前半选中的节点的 key 所组成的数组
			*/
			getHalfCheckedKeys() {
				return this.store.getHalfCheckedKeys();
			},
			
			/*
			 * @description 通过 node 设置某个节点的当前选中状态
			 * @method setCurrentNode
			 * @param {Object} node 待被选节点的 node
			*/
			setCurrentNode(node) {
				if (!this.nodeKey) throw new Error('[Tree] nodeKey is required in setCurrentNode');
				this.store.setUserCurrentNode(node);
			},
			
			/*
			 * @description 通过 key 设置某个节点的当前选中状态
			 * @method setCurrentKey
			 * @param {all} key 待被选节点的 key，若为 null 则取消当前高亮的节点
			*/
			setCurrentKey(key) {
				if (!this.nodeKey) throw new Error('[Tree] nodeKey is required in setCurrentKey');
				this.store.setCurrentNodeKey(key);
			},
			
			/*
			 * @description 根据 data 或者 key 拿到 Tree 组件中的 node
			 * @method getNode
			 * @param {all} data 要获得 node 的 key 或者 data
			*/
			getNode(data) {
				return this.store.getNode(data);
			},
			
			/*
			 * @description 删除 Tree 中的一个节点
			 * @method remove
			 * @param {all} data 要删除的节点的 data 或者 node
			*/
			remove(data) {
				this.store.remove(data);
			},
			
			/*
			 * @description 为 Tree 中的一个节点追加一个子节点
			 * @method append
			 * @param {Object} data 要追加的子节点的 data 
			 * @param {Object} parentNode 子节点的 parent 的 data、key 或者 node
			*/
			append(data, parentNode) {
				this.store.append(data, parentNode);
			},
			
			/*
			 * @description 为 Tree 的一个节点的前面增加一个节点
			 * @method insertBefore
			 * @param {Object} data 要增加的节点的 data 
			 * @param {all} refNode 要增加的节点的后一个节点的 data、key 或者 node
			*/
			insertBefore(data, refNode) {
				this.store.insertBefore(data, refNode);
			},
			
			/*
			 * @description 为 Tree 的一个节点的后面增加一个节点
			 * @method insertAfter
			 * @param {Object} data 要增加的节点的 data 
			 * @param {all} refNode 要增加的节点的前一个节点的 data、key 或者 node
			*/
			insertAfter(data, refNode) {
				this.store.insertAfter(data, refNode);
			},
			
			/*
			 * @description 通过 keys 设置节点子元素
			 * @method updateKeyChildren
			 * @param {String, Number} key 节点 key 
			 * @param {Object} data 节点数据的数组
			*/
			updateKeyChildren(key, data) {
				if (!this.nodeKey) throw new Error('[Tree] nodeKey is required in updateKeyChild');
				this.store.updateChildren(key, data);
			}
		},
		
		created() {
			this.isTree = true;
			
			let props = this.props;
			if (typeof this.props === 'function') props = this.props();
			if (typeof props !== 'object') throw new Error('props must be of object type.');
			
			this.store = new TreeStore({
				key: this.nodeKey,
				data: this.treeData,
				lazy: this.lazy,
				props: props,
				load: this.load,
				showCheckbox: this.showCheckbox,
				showRadio: this.showRadio,
				currentNodeKey: this.currentNodeKey,
				checkStrictly: this.checkStrictly || this.checkOnlyLeaf,
				checkDescendants: this.checkDescendants,
				expandOnCheckNode: this.expandOnCheckNode,
				defaultCheckedKeys: this.defaultCheckedKeys,
				defaultExpandedKeys: this.defaultExpandedKeys,
				expandCurrentNodeParent: this.expandCurrentNodeParent,
				autoExpandParent: this.autoExpandParent,
				defaultExpandAll: this.defaultExpandAll,
				filterNodeMethod: this.filterNodeMethod,
				childVisibleForFilterNode: this.childVisibleForFilterNode,
				showNodeIcon: this.showNodeIcon,
				isInjectParentInNode: this.isInjectParentInNode
			});

			this.childNodesId = this.store.root.childNodesId;
		},
		
		beforeDestroy() {
			if (this.accordion) {
				uni.$off(`${this.elId}-tree-node-expand`)
			}
		}
	};
</script>

<style>
	.ly-tree {
		position: relative;
		background-color: #FFF;
		padding: 30rpx;
		font-size: 30rpx;
		line-height: 2em;
	}
	.ly-tree_loading {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		text-align: center;
		margin-top: 100rpx;
		align-items: center;
	}
	
	.ly-tree_loading-icon {
		width: 50rpx;
		height: 50rpx;
	}
	
	.ly-tree_loading-text {
		font-size: 30rpx;
		color: #606266;
	}
	
	.ly-tree_empty {
		text-align: center;
		margin-top: 100rpx;
		font-size: 30rpx;
		color: #606266;
	}
</style>
