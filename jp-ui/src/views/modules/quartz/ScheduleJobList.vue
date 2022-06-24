<template>
  <div class="page">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
            <!-- 搜索框-->
		     <el-form-item prop="name">
                <el-input size="small" v-model="searchForm.name" placeholder="任务名" clearable></el-input>
		     </el-form-item>
          <el-form-item>
            <el-button  type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
            <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
          </el-form-item>
      </el-form>
      <div class="bg-white top">
        <vxe-toolbar :refresh="{query: refreshList}" export print custom>
          <template #buttons>
            <el-button v-if="hasPermission('quartz:scheduleJob:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('quartz:scheduleJob:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()"
                      :disabled="$refs.jobTable && $refs.jobTable.getCheckboxRecords().length !== 1"  plain>修改</el-button>
            <el-button v-if="hasPermission('quartz:scheduleJob:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()"
                      :disabled="$refs.jobTable && $refs.jobTable.getCheckboxRecords().length === 0" plain>删除</el-button>
            <el-button v-if="hasPermission('quartz:scheduleJob:startNow')" type="success" size="small" icon="el-icon-edit-outline" @click="startNow()"
                      :disabled="$refs.jobTable && $refs.jobTable.getCheckboxRecords().length !== 1" plain>立即执行一次</el-button>
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
              ref="jobTable"
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
              <vxe-column  title="任务名" field="name" sortable>
                  <template slot-scope="scope">
                      <el-link  type="primary" :underline="false" v-if="hasPermission('quartz:scheduleJob:edit')" @click="edit(scope.row.id)">{{scope.row.name}}</el-link>
                      <span v-else>{{scope.row.name}}</span>
                  </template>
              </vxe-column>
              <vxe-column  title="任务组" field="jobGroup" sortable> 
                  <template slot-scope="scope">
                      {{ $dictUtils.getDictLabel("schedule_task_group", scope.row.jobGroup, '-') }}
                  </template>
              </vxe-column>
              <vxe-column  title="定时规则" field="cronExpression" sortable></vxe-column>
              <vxe-column  title="启用状态" field="status" sortable>
                  <template slot-scope="scope">
                      {{ $dictUtils.getDictLabel("yes_no", scope.row.status, '-') }}
                  </template>
               </vxe-column>
              <vxe-column  title="通知用户" field="isInfo" sortable>
                <template slot-scope="scope">
                    {{ $dictUtils.getDictLabel("schedule_task_info", scope.row.isInfo, '-') }}
                </template>
              </vxe-column>
              <vxe-column  title="任务类" field="className" sortable></vxe-column>
              <vxe-column  title="描述" field="remarks" sortable></vxe-column>
              <vxe-column title="操作">
                  <template  slot-scope="scope">
                      <el-button v-if="hasPermission('quartz:scheduleJob:resume')" type="text" icon="el-icon-video-play" size="small" @click="start(scope.row.id)">启动</el-button>
                      <el-button v-if="hasPermission('quartz:scheduleJob:stop')" type="text" icon="el-icon-video-pause" size="small" @click="stop(scope.row.id)">暂停</el-button>
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
    <ScheduleJobForm  ref="scheduleJobForm" @refreshDataList="refreshList"></ScheduleJobForm>
  </div>
</template>

<script>
  import ScheduleJobForm from './ScheduleJobForm'
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
      ScheduleJobForm
    },
    activated () {
      this.refreshList()
    },

    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.$http({
          url: '/quartz/scheduleJob/list',
          method: 'get',
          params: {
            'current': this.tablePage.currentPage,
            'size': this.tablePage.pageSize,
            'orders': this.tablePage.orders,
            ...this.searchForm
          }
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
        this.$refs.scheduleJobForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.jobTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.scheduleJobForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.scheduleJobForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.jobTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.$http({
            url: '/quartz/scheduleJob/delete',
            method: 'delete',
            params: {'ids': ids}
          }).then(({data}) => {
            this.$message.success(data)
            this.refreshList()
            this.loading = false
          })
        })
      },
       // 启动
      start (id) {
        id = id || this.$refs.jobTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$confirm(`确定要启动任务吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.$http.post(`/quartz/scheduleJob/resume?id=${id}`).then(({data}) => {
            this.$message.success(data)
            this.refreshList()
            this.loading = false
          })
        })
      },
             // 暂停
      stop (id) {
        id = id || this.$refs.jobTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$confirm(`确定要暂停任务吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.$http.post(`/quartz/scheduleJob/stop?id=${id}`).then(({data}) => {
            this.$message.success(data)
            this.refreshList()
            this.loading = false
          })
        })
      },
             // 立即执行一次
      startNow (id) {
        id = id || this.$refs.jobTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$confirm(`确定要立即执行一次该任务吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.$http.post(`/quartz/scheduleJob/startNow?id=${id}`).then(({data}) => {
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