<template>
  <div style="padding:10px">
          <vxe-toolbar :refresh="{query: refreshList}"  custom>
            <template #buttons>
              <el-button type="primary" size="small" icon="el-icon-plus" @click="add()">新建</el-button>
              <el-button v-if="hasPermission('sys:dict:del')" type="danger"   size="small" icon="el-icon-delete" @click="del()" :disabled="$refs.dictValueTable && $refs.dictValueTable.getCheckboxRecords().length === 0" plain>删除</el-button>
            </template>
          </vxe-toolbar>
           <vxe-table
            border="inner"
            auto-resize
            resizable
            height="auto"
            :loading="loading"
            size="small"
            ref="dictValueTable"
            show-header-overflow
            show-overflow
            highlight-hover-row
            :menu-config="{}"
            :print-config="{}"
            :import-config="{}"
            :export-config="{}"
            :sort-config="{remote:true}"
            :data="dataList"
            :checkbox-config="{}">
          <vxe-column type="seq" width="40"></vxe-column>
          <vxe-column type="checkbox"  width="40px"></vxe-column>
          <vxe-column 
            field="label"
            title="标签">
            <template slot-scope="scope">
              <el-link  type="primary" :underline="false" v-if="hasPermission('sys:dict:edit')" @click="edit(scope.row.id)">{{scope.row.label}}</el-link>
              <span v-else>{{scope.row.label}}</span>
            </template>
          </vxe-column >
          <vxe-column  title="键值" field="value"></vxe-column>
          <vxe-column  title="排序" field="sort"></vxe-column>
          <vxe-column title="操作" width="250px" fixed="right" align="center">
                <template slot-scope="scope">
                     <el-button v-if="hasPermission('sys:dict:edit')" type="text"  size="small" @click="edit(scope.row.id)">
                       修改
                    </el-button>
                    <el-divider direction="vertical"></el-divider>
                    <el-button v-if="hasPermission('sys:dict:del')" type="text" size="small" @click="del(scope.row.id)">
                      删除
                    </el-button>
                </template>
          </vxe-column>
        </vxe-table>
    <!-- 弹窗, 新增 / 修改 -->
    <dict-value-form ref="dictValueForm" @refreshDataList="refreshList"></dict-value-form>
  </div>
</template>

<script>
  import DictValueForm from './DictValueForm'
  import DictService from '@/api/sys/DictService'
  export default {
    data () {
      return {
        dataList: [],
        dictTypeId: '',
        loading: false
      }
    },
    props: ['dictTypeTitle'],
    components: {
      DictValueForm
    },
    dictService: null,
    created () {
      this.dictService = new DictService()
    },
    methods: {
      // 获取数据列表
      refreshList (dictTypeId) {
        this.loading = true
        if (dictTypeId) {
          this.dictTypeId = dictTypeId
        }
        this.dictService.getDictValue(this.dictTypeId).then(({data}) => {
          this.dataList = data
          this.loading = false
        })
      },
      // 新增
      add () {
        this.dictVisible = true
        this.$nextTick(() => {
          this.$refs.dictValueForm.init('add', {dictValueId: '', dictTypeId: this.dictTypeId})
        })
      },
        // 修改
      edit (id) {
        this.dictVisible = true
        this.$nextTick(() => {
          this.$refs.dictValueForm.init('edit', {dictValueId: id, dictTypeId: this.dictTypeId})
        })
      },
      // 删除
      del (id) {
        let ids = id || this.$refs.dictValueTable.getCheckboxRecords().map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.dictService.deleteDictValue(ids).then(({data}) => {
            this.$message.success(data)
            this.refreshList()
            this.$dictUtils.refreshDictList()
          })
        })
      },
      closeRight () {
        this.$emit('closeRight')
      }
    }
  }
</script>
