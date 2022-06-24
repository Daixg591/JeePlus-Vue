<template>
  <div class="page">
     <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
         <el-form-item prop="name">
          <el-input size="small" v-model="searchForm.name" placeholder="连接名称" clearable></el-input>
         </el-form-item>
      <el-form-item>
        <el-button  type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
        <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
      </el-form-item>
      </el-form>
    <div class="bg-white top">
      <vxe-toolbar :refresh="{query: refreshList}" export print custom>
        <template #buttons>
          <el-button v-if="hasPermission('database:datalink:dataSource:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
          <el-button v-if="hasPermission('database:datalink:dataSource:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()"
          :disabled="$refs.dataSourceTable && $refs.dataSourceTable.getCheckboxRecords().length !== 1"  plain>修改</el-button>
          <el-button v-if="hasPermission('database:datalink:dataSource:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()"
                   :disabled="$refs.dataSourceTable && $refs.dataSourceTable.getCheckboxRecords().length === 0" plain>删除
          </el-button>
        </template>
      </vxe-toolbar>
     <div style="height: calc(100% - 80px);">
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
            @sort-change="sortChangeHandle"
            :sort-config="{remote:true}"
            :data="dataList"
            :checkbox-config="{}">
            <vxe-column type="seq" width="40"></vxe-column>
            <vxe-column type="checkbox"  width="40px"></vxe-column>
            <vxe-column field="name" title="连接名"></vxe-column>
            <vxe-column field="enName" title="连接英文名"></vxe-column>
            <vxe-column field="type" title="数据库类型">
                <template slot-scope="scope">
                    {{ $dictUtils.getDictLabel("db_type", scope.row.type, '-') }}
                </template>
            </vxe-column>
            <vxe-column field="host" title="主机地址"></vxe-column>
            <vxe-column field="port" title="端口"></vxe-column>
            <vxe-column field="databaseName" title="数据库名"></vxe-column>
            <vxe-column field="username" title="数据库用户名"> </vxe-column>
            <vxe-column field="password" title="数据库密码"> </vxe-column>
            <vxe-column
                fixed="right"
                width="200px"
                title="操作">
                <template slot-scope="scope">
                <el-button v-if="hasPermission('database:datalink:dataSource:view')" type="text"  icon="el-icon-view" size="small" @click="view(scope.row.id)">查看</el-button>
                  <el-button v-if="hasPermission('database:datalink:dataSource:edit')" type="text"  size="small" icon="el-icon-edit" @click="edit(scope.row.id)">修改</el-button>
                  <el-button v-if="hasPermission('database:datalink:dataSource:del')" type="text"  size="small" icon="el-icon-delete" @click="del(scope.row.id)">删除</el-button>
                </template>
            </vxe-column>
        </vxe-table>
        <vxe-pager
          background
          size="small"
          :current-page="tablePage.currentPage"
          :page-size="tablePage.pageSize"
          :total="tablePage.total"
          :page-sizes="[10, 20, 100, 1000, {label: '全量数据', value: 1000000}]"
          :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total']"
          @page-change="currentChangeHandle">
        </vxe-pager>
    </div>
    <!-- 弹窗, 新增 / 修改 -->
    <data-source-form  ref="dataSourceForm" @refreshDataList="refreshList"></data-source-form>
  </div>
  </div>
</template>

<script>
  import DataSourceForm from './DataSourceForm'
  import DataSourceService from '@/api/database/DataSourceService'
  export default {
    data () {
      return {
        searchForm: {
          name: ''
        },
        filterText: '',
        dataList: [],
        officeTreeData: [],
        tablePage: {
          total: 0,
          currentPage: 1,
          pageSize: 10,
          orders: []
        },
        loading: false,
        selectOfficeName: ''
      }
    },
    components: {
      DataSourceForm
    },
    dataSourceService: null,
    created () {
      this.dataSourceService = new DataSourceService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.dataSourceService.list({
          'current': this.tablePage.currentPage,
          'size': this.tablePage.pageSize,
          'orders': this.tablePage.orders,
          ...this.searchForm
        }).then(({data}) => {
          this.dataList = data.records
          this.tablePage.total = data.total
          this.loading = false
        })
      },
      // 当前页
      currentChangeHandle ({ currentPage, pageSize }) {
        this.tablePage.currentPage = currentPage
        this.tablePage.pageSize = pageSize
        this.refreshList()
      },
      // 排序
      sortChangeHandle (column) {
        this.tablePage.orders = []
        if (column.order != null) {
          this.tablePage.orders.push({column: this.$utils.toLine(column.property), asc: column.order === 'asc'})
        }
        this.refreshList()
      },
      // 新增
      add () {
        this.$refs.dataSourceForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.dataSourceTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.dataSourceForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.dataSourceForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.dataSourceTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.dataSourceService.delete(ids).then(({data}) => {
            this.$message.success({dangerouslyUseHTMLString: true,
              message: data})
            this.refreshList()
          })
        }).catch(() => {
        })
      },
      resetSearch () {
        this.$refs.searchForm.resetFields()
        this.refreshList()
      }
    }
  }
</script>
