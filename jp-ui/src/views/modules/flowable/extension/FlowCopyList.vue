<template>
      <div class="page bg-white">
        <vxe-toolbar :refresh="{query: refreshList}" export print custom>
          <template #buttons>
              <el-button  type="danger"  v-if="hasPermission('extension:flowCopy:del')" size="small" icon="el-icon-delete" @click="del()"
                   :disabled="$refs.flowCopyTable && $refs.flowCopyTable.getCheckboxRecords().length === 0" >删除
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
                ref="flowCopyTable"
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
                <vxe-column  title="实例标题" field="procInsName" sortable></vxe-column>
                <vxe-column  title="抄送日期" field="createDate" sortable> </vxe-column>
                <vxe-column  title="抄送发起人" field="createBy.name" sortable></vxe-column>
                <vxe-column title="操作" width="150px" type="html" fixed="right" align="center">
                  <template slot-scope="scope">
                      <el-button v-if="hasPermission('extension:flowCopy:view')" type="text" icon="el-icon-view" size="mini" @click="detail(scope.row)">查阅审批</el-button>
                      <el-button v-if="hasPermission('extension:flowCopy:del')" type="text" size="mini" icon="el-icon-delete" @click="del(scope.row.id)">删除</el-button>
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
      
</template>

<script>
  // import FlowChart from '../modeler/FlowChart'
  import pick from 'lodash.pick'
  import TaskService from '@/api/flowable/TaskService'
  import FlowCopyService from '@/api/flowable/FlowCopyService'
  export default {
    data () {
      return {
        tablePage: {
          total: 0,
          currentPage: 1,
          pageSize: 10,
          orders: []
        },
        dataList: [],
        loading: false,
        visible: false
      }
    },
    taskService: null,
    flowCopyService: null,
    created () {
      this.taskService = new TaskService()
      this.flowCopyService = new FlowCopyService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.flowCopyService.list({
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
      trace (row) {
        this.processInstanceId = row.processInstanceId
        this.visible = true
        this.$nextTick(() => {
          this.$refs.preview.init()
        })
      },
      detail (row) {
        this.taskService.getTaskDef({
          procInsId: row.procInsId,
          procDefId: row.procDefId
        }).then(({data}) => {
          this.$router.push({
            path: '/flowable/task/TaskFormDetail',
            query: {readOnly: true, title: row.procInsName, formTitle: row.procInsName, ...pick(data, 'formType', 'formUrl', 'procDefKey', 'taskDefKey', 'procInsId', 'procDefId', 'taskId', 'status', 'title', 'businessId')}
          })
        })
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.flowCopyTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定要删除历史流程吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.flowCopyService.delete(ids).then(({data}) => {
            this.refreshList()
            this.$message.success({dangerouslyUseHTMLString: true,
              message: data})
          })
        })
      }
  
    }
  }
</script>