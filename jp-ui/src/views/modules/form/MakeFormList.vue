<template>
  <div class="page">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
         <el-form-item prop="name">
                <el-input size="small" v-model="searchForm.name" placeholder="表单名称" clearable></el-input>
		     </el-form-item>
            <!-- 搜索框-->
          <el-form-item>
            <el-button  type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
            <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
          </el-form-item>
      </el-form>

      <div class="bg-white top">
        <vxe-toolbar :refresh="{query: refreshList}" export print custom>
          <template #buttons>
            <el-button v-if="hasPermission('form:make:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('form:make:add')" type="primary" size="small" icon="el-icon-edit" @click="design()"
              :disabled="$refs.makeFormTable && $refs.makeFormTable.getCheckboxRecords().length !== 1" plain>设计</el-button>
            <el-button v-if="hasPermission('form:make:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()"
            :disabled="$refs.makeFormTable && $refs.makeFormTable.getCheckboxRecords().length !== 1" plain>修改</el-button>
            <el-button v-if="hasPermission('form:make:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()"
                      :disabled="$refs.makeFormTable && $refs.makeFormTable.getCheckboxRecords().length === 0" plain>删除</el-button>
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
            ref="makeFormTable"
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
            <vxe-column  title="表单名称" field="name" sortable>
                <template slot-scope="scope">
                    <el-link  type="primary" :underline="false" v-if="hasPermission('echarts:edit')" @click="edit(scope.row.id)">{{scope.row.name}}</el-link>
                    <el-link  type="primary" :underline="false" v-else-if="hasPermission('echarts:view')"  @click="view(scope.row.id)">{{scope.row.name}}</el-link>
                    <span v-else>{{scope.row.name}}</span>
                </template>
            </vxe-column>
            <vxe-column  title="表名" field="tableName" sortable> </vxe-column>
            <vxe-column  title="表单key" field="code" sortable></vxe-column>
            <vxe-column  title="所属数据库" field="dataSource.name" sortable>
               <template  slot-scope="scope">
                <p v-if="scope.row.dataSource.id === 'master'">
                  本地数据库
                </p>
                <p v-else>
                  {{scope.row.dataSource.name}}
                </p>
              </template>
            </vxe-column>
            <vxe-column  title="版本号" field="version" sortable> 
                <template slot-scope="scope">
                    {{'V ' + scope.row.version + '.0'}}
                </template>
            </vxe-column>
            <vxe-column title="操作" width="250px" fixed="right" align="center">
                <template  slot-scope="scope">
                    <el-button v-if="hasPermission('form:make:edit')" type="text" icon="el-icon-edit" size="mini" @click="design(scope.row.id)">设计</el-button>
                        <el-divider direction="vertical"></el-divider>
                    <el-button v-if="hasPermission('form:make:del')" type="text" size="mini" icon="el-icon-delete"  @click="del(scope.row.id)">删除</el-button>
                        <el-divider direction="vertical"></el-divider>
                    <el-button v-if="hasPermission('form:make:view')" type="text" size="mini" icon="el-icon-view"  @click="preview(scope.row)"> 预览</el-button>
                        <el-divider direction="vertical"></el-divider>
                    <el-button v-if="hasPermission('form:make:deploy')" type="text" size="mini" icon="el-icon-s-promotion"  @click="release(scope.row)">发布</el-button>
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
    <BasicForm  ref="basicForm" @refreshDataList="refreshList"></BasicForm>
    <MakeForm  ref="makeForm" @refreshDataList="refreshList"></MakeForm>
    <MenuForm ref="menuForm" @refreshDataList="refreshList"></MenuForm>
  </div>
</template>

<script>
  import BasicForm from './BasicForm'
  import MakeForm from './MakeForm'
  import MenuForm from './MenuForm'
  import DataRuleList from './DataRuleList'
  import MakeFormService from '@/api/form/MakeFormService'
  export default {
    data () {
      return {
        searchForm: {
        },
        dataList: [],
        tablePage: {
          total: 0,
          currentPage: 1,
          pageSize: 10,
          orders: []
        },
        dataRuleTitle: '',
        rightVisible: false,
        loading: false
      }
    },
    components: {
      MakeForm,
      BasicForm,
      MenuForm,
      DataRuleList
    },
    makeFormService: null,
    created () {
      this.makeFormService = new MakeFormService()
    },
    activated () {
      this.refreshList()
    },

    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.makeFormService.list({
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
        this.$refs.basicForm.init('add', '')
      },
      // 设计
      design (id) {
        id = id || this.$refs.makeFormTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.makeForm.init('edit', id)
      },
      // 修改
      edit (id) {
        id = id || this.$refs.makeFormTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.basicForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.makeForm.init('view', id)
      },
         // 发布
      release (row) {
        if (!row.source) {
          this.$message.warning('请先设计表单!')
          return
        }
        this.$refs.menuForm.init(row)
      },
       // 查看
      preview (row) {
        if (!row.source) {
          this.$message.warning('请先设计表单!')
          return
        }
        this.makeFormService.queryById(row.id).then(({data}) => {
          if (data.source) {
            this.options = JSON.parse(data.source)
          } else {
            this.options = {'list': [], 'config': {'labelWidth': 100, 'labelPosition': 'right', 'size': 'small', 'customClass': ''}}
          }
          this.$router.push({path: '/form/generateList', query: {title: row.name, id: row.id, previewMode: true}})
        })
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.makeFormTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.makeFormService.delete(ids).then(({data}) => {
            this.loading = false
            this.$message.success(data)
            this.refreshList()
          })
        })
      },
      showRight (row) {
        this.rightVisible = true
        this.$nextTick(() => {
          this.$refs.dataRuleList.form = row
          this.$refs.dataRuleList.refreshList(row.id)
          this.dataRuleTitle = row.name
        })
      },
      closeRight () {
        this.rightVisible = false
      },
      resetSearch () {
        this.$refs.searchForm.resetFields()
        this.refreshList()
      }
    }
  }
</script>