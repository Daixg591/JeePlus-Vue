<template>
  <div class="page">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
            <!-- 搜索框-->
		     <el-form-item prop="name">
                <el-input size="small" v-model="searchForm.name" placeholder="名称" clearable></el-input>
		     </el-form-item>
          <el-form-item>
            <el-button  type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
            <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
          </el-form-item>
      </el-form>
      <div class="top bg-white">
        <vxe-toolbar :refresh="{query: refreshList}" export print custom>
          <template #buttons>
            <el-button v-if="hasPermission('extension:condition:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('extension:condition:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()"  :disabled="$refs.conditionTable && $refs.conditionTable.getCheckboxRecords().length !== 1">修改</el-button>
            <el-button v-if="hasPermission('extension:condition:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()"  :disabled="$refs.conditionTable && $refs.conditionTable.getCheckboxRecords().length !== 0">删除</el-button>
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
            ref="conditionTable"
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
            <vxe-column  title="名称" field="name" sortable>
                <template slot-scope="scope">
                  <el-link  type="primary" :underline="false" v-if="hasPermission('extension:button:edit')" @click="edit(scope.row.id)">{{scope.row.name}}</el-link>
                  <el-link  type="primary" :underline="false" v-else-if="hasPermission('extension:button:view')"  @click="view(scope.row.id)">{{scope.row.name}}</el-link>
                  <span v-else>{{scope.row.name}}</span>
                </template>
            </vxe-column>
            <vxe-column  title="表达式" field="expression" sortable> </vxe-column>
            <vxe-column  title="备注" field="remarks" sortable> </vxe-column>
            <vxe-column title="操作" width="200px" fixed="right" align="center">
              <template  slot-scope="scope">
                <el-button v-if="hasPermission('extension:condition:view')" type="text" icon="el-icon-view" size="mini" @click="view(scope.row.id)">查看</el-button>
                <el-button v-if="hasPermission('extension:condition:edit')" type="text" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)">修改</el-button>
                <el-button v-if="hasPermission('extension:condition:del')" type="text" size="mini" icon="el-icon-delete" @click="del(scope.row.id)">删除</el-button>
              </template>
            </vxe-column>
          </vxe-table>
        </div>
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
    <ConditionForm  ref="conditionForm" @refreshDataList="refreshList"></ConditionForm>
  </div>
</template>

<script>
  import ConditionForm from './ConditionForm'
  import ConditionService from '@/api/flowable/ConditionService'
  export default {
    data () {
      return {
        searchForm: {
          name: ''
        },
        dataList: [],
        tablePage: {
          total: 0,
          currentPage: 1,
          pageSize: 10,
          orders: []
        },
        loading: false
      }
    },
    components: {
      ConditionForm
    },
    conditionService: null,
    created () {
      this.conditionService = new ConditionService()
    },
    activated () {
      this.refreshList()
    },

    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.conditionService.list({
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
        this.$refs.conditionForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.conditionTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.conditionForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.conditionForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.conditionTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.conditionService.delete(ids).then(({data}) => {
            this.loading = false
            this.$message.success(data)
            this.refreshList()
          })
        })
      },
      resetSearch () {
        this.$refs.searchForm.resetFields()
        this.refreshList()
      }
    }
  }
</script>