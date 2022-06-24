<template>
  <div class="page">
    <div class="bg-white top">
        <vxe-toolbar :refresh="{query: refreshList}"  custom>
          <template #buttons>
             <el-button type="primary" size="small" @click="add()">新建</el-button>
          </template>
        </vxe-toolbar>
        <div style="height: calc(100% - 100px);">
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
              :export-config="{}"
              :sort-config="{remote:false}"
              :data="dataList"
              :checkbox-config="{}">
            <vxe-column type="seq" width="40"></vxe-column>
            <vxe-column width="160px"  title="数据规则名称"  field="name" sortable></vxe-column>
            <vxe-column width="160px"  title="规则mapper方法" field="className" sortable></vxe-column>
            <vxe-column width="160px"  title="规则字段" field="field" sortable></vxe-column>
            <vxe-column width="160px"  title="规则条件" field="express" sortable>
              <template slot-scope="scope">
                {{$dictUtils.getDictLabel("t_express", scope.row.express, "")}}
              </template>
            </vxe-column>
            <vxe-column width="160px"  title="规则值" field="value" sortable></vxe-column>
            <vxe-column width="160px"  title="自定义SQL" field="sqlSegment" sortable></vxe-column>
            <vxe-column width="160px"  title="备注信息" field="remarks" sortable></vxe-column>
            <vxe-column width="160px"  title="规则值" field="value" sortable></vxe-column>
            <vxe-column title="操作" width="160px" fixed="right" align="center">
                <template slot-scope="scope">
                    <el-button  size="mini" @click="edit(scope.row.id)">修改</el-button>
                    <el-button type="danger" size="mini" @click="del(scope.row.id)"> 删除 </el-button>
                </template>
            </vxe-column>
          </vxe-table>
        </div>
    <data-rule-form ref="dataRuleForm"   @refreshDataList="refreshList"></data-rule-form>
  </div>
  </div>
</template>

<script>
  import DataRuleForm from './DataRuleForm'
  import DataRuleService from '@/api/sys/DataRuleService'
  export default {
    data () {
      return {
        dataList: [],
        loading: false,
        menuId: '',
        title: ''
      }
    },
    props: ['dataRuleTitle'],
    components: {
      DataRuleForm
    },
    dataRuleService: null,
    created () {
      this.dataRuleService = new DataRuleService()
    },
    activated () {
      this.refreshList(this.menuId)
    },
    methods: {
      // 获取数据列表
      refreshList (menuId) {
        this.menuId = menuId
        this.loading = true
        this.dataRuleService.list({
          'current': 1,
          'size': -1,
          'menuId': menuId
        }).then(({data}) => {
          this.dataList = data.records
          this.loading = false
        })
      },
      // 新增
      add () {
        this.$refs.dataRuleForm.init('add', {id: '', menuId: this.menuId})
      },
      // 修改
      edit (id) {
        this.$refs.dataRuleForm.init('edit', {id: id, menuId: this.menuId})
      },
      // 查看
      view (id) {
        this.$refs.dataRuleForm.init('view', {id: id, menuId: this.menuId})
      },
            // 排序
      sortChangeHandle (column) {
        this.tablePage.orders = []
        this.tablePage.orders.push({column: this.$utils.toLine(column.property), asc: column.order === 'asc'})
        this.refreshList()
      },
      // 删除
      del (id) {
        this.$confirm(`确定删除该条记录吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.dataRuleService.delete(id).then(({data}) => {
            this.loading = false
            this.$message.success(data)
            this.refreshList(this.menuId)
          })
        })
      }
    }
  }
</script>
