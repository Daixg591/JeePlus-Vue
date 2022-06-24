<template>

   <el-cascader :placeholder="placeholder"
   v-model="valueTitle" :disabled="disabled" :clearable="clearable" @clear="clearHandle"
        :props="props"
        :size="size"
         @change="handleChange"
        :options="valueData">
  </el-cascader>
</template>

<script>
export default {
  name: 'el-tree-select',
  props: {
    /* 配置项 */
    props: {
      type: Object,
      default: () => {
        return {
          expandTrigger: 'hover',
          value: 'name',             // ID字段名
          label: 'name',         // 显示名称
          children: 'children'    // 子级字段名
        }
      }
    },
     /* 选项列表数据(树形结构的对象数组) */
    list: {
      type: Array,
      default: () => { return null }
    },
    /* 初始值 */
    value: {
      type: String,
      default: () => { return null }
    },
    placeholder: {
      type: String,
      default: () => { return '请选择' }
    },
        /* 初始值 */
    url: {
      type: String,
      default: () => { return '/sys/area/treeData' }
    },
    disabled: {
      type: Boolean,
      dafault: () => { return false }
    },
    showCheckbox: {
      type: Boolean,
      dafault: () => { return false }
    },
    /* 初始值 */
    label: {
      type: String,
      default: () => { return null }
    },
    /* 可清空选项 */
    clearable: {
      type: Boolean,
      default: () => { return true }
    },
    size: {
      type: String,
      default: () => { return 'small' }
    },
    /* 自动收起 */
    accordion: {
      type: Boolean,
      default: () => { return false }
    }
  },
  data () {
    return {
      valueTitle: [],
      valueData: []
    }
  },
  created () {
    this.$http({
      url: this.url,
      method: 'get'
    }).then(({data}) => {
      this.valueData = data
      if (this.value) {
        this.valueTitle = this.value.split('/')
      }
    })
  },
  methods: {
    // 切换选项
    handleChange (node) {
      this.$emit('getValue', this.valueTitle.join('/'))
    },
    // 清除选中
    clearHandle () {
      this.valueTitle = ''
      this.$emit('getValue', null)
    },
    /* 清空选中样式 */
    clearSelected () {
      let allNode = document.querySelectorAll('#tree-option .el-tree-node')
      allNode.forEach((element) => element.classList.remove('is-current'))
    }
  },
  watch: {
    value () {
      if (this.value === '') {
        this.clearHandle()
      } else if (this.value) {
        this.valueTitle = this.value.split('/')
      }
    }
  }
}
</script>