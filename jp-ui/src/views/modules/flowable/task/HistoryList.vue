<template>
  <div class="page">
      <el-form size="small" :inline="true"  class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
        <el-form-item label="完成时间" prop="searchDates">
          <el-date-picker
            v-model="searchDates"
            type="daterange"
            size="small"
            align="right"
            value-format="yyyy-MM-dd hh:mm:ss"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button  type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
          <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
        </el-form-item>
      </el-form>
      <div class="top bg-white">
        <vxe-toolbar :refresh="{query: refreshList}" export print custom></vxe-toolbar>
        <div style="height: calc(100% - 80px);">
            <vxe-table
                border="inner"
                auto-resize
                resizable
                height="auto"
                :loading="loading"
                size="small"
                ref="todoTable"
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
                <vxe-column  title="任务" field="name" >
                    <template slot-scope="scope">
                      {{scope.row.name}} 
                      <el-button v-if="scope.row.back" type="warning" size="mini" @click="callback(scope.row)">撤销</el-button>
                    </template>
                </vxe-column>
                 <vxe-column  title="实例标题" field="vars.title" > </vxe-column>
                <vxe-column  title="流程名称" field="processDefinitionName" > </vxe-column>
                <vxe-column  title="办理状态" field="status" >
                   <template slot-scope="scope">
                      <el-tag :type="scope.row.level"   effect="dark" size="small">{{scope.row.status}} </el-tag>
                  </template>
                </vxe-column>
                <vxe-column  title="流程发起人" field="vars.userName" ></vxe-column>
                <vxe-column
                  prop="endTime"
                  title="完成时间">
                  <template slot-scope="scope">
                    {{scope.row.endTime | formatDate}}
                  </template>
                </vxe-column>
                <vxe-column title="操作" width="100px" fixed="right" align="center">
                  <template slot-scope="scope">
                     <el-button  type="text" size="small" @click="detail(scope.row)">历史</el-button>
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
  </div>
</template>

<script>
  // import FlowChart from '../modeler/FlowChart'
  import pick from 'lodash.pick'
  import TaskService from '@/api/flowable/TaskService'
  export default {
    data () {
      return {
        searchForm: {
          beginDate: '',
          endDate: ''
        },
        searchDates: '',
        dataList: [],
        tablePage: {
          total: 0,
          currentPage: 1,
          pageSize: 10,
          orders: []
        },
        loading: false,
        visible: false,
        pickerOptions: {
          shortcuts: [{
            text: '最近一周',
            onClick (picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '最近一个月',
            onClick (picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '最近三个月',
            onClick (picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
              picker.$emit('pick', [start, end])
            }
          }]
        }
      }
    },
    taskService: null,
    created () {
      this.taskService = new TaskService()
    },
    activated () {
      this.refreshList()
    },
    watch: {
      searchDates () {
        if (this.searchDates) {
          this.searchForm.beginDate = this.searchDates[0]
          this.searchForm.endDate = this.searchDates[1]
        } else {
          this.searchForm.beginDate = ''
          this.searchForm.endDate = ''
        }
      }
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.taskService.historicList({
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
      detail (row) {
        this.taskService.getTaskDef({
          taskDefKey: row.taskDefinitionKey,
          procInsId: row.processInstanceId,
          procDefId: row.processDefinitionId
        }).then(({data}) => {
          this.$router.push({
            path: '/flowable/task/TaskFormDetail',
            query: {readOnly: true, taskId: row.executionId, title: `${row.processDefinitionName}【${row.name}】`, formTitle: `${row.processDefinitionName}`, ...pick(data, 'formType', 'formUrl', 'procDefKey', 'taskDefKey', 'procInsId', 'procDefId', 'taskId', 'status', 'title', 'businessId')}
          })
        })
      },
    // 取回
      callback (row) {
        console.log(row)
        this.$confirm(`确定撤销该已办任务吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.taskService.callback({'processInstanceId': row.processInstanceId,
            'preTaskDefKey': row.taskDefinitionKey,
            'preTaskId': row.id,
            'currentTaskId': row.currentTask.id,
            'currentTaskDefKey': row.currentTask.taskDefinitionKey
          }).then(({data}) => {
            this.loading = false
            this.$message.success(data)
            this.refreshList()
          })
        })
      },
      resetSearch () {
        this.searchDates = ''
        this.$refs.searchForm.resetFields()
        this.$nextTick(() => {
          this.refreshList()
        })
      }
    }
  }
</script>
