<template>
    <div class="page">
    <el-form size="small" :inline="true"  class="query-form" ref="searchForm" :model="searchForm"  @keyup.enter.native="refreshList()" @submit.native.prevent>
         <el-form-item prop="name">
          <el-input size="small"  v-model="searchForm.name"   placeholder="区域名称"   clearable></el-input>
        </el-form-item>
      <el-form-item>
        <el-button  type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
        <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="bg-white top">
    <vxe-toolbar :refresh="{query: refreshList}" export print custom>
      <template #buttons>
        <el-row>
          <el-button v-if="hasPermission('sys:area:add')" type="primary" icon="el-icon-plus" size="small" @click="add()">新增</el-button>
          <el-button v-if="hasPermission('sys:area:del')" :disabled="$refs.xTree && $refs.xTree.getCheckboxRecords().length === 0" type="danger" size="small" icon="el-icon-delete" @click="del()">删除</el-button>
        </el-row>
      </template>
    </vxe-toolbar>
    <div style="height: calc(100% - 50px);">
      <vxe-table
        ref="xTree"
        border="inner"
        auto-resize
        resizable
        height="auto"
        row-id="id"
        size="small"
        show-header-overflow
        show-overflow
        highlight-hover-row
        :print-config="{}"
        :export-config="{}"
        :tree-config="{expandRowKeys: ['100000']}"
        :loading="loading"
        :checkbox-config="{labelField: ''}"
        :data="dataList">
        <vxe-column type="checkbox" width="40px"> </vxe-column>
        <vxe-column  title="区域名称"  field="name"  align="left" tree-node >
          <template slot-scope="scope">
            <el-link  type="primary" :underline="false" v-if="hasPermission('sys:area:edit')" @click="edit(scope.row.id)">{{scope.row.name}}</el-link>
            <el-link  type="primary" :underline="false" v-else-if="hasPermission('sys:area:view')"  @click="view(scope.row.id)">{{scope.row.name}}</el-link>
            <span v-else>{{scope.row.name}}</span>
          </template>
        </vxe-column>
        <vxe-column  title="区域编码" field="code" align="center"></vxe-column>
        <vxe-column  title="区域类型" field="type" align="center">
          <template slot-scope="scope">
            {{$dictUtils.getDictLabel("sys_area_type", scope.row.type)}}
          </template>
        </vxe-column>
         <vxe-column  title="排序" field="sort" align="center"></vxe-column>
        <vxe-column  title="备注" field="remarks" align="center"></vxe-column>
        <vxe-column title="操作" width="300px" fixed="right" align="center">
          <template slot-scope="scope">
            <el-button v-if="hasPermission('sys:area:view')" type="text" size="small"  icon="el-icon-view" @click="view(scope.row.id)">查看</el-button>
            <el-button v-if="hasPermission('sys:area:edit')" type="text" size="small" icon="el-icon-edit" @click="edit(scope.row.id)">修改</el-button>
            <el-button v-if="hasPermission('sys:area:del')" type="text" size="small" icon="el-icon-delete" @click="del(scope.row.id)">删除</el-button>
            <el-button v-if="hasPermission('sys:area:add')" type="text" size="small" icon="el-icon-circle-plus-outline" @click="addChild(scope.row.id, scope.row.name)">添加下级区域</el-button>
          </template>
        </vxe-column>
      </vxe-table>
    </div>
    </div>
    <!-- 弹窗, 新增 / 修改 -->
    <AreaForm ref="areaForm" @refreshDataList="refreshList"></AreaForm>
  </div>
</template>

<script>
  import AreaForm from './AreaForm'
  import AreaService from '@/api/sys/AreaService'
  import XEUtils from 'xe-utils'
  export default {
    data () {
      return {
        loading: false,
        searchForm: {
          name: ''
        },
        dataList: []
      }
    },
    components: {
      AreaForm
    },
    areaService: null,
    created () {
      this.areaService = new AreaService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.areaService.treeData().then(({data}) => {
          this.dataList = data
          this.handleSearch()
          this.loading = false
        })
      },
      handleSearch () {
        let filterName = XEUtils.toValueString(this.searchForm.name).trim()
        if (filterName) {
          let options = { children: 'children' }
          let searchProps = ['name']
          this.dataList = XEUtils.searchTree(this.dataList, item => searchProps.some(key => XEUtils.toValueString(item[key]).indexOf(filterName) > -1), options)
          this.$nextTick(() => {
            if (filterName !== '中国' && filterName !== '中' && filterName !== '国') { // 禁止全部展开，因为性能问题
              this.$refs.xTree.setAllTreeExpand(true)
            }
          })
        }
      },
      resetSearch () {
        this.$refs.searchForm.resetFields()
        this.refreshList()
      },
      // 新增下级
      addChild (id, name) {
        this.$refs.areaForm.init('addChild', {id: '', parent: {id: id, name: name}})
      },
       // 新增
      add () {
        this.$refs.areaForm.init('add', {id: '', parent: {id: '', name: ''}})
      },
      // 修改
      edit (id) {
        this.$refs.areaForm.init('edit', {id: id, parent: {id: '', name: ''}})
      },
      // 查看
      view (id) {
        this.$refs.areaForm.init('view', {id: id, parent: {id: '', name: ''}})
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.xTree.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除该记录吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.areaService.delete(ids).then(({data}) => {
            this.loading = false
            this.$message({
              message: data,
              type: 'success',
              duration: 1500
            })
            this.refreshList()
          })
        })
      }
    }
  }
</script>
