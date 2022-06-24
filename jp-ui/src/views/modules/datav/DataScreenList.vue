<template>
  <div class="page">
      <el-form size="small" :inline="true" class="query-form" ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
            <!-- 搜索框-->
         <el-form-item prop="categoryId">
            <SelectTree
                  ref="category"
                  :props="{
                      value: 'id',             // ID字段名
                      label: 'name',         // 显示名称
                      children: 'children'    // 子级字段名
                    }"
                  placeholder="请选择大屏分类"
                  size="small"
                  url="/datav/dataScreenCategory/treeData"
                  :value="searchForm.categoryId"
                  :clearable="true"
                  :accordion="true"
                  @getValue="(value) => {searchForm.categoryId=value}"/>
         </el-form-item>
         <el-form-item prop="name">
                <el-input size="small" v-model="searchForm.name" placeholder="大屏名称" clearable></el-input>
         </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="refreshList()" size="small" icon="el-icon-search">查询</el-button>
            <el-button @click="resetSearch()" size="small" icon="el-icon-refresh-right">重置</el-button>
          </el-form-item>
      </el-form>

      <div class="bg-white top" style="padding:0px">
      `<div class="el-scrollbar__wrap" style="padding:10px;height:calc(100% - 70px)">
        <div class="el-scrollbar__view">
        <el-row>
          <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" >
              <el-card style="margin: 8px" :body-style="{ padding: '0px' }" shadow="always">
                <div class="jp-card jp-card-bordered">
                    <div class="add-border">
                      <a @click="add()">
                        <i class="el-icon-plus"></i> 
                        <p>新建大屏</p>
                      </a>
                    </div>
                </div>
              </el-card>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4"  v-for="(datascreen, index) in dataList" :key="index">
            <el-card style="margin: 8px" :body-style="{ padding: '0px' }" shadow="always">
              <img :src="datascreen.screenShot" class="image">
                <h3 class="jp-card-label">
                  <el-row>
                    <el-col :span="16">
                        <div class="left">{{datascreen.name}}</div>
                    </el-col>
                    <el-col :span="8">
                        <el-link :underline="false" class="el-icon-edit-outline right" @click="design(datascreen.id)"> 设计</el-link>
                    </el-col>
                    </el-row>
                </h3>
                
              <ul class="jp-card-actions">
                  <li style="width: 25%">
                    <el-link :underline="false" @click="edit(datascreen.id)"><i class="el-icon-edit-outline"></i> 编辑</el-link>
                  </li>
                  <li style="width: 25%">
                    <el-link :underline="false" @click="copy(datascreen.id)"><i class="el-icon-document-copy"></i> 复制</el-link>
                  </li>
                  <li style="width: 25%">
                    <el-link :underline="false"  @click="del(datascreen.id)"><i class="el-icon-delete"></i> 删除</el-link>
                  </li>
                  <li style="width: 25%">
                    <el-link :underline="false"  @click="view(datascreen.id)"><i class="el-icon-view"></i> 预览</el-link>
                  </li>
              </ul>
            </el-card>
          </el-col>
        </el-row>
        </div>`
      </div>
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
          <!-- 弹窗, 新增 / 修改 -->
      <DataScreenForm  ref="dataScreenForm" @refreshDataList="refreshList"></DataScreenForm>
  </div>
  </div>
</template>

<script>
  import DataScreenForm from './DataScreenForm'
  import SelectTree from '@/components/treeSelect/treeSelect.vue'
  import DataScreenService from '@/api/datav/DataScreenService'
  export default {
    data () {
      return {
        searchForm: {
          categoryId: '',
          name: ''
        },
        dataList: [],
        tablePage: {
          total: 0,
          currentPage: 1,
          pageSize: 11,
          orders: []
        },
        loading: false
      }
    },
    components: {
      SelectTree,
      DataScreenForm
    },
    dataScreenService: null,
    created () {
      this.dataScreenService = new DataScreenService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.dataScreenService.list({
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
        this.$refs.dataScreenForm.init('add', '')
      },
      // 修改
      edit (id) {
        this.$refs.dataScreenForm.init('edit', id)
      },
      // 设计
      design (id) {
        window.open('datav.html#/design?id=' + id, '_blank')
      },
      // 查看
      view (id) {
        window.open('datav.html#/view?id=' + id, '_blank')
      },
      // 删除
      del (id) {
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.dataScreenService.delete(id).then(({data}) => {
            this.$message.success(data)
            this.refreshList()
            this.loading = false
          })
        })
      },
      // 复制
      copy (id) {
        this.$confirm(`确定复制所选大屏吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.dataScreenService.copy(id).then(({data}) => {
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
<style lang="less" scoped>
.image{
  width: 100%;
  height: 156px;
  overflow: hidden;
}
.bottom{
    background-color: #f5f7fa;
    height: 50px;
    line-height: 50px;
    padding: 0 15px;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: justify;
    -ms-flex-pack: justify;
    justify-content: space-between;
    font-size: 12px;
}
.jp-card {
    height: 236px;
}
.jp-card-label{

    font-size: 14px; 
    padding-left:10px;
    padding-right:10px;
    height: 36px;
    margin: 0;
    .left{
      line-height: 36px;
      font-weight: 400;
      overflow: hidden;
      text-overflow:ellipsis;
      color: rgba(0, 0, 0, .55) !important;
      white-space: nowrap;
    }
    .right {
      line-height: 38px;
      justify-content: center;
      text-decoration: none;
      float: right;
    }
}

</style>