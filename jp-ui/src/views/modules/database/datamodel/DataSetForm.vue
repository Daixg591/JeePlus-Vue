<template>
<div class="el-scrollbar__wrap wrap-white padding-20">
  <div class="el-scrollbar__view">    
      <el-row :gutter="20" v-loading="loading">
        <el-col :span="24">
           <el-row :gutter="20">
              <el-col :span="12"><h3>数据模型配置</h3></el-col>
               <el-col :span="12" style="text-align:right">
                  <el-button v-if="$route.query.method!='view'" type="primary" @click="doSubmit" size="small">保存数据模型</el-button>
                  <el-button type="info" size="small" @click="goBack">返回</el-button>
                </el-col>
           </el-row>
        </el-col>
        <el-col :span="12">
          <el-form size="small" :model="inputForm" ref="inputForm" v-loading="loading" label-width="150px">
                        <el-form-item label="目标数据库" prop="dataSource.id"
                            :rules="[
                              {required: true, message:'目标数据库不能为空', trigger:'blur'}
                            ]">
                          <SelectTree
                            ref="dataSource"
                            :props="{
                                value: 'id',             // ID字段名
                                label: 'label',         // 显示名称
                                children: 'children'    // 子级字段名
                              }"

                            url="/database/datalink/dataSource/treeData"
                            :value="inputForm.dataSource.id"
                            :clearable="true"
                            :accordion="true"
                            @getValue="(value) => {inputForm.dataSource.id=value}"/>
                      </el-form-item>
                        <el-form-item label="模型名称" prop="name"
                            :rules="[
                              {required: true, message:'模型名称不能为空', trigger:'blur'}
                            ]">
                          <el-input v-model="inputForm.name" placeholder="请填写模型名称"     ></el-input>
                      </el-form-item>
                        <el-form-item label="sql语句" prop="sqlCmd"
                            :rules="[
                              {required: true, message:'sql语句不能为空', trigger:'blur'}
                            ]">
                          <editor v-model="inputForm.sqlCmd" @init="editorInitSQL" lang="sql" height="200px" style="border: 1px solid #d9d9d9;"></editor>
                      </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="12">
           <el-table
            :data="paramForm.tableData">
             <el-table-column
              type="index"
              width="50">
            </el-table-column>
            <el-table-column
              prop="field"
              label="参数名">
               <template slot-scope="scope">
                <el-input v-model="scope.row.field" placeholder="请输入内容"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="defaultValue"
              label="默认值">
               <template slot-scope="scope">
                <el-input v-model="scope.row.defaultValue" placeholder="请输入内容"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              fixed="right"
              :key="Math.random()"
              label="操作">
              <template slot-scope="scope">
                <el-button
                  @click.native.prevent="deleteRow(scope.$index, paramForm.tableData)"
                  type="text"
                  size="small">
                  移除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
           <el-button type="primary" size="small" @click="addRow" style="margin-top:10px;margin-bottom:10px">增加参数</el-button>
           <el-alert
            title="SQL中添加参数的方式：格式：{#参数名#}，示例：select * from table where id = '{#ID#}'"
            :closable="false"
            type="success">
          </el-alert>
          <el-button type="primary" @click="doPreviewTable" size="small" style="margin-top:10px;margin-bottom:10px"><i class="fa fa-eye"></i> 解析</el-button>
          <el-button type="primary" @click="doPreviewData" size="small" style="margin-top:10px;margin-bottom:10px"> <i class="fa fa-eye"></i> 预览数据</el-button>
        </el-col>
        <el-col :span="24">
          <h3>数据源列配置</h3>
           <el-table
            :data="columnForm.columnList"
            style="width: 100%">
            <el-table-column
              prop="name"
              label="字段名"
              >
            </el-table-column>
            <el-table-column
              prop="type"
              label="类型"
              >
            </el-table-column>
            <el-table-column
              prop="label"
              label="标签">
              <template slot-scope="scope">
                <el-input v-model="scope.row.label"></el-input>
              </template>
            </el-table-column>
             <el-table-column>
                 <template slot="header">
                    <el-checkbox v-model="columnForm.isNeedAll" :indeterminate="isIndeterminate"  @change="handleCheckAllChange">参与分析</el-checkbox>
                </template>
                <template slot-scope="scope">
                     <el-checkbox v-model="scope.row.isNeed" @change="handleCheckedNeedChange"></el-checkbox>
                </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    <el-dialog title="数据预览" :visible.sync="previewVisible"  v-dialogDrag>
     <el-tabs type="border-card">
        <el-tab-pane label="HTML">
              <el-table
                :data="previewData.json"
                style="height:500px; width: 100%">
                <el-table-column
                  v-for="(value, key, index) in previewData.column"
                  :key = "index"
                  :prop="key"
                  :label="key"
                  width="180">
                </el-table-column>
              </el-table>
        </el-tab-pane>
        <el-tab-pane label="JSON">
            <editor v-model="previewData.json_str" @init="editorInitJSON" lang="json" theme="twilight" width="100%" height="500px"></editor>
        </el-tab-pane>
        <el-tab-pane label="XML">
            <editor v-model="previewData.xml" @init="editorInitXML" lang="xml" theme="twilight" width="100%" height="500px"></editor>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
</div>
</div>
</template>

<script>
  import omit from 'lodash.omit'
  import SelectTree from '@/components/treeSelect/treeSelect.vue'
  import DataSetService from '@/api/database/DataSetService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        loading: false,
        previewVisible: false,
        isIndeterminate: false,
        inputForm: {
          id: '',
          dataSource: {
            id: ''
          },
          name: '',
          sqlCmd: ''
        },
        paramForm: {
          tableData: []
        },
        previewData: {
          column: [],
          html: '',
          json: [],
          json_str: '',
          xml: ''
        },
        columnForm: {
          isNeedAll: true,
          columnList: []
        }
      }
    },
    dataSetService: null,
    created () {
      this.dataSetService = new DataSetService()
    },
    activated () {
      this.$refs.inputForm.resetFields()
      this.inputForm.id = this.$route.query.id
      if (this.inputForm.id) {
        this.dataSetService.queryById(this.inputForm.id).then(({data}) => {
          this.inputForm = this.recover(this.inputForm, data)
          this.columnForm.columnList = data.columnList
          this.paramForm.tableData = data.params
        })
      }
    },
    components: {
      SelectTree,
      editor: require('vue2-ace-editor')
    },
    methods: {
      deleteRow (index, rows) {
        rows.splice(index, 1)
      },
      addRow () {
        this.paramForm.tableData.push({field: '', defaultValue: ''})
      },
      doPreviewData: function () {
        let fields = this.paramForm.tableData.map((row) => {
          return row.field
        })
        let defaultValues = this.paramForm.tableData.map((row) => {
          return row.defaultValue
        })
        defaultValues.push('')
        this.dataSetService.exec({
          dataSourceId: this.inputForm.dataSource.id,
          sql: this.inputForm.sqlCmd,
          field: fields,
          defaultValue: defaultValues
        }).then(({data}) => {
          this.previewData.json = data.json
          this.previewData.json_str = JSON.stringify(data.json)
          this.previewData.xml = data.xml
          this.previewData.column = data.json[0] || { }
          this.previewVisible = true
        })
      },
      doPreviewTable () {
        let fields = this.paramForm.tableData.map((row) => {
          return row.field
        })
        let defaultValues = this.paramForm.tableData.map((row) => {
          return row.defaultValue
        })
        defaultValues.push('')
        this.dataSetService.getMeta({
          dataSourceId: this.inputForm.dataSource.id,
          sql: this.inputForm.sqlCmd,
          field: fields,
          defaultValue: defaultValues
        }).then(({data}) => {
          this.columnForm.isNeedAll = true
          data.forEach((column) => {
            column.isNeed = true
          })
          this.columnForm.columnList = JSON.parse(JSON.stringify(data)).map((column) => {
            return this.columnForm.columnList.filter((oldColumn) => {
              return oldColumn.name === column.name
            })[0] || column
          })
        })
      },
      handleCheckAllChange (val) {
        this.columnForm.columnList.forEach((column) => {
          column.isNeed = val
        })
        this.columnForm.columnList = JSON.parse(JSON.stringify(this.columnForm.columnList))
        this.isIndeterminate = false
      },
      handleCheckedNeedChange (val) {
        let trueCount = 0
        let falseCount = 0
        this.columnForm.columnList.forEach((column) => {
          if (column.isNeed) {
            trueCount++
          } else {
            falseCount++
          }
        })
        this.isIndeterminate = trueCount > 0 && falseCount > 0
        this.columnForm.isNeedAll = trueCount > 0
      },
      editorInitXML: function () {
        require('brace/ext/language_tools') // language extension prerequsite...
        require('brace/mode/xml')
        require('brace/theme/twilight')
      },
      editorInitSQL: function () {
        require('brace/ext/language_tools') // language extension prerequsite...
        require('brace/mode/sql')
        require('brace/theme/chrome')
      },
      editorInitJSON: function () {
        require('brace/ext/language_tools') // language extension prerequsite...
        require('brace/mode/json')
        require('brace/theme/twilight')
      },
      // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            let params = []
            let columnList = []
            this.paramForm.tableData.forEach((item, index) => {
              item.sort = index
              params.push(omit(item, 'id'))
            })
            this.columnForm.columnList.forEach((item, index) => {
              item.sort = index
              columnList.push(omit(item, 'id'))
            })
            this.dataSetService.save({
              ...this.inputForm,
              params: params,
              columnList: columnList

            }).then(({data}) => {
              this.loading = false
              this.goBack()
            }).catch(() => {
              this.loading = false
            })
          }
        }).catch(() => {
          this.loading = false
        })
      },
      goBack () {
        this.$store.dispatch('tagsView/delView', {fullPath: this.$route.fullPath})
        this.$router.push('/database/datamodel/DataSetList')
      }
    }
  }
</script>