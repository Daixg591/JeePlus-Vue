<template>
    <div class="page">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
            <!-- 搜索框-->
         <el-form-item prop="user.id">
            <user-select :limit='1' size="small" placeholder="请选择员工姓名" :value="searchForm.user.id" @getValue='(value) => {searchForm.user.id=value}'></user-select>
         </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
            <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
          </el-form-item>
      </el-form>

     <div class="bg-white top">
        <vxe-toolbar :refresh="{query: refreshList}" export print custom>
          <template #buttons>
            <el-button v-if="hasPermission('test:activiti:testActivitiExpense:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('test:activiti:testActivitiExpense:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()" :disabled="$refs.testActivitiExpense && $refs.testActivitiExpense.getCheckboxRecords().length !== 1" plain>修改</el-button>
            <el-button v-if="hasPermission('test:activiti:testActivitiExpense:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.testActivitiExpense && $refs.testActivitiExpense.getCheckboxRecords().length === 0" plain>删除</el-button>
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
            ref="testActivitiExpenseTable"
            show-header-overflow
            show-overflow
            highlight-hover-row
            :menu-config="{}"
            :print-config="{}"
            :import-config="{}"
            :export-config="{}"
            @sort-change="sortChangeHandle"
            :sort-config="{remote:true}"
            :expand-config="{accordion: true, lazy: true,loadMethod:detail}"
            :data="dataList"
            :checkbox-config="{}">
            <vxe-column type="seq" width="40"></vxe-column>
            <vxe-column type="checkbox"  width="40px"></vxe-column>
            <vxe-column type="expand" width="80" >
                <template #content="{ row }">
                    <el-tabs>
                    </el-tabs>
                </template>
            </vxe-column>
      <vxe-column
        field="user.name"
        sortable
        title="员工姓名">
            <template slot-scope="scope">
              <el-link  type="primary" :underline="false" v-if="hasPermission('test:activiti:testActivitiExpense:edit')" @click="edit(scope.row.id)">{{scope.row.user && scope.row.user.name}}</el-link>
              <el-link  type="primary" :underline="false" v-else-if="hasPermission('test:activiti:testActivitiExpense:view')"  @click="view(scope.row.id)">{{scope.row.user &&  scope.row.user.name}}</el-link>
              <span v-else>{{scope.row.user && scope.row.user.name}}</span>
            </template>
      </vxe-column>
    <vxe-column
        field="updateDate"
        sortable
        title="更新时间">
      </vxe-column>
    <vxe-column
        field="remarks"
        sortable
        title="备注信息">
      </vxe-column>
      <vxe-column
        fixed="right"
        align="center"
        width="200"
        title="操作">
        <template  slot-scope="scope">
          <el-button v-if="hasPermission('test:activiti:testActivitiExpense:view')" type="text" icon="el-icon-view" size="small" @click="view(scope.row.id)">查看</el-button>
          <el-button v-if="hasPermission('test:activiti:testActivitiExpense:edit')" type="text" icon="el-icon-edit" size="small" @click="edit(scope.row.id)">修改</el-button>
          <el-button v-if="hasPermission('test:activiti:testActivitiExpense:del')" type="text"  icon="el-icon-delete" size="small" @click="del(scope.row.id)">删除</el-button>
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
        <!-- 弹窗, 新增 / 修改 -->
    <TestActivitiExpenseForm  ref="testActivitiExpenseForm" @refreshDataList="refreshList"></TestActivitiExpenseForm>
  </div>
</template>

<script>
  import TestActivitiExpenseForm from './TestActivitiExpenseForm'
  import TestActivitiExpenseService from '@/api/test/activiti/TestActivitiExpenseService'
  import UserSelect from '@/components/userSelect'
  export default {
    data () {
      return {
        searchForm: {
          user: {
            id: ''
          }
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
      UserSelect,
      TestActivitiExpenseForm
    },
    testActivitiExpenseService: null,
    created () {
      this.testActivitiExpenseService = new TestActivitiExpenseService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.testActivitiExpenseService.list({
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
        this.$refs.testActivitiExpenseForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.testActivitiExpenseTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.testActivitiExpenseForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.testActivitiExpenseForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.testActivitiExpenseTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.testActivitiExpenseService.delete(ids).then(({data}) => {
            this.$message.success(data)
            this.refreshList()
            this.loading = false
          })
        })
      },
        // 查看详情
      detail ({row}) {
        return new Promise(resolve => {
          this.testActivitiExpenseService.queryById(row.id).then(({data}) => {
            this.dataList.forEach((item, index) => {
              if (item.id === row.id) {
              }
            })
            resolve()
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

