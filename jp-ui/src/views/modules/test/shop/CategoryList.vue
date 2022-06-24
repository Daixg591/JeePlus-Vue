<template>
    <div class="page">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
            <!-- 搜索框-->
         <el-form-item prop="name">
                <el-input size="small" v-model="searchForm.name" placeholder="类型名" clearable></el-input>
         </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
            <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
          </el-form-item>
      </el-form>
    <div class="bg-white top">
    <vxe-toolbar :refresh="{query: refreshList}" export print custom>
      <template #buttons>
          <el-row>
            <el-button v-if="hasPermission('test:shop:category:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('test:shop:category:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()"
                 :disabled="$refs.category && $refs.category.getCheckboxRecords().length !== 1" plain>修改</el-button>
            <el-button v-if="hasPermission('test:shop:category:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()"
                  :disabled="$refs.category && $refs.category.getCheckboxRecords().length === 0" plain>删除</el-button>
          </el-row>
      </template>
    </vxe-toolbar>
    <div style="height: calc(100% - 50px);">
      <vxe-table
        resizable
        ref="category"
        border="inner"
        auto-resize
        height="auto"
        row-id="id"
        size="small"
        show-header-overflow
        show-overflow
        highlight-hover-row
        :print-config="{}"
        :export-config="{}"
        :tree-config="{}"
        :loading="loading"
        :checkbox-config="{checkStrictly: true}"
        :data="dataList">
      <vxe-table-column type="checkbox" width="40px"> </vxe-table-column>
    <vxe-table-column
        field="name"
        align="left"
        tree-node
        title="类型名">
            <template slot-scope="scope">
              <el-link  type="primary" :underline="false" v-if="hasPermission('test:shop:category:edit')" @click="edit(scope.row.id)">{{scope.row.name}}</el-link>
              <el-link  type="primary" :underline="false" v-else-if="hasPermission('test:shop:category:view')"  @click="view(scope.row.id)">{{scope.row.name}}</el-link>
              <span v-else>{{scope.row.name}}</span>
            </template>
      </vxe-table-column>
    <vxe-table-column
        field="remarks"
        title="备注信息">
      </vxe-table-column>
      <vxe-table-column title="操作" width="300px" fixed="right" align="center">
        <template  slot-scope="scope">
          <el-button v-if="hasPermission('test:shop:category:view')" type="text" size="small" icon="el-icon-view" @click="view(scope.row.id)">查看</el-button>
          <el-button v-if="hasPermission('test:shop:category:edit')" type="text" size="small" icon="el-icon-edit" @click="edit(scope.row.id)">修改</el-button>
          <el-button v-if="hasPermission('test:shop:category:del')" type="text" size="small" icon="el-icon-delete" @click="del(scope.row.id)">删除</el-button>
          <el-button v-if="hasPermission('test:shop:category:add')" type="text" size="small" icon="el-icon-circle-plus-outline" @click="addChild(scope.row.id, scope.row.name)">添加下级商品类型</el-button>
        </template>
      </vxe-table-column>
    </vxe-table>
    </div>
    </div>
        <!-- 弹窗, 新增 / 修改 -->
    <CategoryForm  ref="categoryForm" @refreshDataList="refreshList"></CategoryForm>
  </div>
</template>

<script>
  import CategoryForm from './CategoryForm'
  import XEUtils from 'xe-utils'
  import CategoryService from '@/api/test/shop/CategoryService'
  export default {
    data () {
      return {
        searchForm: {
          name: ''
        },
        dataList: [],
        loading: false
      }
    },
    components: {
      CategoryForm
    },
    categoryService: null,
    created () {
      this.categoryService = new CategoryService()
    },
    activated () {
      this.refreshList()
    },

    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.categoryService.treeData().then(({data}) => {
          this.dataList = data
          this.handleSearch()
          this.loading = false
        })
      },
      handleSearch () {
        let options = { children: 'children' }
        let searchProps = ['name']
        this.dataList = XEUtils.searchTree(this.dataList, item => searchProps.every(key => XEUtils.toValueString(this.searchForm[key]).trim() === '' || XEUtils.toValueString(item[key]).indexOf(this.searchForm[key]) > -1), options)
        this.$nextTick(() => {
          this.$refs.category.setAllTreeExpand(true)
        })
      },
      resetSearch () {
        this.$refs.searchForm.resetFields()
        this.refreshList()
      },
        // 新增下级
      addChild (id, name) {
        this.$refs.categoryForm.init('addChild', {id: '', parent: {id: id, name: name}})
      },
       // 新增
      add () {
        this.$refs.categoryForm.init('add', {id: '', parent: {id: '', name: ''}})
      },
      // 修改
      edit (id) {
        this.$refs.categoryForm.init('edit', {id: id, parent: {id: '', name: ''}})
      },
      // 查看
      view (id) {
        this.$refs.categoryForm.init('view', {id: id, parent: {id: '', name: ''}})
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.category.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.categoryService.delete(ids).then(({data}) => {
            this.$message.success(data)
            this.refreshList()
            this.loading = false
          })
        })
      }
    }
  }
</script>

