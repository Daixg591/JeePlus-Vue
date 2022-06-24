<template>
  <div class="page">
    <el-form size="small" :inline="true"  class="query-form" ref="searchForm" :model="searchForm"  @keyup.enter.native="refreshList()" @submit.native.prevent>
         <el-form-item prop="name">
          <el-input size="small"  v-model="searchForm.name"   placeholder="菜单名称"   clearable></el-input>
        </el-form-item>
      <el-form-item>
        <el-button  type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
        <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="bg-white top">
    <vxe-toolbar :refresh="{query: refreshList}" export print custom>
      <template #buttons>
        <el-button v-if="hasPermission('sys:menu:add')" type="primary" icon="el-icon-plus" size="small" @click="add()">新增</el-button>
        <el-button v-if="hasPermission('sys:menu:del')" :disabled="$refs.xTree && $refs.xTree.getCheckboxRecords().length === 0" type="danger" size="small" icon="el-icon-delete" @click="del()" plain>删除</el-button>
      </template>
    </vxe-toolbar>
    <div style="height: calc(100% - 50px);">
      <vxe-table
        ref="xTree"
        border="inner"
        auto-resize
        resizable
        height="auto"
        row-id="id"
        size="small"
        show-header-overflow
        show-overflow
        highlight-hover-row
        :print-config="{}"
        :export-config="{}"
        :tree-config="{}"
        :loading="loading"
        :checkbox-config="{checkStrictly: true}"
        :data="dataList">
        <vxe-column type="checkbox"  width="40px">
        </vxe-column>
        <vxe-column  title="名称" field="name" align="left" tree-node>
          <template slot-scope="scope">
              <el-link  type="primary" :underline="false" v-if="hasPermission('sys:menu:edit')" @click="edit(scope.row.id)">{{scope.row.name}}</el-link>
              <el-link  type="primary" :underline="false" v-else-if="hasPermission('sys:menu:view')"  @click="view(scope.row.id)">{{scope.row.name}}</el-link>
              <span v-else>{{scope.row.name}}</span>
            </template>
        </vxe-column>
        <vxe-column  title="图标" field="icon" align="center">
            <template slot-scope="scope">
              <i :class="scope.row.icon"></i>
            </template>
        </vxe-column>
        <vxe-column  title="类型" field="type" align="center">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.type === '0'" size="small">目录</el-tag>
                <el-tag v-else-if="scope.row.type === '1'" size="small" type="success">菜单</el-tag>
                <el-tag v-else-if="scope.row.type === '2'" size="small" type="info">按钮</el-tag>
                <el-tag v-else-if="scope.row.type === '3'" size="small" type="info">路由</el-tag>
              </template>
        </vxe-column>
        <vxe-column title="排序号" field="sort" width="170px" align="center">
            <template slot-scope="scope">
                  <el-input-number size="small" v-model="scope.row.sort" @change="sortChange(scope.row)" :step="30"  :min="0" :max="10000" label="描述文字"></el-input-number>
            </template>
        </vxe-column>
        <vxe-column title="是否显示" field="isShow" align="center">
          <template slot-scope="scope">
                    <el-switch
                      v-model="scope.row.isShow"
                      v-if="scope.row.type === '0' || scope.row.type === '1'"
                      @change="statusChange(scope.row)"
                      active-color="#13ce66"
                      inactive-color="#ff4949"
                      active-value="1"
                      inactive-value="0">
                    </el-switch>
                </template>
        </vxe-column>
        <vxe-column  title="菜单路由" field="href" ></vxe-column>
        <vxe-column  title="权限标志" field="permission"></vxe-column>
        <vxe-column title="操作" width="150px" fixed="right" align="center">
          <template slot-scope="scope">
                  <el-button  type="text" size="small" @click="showRight(scope.row)">数据规则</el-button>
                  <el-divider direction="vertical"></el-divider>
                  <el-dropdown size="small"  @command="handleCommand">
                    <span class="el-dropdown-link">
                      更多<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item  v-if="hasPermission('sys:menu:view')" :command="{method:'view', id:scope.row.id}">
                      查看
                      </el-dropdown-item>
                      <el-dropdown-item v-if="hasPermission('sys:menu:edit')" :command="{method:'edit', id:scope.row.id}">
                        修改
                      </el-dropdown-item>
                      <el-dropdown-item v-if="hasPermission('sys:menu:del')" :command="{method:'del', id:scope.row.id}">
                        删除
                      </el-dropdown-item>
                      <el-dropdown-item v-if="hasPermission('sys:menu:add')" :command="{method:'addChild', row:scope.row}">
                        添加下级菜单
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
          </template>
      </vxe-column>
      </vxe-table>
    </div>
    </div>
    <el-drawer
      size = "700px"
      :title="`数据规则列表，所属菜单: ${this.dataRuleTitle}`"
      :visible.sync="rightVisible"
      direction="rtl">
      <data-rule-list  ref="dataRuleList" @closeRight="closeRight"></data-rule-list>
    </el-drawer>
  
    <!-- 弹窗, 新增 / 修改 -->
    <menu-form ref="menuForm"   @refreshDataList="refreshList"></menu-form>
  </div>
</template>

<script>
  import MenuForm from './MenuForm'
  import DataRuleList from './DataRuleList'
  import XEUtils from 'xe-utils'
  import MenuService from '@/api/sys/MenuService'
  export default {
    data () {
      return {
        rightVisible: false,
        loading: false,
        dataRuleTitle: '',
        searchForm: {
          name: ''
        },
        dataList: []
      }
    },
    components: {
      MenuForm,
      DataRuleList
    },
    menuService: null,
    created () {
      this.menuService = new MenuService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.menuService.treeData({isShowHide: 1}).then(({data}) => {
          this.loading = false
          this.dataList = data[0].children
          this.handleSearch()
        })
      },
      handleSearch () {
        let filterName = XEUtils.toValueString(this.searchForm.name).trim()
        if (filterName) {
          let options = { children: 'children' }
          let searchProps = ['name']
          this.dataList = XEUtils.searchTree(this.dataList, item => searchProps.some(key => XEUtils.toValueString(item[key]).indexOf(filterName) > -1), options)
          this.$nextTick(() => {
            this.$refs.xTree.setAllTreeExpand(true)
          })
        }
      },
      resetSearch () {
        this.$refs.searchForm.resetFields()
        this.refreshList()
      },
      // 新增下级
      addChild (row) {
        this.$refs.menuForm.init('addChild', {id: '', parent: {id: row.id, name: name}})
      },
      // 新增
      add () {
        this.$refs.menuForm.init('add', {id: '', parent: {id: '', name: ''}})
      },
      // 修改
      edit (id) {
        this.$refs.menuForm.init('edit', {id: id, parent: {id: '', name: ''}})
      },
      // 查看
      view (id) {
        this.$refs.menuForm.init('view', {id: id, parent: {id: '', name: ''}})
      },
      handleCommand (command) {
        if (command.method === 'view') {
          this.view(command.id)
        } else if (command.method === 'edit') {
          this.edit(command.id)
        } else if (command.method === 'del') {
          this.del(command.id)
        } else if (command.method === 'addChild') {
          this.addChild(command.row)
        }
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.xTree.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除该条记录吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.menuService.delete(ids).then(({data}) => {
            this.loading = false
            this.$message({
              message: data,
              type: 'success',
              duration: 1500
            })
            this.refreshList()
          })
        })
      },
      statusChange (row) {
        this.loading = true
        this.menuService.save({id: row.id, isShow: row.isShow}).then(({data}) => {
          this.$message({
            message: data,
            type: 'success',
            duration: 1500
          })
          this.loading = false
          this.refreshList()
        })
      },
      sortChange (row) {
        this.loading = true
        this.menuService.save({id: row.id, sort: row.sort}).then(({data}) => {
          this.$message({
            message: data,
            type: 'success',
            duration: 1500
          })
          this.loading = false
          this.refreshList()
        })
      },
      showRight (row) {
        this.rightVisible = true
        this.$nextTick(() => {
          this.$refs.dataRuleList.refreshList(row.id)
          this.dataRuleTitle = row.name
        })
      },
      closeRight () {
        this.rightVisible = false
      }
    }
  }
</script>
