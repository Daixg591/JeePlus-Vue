<template>
<div>
<div class="el-scrollbar__wrap wrap-white padding-20">
  <div class="el-scrollbar__view">
     <el-page-header @back="goBack" content="编辑表" class="page-header"></el-page-header>
     <el-form size="small" :model="inputForm" ref="inputForm" v-loading="loading" label-width="150px">    
      <el-row :gutter="20" v-loading="loading">
        <el-col :span="24">
             <el-col :span="12">
                <el-form-item label="表名" prop="name" :rules="[  {required: true, message:'模型名称不能为空', trigger:'blur'} ]">
                  <el-input size="small" v-model="inputForm.name" :disabled="method === 'edit'" placeholder=""     ></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="模式" prop="schema">
                    <el-input size="small" v-model="inputForm.schema" :disabled="true" placeholder=""     ></el-input>
                </el-form-item>
             </el-col>
            <el-col :span="12">
                <el-form-item label="数据库" prop="catalog" >
                    <el-input size="small" v-model="inputForm.catalog" :disabled="true" placeholder="" ></el-input>
                </el-form-item>
             </el-col>
             <el-col :span="12">
                <el-form-item label="描述" prop="description">
                    <el-input size="small" v-model="inputForm.description" placeholder="请填写描述"     ></el-input>
                </el-form-item>
             </el-col>       
        </el-col>
        <el-col :span="24">
            <el-row>
              <el-col :span="12"><label style="font-wight:fold">数据源列配置</label></el-col>
              <el-button-group class="pull-right">
                  <el-button type="default" size="small" icon="el-icon-plus" @click="addRow">增加一列</el-button>
                  <el-button type="primary" size="small" icon="el-icon-circle-check" @click="doSubmit">保存</el-button>
              </el-button-group>
            </el-row>
           <el-table
            size="small"
            :data="inputForm.columns"
            style="width: 100%">
            <el-table-column
              prop="name"
              label="列名">
              <template slot-scope="scope">
                <el-input size="small" v-model="scope.row.name"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="type"
              label="类型">
               <template slot-scope="scope">
                 <el-select v-model="scope.row.type" size="small" placeholder="请选择"  style="width: 100%;">
                    <el-option
                      v-for="(item, index) in  columnTypes"
                      :key="index"
                      :label="item.name"
                      :value="item.name">
                    </el-option>
                </el-select>
              </template>
            </el-table-column>
             <el-table-column
              prop="size"
              label="长度">
              <template slot-scope="scope">
                <el-input size="small" v-model="scope.row.size"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="defaultValue"
              label="默认值">
              <template slot-scope="scope">
                <el-input size="small" v-model="scope.row.defaultValue"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="主键">
              <template slot-scope="scope">
                 <el-checkbox v-model="scope.row.primaryKey" >主键</el-checkbox>
              </template>
           </el-table-column>
           <el-table-column label="非空">
             <template slot-scope="scope">
                 <el-checkbox v-model="scope.row.required">非空</el-checkbox>
             </template>
            </el-table-column>
            <el-table-column label="自增">
              <template slot-scope="scope">
                 <el-checkbox v-model="scope.row.autoIncrement" >自增</el-checkbox>
              </template>
            </el-table-column>
            <el-table-column
              prop="description"
              label="说明">
              <template slot-scope="scope">
                <el-input size="small" v-model="scope.row.description"></el-input>
              </template>
            </el-table-column>
             <el-table-column title="操作" width="200px" fixed="right" align="center">
                <template  slot-scope="scope">
                  <el-button @click.native.prevent="deleteRow(scope.$index, inputForm.columns)" type="text" size="small">
                    移除
                  </el-button>
                </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
      </el-form>
</div>
</div>
</div>
</template>

<script>
  import SelectTree from '@/components/treeSelect/treeSelect.vue'
  import DataTableService from '@/api/database/DataTableService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        loading: false,
        columnTypes: [],
        inputForm: {
          dataSourceId: '',
          name: '',
          schema: '',
          catalog: '',
          description: '',
          columns: []
        }
      }
    },
    dataTableService: null,
    created () {
      this.dataTableService = new DataTableService()
    },
    activated () {
      this.$refs.inputForm.resetFields()
      this.inputForm.columns = []
      this.inputForm.dataSourceId = this.$route.query.dataSourceId
      this.inputForm.name = this.$route.query.name
      this.method = this.$route.query.method
      if (this.method === 'edit') {
        this.dataTableService.queryAlter(this.inputForm.dataSourceId, this.inputForm.name).then(({data}) => {
          this.inputForm = this.recoverNotNull(this.inputForm, data.table)
          this.columnTypes = data.columnTypes
        })
      } else {
        this.dataTableService.queryCreate(this.inputForm.dataSourceId).then(({data}) => {
          this.inputForm = this.recoverNotNull(this.inputForm, data.table)
          this.columnTypes = data.columnTypes
        })
      }
    },
    components: {
      SelectTree
    },
    methods: {
      deleteRow (index, rows) {
        rows.splice(index, 1)
      },
      addRow () {
        this.inputForm.columns.push({name: '', type: '', size: 64, defaultValue: '', primaryKey: false, required: false, autoIncrement: false, description: ''})
      },
      // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            if (this.method === 'add') {
              this.dataTableService.submitCreate(this.inputForm).then(({data}) => {
                this.$message.success(data)
                this.goBack()
                this.loading = false
              }).catch(() => {
                this.loading = false
              })
            } else {
              this.dataTableService.submitAlter(this.inputForm).then(({data}) => {
                this.$message.success(data)
                this.goBack()
                this.loading = false
              }).catch(() => {
                this.loading = false
              })
            }
          }
        })
      },
      goBack () {
        this.$store.dispatch('tagsView/delView', {fullPath: this.$route.fullPath})
        this.$router.push({path: `/database/datatable/Table`})
      }
    }
  }
</script>
<style scoped>
.page-header{
  margin-bottom: 24px;
  padding-bottom: 12px;
  border-bottom: 1px solid #eaeefb;
  overflow: hidden;
}
</style>