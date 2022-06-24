<template>
  <div class="page">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
            <!-- 搜索框-->
		     <el-form-item prop="name">
                <el-input size="small" v-model="searchForm.name" placeholder="数据源名称" clearable></el-input>
		     </el-form-item>
          <el-form-item>
            <el-button  type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
            <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
          </el-form-item>
      </el-form>
      <div class="top bg-white">
        <vxe-toolbar :refresh="{query: refreshList}" export print custom>
          <template #buttons>
            <el-button v-if="hasPermission('database:datamodel:dataSet:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('database:datamodel:dataSet:edit')" type="success" size="small" icon="el-icon-edit-outline" @click="edit()"
            :disabled="$refs.dataSetTable && $refs.dataSetTable.getCheckboxRecords().length !== 1" plain>修改</el-button>
            <el-button v-if="hasPermission('database:datamodel:dataSet:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()"
                      :disabled="$refs.dataSetTable && $refs.dataSetTable.getCheckboxRecords().length === 0" plain>删除
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
            ref="dataSetTable"
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
            <vxe-column
              field="name"
              sortable
              title="模型名称">
              <template slot-scope="scope">
                <el-link  type="primary" :underline="false"  v-if="hasPermission('database:datamodel:dataSet:edit')" @click="edit(scope.row.id)">{{scope.row.name}}</el-link>
                <el-link  type="primary" :underline="false"  v-else-if="hasPermission('database:datamodel:dataSet:view')"  @click="view(scope.row.id)">{{scope.row.name}}</el-link>
                <span v-else>{{scope.row.name}}</span>
              </template>
            </vxe-column>
          <vxe-column
              field="dataSource.name"
              sortable
              title="目标数据库">
              <template  slot-scope="scope">
                <p v-if="scope.row.dataSource.id === 'master'">
                  本地数据库
                </p>
                <p v-else>
                  {{scope.row.dataSource.name}}
                </p>
              </template>
          </vxe-column>
          <vxe-column
              field="sqlCmd"
              sortable
              show-overflow-tooltip
              title="sql语句">
            </vxe-column>
            <vxe-column
              fixed="right"
              width="300"
              title="操作">
              <template  slot-scope="scope">
                <el-button v-if="hasPermission('database:datamodel:dataSet:view')" type="text" icon="el-icon-view" size="mini" @click="view(scope.row.id)">查看</el-button>
                <el-button v-if="hasPermission('database:datamodel:dataSet:edit')" type="text" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)">修改</el-button>
                <el-button type="text" size="mini" icon="el-icon-coin" @click="getDbInterface(scope.row.id)">获取数据接口</el-button>
                <el-button v-if="hasPermission('database:datamodel:dataSet:del')" type="text" size="mini" icon="el-icon-delete" @click="del(scope.row.id)">删除</el-button>
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
      </div>
    <el-dialog title="数据接口" :visible.sync="dialogInterfaceVisible"  v-dialogDrag>
      <el-table :data="interfaceTable">
        <el-table-column property="type" width="120px" title="接口格式"></el-table-column>
        <el-table-column property="url" title="接口地址"></el-table-column>
        <el-table-column>
          <template slot-scope="scope">
             <el-button type="text" icon="el-icon-copy-document"
                       v-clipboard:copy="scope.row.url"
                       v-clipboard:success="onCopy"
                       v-clipboard:error="copyError" style="padding-left: 10px"></el-button>
          </template>
        </el-table-column>
      </el-table>
      <br>
      <h4> 接口传递参数说明：/接口url?参数名=参数值, 如果不传递参数，则使用默认值</h4>
    </el-dialog>
  </div>
</template>

<script>
  import DataSetService from '@/api/database/DataSetService'
  export default {
    data () {
      return {
        searchForm: {
          db: {
            id: '',
            name: ''
          },
          name: ''
        },
        dataList: [],
        tablePage: {
          total: 0,
          currentPage: 1,
          pageSize: 10,
          orders: []
        },
        isImportCollapse: false,
        dialogInterfaceVisible: false,
        interfaceTable: [],
        loading: false
      }
    },
    dataSetService: null,
    created () {
      this.dataSetService = new DataSetService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.dataSetService.list({
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
        this.$router.push('/database/datamodel/DataSetForm')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.dataSetTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$router.push({path: `/database/datamodel/DataSetForm`, query: {id: id, title: '数据模型配置'}})
      },
      // 查看
      view (id) {
        id = id || this.$refs.dataSetTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$router.push({path: `/database/datamodel/DataSetForm`, query: {method: 'view', id: id, title: '数据模型配置'}})
      },
      onCopy (e) {
        this.$message.success('复制成功：' + e.text)
      },
      copyError (e) {
        this.$message.error('复制失败!')
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.dataSetTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.dataSetService.delete(ids).then(({data}) => {
            this.$message.success(data)
            this.loading = false
            this.refreshList()
          })
        })
      },
      // 获取数据接口
      getDbInterface (id) {
        this.dialogInterfaceVisible = true
        this.interfaceTable = []
        this.interfaceTable.push({type: 'JSON', url: `/database/datamodel/dataSet/getData/${id}/json`})
        this.interfaceTable.push({type: 'XML', url: `/database/datamodel/dataSet/getData/${id}/xml`})
        this.interfaceTable.push({type: 'TABLE', url: `/database/datamodel/dataSet/getData/${id}/html`})
      },
      resetSearch () {
        this.$refs.searchForm.resetFields()
        this.refreshList()
      }
    }
  }
</script>