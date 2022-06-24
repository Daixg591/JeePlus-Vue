<template>
    <div class="page">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
            <!-- 搜索框-->
         <el-form-item prop="name">
                <el-input size="small" v-model="searchForm.name" placeholder="地图名称" clearable></el-input>
         </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
            <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
          </el-form-item>
      </el-form>
      <div class="bg-white top">
      <vxe-toolbar :refresh="{query: refreshList}" export print custom>
        <template #buttons>
          <el-button v-if="hasPermission('datav:dataMap:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
          <el-button v-if="hasPermission('datav:dataMap:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()" :disabled="$refs.dataMapTable && $refs.dataMapTable.getCheckboxRecords().length !== 1" plain>修改</el-button>
          <el-button v-if="hasPermission('datav:dataMap:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.dataMapTable && $refs.dataMapTable.getCheckboxRecords().length === 0" plain>删除</el-button>
          <el-button icon="el-icon-add-location" size="small">
              <a target="_blank" href="https://datav.aliyun.com/tools/atlas/#&lat=33.521903996156105&lng=104.29849999999999&zoom=4">添加更多地图</a>
          </el-button>
          <el-button icon="el-icon-location-information" size="small">
              <a target="_blank" href=" http://geojson.io/#map">画地图</a>
          </el-button>
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
            ref="dataMapTable"
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
            <vxe-column  title="地图名称" field="name" sortable>
              <template slot-scope="scope">
                <el-link  type="primary" :underline="false" v-if="hasPermission('datav:dataMap:edit')" @click="edit(scope.row.id)">{{scope.row.name}}</el-link>
                <el-link  type="primary" :underline="false" v-else-if="hasPermission('datav:dataMap:view')"  @click="view(scope.row.id)">{{scope.row.name}}</el-link>
                <span v-else>{{scope.row.name}}</span>
              </template>
            </vxe-column>
      
            <vxe-column  title="地图说明" field="remarks" sortable></vxe-column>
            <vxe-column title="操作" width="200px" fixed="right" align="center">
                <template  slot-scope="scope">
                  <el-button v-if="hasPermission('datav:dataMap:view')" type="text" icon="el-icon-view" size="small" @click="view(scope.row.id)">查看</el-button>
                  <el-button v-if="hasPermission('datav:dataMap:edit')" type="text" icon="el-icon-edit" size="small" @click="edit(scope.row.id)">修改</el-button>
                  <el-button v-if="hasPermission('datav:dataMap:del')" type="text"  icon="el-icon-delete" size="small" @click="del(scope.row.id)">删除</el-button>
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
    <DataMapForm  ref="dataMapForm" @refreshDataList="refreshList"></DataMapForm>
  </div>
</template>

<script>
  import DataMapForm from './DataMapForm'
  import DataMapService from '@/api/datav/DataMapService'
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
        loading: false
      }
    },
    components: {
      DataMapForm
    },
    dataMapService: null,
    created () {
      this.dataMapService = new DataMapService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.dataMapService.list({
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
        this.$refs.dataMapForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.dataMapTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.dataMapForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.dataMapForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.dataMapTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.dataMapService.delete(ids).then(({data}) => {
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