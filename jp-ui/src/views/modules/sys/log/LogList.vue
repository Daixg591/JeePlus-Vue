<template>
    <div class="jp-common-layout page">
      <div class="jp-common-layout-left">
      <div class="jp-common-el-tree-scrollbar el-scrollbar">
      <div class="el-scrollbar__wrap">
        <div class="el-scrollbar__view">
        <el-menu @select="changeLog" style="margin-top:10px">
          <el-menu-item index="1">
            <i class="el-icon-setting"></i>
            <span slot="title">登陆日志</span>
          </el-menu-item>
          <el-menu-item index="2">
            <i class="el-icon-setting"></i>
            <span slot="title">访问日志</span>
          </el-menu-item>
          <el-menu-item index="3">
            <i class="el-icon-setting"></i>
            <span slot="title">异常日志</span>
          </el-menu-item>
        </el-menu>
        </div>
      </div>
        </div>
      </div>
      <div class="jp-common-layout-center jp-flex-main">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
        <el-form-item prop="searchDates">
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
        <el-form-item prop="title">
            <el-input size="small" v-model="searchForm.title" placeholder="操作菜单" clearable></el-input>
        </el-form-item>
        <el-form-item prop="createBy.name">
            <el-input size="small" v-model="searchForm.createBy.name" placeholder="操作用户" clearable></el-input>
        </el-form-item>
        <el-form-item prop="requestUri">
            <el-input size="small" v-model="searchForm.requestUri" placeholder="URI" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button  type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
          <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
        </el-form-item>
      </el-form>
      <div class="bg-white top">

      <vxe-toolbar :refresh="{query: refreshList}" export print custom>
        <template #buttons>
          <el-button v-if="hasPermission('sys:log:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.logTable && $refs.logTable.getCheckboxRecords().length === 0" plain>删除</el-button>
          <el-button v-if="hasPermission('sys:log:del')" type="danger"   size="small" icon="el-icon-delete" @click="empty()"  plain>清空</el-button>
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
            ref="logTable"
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
            <vxe-column type="expand" width="80"  v-if="searchForm.type == '3'">
              <template #content="{ row }">
                    <el-form size="small" label-position="left" inline class="demo-table-expand">
                      <el-form-item label="异常信息：">
                        <p v-html="row.exception" style="color:red; white-space: pre-line;"></p>
                      </el-form-item>
                    </el-form>
              </template>
            </vxe-column>
          <vxe-column width="150"  title="访问功能" field="title" sortable></vxe-column>
   
          <vxe-column width="150"  title="URI" field="requestUri" sortable></vxe-column>
          <vxe-column width="100"  title="提交方式" field="requestType" sortable></vxe-column>
          <vxe-column width="150"  title="访问方法" field="method" sortable></vxe-column>
          <vxe-column width="110"  title="耗时(毫秒)" field="recordTime" sortable></vxe-column>
          <vxe-column width="150"  title="请求参数" field="params" sortable></vxe-column>
          <vxe-column width="150"  title="返回结果" field="result" sortable></vxe-column>
          <vxe-column width="150"  title="公司" field="createBy.companyDTO.name" sortable></vxe-column>
          <vxe-column width="100"  title="部门" field="createBy.officeDTO.name" sortable></vxe-column>
          <vxe-column width="90"  fixed="right" title="用户" field="createBy.name" sortable></vxe-column>
          <vxe-column width="85"  fixed="right" title="IP地址" field="remoteAddr" sortable></vxe-column>
          <vxe-column width="170" fixed="right" title="访问时间" field="createDate" sortable>
              <template slot-scope="scope">
                <p>{{scope.row.createDate | formatDate}}</p>
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
    </div>
</template>

<script>
  import LogService from '@/api/sys/LogService'
  export default {
    data () {
      return {
        searchForm: {
          type: '1',
          title: '',
          createBy: {
            name: ''
          },
          requestUri: '',
          beginCreateDate: '',
          endCreateDate: ''
        },
        searchDates: [],
        dataList: [],
        tablePage: {
          total: 0,
          currentPage: 1,
          pageSize: 10,
          orders: [{column: 'create_date', asc: false}]
        },
        loading: false,
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
    logService: null,
    created () {
      this.logService = new LogService()
    },
    activated () {
      this.refreshList()
    },
    watch: {
      searchDates () {
        if (this.searchDates) {
          this.searchForm.beginCreateDate = this.searchDates[0]
          this.searchForm.endCreateDate = this.searchDates[1]
        } else {
          this.searchForm.beginCreateDate = ''
          this.searchForm.endCreateDate = ''
        }
      }
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.logService.list({
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
      changeLog (index) {
        this.searchForm.type = index
        this.refreshList()
      },
      // 当前页
      currentChangeHandle ({ currentPage, pageSize }) {
        this.tablePage.currentPage = currentPage
        this.tablePage.pageSize = pageSize
        this.refreshList()
      },
      // 排序
      sortChangeHandle (column) {
        if (column.property === 'createBy.companyDTO.name') {
          column.property = 'c.name'
        } else if (column.property === 'createBy.officeDTO.name') {
          column.property = 'o.name'
        } else if (column.property === 'createBy.name') {
          column.property = 'u.name'
        }
        this.tablePage.orders = []
        if (column.order != null) {
          this.tablePage.orders.push({column: this.$utils.toLine(column.property), asc: column.order === 'asc'})
        }
        this.refreshList()
      },
          // 删除
      del () {
        let ids = this.$refs.logTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.logService.delete(ids).then(({data}) => {
            this.$message.success({dangerouslyUseHTMLString: true,
              message: data})
            this.refreshList()
          })
        })
      },
      empty () {
        this.$confirm(`确定清空日志吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.logService.empty().then(({data}) => {
            this.$message.success({dangerouslyUseHTMLString: true,
              message: data})
            this.refreshList()
          })
        })
      },
      resetSearch () {
        this.searchDates = []
        this.$refs.searchForm.resetFields()
        this.$nextTick(() => {
          this.refreshList()
        })
      }
    }
  }
</script>
