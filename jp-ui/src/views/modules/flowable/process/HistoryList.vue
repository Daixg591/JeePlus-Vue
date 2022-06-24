<template>
      <div class="page bg-white">
        <vxe-toolbar :refresh="{query: refreshList}" export print custom>
          <template #buttons>
              <el-button  type="danger"   size="small" icon="el-icon-delete" @click="del()"
                   :disabled="$refs.historyTable && $refs.historyTable.getCheckboxRecords().length === 0" >删除
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
                ref="historyTable"
                show-header-overflow
                show-overflow
                highlight-hover-row
                :menu-config="{}"
                :print-config="{}"
                :import-config="{}"
                :export-config="{}"
                :data="dataList"
                :checkbox-config="{}">
                <vxe-column type="seq" width="40"></vxe-column>
                <vxe-column type="checkbox"  width="40px"></vxe-column>
                <vxe-column  title="实例名称" field="vars.title" >
                </vxe-column>
                <vxe-column  title="流程状态" field="status" >
                  <template slot-scope="scope">
                    <el-tag :type="scope.row.level"   effect="dark" size="small">{{scope.row.status}} </el-tag>
                  </template>
                </vxe-column>
                <vxe-column  title="流程发起人" field="vars.userName" ></vxe-column>
                <vxe-column  title="发起时间 " field="startTime" >
                  <template slot-scope="scope">
                    <p>{{scope.row.startTime | formatDate}}</p>
                  </template>
                </vxe-column>
                <vxe-column  title="结束时间" field="endTime" >
                  <template slot-scope="scope">
                    <p class="text-grey">{{scope.row.endTime | formatDate}}</p>
                  </template>
                </vxe-column>
                <vxe-column title="操作" width="150px" type="html" fixed="right" align="center">
                  <template slot-scope="scope">
                      <el-button  type="text" size="small"
                              @click="detail(scope.row)">历史
                    </el-button>
                    <el-button  type="text" size="small"
                              @click="trace(scope.row)">流程图
                    </el-button>
                    <el-button  type="text" size="small"
                              @click="del(scope.row.id)">删除
                    </el-button>

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
          <el-dialog
          title="查看进度"
          :close-on-click-modal="true"
          :visible.sync="visible"
          v-dialogDrag
          width="70%"
          height="600px">
          <flow-chart ref="preview" :processInstanceId="processInstanceId"></flow-chart>
        </el-dialog>
        </div>
      </div>
      
</template>

<script>
  // import FlowChart from '../modeler/FlowChart'
  import pick from 'lodash.pick'
  import TaskService from '@/api/flowable/TaskService'
  import ProcessService from '@/api/flowable/ProcessService'
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
        visible: false,
        processInstanceId: ''
      }
    },
    taskService: null,
    processService: null,
    created () {
      this.taskService = new TaskService()
      this.processService = new ProcessService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.processService.historyListData({
          'current': this.tablePage.currentPage,
          'size': this.tablePage.pageSize,
          'orders': this.tablePage.orders
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
      trace (row) {
        this.processInstanceId = row.processInstanceId
        this.visible = true
        this.$nextTick(() => {
          this.$refs.preview.init()
        })
      },
      detail (row) {
        this.taskService.getTaskDef({
          procInsId: row.processInstanceId,
          procDefId: row.processDefinitionId
        }).then(({data}) => {
          this.$router.push({
            path: '/flowable/task/TaskFormDetail',
            query: {readOnly: true, title: row.vars.title, formTitle: row.vars.title, ...pick(data, 'formType', 'formUrl', 'procDefKey', 'taskDefKey', 'procInsId', 'procDefId', 'taskId', 'status', 'title', 'businessId')}
          })
        })
      },
      // 撤销申请
      del (id) {
        let ids = id || this.$refs.historyTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定要删除历史流程吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.processService.deleteAllProcIns(ids).then(({data}) => {
            this.refreshList()
            this.$message.success({dangerouslyUseHTMLString: true,
              message: data})
          })
        })
      }
    }
  }
</script>