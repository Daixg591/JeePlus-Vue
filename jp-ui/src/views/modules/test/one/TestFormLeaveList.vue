<template>
    <div class="page">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
            <!-- 搜索框-->
         <el-form-item prop="office.id">
            <SelectTree
                  ref="office.id"
                  :props="{
                      value: 'id',             // ID字段名
                      label: 'name',         // 显示名称
                      children: 'children'    // 子级字段名
                    }"
                  placeholder="请选择归属部门"
                  size="small"
                  url="/sys/office/treeData?type=2"
                  :value="searchForm.office.id"
                  :clearable="true"
                  :accordion="true"
                  @getValue="(value) => {searchForm.office.id=value}"/>
         </el-form-item>
         <el-form-item prop="user.id">
            <user-select :limit='1' size="small" placeholder="请选择员工" :value="searchForm.user.id" @getValue='(value) => {searchForm.user.id=value}'></user-select>
         </el-form-item>
         <el-form-item prop="area.id">
                <SelectTree
                  ref="area.id"
                  :props="{
                      value: 'id',             // ID字段名
                      label: 'name',         // 显示名称
                      children: 'children'    // 子级字段名
                    }"
                  placeholder="请选择归属区域"
                  size="small"
                  url="/sys/area/treeData"
                  :value="searchForm.area.id"
                  :clearable="true"
                  :accordion="true"
                  @getValue="(value) => {searchForm.area.id=value}"/>
         </el-form-item>
         <el-form-item prop="beginDate">
               <el-date-picker
                    v-model="searchForm.beginDate"
                    type="daterange"
                    size="small"
                    align="right"
                    value-format="yyyy-MM-dd hh:mm:ss"
                    unlink-panels
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
                 </el-date-picker>
         </el-form-item>
         <el-form-item prop="endDate">
               <el-date-picker
                    v-model="searchForm.endDate"
                    type="daterange"
                    size="small"
                    align="right"
                    value-format="yyyy-MM-dd hh:mm:ss"
                    unlink-panels
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
                 </el-date-picker>
         </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
            <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
          </el-form-item>
      </el-form>

     <div class="bg-white top">
        <vxe-toolbar :refresh="{query: refreshList}" export print custom>
          <template #buttons>
            <el-button v-if="hasPermission('test:one:testFormLeave:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('test:one:testFormLeave:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()" :disabled="$refs.testFormLeave && $refs.testFormLeave.getCheckboxRecords().length !== 1" plain>修改</el-button>
            <el-button v-if="hasPermission('test:one:testFormLeave:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.testFormLeave && $refs.testFormLeave.getCheckboxRecords().length === 0" plain>删除</el-button>
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
            ref="testFormLeaveTable"
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
        field="office.name"
        sortable
        title="归属部门">
            <template slot-scope="scope">
              <el-link  type="primary" :underline="false" v-if="hasPermission('test:one:testFormLeave:edit')" @click="edit(scope.row.id)">{{scope.row.office && scope.row.office.name}}</el-link>
              <el-link  type="primary" :underline="false" v-else-if="hasPermission('test:one:testFormLeave:view')"  @click="view(scope.row.id)">{{scope.row.office &&  scope.row.office.name}}</el-link>
              <span v-else>{{scope.row.office && scope.row.office.name}}</span>
            </template>
      </vxe-column>
      <vxe-column
        field="user.name"
        sortable
        title="员工">
            <template slot-scope="scope">
                {{scope.row.user && scope.row.user.name}}
            </template>
      </vxe-column>
      <vxe-column
        field="area.name"
        sortable
        title="归属区域">
            <template slot-scope="scope">
                {{scope.row.area && scope.row.area.name}}
            </template>
      </vxe-column>
    <vxe-column
        field="beginDate"
        sortable
        title="请假开始日期">
      </vxe-column>
    <vxe-column
        field="endDate"
        sortable
        title="请假结束日期">
      </vxe-column>
      <vxe-column
        fixed="right"
        align="center"
        width="200"
        title="操作">
        <template  slot-scope="scope">
          <el-button v-if="hasPermission('test:one:testFormLeave:view')" type="text" icon="el-icon-view" size="small" @click="view(scope.row.id)">查看</el-button>
          <el-button v-if="hasPermission('test:one:testFormLeave:edit')" type="text" icon="el-icon-edit" size="small" @click="edit(scope.row.id)">修改</el-button>
          <el-button v-if="hasPermission('test:one:testFormLeave:del')" type="text"  icon="el-icon-delete" size="small" @click="del(scope.row.id)">删除</el-button>
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
    <TestFormLeaveForm  ref="testFormLeaveForm" @refreshDataList="refreshList"></TestFormLeaveForm>
  </div>
</template>

<script>
  import TestFormLeaveForm from './TestFormLeaveForm'
  import TestFormLeaveService from '@/api/test/one/TestFormLeaveService'
  import SelectTree from '@/components/treeSelect/treeSelect.vue'
  import UserSelect from '@/components/userSelect'
  export default {
    data () {
      return {
        searchForm: {
          office: {
            id: ''
          },
          user: {
            id: ''
          },
          area: {
            id: ''
          },
          beginDate: '',
          endDate: ''
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
      SelectTree,
      UserSelect,
      TestFormLeaveForm
    },
    testFormLeaveService: null,
    created () {
      this.testFormLeaveService = new TestFormLeaveService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.testFormLeaveService.list({
          'current': this.tablePage.currentPage,
          'size': this.tablePage.pageSize,
          'orders': this.tablePage.orders,
          beginBeginDate: this.searchForm.beginDate[0],
          endBeginDate: this.searchForm.beginDate[1],
          ...this.lodash.omit(this.searchForm, 'beginDate')
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
        this.$refs.testFormLeaveForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.testFormLeaveTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.testFormLeaveForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.testFormLeaveForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.testFormLeaveTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.testFormLeaveService.delete(ids).then(({data}) => {
            this.$message.success(data)
            this.refreshList()
            this.loading = false
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

