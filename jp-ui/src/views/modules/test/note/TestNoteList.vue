<template>
    <div class="page">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
            <!-- 搜索框-->
         <el-form-item prop="title">
                <el-input size="small" v-model="searchForm.title" placeholder="标题" clearable></el-input>
         </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
            <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
          </el-form-item>
      </el-form>

     <div class="bg-white top">
        <vxe-toolbar :refresh="{query: refreshList}" export print custom>
          <template #buttons>
            <el-button v-if="hasPermission('test:note:testNote:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('test:note:testNote:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()" :disabled="$refs.testNote && $refs.testNote.getCheckboxRecords().length !== 1" plain>修改</el-button>
            <el-button v-if="hasPermission('test:note:testNote:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.testNote && $refs.testNote.getCheckboxRecords().length === 0" plain>删除</el-button>
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
            ref="testNoteTable"
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
        field="title"
        sortable
        title="标题">
            <template slot-scope="scope">
              <el-link  type="primary" :underline="false" v-if="hasPermission('test:note:testNote:edit')" @click="edit(scope.row.id)">{{scope.row.title}}</el-link>
              <el-link  type="primary" :underline="false" v-else-if="hasPermission('test:note:testNote:view')"  @click="view(scope.row.id)">{{scope.row.title}}</el-link>
              <span v-else>{{scope.row.title}}</span>
            </template>
      </vxe-column>
      <vxe-column
        field="contents1"
        sortable
        title="富文本1">
        <template slot-scope="scope">
            <p v-html="$utils.unescapeHTML(scope.row.contents1)"></p>
        </template>
      </vxe-column>
      <vxe-column
        field="contents2"
        sortable
        title="富文本2">
        <template slot-scope="scope">
            <p v-html="$utils.unescapeHTML(scope.row.contents2)"></p>
        </template>
      </vxe-column>
      <vxe-column
        fixed="right"
        align="center"
        width="200"
        title="操作">
        <template  slot-scope="scope">
          <el-button v-if="hasPermission('test:note:testNote:view')" type="text" icon="el-icon-view" size="small" @click="view(scope.row.id)">查看</el-button>
          <el-button v-if="hasPermission('test:note:testNote:edit')" type="text" icon="el-icon-edit" size="small" @click="edit(scope.row.id)">修改</el-button>
          <el-button v-if="hasPermission('test:note:testNote:del')" type="text"  icon="el-icon-delete" size="small" @click="del(scope.row.id)">删除</el-button>
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
    <TestNoteForm  ref="testNoteForm" @refreshDataList="refreshList"></TestNoteForm>
  </div>
</template>

<script>
  import TestNoteForm from './TestNoteForm'
  import TestNoteService from '@/api/test/note/TestNoteService'
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
      TestNoteForm
    },
    testNoteService: null,
    created () {
      this.testNoteService = new TestNoteService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.testNoteService.list({
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
        this.$refs.testNoteForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.testNoteTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.testNoteForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.testNoteForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.testNoteTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.testNoteService.delete(ids).then(({data}) => {
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

