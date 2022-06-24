<template>
  <div class="page">
      <el-form size="small" :inline="true"  class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
        <el-form-item label="创建时间" prop="searchDates">
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
                ref="applyTable"
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
                <vxe-column  title="流程标题" field="vars.title" >
                </vxe-column>
                <vxe-column  title="流程名称" field="processDefinitionName" > </vxe-column>
                <vxe-column  title="当前节点" field="taskName" ></vxe-column>
                <vxe-column  title="流程状态" field="status" >
                  <template slot-scope="scope">
                    <el-tag :type="scope.row.level"   effect="dark" size="small">{{scope.row.status}} </el-tag>
                  </template>
                </vxe-column>
                <vxe-column
                  field="startTime"
                  type="html"
                  title="发起时间">
                  <template slot-scope="scope">
                    <span>{{scope.row.startTime | formatDate}}</span>
                  </template>
                </vxe-column>
                <vxe-column
                  field="endTime"
                  type="html"
                  title="结束时间">
                  <template slot-scope="scope">
                    <span class="text-grey">{{scope.row.endTime | formatDate}}</span>
                  </template>
                </vxe-column>
                <vxe-column title="操作" width="150px" type="html" fixed="right" align="center">
                  <template slot-scope="scope">
                      <el-button  type="text" size="small" @click="detail(scope.row)">历史</el-button>
                      <el-dropdown  size="small" style=" margin-left: 10px;">
                          <el-button type="text" size="small">
                                更多<i class="el-icon-arrow-down el-icon--right"></i>
                          </el-button>
                          <el-dropdown-menu slot="dropdown" >
                            <el-dropdown-item v-if="scope.row.code === 1" ><el-button type="text"  size="small" @click="urge(scope.row)">催办</el-button></el-dropdown-item>
                            <el-dropdown-item v-if="scope.row.code === 1"><el-button type="text"  size="small" @click="revoke(scope.row)">撤销</el-button></el-dropdown-item>
                            <el-dropdown-item v-if="scope.row.code === 3 || scope.row.code === 4"><el-button  type="text" color="red"  size="small" @click="restart(scope.row)">编辑</el-button></el-dropdown-item>
                          </el-dropdown-menu>
                      </el-dropdown>
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
       <el-dialog
        title="查看流程历史"
        :close-on-click-modal="true"
        :visible.sync="visible"
        v-dialogDrag
        height="600px">
          <iframe :src="processPhotoUrl" frameborder="0" scrolling="auto" width="100%" height="600px"></iframe>
        </el-dialog>
        <urge-form ref="urgeForm"></urge-form>
  </div>
</template>

<script>
  // import FlowChart from '../modeler/FlowChart'
  import pick from 'lodash.pick'
  import UrgeForm from './UrgeForm'
  import TaskService from '@/api/flowable/TaskService'
  import ProcessService from '@/api/flowable/ProcessService'
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
        processPhotoUrl: '',
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
    proccessService: null,
    created () {
      this.proccessService = new ProcessService()
      this.taskService = new TaskService()
    },
    activated () {
      this.refreshList()
    },
    components: {
      UrgeForm
      // FlowChart
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
        this.taskService.myApplyedList({
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
      trace (row) {
        this.processPhotoUrl = `${this.$http.BASE_URL}/flowable/task/trace/photo/${row.processInstanceId}`
        this.visible = true
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
      // 重新填写
      restart (row) {
              // 读取流程表单
        this.taskService.getTaskDef({
          procInsId: row.processInstanceId,
          procDefId: row.processDefinitionId
        }).then(({data}) => {
          this.$router.push({
            path: '/flowable/task/TaskFormEdit',
            query: {status: 'start', title: row.vars.title, formTitle: row.vars.title, ...pick(data, 'formType', 'formUrl', 'procDefKey', 'taskDefKey', 'procInsId', 'procDefId', 'taskId', 'status', 'title', 'businessId')}
          })
        })
      },
          // 撤销申请
      revoke (row) {
        this.$confirm(`确定要撤销该流程吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.pro.revokeProcIns(row.processInstanceId).then(({data}) => {
            this.$message.success(data)
            this.refreshList()
          })
        })
      },
      urge (row) {
        this.$refs.urgeForm.init()
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