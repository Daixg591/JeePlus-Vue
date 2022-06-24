<template>
<div class="page bg-white">
      <vxe-toolbar :refresh="{query: refreshList}" export print custom>
        <template #buttons>
          <el-button type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
        </template>
      </vxe-toolbar>
     <div style="height: calc(100% - 40px);">
        <vxe-table
            border="inner"
            auto-resize
            resizable
            height="auto"
            :loading="loading"
            size="small"
            ref="reportsTable"
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
            <vxe-column  title="报表名" field="name" sortable> </vxe-column>
            <vxe-column  title="更新日期" field="updateDate" sortable>
              <template slot-scope="scope">
                {{moment(scope.row.updateDate).format('YYYY-MM-DD HH:mm:ss')}}
              </template>
            </vxe-column>
       
            <vxe-column title="操作" width="200px" fixed="right" align="center">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  @click="pre(scope.row)" >预览</el-button>
                  <el-divider direction="vertical"></el-divider>
                <el-button
                  size="mini"
                  type="text"
                  @click="design(scope.row)" >设计</el-button>
                  <el-divider direction="vertical"></el-divider>
                <el-button
                  size="mini"
                  type="text"
                  @click="del(scope.row)" >删除</el-button>
                  <el-divider direction="vertical"></el-divider>
                  <el-button
                  size="mini"
                  type="text"
                  @click="createMenu(scope.row)" >发布</el-button>
              </template>
            </vxe-column>
        </vxe-table>
        <gen-menu-form ref="genMenuForm"></gen-menu-form>
     </div>
     </div>
</template>
<script>
import GenMenuForm from '@/components/menu/GenMenuForm'
import ReportService from '@/api/reports/ReportService'
export default {
  data () {
    return {
      dataList: [],
      loading: false
    }
  },
  components: {
    GenMenuForm
  },
  reportService: null,
  created () {
    this.reportService = new ReportService()
  },
  activated () {
    this.refreshList()
  },
  methods: {
      // 获取数据列表
    refreshList () {
      this.loading = true
      this.reportService.list().then(({data}) => {
        this.dataList = data.reports
        this.loading = false
      })
    },
    add () {
      this.$router.push({path: '/ureport/designer', query: {title: '新建报表', iframeUrl: `${process.env.VUE_APP_SERVER_URL}/ureport/designer`}})
    },
    pre (report) {
      this.$router.push({path: `/ureport/preview`, query: {title: '预览报表', iframeUrl: `${process.env.VUE_APP_SERVER_URL}/ureport/preview?_u=file:${report.name}`}})
    },
    design (report) {
      this.$router.push({path: `/ureport/designer`, query: {iframeUrl: `${process.env.VUE_APP_SERVER_URL}/ureport/designer?_u=file:${report.name}`, title: '编辑报表'}})
    },
  // 删除
    del (report) {
      this.$confirm(`确定删除所选项吗?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        this.reportService.delete(`file:${report.name}`).then(({data}) => {
          this.loading = false
          this.$message.success(data)
          this.refreshList()
        })
      })
    },
      // 创建菜单
    createMenu (report) {
      this.$refs.genMenuForm.init(`/ureport/preview?iframeUrl=${process.env.VUE_APP_SERVER_URL}/ureport/preview?_u=file:${report.name}`, 'iframe')
    }

  }
}
</script>