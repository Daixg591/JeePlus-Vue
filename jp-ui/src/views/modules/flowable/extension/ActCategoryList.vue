<template>
  <div class="page">
    <el-form size="small" :inline="true"  class="query-form" ref="searchForm" :model="searchForm"  @keyup.enter.native="refreshList()" @submit.native.prevent>
        <el-form-item prop="name">
          <el-input size="small"  v-model="searchForm.name"   placeholder="名称"   clearable></el-input>
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
          <el-button v-if="hasPermission('extension:actCategory:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
          <el-button v-if="hasPermission('extension:actCategory:del')" :disabled="$refs.xTree && $refs.xTree.getCheckboxRecords().length === 0" type="danger" size="small" icon="el-icon-delete" @click="del()">删除</el-button>
        </el-row>
      </template>
    </vxe-toolbar>
    <div style="height: calc(100% - 50px);">
      <vxe-table
        resizable
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
        :tree-config="{expandAll: true}"
        :loading="loading"
        :checkbox-config="{labelField: ''}"
        :data="dataList">
        <vxe-column type="checkbox" width="40px"> </vxe-column>
        <vxe-column  title="名称"  field="name"  align="left" tree-node >
          <template slot-scope="scope">
            <el-link  type="primary" :underline="false" v-if="hasPermission('extension:actCategory:edit')" @click="edit(scope.row.id)">{{scope.row.name}}</el-link>
            <el-link  type="primary" :underline="false" v-else-if="hasPermission('extension:actCategory:view')"  @click="view(scope.row.id)">{{scope.row.name}}</el-link>
            <span v-else>{{scope.row.name}}</span>
          </template>
        </vxe-column>
        <vxe-column  title="备注信息" field="remarks" align="center"></vxe-column>
        <vxe-column title="操作" width="300px" fixed="right" align="center">
          <template  slot-scope="scope">
            <el-button v-if="hasPermission('extension:actCategory:view')" type="text" icon="el-icon-view" size="mini" @click="view(scope.row.id)">
              查看
            </el-button>
            <el-button v-if="hasPermission('extension:actCategory:edit')" type="text" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)">
              修改
            </el-button>
            <el-button v-if="hasPermission('extension:actCategory:del')" type="text" size="mini" icon="el-icon-delete"  @click="del(scope.row.id)">
              删除
            </el-button>
            <el-button v-if="hasPermission('extension:actCategory:del')" type="text" size="mini" icon="el-icon-circle-plus-outline" @click="addChild(scope.row.id, scope.row.name)">
              添加下级
            </el-button>
          </template>
        </vxe-column>
      </vxe-table>
    </div>
    </div>
    <!-- 弹窗, 新增 / 修改 -->
    <ActCategoryForm  ref="actCategoryForm" @refreshDataList="refreshList"></ActCategoryForm>
  </div>
</template>


<script>
  import ActCategoryForm from './ActCategoryForm'
  import ActCategoryService from '@/api/flowable/ActCategoryService'
  import XEUtils from 'xe-utils'
  export default {
    data () {
      return {
        dataList: [],
        searchForm: {
          name: ''
        },
        loading: false
      }
    },
    components: {
      ActCategoryForm
    },
    actCategoryService: null,
    created () {
      this.actCategoryService = new ActCategoryService()
    },
    activated () {
      this.refreshList()
    },

    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.actCategoryService.treeData().then(({data}) => {
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
            this.$refs.xTree.setAllTreeExpand(true)
          })
        }
      },
      resetSearch () {
        this.$refs.searchForm.resetFields()
        this.refreshList()
      },
      // 新增下级
      addChild (id, name) {
        this.$refs.actCategoryForm.init('addChild', {id: '', parent: {id: id, name: name}})
      },
      // 新增
      add () {
        this.$refs.actCategoryForm.init('add', {id: '', parent: {id: '', name: ''}})
      },
      // 修改
      edit (id) {
        this.$refs.actCategoryForm.init('edit', {id: id, parent: {id: '', name: ''}})
      },
      // 查看
      view (id) {
        this.$refs.actCategoryForm.init('view', {id: id, parent: {id: '', name: ''}})
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.xTree.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.actCategoryService.delete(ids).then(({data}) => {
            this.loading = false
            this.$message.success(data)
            this.refreshList()
          })
        })
      }
    }
  }
</script>