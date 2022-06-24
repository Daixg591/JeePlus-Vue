<template>
  <div class="page">
      <el-form size="small" :inline="true"  class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
         <el-form-item prop="type">
          <el-input size="small" v-model="searchForm.type" placeholder="类型" clearable></el-input>
        </el-form-item>
      <el-form-item>
        <el-button  type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
        <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
      </el-form-item>
      </el-form>
      <div class="top bg-white">
          <vxe-toolbar :refresh="{query: refreshList}" export print custom>
            <template #buttons>
              <el-button v-if="hasPermission('sys:dict:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
              <el-button v-if="hasPermission('sys:dict:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()" :disabled="$refs.dictTable && $refs.dictTable.getCheckboxRecords().length !== 1" plain>修改</el-button>
              <el-button v-if="hasPermission('sys:dict:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.dictTable && $refs.dictTable.getCheckboxRecords().length === 0" plain>删除</el-button>
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
            ref="dictTable"
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
          <vxe-column  title="类型" field="type" sortable>
            <template slot-scope="scope">
              <el-link  type="primary" :underline="false" v-if="hasPermission('sys:dict:edit')" @click="edit(scope.row.id)">{{scope.row.type}}</el-link>
              <el-link  type="primary" :underline="false" v-else-if="hasPermission('sys:dict:view')"  @click="view(scope.row.id)">{{scope.row.type}}</el-link>
              <span v-else>{{scope.row.type}}</span>
            </template>
          </vxe-column>
          <vxe-column  title="描述" field="remarks" sortable></vxe-column>
          <vxe-column title="操作" width="250px" fixed="right" align="center">
                <template slot-scope="scope">
                    <el-button v-if="hasPermission('sys:dict:view')" type="text" size="small" @click="view(scope.row.id)">查看</el-button>
                    <el-divider direction="vertical"></el-divider>
                    <el-button v-if="hasPermission('sys:dict:edit')" type="text" size="small" @click="edit(scope.row.id)">修改 </el-button>
                    <el-divider direction="vertical"></el-divider>
                    <el-button v-if="hasPermission('sys:dict:del')" type="text" size="small" @click="del(scope.row.id)"> 删除 </el-button>
                    <el-divider direction="vertical"></el-divider>
                    <el-button v-if="hasPermission('sys:dict:edit')" type="text" size="small" @click="showRight(scope.row)">管理键值</el-button>
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
    <dict-type-form  ref="dictTypeForm" @refreshDataList="refreshList"></dict-type-form>
    <el-drawer
      size = "700px"
      :title="`数据字典值列表，所属类型: ${this.dictTypeTitle}`"
      :visible.sync="rightVisible"
      direction="rtl">
      <dict-value-list  :dict-type-title="dictTypeTitle" ref="dictValueList" @closeRight="closeRight"></dict-value-list>
    </el-drawer>

  </div>
</template>

<script>
  import DictTypeForm from './DictTypeForm'
  import DictValueList from './DictValueList'
  import DictService from '@/api/sys/DictService'
export default {
    data () {
      return {
        searchForm: {
          type: ''
        },
        dataList: [],
        tablePage: {
          total: 0,
          currentPage: 1,
          pageSize: 10,
          orders: []
        },
        dictTypeTitle: '',
        rightVisible: false,
        loading: false
      }
    },
    components: {
      DictTypeForm,
      DictValueList
    },
    dictService: null,
    created () {
      this.dictService = new DictService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.dictService.list({
          'current': this.tablePage.currentPage,
          'size': this.tablePage.pageSize,
          'orders': this.tablePage.orders,
          ...this.searchForm
        }).then(({data}) => {
          this.dataList = data.records
          this.tablePage.total = data.total
          this.tablePage.currentPage = data.current
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
        this.$refs.dictTypeForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.dictTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.dictTypeForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.dictTypeForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.dictTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.dictService.delete(ids).then(({data}) => {
            this.$message.success(data)
            this.refreshList()
            this.$dictUtils.refreshDictList()
          })
        })
      },
      resetSearch () {
        this.$refs.searchForm.resetFields()
        this.refreshList()
      },
      showRight (row) {
        this.rightVisible = true
        this.$nextTick(() => {
          this.$refs.dictValueList.refreshList(row.id)
          this.dictTypeTitle = row.type
        })
      },
      closeRight () {
        this.rightVisible = false
      }
    }
  }
</script>
