<template>
  <div class="page">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
            <!-- 搜索框-->
         <el-form-item prop="name">
                <el-input size="small" v-model="searchForm.name" placeholder="岗位名称" clearable></el-input>
         </el-form-item>
         <el-form-item prop="code">
                <el-input size="small" v-model="searchForm.code" placeholder="岗位编码" clearable></el-input>
         </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
            <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
          </el-form-item>
      </el-form>
     <div class="bg-white top">
        <vxe-toolbar :refresh="{query: refreshList}" export print custom>
          <template #buttons>
            <el-button v-if="hasPermission('sys:post:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('sys:post:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()" :disabled="$refs.postTable && $refs.postTable.getCheckboxRecords().length !== 1" plain>修改</el-button>
            <el-button v-if="hasPermission('sys:post:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.postTable && $refs.postTable.getCheckboxRecords().length === 0" plain>删除</el-button>
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
                ref="postTable"
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
                <vxe-column  title="岗位名称" field="name" sortable>
                    <template slot-scope="scope">
                        <el-link  type="primary" :underline="false" v-if="hasPermission('sys:post:edit')" @click="edit(scope.row.id)">{{scope.row.name}}</el-link>
                        <el-link  type="primary" :underline="false" v-else-if="hasPermission('sys:post:view')"  @click="view(scope.row.id)">{{scope.row.name}}</el-link>
                        <span v-else>{{scope.row.name}}</span>
                    </template>
                </vxe-column>
                <vxe-column  title="岗位编码" field="code" sortable> </vxe-column>
                <vxe-column  title="岗位类型" field="type" sortable>
                  <template slot-scope="scope">
                      {{ $dictUtils.getDictLabel("sys_post_type", scope.row.type, '-') }}
                  </template>
                </vxe-column>
                <vxe-column  title="是否可用" field="status" sortable>
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.status==='1'" type="primary">
                      {{ $dictUtils.getDictLabel("yes_no", scope.row.status, '-') }}
                    </el-tag>
                    <el-tag v-else type="danger">
                      {{ $dictUtils.getDictLabel("yes_no", scope.row.status, '-') }}
                    </el-tag>
                  </template>
                </vxe-column>
                <vxe-column  title="岗位排序" field="sort" sortable></vxe-column>
                <vxe-column  title="备注信息" field="remarks" sortable></vxe-column>
                <vxe-column title="操作" width="200px" fixed="right" align="center">
                    <template  slot-scope="scope">
                      <el-button v-if="hasPermission('sys:post:view')" type="text" icon="el-icon-view" size="small" @click="view(scope.row.id)">查看</el-button>
                      <el-button v-if="hasPermission('sys:post:edit')" type="text" icon="el-icon-edit" size="small" @click="edit(scope.row.id)">修改</el-button>
                      <el-button v-if="hasPermission('sys:post:del')" type="text"  icon="el-icon-delete" size="small" @click="del(scope.row.id)">删除</el-button>
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
    <PostForm  ref="postForm" @refreshDataList="refreshList"></PostForm>
  </div>
</template>

<script>
  import PostForm from './PostForm'
  import PostService from '@/api/sys/PostService'
  export default {
    data () {
      return {
        searchForm: {
          name: '',
          code: ''
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
    postService: null,
    created () {
      this.postService = new PostService()
    },
    components: {
      PostForm
    },
    activated () {
      this.refreshList()
    },

    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.postService.list({
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
        this.$refs.postForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.postTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.postForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.postForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.postTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.postService.delete(ids).then(({data}) => {
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