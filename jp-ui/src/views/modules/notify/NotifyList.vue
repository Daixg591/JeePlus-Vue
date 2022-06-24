<template>
  <div class="page">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
            <!-- 搜索框-->
		     <el-form-item prop="title">
                <el-input size="small" v-model="searchForm.title" placeholder="标题" clearable></el-input>
		     </el-form-item>
          <el-form-item>
            <el-button  type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
            <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
          </el-form-item>
      </el-form>
     <div class="bg-white top">
      <vxe-toolbar :refresh="{query: refreshList}" export print custom>
        <template #buttons>
          <el-button v-if="hasPermission('notify:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
          <el-button v-if="hasPermission('notify:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()" :disabled="$refs.notifyTable && $refs.notifyTable.getCheckboxRecords().length !== 1" plain>修改</el-button>
          <el-button v-if="hasPermission('notify:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.notifyTable && $refs.notifyTable.getCheckboxRecords().length === 0" plain>删除</el-button>
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
            ref="notifyTable"
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
            <vxe-column  title="标题" field="title" sortable>
              <template slot-scope="scope">
                <el-link  type="primary" :underline="false" v-if="hasPermission('notify:edit') && scope.row.status==='0'" @click="edit(scope.row.id)">{{scope.row.title}}</el-link>
                <el-link  type="primary" :underline="false" v-else-if="hasPermission('notify:view')"  @click="view(scope.row.id)">{{scope.row.title}}</el-link>
                <span v-else>{{scope.row.title}}</span>
              </template>
            </vxe-column>
            <vxe-column  title="类型" field="type" sortable> 
                <template slot-scope="scope">
                  {{ $dictUtils.getDictLabel("oa_notify_type", scope.row.type, '-') }}
                </template>
            </vxe-column>
            <vxe-column  title="内容" field="content" sortable></vxe-column>
            <vxe-column  title="附件" field="files" sortable>
              <template slot-scope="scope">
                  <a :href="item" target="_blank" :key="index" v-for="(item, index) in (scope.row.files || '').split('|')">
                      {{decodeURIComponent(item.substring(item.lastIndexOf("/")+1))}}
                  </a>
              </template>
            </vxe-column>
            <vxe-column  title="状态" field="status" sortable>
              <template slot-scope="scope">
                <el-tag type="success" v-if="scope.row.status === '1'"> {{ $dictUtils.getDictLabel("oa_notify_status", scope.row.status, '-') }}</el-tag>
                <el-tag type="danger" v-if="scope.row.status === '0'"> {{ $dictUtils.getDictLabel("oa_notify_status", scope.row.status, '-') }}</el-tag>
              </template>
            </vxe-column>
            <vxe-column  title="查阅状态" field="status" sortable>
               <template slot-scope="scope">
                {{scope.row.readNum + "/" + (parseInt(scope.row.readNum) + parseInt(scope.row.unReadNum))}}
              </template>
            </vxe-column>
             <vxe-column  title="发布者" field="createBy.name" sortable></vxe-column>
             
            <vxe-column title="操作" width="200px" fixed="right" align="center">
                <template  slot-scope="scope">
                  <el-button v-if="hasPermission('notify:view')" type="text" icon="el-icon-view" size="small" @click="view(scope.row.id)">查看</el-button>
                  <el-dropdown  size="small" style=" margin-left: 10px;">
                    <el-button type="text" size="small">
                          更多<i class="el-icon-arrow-down el-icon--right"></i>
                    </el-button>
                    <el-dropdown-menu slot="dropdown" >
                      <el-dropdown-item  v-if="hasPermission('notify:edit') && scope.row.status==='0'"><el-button type="text" icon="el-icon-edit" size="small" @click="edit(scope.row.id)">修改</el-button></el-dropdown-item>
                      <el-dropdown-item v-if="hasPermission('notify:del')">    <el-button  type="text" size="small" icon="el-icon-delete" @click="del(scope.row.id)">删除</el-button></el-dropdown-item>
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
        <!-- 弹窗, 新增 / 修改 -->
    <NotifyForm  ref="notifyForm" @refreshDataList="refreshList"></NotifyForm>
  </div>
  </div>
</template>

<script>
  import NotifyForm from './NotifyForm'
  import NotifyService from '@/api/notify/NotifyService'
  export default {
    data () {
      return {
        searchForm: {
          title: ''
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
      NotifyForm
    },
    notifyService: null,
    created () {
      this.notifyService = new NotifyService()
    },
    activated () {
      this.refreshList()
    },

    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.notifyService.list({
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
        this.$refs.notifyForm.init('add', '')
      },
            // 修改
      edit (id) {
        id = id || this.$refs.notifyTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.notifyForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.notifyForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.notifyTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.notifyService.delete(ids).then(({data}) => {
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