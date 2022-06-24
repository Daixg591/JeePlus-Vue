<template>
    <div class="jp-common-layout page">
      <div class="jp-common-layout-left">
        <div class="jp-common-title"> 
          <el-input
          placeholder="数据库:请输入关键字过滤"
          size="small"
          clearable
          v-model="filterText">
        </el-input>
        </div>
      <div class="jp-common-el-tree-scrollbar el-scrollbar">
      <div class="el-scrollbar__wrap">
        <div class="el-scrollbar__view">
      <el-tree
         class="filter-tree"
        :data="databaseTreeData"
        :props="{
              value: 'id',             // ID字段名
              label: 'label',         // 显示名称
              children: 'children'    // 子级字段名
            }"
        default-expand-all
        :filter-node-method="filterNode"
        :expand-on-click-node="false"
        node-key="id"
        highlight-current
        @node-click="handleNodeClick"
        ref="databaseTree">
      </el-tree>
        </div>
      </div>
      </div>
      </div>
      <div class="jp-common-layout-center jp-flex-main">
     <el-form size="small" :inline="true"  class="query-form"  ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
         <el-form-item prop="name">
           <el-input size="small" v-model="searchForm.name" placeholder="表名" clearable></el-input>
        </el-form-item>
      <el-form-item>
        <el-button  type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
        <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
      </el-form-item>
      </el-form>
      
    <div class="bg-white top">
        <vxe-toolbar :refresh="{query: refreshList}" export print custom>
          <template #buttons>
            <el-button  type="primary" size="small" icon="el-icon-plus" @click="add()">创建表</el-button>
        </template>
      </vxe-toolbar>
       <div style="height: calc(100% - 20px);">
        <vxe-table
              border="inner"
              auto-resize
              resizable
              height="auto"
              :loading="loading"
              size="small"
              ref="dataSourceTable"
              show-header-overflow
              show-overflow
              highlight-hover-row
              :menu-config="{}"
              :print-config="{}"
              :import-config="{}"
              :export-config="{}"
              :data="dataList"
              :checkbox-config="{}">
            <vxe-column
              type="selection"
              header-align="center"
              align="center"
              width="50">
            </vxe-column>>
            <vxe-column
              field="name"
              sortable
              title="表名">
              <template slot-scope="scope">
                <el-link type="primary" :underline="false" @click="view(scope.row)">{{scope.row.name}}</el-link>
              </template>
            </vxe-column>
            <vxe-column
              field="schema"
              sortable
              title="模式">
            </vxe-column>
            <vxe-column
              field="catalog"
              sortable
              title="数据库">
            </vxe-column>
            <vxe-column
              field="description"
              sortable
              title="说明">
            </vxe-column>
            <vxe-column 
            align="center"
            title="操作">
              <template slot-scope="scope">
                <el-button
                  icon="el-icon-edit" type="text" size="small"
                  @click="edit(scope.row)">编辑表</el-button>
               <el-button
                  icon="el-icon-delete" type="text" size="small"
                  @click="drop(scope.row)">删除表</el-button>
                <el-button
                  icon="el-icon-view" type="text" size="small"
                  @click="view(scope.row)">查看数据</el-button>
              </template>
          </vxe-column>
        </vxe-table>
        <el-alert
          :title = "`总共${dataList.length}条记录`"
          type="success"
          :closable="false"/>
       </div>
    </div>
    </div>

    <el-dialog title="查看数据" width="80%" v-if="viewDataVisible"  v-dialogDrag :visible.sync="viewDataVisible">
      <el-table size="small" :data="viewData.dataList">
        <el-table-column v-for="column in viewData.columnList" show-overflow-tooltip :key="column.name" :property="column.name" :label="column.description || column.name" ></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
  import DataSourceService from '@/api/database/DataSourceService'
  import DataTableService from '@/api/database/DataTableService'
  export default {
    data () {
      return {
        searchForm: {
          name: '',
          dataSourceId: ''
        },
        filterText: '',
        viewDataVisible: false,
        dataList: [],
        databaseTreeData: [],
        loading: false,
        dataSourceId: '',
        viewData: {
          columnList: [],
          dataList: []
        }
      }
    },
    watch: {
      filterText (val) {
        this.$refs.databaseTree.filter(val)
      }
    },
    dataSourceService: null,
    dataTableService: null,
    created () {
      this.dataSourceService = new DataSourceService()
      this.dataTableService = new DataTableService()
    },
    activated () {
      this.refreshTree()
      if (this.searchForm.dataSourceId !== '') {
        this.refreshList()
        this.$nextTick(() => {
          this.$refs.databaseTree.setCurrentKey(this.searchForm.dataSourceId)
        })
      }
    },
    methods: {
      add () {
        this.$router.push({ path: '/database/datatable/TableForm', query: {dataSourceId: this.searchForm.dataSourceId, method: 'add', title: '创建表'} })
      },
      // 修改
      edit (row) {
        row = row || this.$refs.dataSetTable.getCheckboxRecords().map(item => {
          return item
        })[0]
        this.$router.push({path: `/database/datatable/TableForm`, query: {name: row.name, dataSourceId: this.searchForm.dataSourceId, method: 'edit', title: '编辑表'}})
      },
       // 删除
      drop (row) {
        this.$confirm(`确定删除表吗?删除之后不可恢复！`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.dataTableService.drop(row.name, this.dataSourceId).then(({data}) => {
            this.$message.success({dangerouslyUseHTMLString: true,
              message: data})
            this.refreshList()
            this.loading = false
          })
        }).catch(() => {
          this.loading = false
        })
      },
      filterNode (value, data) {
        if (!value) return true
        return data.label.indexOf(value) !== -1
      },
      // 获取数据列表
      refreshList () {
        if (!this.searchForm.dataSourceId) {
          this.$message.warning('请选择一个数据库!')
          return
        }
        this.loading = true
        this.dataTableService.list(this.searchForm).then(({data}) => {
          let results = data.rows.filter((row) => {
            if (row.name.indexOf(this.searchForm.name) >= 0) {
              return row
            }
          })
          this.dataList = results
          this.loading = false
        }).catch(({data}) => {
          this.dataList = []
          this.loading = false
        })
      },
      refreshTree () {
        this.dataSourceService.treeData().then(({data}) => {
          this.databaseTreeData = data
        })
      },
      handleNodeClick (data) {
        if (data.type === 'db') {
          this.searchForm.dataSourceId = data.id
          this.dataSourceId = data.id
          this.refreshList()
        }
      },
      handleNodeClose () {
        this.searchForm.dataSourceId = ''
        this.dataSourceId = ''
        this.dataList = []
      },
      resetSearch () {
        this.$refs.searchForm.resetFields()
        this.refreshList()
      },
      view (row) {
        this.viewDataVisible = true
        this.dataTableService.executeSql(row.name, this.dataSourceId).then(({data}) => {
          this.viewData.columnList = data.columns
          this.viewData.dataList = data.list
        })
      }
    }
  }
</script>
