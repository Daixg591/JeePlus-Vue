<template>
    <div class="jp-common-layout page">
      <div class="jp-common-layout-left">
        <div class="jp-common-title"> 
          <el-input
          placeholder="组织机构:请输入关键字过滤"
          size="small"
          clearable
          v-model="filterText">
        </el-input>
        </div>
      <div class="jp-common-el-tree-scrollbar el-scrollbar">
      <div class="el-scrollbar__wrap">
        <div class="el-scrollbar__view">
            <el-tree
              class="filter-tree jp-common-el-tree"
              :render-content="renderContent"
              :data="officeTreeData"
              :props="{
                    value: 'id',             // ID字段名
                    label: 'name',         // 显示名称
                    children: 'children'    // 子级字段名
                  }"
              node-key="id"
              default-expand-all
              :filter-node-method="filterNode"
              :expand-on-click-node="false"
              highlight-current
              @node-click="handleNodeClick"
              ref="officeTree">
            </el-tree>
        </div>
      </div>
      </div>
      </div>
      <div class="jp-common-layout-center jp-flex-main">
        <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
          <el-form-item prop="loginName">
            <el-input size="small" v-model="searchForm.loginName" placeholder="登录名" clearable></el-input>
          </el-form-item>
          <el-form-item prop="name">
            <el-input size="small" v-model="searchForm.name" placeholder="姓名" clearable></el-input>
          </el-form-item>
        <el-form-item>
          <el-button  type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
          <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
        </el-form-item>
        </el-form>
        <el-dialog  title="导入Excel" :visible.sync="isImportCollapse">
          <el-form size="small" :inline="true" ref="importForm">
            <el-form-item>
              <el-button  type="default" @click="downloadTpl()" size="small">下载模板</el-button>
            </el-form-item>
            <el-form-item prop="loginName">
                <el-upload
                  class="upload-demo"
                  :action="`${this.$http.BASE_URL}/sys/user/import`"
                  :on-success="handleSuccess"
                  :before-upload="beforeUpload"
                  :show-file-list="true">
                  <el-button size="small" type="primary">点击上传</el-button>
                  <div slot="tip" class="el-upload__tip">只允许导入“xls”或“xlsx”格式文件！</div>
                </el-upload>
            </el-form-item>
          </el-form>
        </el-dialog>
      <div class="bg-white top">
          <vxe-toolbar :refresh="{query: refreshList}" export print custom>
              <template #buttons>
                <el-button v-if="hasPermission('sys:user:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
                <el-button v-if="hasPermission('sys:user:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()" :disabled="$refs.userTable && $refs.userTable.getCheckboxRecords().length !== 1" plain>修改</el-button>
                <el-button v-if="hasPermission('sys:user:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.userTable && $refs.userTable.getCheckboxRecords().length === 0" plain>删除</el-button>
    
                <el-button v-if="hasPermission('sys:user:import')" type="default" size="small" icon="el-icon-upload2" title="导入" @click="isImportCollapse = !isImportCollapse">导入</el-button>
    
                <el-button v-if="hasPermission('sys:user:export')" type="default" size="small" icon="el-icon-download" title="导出" @click="exportExcel3()">导出</el-button>
      
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
                  ref="userTable"
                  show-header-overflow
                  show-overflow
                  highlight-hover-row
          
                  :menu-config="{}"
                  :print-config="{}"
                  :import-config="{}"
                  :export-config="{  remote: true,
                    exportMethod: exportExcel3,
                    modes: ['current', 'selected', 'all']}"
                  @sort-change="sortChangeHandle"
                  :sort-config="{remote:true}"
                  :data="dataList"
                  :checkbox-config="{}">
                <vxe-column type="seq" width="40"></vxe-column>
                <vxe-column type="checkbox"  width="40px"></vxe-column>
                <vxe-column  title="头像" field="photo">
                    <template slot-scope="scope">
                      <img :src="scope.row.photo === ''?'/static/img/avatar.png':scope.row.photo" style="height:35px"/>
                    </template>
                </vxe-column>
                <vxe-column  title="登录名" field="loginName" sortable>
                  <template slot-scope="scope">
                    <el-link  type="primary" :underline="false" v-if="hasPermission('sys:user:edit')" @click="edit(scope.row.id)">{{scope.row.loginName}}</el-link>
                    <el-link  type="primary" :underline="false" v-else-if="hasPermission('sys:user:view')"  @click="view(scope.row.id,)">{{scope.row.loginName}}</el-link>
                    <span v-else>{{scope.row.loginName}}</span>
                  </template>
                </vxe-column>
              
                <vxe-column  title="姓名" field="name" sortable></vxe-column>
                <vxe-column  title="公司" field="companyDTO.name" sortable>
                  <template slot-scope="scope">
                    <el-tag>{{scope.row.companyDTO && scope.row.companyDTO.name}}</el-tag>
                  </template>
                </vxe-column>
                <vxe-column  title="部门" field="officeDTO.name" sortable>
                  <template slot-scope="scope">
                    <el-tag>{{scope.row.officeDTO && scope.row.officeDTO.name}}</el-tag>
                  </template>
                </vxe-column>
                <vxe-column  title="状态" field="loginFlag" sortable>
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.loginFlag === '1'" size="small" type="success">正常</el-tag>
                    <el-tag v-else-if="scope.row.loginFlag === '0'" size="small" type="danger">禁用</el-tag>
                  </template>
                </vxe-column>
                <vxe-column title="操作" width="200px" fixed="right" align="center">
                  <template slot-scope="scope">
                    <el-button v-if="hasPermission('sys:user:view')" type="text" size="small" icon="el-icon-view" @click="view(scope.row.id)">查看</el-button>
                    <el-button v-if="hasPermission('sys:user:edit')" type="text" size="small" icon="el-icon-edit" @click="edit(scope.row.id)">修改</el-button>
                    <el-button v-if="hasPermission('sys:user:del')" type="text" size="small" icon="el-icon-delete" @click="del(scope.row.id)">删除</el-button>
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
      <user-form  ref="userForm" @refreshDataList="refreshList"></user-form>
      </div>
    </div>
    </div>
</template>

<script>
  import UserForm from './UserForm'
  import UserService from '@/api/sys/UserService'
  import OfficeService from '@/api/sys/OfficeService'

  export default {
    data () {
      return {
        searchForm: {
          loginName: '',
          name: '',
          companyDTO: {
            id: ''
          },
          officeDTO: {
            id: ''
          }
        },
        filterText: '',
        dataList: [],
        officeTreeData: [],
        tablePage: {
          total: 0,
          currentPage: 1,
          pageSize: 10,
          orders: []
        },
        isImportCollapse: false,
        loading: false
      }
    },
    components: {
      UserForm
    },
    userService: null,
    officeService: null,
    created () {
      this.userService = new UserService()
      this.officeService = new OfficeService()
    },
    activated () {
      this.refreshTree()
      this.refreshList()
    },
    watch: {
      filterText (val) {
        this.$refs.officeTree.filter(val)
      }
    },
    methods: {
      filterNode (value, data) {
        if (!value) return true
        return data.name.indexOf(value) !== -1
      },
      renderContent (h, { node, data, store }) {
        return (
              <span class="custom-tree-node">
                {
                  data.type === '1' ? <i class="fa fa-sitemap"></i> : <i class="fa fa-users"></i>
                }
                <span class="text">{node.label}</span>
              </span>
        )
      },
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.userService.list({
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
      refreshTree () {
        this.officeService.treeData().then(({data}) => {
          this.officeTreeData = data
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
          if (column.property === 'officeDTO.name') {
            column.property = 'o.name'
          }
          if (column.property === 'companyDTO.name') {
            column.property = 'c.name'
          }
          if (column.property === 'postDTO.name') {
            column.property = 'p.name'
          }
          this.tablePage.orders.push({column: this.$utils.toLine(column.property), asc: column.order === 'asc'})
        }
        this.refreshList()
      },
      // 新增
      add () {
        this.$refs.userForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.userTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.userForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.userForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.userTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.userService.delete(ids).then(({data}) => {
            this.loading = false
            this.$message.success({dangerouslyUseHTMLString: true,
              message: data})
            this.refreshList()
          }).catch(() => {
            this.loading = false
          })
        })
      },
      // 导入
      importExcel () {

      },
      // 下载模板
      downloadTpl () {
        this.$utils.download('/sys/user/import/template')
      },
      handleSuccess (res, file) {
        if (res.success) {
          this.$message.success({dangerouslyUseHTMLString: true,
            message: res.msg})
        } else {
          this.$message.error(res.msg)
        }
      },
      handleNodeClick (data) {
        if (data.type === '1') {
          this.searchForm.companyDTO.id = data.id
          this.searchForm.officeDTO.id = ''
        } else {
          this.searchForm.companyDTO.id = ''
          this.searchForm.officeDTO.id = data.id
        }
        this.refreshList()
      },
      beforeUpload (file) {
        const isExcel = file.name.indexOf('.xls') >= 0
        const isLt2M = file.size / 1024 / 1024 < 10

        if (!isExcel) {
          this.$message.error('只能上传excel文件!')
          return false
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 10MB!')
          return false
        }
        return true
      },
      exportExcel3 ({ options }) {
        this.$refs.userTable.exportData()
      },
      exportExcel2 () {
        var foo = {
          then: (resolve, reject) => {
            this.$refs.userTable.openExport({ types: ['csv'] })
          }
        }
        return foo
      // this.$refs.userTable.openExport({ types: ['csv'] })
        // this.$refs.userTable.openExport({
        //           remote: true,
        //           type: "csv",
        //           types: ['csv'],
        //           mode: "selected",
        //           modes: ['selected'],
        //           isHeader: true,
        //           exportMethod: function (datas) {
        //             this.$http({
        //                 url: '/sys/user/list',
        //                 method: 'post',
        //                 data: {
        //                     'size': -1,
        //                     'orders': this.tablePage.orders,
        //                     ...this.searchForm
        //                   }
        //                 }).then(({data}) => {
        //                     datas.row.templateData = data.records
        //               })
        //           },
        //       })
      },
      exportExcel ({options}) {
        const body = {
          filename: options.filename,
          sheetName: options.sheetName,
          isHeader: options.isHeader,
          original: options.original,
          mode: options.mode,
          page: this.tablePage,
          ids: options.mode === 'selected' ? options.data.map(item => item.id) : [],
          fields: options.columns.map(column => {
            return {
              field: column.property,
              title: column.title
            }
          })
        }
        let params = {
          ...this.searchForm,
          ...body
        }
        this.$utils.download('/sys/user/export', params)
      },
      resetSearch () {
        this.searchForm.companyDTO.id = ''
        this.searchForm.officeDTO.id = ''
        this.filterText = ''
        this.$refs.officeTree.setCurrentKey(null)
        this.$refs.searchForm.resetFields()
        this.refreshList()
      }
    }
  }
</script>
<style lang="scss">
.el-card__body {
    overflow: auto;
}
</style>
