<template>
  <div class="page">
    <el-form size="small" :inline="true" ref="searchForm" class="query-form" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
        <el-form-item prop="name">
        <el-input size="small" v-model="searchForm.name" placeholder="角色名" clearable></el-input>
        </el-form-item>
      <el-form-item>
        <el-button  type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
        <el-button @click="resetSearch()"  size="small" icon="el-icon-refresh-right">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="top bg-white">
          <vxe-toolbar :refresh="{query: refreshList}" export print custom>
            <template #buttons>
              <el-button v-if="hasPermission('sys:role:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
              <el-button v-if="hasPermission('sys:role:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()" :disabled="$refs.roleTable && $refs.roleTable.getCheckboxRecords().length !== 1" plain>修改</el-button>
              <el-button v-if="hasPermission('sys:role:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.roleTable && $refs.roleTable.getCheckboxRecords().length === 0" plain>删除</el-button>
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
            ref="roleTable"
            show-header-overflow
            show-overflow
            highlight-hover-row
            :menu-config="{}"
            :print-config="{}"
            :import-config="{}"
       
                   :export-config="{  remote: true,
                    exportMethod: exportExcel,
                    modes: ['current', 'selected', 'all']}"
            @sort-change="sortChangeHandle"
            :sort-config="{remote:true}"
            :data="dataList"
            :checkbox-config="{}">
          <vxe-column type="seq" width="40"></vxe-column>
          <vxe-column type="checkbox"  width="40px"></vxe-column>
          <vxe-column  title="角色名称" field="name" sortable>
              <template slot-scope="scope">
                <el-link  type="primary" :underline="false" v-if="hasPermission('sys:role:edit')" @click="edit(scope.row.id)">{{scope.row.name}}</el-link>
                <el-link  type="primary" :underline="false" v-else-if="hasPermission('sys:role:view')"  @click="view(scope.row.id)">{{scope.row.name}}</el-link>
                <span v-else>{{scope.row.name}}</span>
              </template>
          </vxe-column>
          <vxe-column  title="英文名称" field="enName" sortable></vxe-column>
          <vxe-column  title="备注" field="remarks" sortable></vxe-column>
          <vxe-column  title="是否可用" field="useable" sortable>
              <template slot-scope="scope">
                <el-tag :type="scope.row.useable == '0' ? 'danger' : 'success'">
                  {{ $dictUtils.getDictLabel("yes_no", scope.row.useable, '否') }}
                </el-tag>
              </template>
          </vxe-column>
          <vxe-column title="操作" width="280px" fixed="right" align="center">
              <template slot-scope="scope">
                <el-button v-if="hasPermission('sys:role:edit')" icon="el-icon-edit" type="text" size="mini" @click="edit(scope.row.id)">修改</el-button>
                <el-button v-if="hasPermission('sys:role:del')" icon="el-icon-delete" type="text" size="mini" @click="del(scope.row.id)">删除</el-button>
                <el-button v-if="hasPermission('sys:role:assign')" icon="el-icon-setting" type="text" size="mini" @click="showAuth(scope.row)">角色授权</el-button>
                <el-button v-if="hasPermission('sys:role:assign')" icon="el-icon-user-solid" type="text" size="mini" @click="showRight(scope.row)">分配用户</el-button>
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
    <el-drawer
      size = "700px"
      :title="`用户列表，所属角色: ${this.roleUserTitle}`"
      :visible.sync="rightVisible"
      direction="rtl">
      <role-user-list  :role-user-title="roleUserTitle" :role-id="roleId" ref="roleUserList" @closeRight="closeRight"></role-user-list>
    </el-drawer>

    <auth-form  :auth-form-title="roleUserTitle" :role-id="roleId" ref="authForm"></auth-form>

        <!-- 弹窗, 新增 / 修改 -->
    <role-form  ref="roleForm" @refreshDataList="refreshList"></role-form>
  </div>
</template>
<script>
  import RoleForm from './RoleForm'
  import AuthForm from './AuthForm'
  import RoleUserList from './RoleUserList'
  import RoleService from '@/api/sys/RoleService'
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
        rightVisible: false,
        roleUserTitle: '',
        roleId: '',
        loading: false
      }
    },
    components: {
      RoleForm,
      AuthForm,
      RoleUserList
    },
    roleService: null,
    created () {
      this.roleService = new RoleService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.roleService.list({
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
        this.$refs.roleForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.roleTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.roleForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.roleForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.roleTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.roleService.delete(ids).then(({data}) => {
            this.loading = false
            this.$message.success({dangerouslyUseHTMLString: true,
              message: data})
            this.refreshList()
          })
        })
      },
      resetSearch () {
        this.$refs.searchForm.resetFields()
        this.refreshList()
      },
      showRight (row) {
        this.roleId = row.id
        this.rightVisible = true
        this.$nextTick(() => {
          this.$refs.roleUserList.refreshList()
          this.roleUserTitle = row.name
        })
      },
      closeRight () {
        this.rightVisible = false
      },
      showAuth (row) {
        this.roleId = row.id
        this.authVisible = true
        this.$nextTick(() => {
          this.$refs.authForm.init(row.id)
          this.roleUserTitle = row.name
        })
      }
    }
  }
</script>
