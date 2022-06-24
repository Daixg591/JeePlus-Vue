<template>
  <div class="page">

      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
         <el-form-item label="模型名称" prop="filterText">
            <el-input v-model="searchForm.filterText" size="small" placeholder="请输入关键词"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button  type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
          <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
        </el-form-item>
      </el-form>
      <div class="top bg-white">
          <vxe-toolbar :refresh="{query: refreshList}" export print custom>
          <template #buttons>
            <el-button type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
            <el-button type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.modelTable && $refs.modelTable.getCheckboxRecords().length === 0" plain>删除</el-button>
            <el-button type="success" :disabled="$refs.modelTable && $refs.modelTable.getCheckboxRecords().length  !== 1" size="small" @click="setCategory()">设置分类</el-button>
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
                ref="modelTable"
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
                <vxe-column  title="流程名称" field="name" sortable> </vxe-column>
                <vxe-column  title="流程KEY" field="key" sortable> </vxe-column>
                <vxe-column  title="分类" field="procDef.category" sortable> </vxe-column>
                <vxe-column  title="流程版本" field="version" sortable>
                    <template slot-scope="scope">       
                      <el-tag>{{scope.row.procDef.version || '0'}}</el-tag>
                    </template>
                </vxe-column>
                 <vxe-column  title="流程状态" field="version" sortable>
                    <template slot-scope="scope">       
                      <el-tag :type="scope.row.procDef.suspended===false?'success':(scope.row.procDef.suspended===undefined?'primary':'danger')">{{scope.row.procDef.suspended===false?'已发布':(scope.row.procDef.suspended===undefined?'草稿':'已挂起')}}</el-tag>
                    </template>
                </vxe-column>
                <vxe-column  title="更新时间" field="lastUpdated" sortable>
                    <template slot-scope="scope">
                      {{scope.row.lastUpdated | formatDate}}
                    </template>
                </vxe-column>
                <vxe-column title="操作" width="200px" fixed="right" align="center">
                      <template slot-scope="scope">
                        <el-button type="text" size="small" @click="design(scope.row)">设计</el-button>
                        <el-button  type="text" size="small" @click="deploy(scope.row)">发布</el-button>
                        <el-dropdown  size="small" style=" margin-left: 10px;">
                          <el-button type="text" size="small">
                            更多<i class="el-icon-arrow-down el-icon--right"></i>
                          </el-button>
                          <el-dropdown-menu slot="dropdown" >
                            <el-dropdown-item v-if="scope.row.procDef.suspended===true"><el-button type="text" size="small" @click="active(scope.row.procDef)">激活</el-button></el-dropdown-item>
                            <el-dropdown-item v-if="scope.row.procDef.suspended===false"><el-button type="text" size="small" @click="suspend(scope.row.procDef)">挂起</el-button></el-dropdown-item>
                            <el-dropdown-item><el-button  type="text" size="small" @click="exportXML(scope.row)">导出</el-button></el-dropdown-item>
                            <el-dropdown-item> <el-button  type="text" size="small" @click="copy(scope.row.id)">复制</el-button> </el-dropdown-item>
                            <el-dropdown-item> <el-button  type="text" size="small" @click="del(scope.row.id)">删除</el-button> </el-dropdown-item>
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
       <el-dialog
        title="查看进度"
        :close-on-click-modal="true"
        :visible.sync="visible"
         v-dialogDrag
        height="600px">
       
          <iframe :src="processPhotoUrl" frameborder="0" scrolling="auto" width="100%" height="600px"></iframe>
        </el-dialog>

        
    <!-- 弹窗, 新增 / 修改 -->
    <model-form  ref="modelForm"  @create="create"></model-form>
    <!--模型编辑-->
    <design-form ref="designForm" @refreshList="refreshList"></design-form>
    <category-form ref="categoryForm" @refreshList="refreshList"></category-form>
    </div>
  </div>
</template>

<script>
  import ModelForm from './ModelForm'
  import CategoryForm from './CategoryForm'
  import ProcessService from '@/api/flowable/ProcessService'
  import ModelService from '@/api/flowable/ModelService'
  import ActCategoryService from '@/api/flowable/ActCategoryService'
  export default {
    data () {
      return {
        searchForm: {
          filterText: ''
        },
        filterText: '',
        selectCategoryName: '',
        categoryTreeData: [],
        dataList: [],
        tablePage: {
          total: 0,
          currentPage: 1,
          pageSize: 10,
          orders: []
        },
        loading: false,
        visible: false,
        processPhotoUrl: ''
      }
    },
    components: {
      ModelForm,
      CategoryForm
    },
    processService: null,
    modelService: null,
    actCategoryService: null,
    created () {
      this.processService = new ProcessService()
      this.modelService = new ModelService()
      this.actCategoryService = new ActCategoryService()
    },
    activated () {
      this.refreshList()
    },
    watch: {
      filterText (val) {
        this.$refs.categoryTree.filter(val)
      }
    },
    methods: {
      filterNode (value, data) {
        if (!value) return true
        return data.name.indexOf(value) !== -1
      },
      // 获取树数据
      refreshTree () {
        this.actCategoryService.treeData().then(({data}) => {
          this.categoryTreeData = data
        })
      },
      handleNodeClick (data) {
        this.searchForm.category = data.id
        this.selectCategoryName = '已选类型: ' + data.name
        this.refreshList()
      },
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.modelService.list({
          'filter': 'processes',
          'modelType': 0,
          'current': this.tablePage.currentPage,
          'size': this.tablePage.pageSize,
          'orders': this.tablePage.orders,
          ...this.searchForm
        }).then(({data}) => {
          this.dataList = data.records.slice((this.tablePage.currentPage - 1) * this.tablePage.pageSize, this.tablePage.currentPage * this.tablePage.pageSize)
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

      create (data) {
        // window.open(`${process.env.SERVER_URL}/static/modler/index.html#/editor/${data.id}`)
        this.$refs.designForm.init(data.id)
      },
      design (row) {
        this.$refs.designForm.init(row.id, row.procDef.category)
      },
      design1 (row) {
        this.$refs.designForm1.init(row.id)
      },
      design2 (row) {
        window.open(`${process.env.VUE_APP_SERVER_URL}/static/modler/index.html#/editor/${row.id}`)
      },
      exportXML (row) {
        window.open(`${process.env.VUE_APP_SERVER_URL}/app/rest/models/${row.id}/bpmn20?version=` + new Date().getTime())
      },
      // 部署
      deploy (row) {
        this.$confirm(`确认要发布流程吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let category = row.procDef.category ? row.procDef.category : '未分类'
          this.modelService.deploy({id: row.id, category: category}).then(({data}) => {
            this.$message.success({dangerouslyUseHTMLString: true,
              message: data})
            this.refreshList()
          })
        })
      },
       // 新增
      add () {
        // this.$refs.modelForm.init()
        this.$refs.designForm.init()
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.modelTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除该流程吗?删除流程会级联删除已经存在的实例与历史数据，且不可恢复，请谨慎操作!`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.modelService.delete(ids).then(({data}) => {
            this.loading = false
            this.$message.success(data)
            this.refreshList()
          })
        })
      },
      // 复制
      copy (id) {
        this.$confirm(`确定复制该流程吗？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.modelService.copy(id).then(({data}) => {
            this.loading = false
            this.$message.success(data)
            this.refreshList()
          })
        })
      },
      suspend (row) {
        this.$confirm(`确认要挂起该流程吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.processService.suspend(row.id).then(({data}) => {
            this.$message.success({dangerouslyUseHTMLString: true,
              message: data})
            this.refreshList()
          })
        })
      },
      active (row) {
        this.$confirm(`确定要激活码?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.processService.active(row.id).then(({data}) => {
            this.$message.success({dangerouslyUseHTMLString: true,
              message: data})
            this.refreshList()
          })
        })
      },
      setCategory () {
        let row = this.$refs.modelTable.getCheckboxRecords()[0]
        if (row.procDef.id) {
          this.$refs.categoryForm.init(row.procDef.id, row.procDef.category)
        } else {
          this.$message.error('未发布的流程不能设置分类，请先发布流程')
        }
      },
      resetSearch () {
        this.searchForm.category = ''
        this.selectCategoryName = ''
        this.$refs.searchForm.resetFields()
        this.$nextTick(() => {
          this.refreshList()
        })
      }
    }
  }
</script>
