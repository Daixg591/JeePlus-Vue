<template>
    <div class="page">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
            <!-- 搜索框-->
         <el-form-item prop="num">
                <el-input size="small" v-model="searchForm.num" placeholder="浮点数字" clearable></el-input>
         </el-form-item>
         <el-form-item prop="num2">
                <el-input size="small" v-model="searchForm.num2" placeholder="整数" clearable></el-input>
         </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
            <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
          </el-form-item>
      </el-form>

     <div class="bg-white top">
        <vxe-toolbar :refresh="{query: refreshList}" export print custom>
          <template #buttons>
            <el-button v-if="hasPermission('test:validation:testValidation:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('test:validation:testValidation:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()" :disabled="$refs.testValidation && $refs.testValidation.getCheckboxRecords().length !== 1" plain>修改</el-button>
            <el-button v-if="hasPermission('test:validation:testValidation:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.testValidation && $refs.testValidation.getCheckboxRecords().length === 0" plain>删除</el-button>
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
            ref="testValidationTable"
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
        field="num"
        sortable
        title="浮点数字">
            <template slot-scope="scope">
              <el-link  type="primary" :underline="false" v-if="hasPermission('test:validation:testValidation:edit')" @click="edit(scope.row.id)">{{scope.row.num}}</el-link>
              <el-link  type="primary" :underline="false" v-else-if="hasPermission('test:validation:testValidation:view')"  @click="view(scope.row.id)">{{scope.row.num}}</el-link>
              <span v-else>{{scope.row.num}}</span>
            </template>
      </vxe-column>
    <vxe-column
        field="num2"
        sortable
        title="整数">
      </vxe-column>
    <vxe-column
        field="str"
        sortable
        title="字符串">
      </vxe-column>
    <vxe-column
        field="email"
        sortable
        title="邮件">
      </vxe-column>
    <vxe-column
        field="url"
        sortable
        title="网址">
      </vxe-column>
    <vxe-column
        field="newDate"
        sortable
        title="日期">
      </vxe-column>
    <vxe-column
        field="remarks"
        sortable
        title="备注信息">
      </vxe-column>
    <vxe-column
        field="c1"
        sortable
        title="浮点数小于等于0">
      </vxe-column>
    <vxe-column
        field="c2"
        sortable
        title="身份证号码">
      </vxe-column>
    <vxe-column
        field="c3"
        sortable
        title="QQ号码">
      </vxe-column>
    <vxe-column
        field="c4"
        sortable
        title="手机号码">
      </vxe-column>
    <vxe-column
        field="c5"
        sortable
        title="中英文数字下划线">
      </vxe-column>
    <vxe-column
        field="c6"
        sortable
        title="合法字符(a-z A-Z 0-9)">
      </vxe-column>
    <vxe-column
        field="en"
        sortable
        title="英语">
      </vxe-column>
    <vxe-column
        field="zn"
        sortable
        title="汉字">
      </vxe-column>
    <vxe-column
        field="enzn"
        sortable
        title="汉英字符">
      </vxe-column>
      <vxe-column
        fixed="right"
        align="center"
        width="200"
        title="操作">
        <template  slot-scope="scope">
          <el-button v-if="hasPermission('test:validation:testValidation:view')" type="text" icon="el-icon-view" size="small" @click="view(scope.row.id)">查看</el-button>
          <el-button v-if="hasPermission('test:validation:testValidation:edit')" type="text" icon="el-icon-edit" size="small" @click="edit(scope.row.id)">修改</el-button>
          <el-button v-if="hasPermission('test:validation:testValidation:del')" type="text"  icon="el-icon-delete" size="small" @click="del(scope.row.id)">删除</el-button>
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
    <TestValidationForm  ref="testValidationForm" @refreshDataList="refreshList"></TestValidationForm>
  </div>
</template>

<script>
  import TestValidationForm from './TestValidationForm'
  import TestValidationService from '@/api/test/validation/TestValidationService'
  export default {
    data () {
      return {
        searchForm: {
          num: '',
          num2: ''
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
      TestValidationForm
    },
    testValidationService: null,
    created () {
      this.testValidationService = new TestValidationService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.testValidationService.list({
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
        this.$refs.testValidationForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.testValidationTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.testValidationForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.testValidationForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.testValidationTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.testValidationService.delete(ids).then(({data}) => {
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

