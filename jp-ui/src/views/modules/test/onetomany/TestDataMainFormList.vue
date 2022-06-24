<template>
    <div class="page">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
            <!-- 搜索框-->
         <el-form-item prop="tuser.id">
            <user-select :limit='1' size="small" placeholder="请选择用户" :value="searchForm.tuser.id" @getValue='(value) => {searchForm.tuser.id=value}'></user-select>
         </el-form-item>
         <el-form-item prop="name">
                <el-input size="small" v-model="searchForm.name" placeholder="名称" clearable></el-input>
         </el-form-item>
         <el-form-item prop="sex">
                  <el-radio-group v-model="searchForm.sex">
                    <el-radio v-for="item in $dictUtils.getDictList('sex')" :label="item.value" :key="item.value">{{item.label}}</el-radio>
                  </el-radio-group>
         </el-form-item>
         <el-form-item prop="inDate">
               <el-date-picker
                    v-model="searchForm.inDate"
                    type="daterange"
                    size="small"
                    align="right"
                    value-format="yyyy-MM-dd hh:mm:ss"
                    unlink-panels
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
                 </el-date-picker>
         </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
            <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
          </el-form-item>
      </el-form>

     <div class="bg-white top">
        <vxe-toolbar :refresh="{query: refreshList}" export print custom>
          <template #buttons>
            <el-button v-if="hasPermission('test:onetomany:testDataMainForm:add')" type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
            <el-button v-if="hasPermission('test:onetomany:testDataMainForm:edit')" type="warning" size="small" icon="el-icon-edit-outline" @click="edit()" :disabled="$refs.testDataMainForm && $refs.testDataMainForm.getCheckboxRecords().length !== 1" plain>修改</el-button>
            <el-button v-if="hasPermission('test:onetomany:testDataMainForm:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.testDataMainForm && $refs.testDataMainForm.getCheckboxRecords().length === 0" plain>删除</el-button>
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
            ref="testDataMainFormTable"
            show-header-overflow
            show-overflow
            highlight-hover-row
            :menu-config="{}"
            :print-config="{}"
            :import-config="{}"
            :export-config="{}"
            @sort-change="sortChangeHandle"
            :sort-config="{remote:true}"
            :expand-config="{accordion: true, lazy: true,loadMethod:detail}"
            :data="dataList"
            :checkbox-config="{}">
            <vxe-column type="seq" width="40"></vxe-column>
            <vxe-column type="checkbox"  width="40px"></vxe-column>
            <vxe-column type="expand" width="80" >
                <template #content="{ row }">
                    <el-tabs>
                            <el-tab-pane label="汽车票">
                                <el-table
                                        size="small"
                                        :data="row.testDataChild23DTOList"
                                        style="width: 100%">
                                                    <el-table-column
                                                            prop="startArea.name"
                                                            label="出发地">
                                                    </el-table-column>
                                                    <el-table-column
                                                            prop="endArea.name"
                                                            label="目的地">
                                                    </el-table-column>
                                                    <el-table-column
                                                            prop="startTime"
                                                            show-overflow-tooltip
                                                            label="出发时间">
                                                    </el-table-column>
                                                    <el-table-column
                                                            prop="endTime"
                                                            show-overflow-tooltip
                                                            label="到达时间">
                                                    </el-table-column>
                                                    <el-table-column
                                                            prop="price"
                                                            show-overflow-tooltip
                                                            label="代理价格">
                                                    </el-table-column>
                                                    <el-table-column
                                                            prop="remarks"
                                                            show-overflow-tooltip
                                                            label="备注信息">
                                                    </el-table-column>
                                </el-table>
                            </el-tab-pane>
                            <el-tab-pane label="火车票">
                                <el-table
                                        size="small"
                                        :data="row.testDataChild21DTOList"
                                        style="width: 100%">
                                                    <el-table-column
                                                            prop="startArea.name"
                                                            label="出发地">
                                                    </el-table-column>
                                                    <el-table-column
                                                            prop="endArea.name"
                                                            label="目的地">
                                                    </el-table-column>
                                                    <el-table-column
                                                            prop="starttime"
                                                            show-overflow-tooltip
                                                            label="出发时间">
                                                    </el-table-column>
                                                    <el-table-column
                                                            prop="endtime"
                                                            show-overflow-tooltip
                                                            label="到达时间">
                                                    </el-table-column>
                                                    <el-table-column
                                                            prop="price"
                                                            show-overflow-tooltip
                                                            label="代理价格">
                                                    </el-table-column>
                                                    <el-table-column
                                                            prop="remarks"
                                                            show-overflow-tooltip
                                                            label="备注信息">
                                                    </el-table-column>
                                </el-table>
                            </el-tab-pane>
                            <el-tab-pane label="飞机票">
                                <el-table
                                        size="small"
                                        :data="row.testDataChild22DTOList"
                                        style="width: 100%">
                                                    <el-table-column
                                                            prop="startArea.name"
                                                            label="出发地">
                                                    </el-table-column>
                                                    <el-table-column
                                                            prop="endArea.name"
                                                            label="目的地">
                                                    </el-table-column>
                                                    <el-table-column
                                                            prop="startTime"
                                                            show-overflow-tooltip
                                                            label="出发时间">
                                                    </el-table-column>
                                                    <el-table-column
                                                            prop="endTime"
                                                            show-overflow-tooltip
                                                            label="到达时间">
                                                    </el-table-column>
                                                    <el-table-column
                                                            prop="price"
                                                            show-overflow-tooltip
                                                            label="代理价格">
                                                    </el-table-column>
                                                    <el-table-column
                                                            prop="remarks"
                                                            show-overflow-tooltip
                                                            label="备注信息">
                                                    </el-table-column>
                                </el-table>
                            </el-tab-pane>
                    </el-tabs>
                </template>
            </vxe-column>
      <vxe-column
        field="tuser.name"
        sortable
        title="用户">
            <template slot-scope="scope">
              <el-link  type="primary" :underline="false" v-if="hasPermission('test:onetomany:testDataMainForm:edit')" @click="edit(scope.row.id)">{{scope.row.tuser && scope.row.tuser.name}}</el-link>
              <el-link  type="primary" :underline="false" v-else-if="hasPermission('test:onetomany:testDataMainForm:view')"  @click="view(scope.row.id)">{{scope.row.tuser &&  scope.row.tuser.name}}</el-link>
              <span v-else>{{scope.row.tuser && scope.row.tuser.name}}</span>
            </template>
      </vxe-column>
      <vxe-column
        field="office.name"
        sortable
        title="所属部门">
            <template slot-scope="scope">
                {{scope.row.office && scope.row.office.name}}
            </template>
      </vxe-column>
      <vxe-column
        field="area.name"
        sortable
        title="所属区域">
            <template slot-scope="scope">
                {{scope.row.area && scope.row.area.name}}
            </template>
      </vxe-column>
    <vxe-column
        field="name"
        sortable
        title="名称">
      </vxe-column>
    <vxe-column
        field="sex"
        sortable
        title="性别">
        <template slot-scope="scope">
             {{ $dictUtils.getDictLabel("sex", scope.row.sex, '-') }}
        </template>
      </vxe-column>
    <vxe-column
        field="file"

        sortable
        title="身份证照片">
        <template slot-scope="scope" v-if="scope.row.file">
          <el-image
            style="height: 50px;width:50px;margin-right:10px;"
            :src="src" v-for="(src, index) in scope.row.file.split('|')" :key="index"
            :preview-src-list="scope.row.file.split('|')">
          </el-image>
        </template>
      </vxe-column>
    <vxe-column
        field="inDate"
        sortable
        title="加入日期">
      </vxe-column>
      <vxe-column
        fixed="right"
        align="center"
        width="200"
        title="操作">
        <template  slot-scope="scope">
          <el-button v-if="hasPermission('test:onetomany:testDataMainForm:view')" type="text" icon="el-icon-view" size="small" @click="view(scope.row.id)">查看</el-button>
          <el-button v-if="hasPermission('test:onetomany:testDataMainForm:edit')" type="text" icon="el-icon-edit" size="small" @click="edit(scope.row.id)">修改</el-button>
          <el-button v-if="hasPermission('test:onetomany:testDataMainForm:del')" type="text"  icon="el-icon-delete" size="small" @click="del(scope.row.id)">删除</el-button>
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
    <TestDataMainFormForm  ref="testDataMainFormForm" @refreshDataList="refreshList"></TestDataMainFormForm>
  </div>
</template>

<script>
  import TestDataMainFormForm from './TestDataMainFormForm'
  import TestDataMainFormService from '@/api/test/onetomany/TestDataMainFormService'
  import UserSelect from '@/components/userSelect'
  export default {
    data () {
      return {
        searchForm: {
          tuser: {
            id: ''
          },
          name: '',
          sex: '',
          inDate: ''
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
      UserSelect,
      TestDataMainFormForm
    },
    testDataMainFormService: null,
    created () {
      this.testDataMainFormService = new TestDataMainFormService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.testDataMainFormService.list({
          'current': this.tablePage.currentPage,
          'size': this.tablePage.pageSize,
          'orders': this.tablePage.orders,
          beginInDate: this.searchForm.inDate[0],
          endInDate: this.searchForm.inDate[1],
          ...this.lodash.omit(this.searchForm, 'inDate')
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
        this.$refs.testDataMainFormForm.init('add', '')
      },
      // 修改
      edit (id) {
        id = id || this.$refs.testDataMainFormTable.getCheckboxRecords().map(item => {
          return item.id
        })[0]
        this.$refs.testDataMainFormForm.init('edit', id)
      },
      // 查看
      view (id) {
        this.$refs.testDataMainFormForm.init('view', id)
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.testDataMainFormTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.testDataMainFormService.delete(ids).then(({data}) => {
            this.$message.success(data)
            this.refreshList()
            this.loading = false
          })
        })
      },
        // 查看详情
      detail ({row}) {
        return new Promise(resolve => {
          this.testDataMainFormService.queryById(row.id).then(({data}) => {
            this.dataList.forEach((item, index) => {
              if (item.id === row.id) {
                item.testDataChild23DTOList = data.testDataChild23DTOList
                item.testDataChild21DTOList = data.testDataChild21DTOList
                item.testDataChild22DTOList = data.testDataChild22DTOList
              }
            })
            resolve()
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

