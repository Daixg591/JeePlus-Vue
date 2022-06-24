<template>
  <label>{{valueTitle}}</label>
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
          value: 'id',             // ID字段名
          label: 'label',         // 显示名称
          children: 'children'    // 子级字段名
        }
      }
    },
    /* 初始值 */
    value: {
      type: String,
      default: () => { return null }
    },
        /* 初始值 */
    url: {
      type: String,
      default: () => { return null }
    }
  },
  data () {
    return {
      valueId: this.value,    // 初始值
      valueTitle: this.label,
      valueData: [],
      treeList: [],
      map: new Map()
    }
  },
  methods: {
    setTreeList (datas) { // 遍历树  获取id数组
      for (var i in datas) {
        this.treeList.push(datas[i])
        if (datas[i].children) {
          this.setTreeList(datas[i].children)
        }
      }
    }
  },
  watch: {
    value: {
      handler (newVal) {
        this.valueId = newVal
        if (this.valueId === '' || this.valueId === null || this.valueId === undefined) {
          this.valueTitle = ''
        } else {
          if (this.url !== null) {
            let treeList = this.map.get(this.url)
            if (treeList) {
              let titles = []
              this.valueId.split(',').forEach((id) => {
                this.treeList.forEach((d) => {
                  if (id === d[this.props.value]) {
                    titles.push(d[this.props.label])
                  }
                })
              })
              this.valueTitle = titles.join(',')
            } else {
              this.$http({
                url: this.url,
                method: 'get'
              }).then(({data}) => {
                this.valueData = data.treeData
              // this.valueTitle = this.valueData
                this.setTreeList(this.valueData)
                this.map.set(this.url, this.treeList)
                let titles = []
                this.valueId.split(',').forEach((id) => {
                  this.treeList.forEach((d) => {
                    if (id === d[this.props.value]) {
                      titles.push(d[this.props.label])
                    }
                  })
                })
                this.valueTitle = titles.join(',')
              })
            }
          }
        }
      },
      immediate: true,
      deep: false
    }
  }
}
</script>

