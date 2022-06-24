<template>
<div>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading" :class="method==='view'?'readonly':''"  :disabled="method==='view'"
             label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="12">
            <el-form-item label="用户" prop="tuser.id"
                :rules="[
                  {required: true, message:'用户不能为空', trigger:'blur'}
                 ]">
                <user-select :limit='1' :value="inputForm.tuser.id" @getValue='(value) => {inputForm.tuser.id=value}'></user-select>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="所属部门" prop="office.id"
                :rules="[
                  {required: true, message:'所属部门不能为空', trigger:'blur'}
                 ]">
          <SelectTree
                      ref="office"
                      :props="{
                          value: 'id',             // ID字段名
                          label: 'name',         // 显示名称
                          children: 'children'    // 子级字段名
                        }"

                      url="/sys/office/treeData?type=2"
                      :value="inputForm.office.id"
                      :clearable="true"
                      :accordion="true"
                      @getValue="(value) => {inputForm.office.id=value}"/>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="所属区域" prop="area.id"
                :rules="[
                  {required: true, message:'所属区域不能为空', trigger:'blur'}
                 ]">
              <SelectTree
                      ref="area"
                      :props="{
                          value: 'id',             // ID字段名
                          label: 'name',         // 显示名称
                          children: 'children'    // 子级字段名
                        }"

                      url="/sys/area/treeData"
                      :value="inputForm.area.id"
                      :clearable="true"
                      :accordion="true"
                      @getValue="(value) => {inputForm.area.id=value}"/>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="名称" prop="name"
                :rules="[
                  {required: true, message:'名称不能为空', trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.name" placeholder="请填写名称"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="性别" prop="sex"
                :rules="[
                  {required: true, message:'性别不能为空', trigger:'blur'}
                 ]">
                    <el-radio-group v-model="inputForm.sex">
                        <el-radio v-for="item in $dictUtils.getDictList('sex')" :label="item.value" :key="item.value">{{item.label}}</el-radio>
                    </el-radio-group>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="身份证照片" prop="file"
                :rules="[
                 ]">
              <el-upload ref="file"
              v-if="visible"
              list-type="picture-card"
                    :action="`${this.$http.BASE_URL}/sys/file/webupload/upload?uploadPath=/test/onetomany/testDataMainForm`"
                    :headers="{token: $cookie.get('token')}"
                    :on-preview="(file, fileList) => {
                        $alert(`<img style='width:100%' src=' ${(file.response && file.response.url) || file.url}'/>`,  {
                          dangerouslyUseHTMLString: true,
                          showConfirmButton: false,
                          closeOnClickModal: true,
                          customClass: 'showPic'
                        });
                    }"
                    :on-success="(response, file, fileList) => {
                       inputForm.file = fileList.map(item => (item.response && item.response.url) || item.url).join('|')
                    }"
                    :on-remove="(file, fileList) => {
                      $http.delete(`/sys/file/webupload/deleteByUrl?url=${(file.response && file.response.url) || file.url}`).then(({data}) => {
                        $message.success(data)
                      })
                      inputForm.file = fileList.map(item => item.url).join('|')
                    }"
                    :before-remove="(file, fileList) => {
                      return $confirm(`确定移除 ${file.name}？`)
                    }"
                    multiple
                    :limit="5"
                    :on-exceed="(files, fileList) =>{
                      $message.warning(`当前限制选择 5 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
                    }"
                    :file-list="fileArra">
                    <i class="el-icon-plus"></i>
                  </el-upload>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="加入日期" prop="inDate"
                :rules="[
                 ]">
                <el-date-picker
                      style="width: 100%;"
                      v-model="inputForm.inDate"
                      type="datetime"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      placeholder="选择日期时间">
                    </el-date-picker>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="备注信息" prop="remarks"
                :rules="[
                 ]">
          <el-input type="textarea" v-model="inputForm.remarks" placeholder="请填写备注信息"     ></el-input>
           </el-form-item>
        </el-col>
    <el-col :span="24">
     <el-form-item label-width="0">
            <el-tabs v-model="testDataMainFormTab">
            <el-tab-pane label="汽车票">
                  <el-button size="small" @click="addTestDataChild23Row" type="primary">新增</el-button>
                  <el-table
                  class="table"
                  size="small"
                  :data="inputForm.testDataChild23DTOList.filter(function(item){ return item.delFlag !== '1'})"
                  style="width: 100%">
                  <el-table-column
                    prop="startArea.name"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="出发地">
                  </el-table-column>
                  <el-table-column
                    prop="endArea.name"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="目的地">
                  </el-table-column>
                <el-table-column
                    prop="startTime"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="出发时间">
                  </el-table-column>
                <el-table-column
                    prop="endTime"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="到达时间">
                  </el-table-column>
                <el-table-column
                    prop="price"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="代理价格">
                  </el-table-column>
                <el-table-column
                    prop="remarks"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="备注信息">
                  </el-table-column>
                  <el-table-column
                    fixed="right"
                    label="操作"
                    width="150">
                    <template slot-scope="scope">
                      <el-button @click="viewTestDataChild23Row(scope.row)" type="text" size="small">查看</el-button>
                      <el-button  @click="editTestDataChild23Row(scope.row)" type="text" size="small">编辑</el-button>
                      <el-button  @click="delTestDataChild23Row(scope.row)" type="text" size="small">删除</el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </el-tab-pane>
            <el-tab-pane label="火车票">
                  <el-button size="small" @click="addTestDataChild21Row" type="primary">新增</el-button>
                  <el-table
                  class="table"
                  size="small"
                  :data="inputForm.testDataChild21DTOList.filter(function(item){ return item.delFlag !== '1'})"
                  style="width: 100%">
                  <el-table-column
                    prop="startArea.name"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="出发地">
                  </el-table-column>
                  <el-table-column
                    prop="endArea.name"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="目的地">
                  </el-table-column>
                <el-table-column
                    prop="starttime"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="出发时间">
                  </el-table-column>
                <el-table-column
                    prop="endtime"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="到达时间">
                  </el-table-column>
                <el-table-column
                    prop="price"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="代理价格">
                  </el-table-column>
                <el-table-column
                    prop="remarks"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="备注信息">
                  </el-table-column>
                  <el-table-column
                    fixed="right"
                    label="操作"
                    width="150">
                    <template slot-scope="scope">
                      <el-button @click="viewTestDataChild21Row(scope.row)" type="text" size="small">查看</el-button>
                      <el-button  @click="editTestDataChild21Row(scope.row)" type="text" size="small">编辑</el-button>
                      <el-button  @click="delTestDataChild21Row(scope.row)" type="text" size="small">删除</el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </el-tab-pane>
            <el-tab-pane label="飞机票">
                  <el-button size="small" @click="addTestDataChild22Row" type="primary">新增</el-button>
                  <el-table
                  class="table"
                  size="small"
                  :data="inputForm.testDataChild22DTOList.filter(function(item){ return item.delFlag !== '1'})"
                  style="width: 100%">
                  <el-table-column
                    prop="startArea.name"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="出发地">
                  </el-table-column>
                  <el-table-column
                    prop="endArea.name"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="目的地">
                  </el-table-column>
                <el-table-column
                    prop="startTime"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="出发时间">
                  </el-table-column>
                <el-table-column
                    prop="endTime"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="到达时间">
                  </el-table-column>
                <el-table-column
                    prop="price"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="代理价格">
                  </el-table-column>
                <el-table-column
                    prop="remarks"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="备注信息">
                  </el-table-column>
                  <el-table-column
                    fixed="right"
                    label="操作"
                    width="150">
                    <template slot-scope="scope">
                      <el-button @click="viewTestDataChild22Row(scope.row)" type="text" size="small">查看</el-button>
                      <el-button  @click="editTestDataChild22Row(scope.row)" type="text" size="small">编辑</el-button>
                      <el-button  @click="delTestDataChild22Row(scope.row)" type="text" size="small">删除</el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </el-tab-pane>
            </el-tabs>
           </el-form-item>
          </el-col>
        </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false">关闭</el-button>
      <el-button size="small" type="primary" v-if="method != 'view'" @click="doSubmit()" v-noMoreClick>确定</el-button>
    </span>
  </el-dialog>
  <TestDataChild23Form ref="testDataChild23Form" @addRow="saveTestDataChild23Row(arguments)"></TestDataChild23Form>
  <TestDataChild21Form ref="testDataChild21Form" @addRow="saveTestDataChild21Row(arguments)"></TestDataChild21Form>
  <TestDataChild22Form ref="testDataChild22Form" @addRow="saveTestDataChild22Row(arguments)"></TestDataChild22Form>
</div>
</template>

<script>
  import TestDataChild23Form from './TestDataChild23Form'
  import TestDataChild21Form from './TestDataChild21Form'
  import TestDataChild22Form from './TestDataChild22Form'
  import UserSelect from '@/components/userSelect'
  import SelectTree from '@/components/treeSelect/treeSelect.vue'
  import TestDataMainFormService from '@/api/test/onetomany/TestDataMainFormService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        testDataMainFormTab: '0',
        fileArra: [],
        inputForm: {
          id: '',
          testDataChild23DTOList: [],
          testDataChild21DTOList: [],
          testDataChild22DTOList: [],
          tuser: {
            id: ''
          },
          office: {
            id: ''
          },
          area: {
            id: ''
          },
          name: '',
          sex: '',
          file: '',
          inDate: '',
          remarks: ''
        }
      }
    },
    components: {
      UserSelect,
      SelectTree,
      TestDataChild23Form,
      TestDataChild21Form,
      TestDataChild22Form
    },
    testDataMainFormService: null,
    created () {
      this.testDataMainFormService = new TestDataMainFormService()
    },
    methods: {
      init (method, id) {
        this.method = method
        if (method === 'add') {
          this.title = `新建票务代理`
        } else if (method === 'edit') {
          this.title = '修改票务代理'
        } else if (method === 'view') {
          this.title = '查看票务代理'
        }
        this.fileArra = []
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.inputForm.id = id
          this.testDataMainFormTab = '0'
          this.inputForm.testDataChild23DTOList = []
          this.inputForm.testDataChild21DTOList = []
          this.inputForm.testDataChild22DTOList = []
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.testDataMainFormService.queryById(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.inputForm.file.split('|').forEach((item) => {
                if (item.trim().length > 0) {
                  this.fileArra.push({name: decodeURIComponent(item.substring(item.lastIndexOf('/') + 1)), url: item})
                }
              })
              this.loading = false
            })
          }
        })
      },
      saveTestDataChild23Row (child) {
        if (child[0] === '') {
          this.inputForm.testDataChild23DTOList.push(child[1])
        } else {
          this.inputForm.testDataChild23DTOList.forEach((item, index) => {
            if (item === child[0]) {
              this.inputForm.testDataChild23DTOList.splice(index, 1, child[1])
            }
          })
        }
      },
      addTestDataChild23Row (child) {
        this.$refs.testDataChild23Form.init('add')
      },
      viewTestDataChild23Row (child) {
        this.$refs.testDataChild23Form.init('view', child)
      },
      editTestDataChild23Row (child) {
        this.$refs.testDataChild23Form.init('edit', child)
      },
      delTestDataChild23Row (child) {
        this.inputForm.testDataChild23DTOList.forEach((item, index) => {
          if (item === child && item.id === '') {
            this.inputForm.testDataChild23DTOList.splice(index, 1)
          } else if (item === child) {
            item.delFlag = '1'
            this.inputForm.testDataChild23DTOList.splice(index, 1, item)
          }
        })
      },
      saveTestDataChild21Row (child) {
        if (child[0] === '') {
          this.inputForm.testDataChild21DTOList.push(child[1])
        } else {
          this.inputForm.testDataChild21DTOList.forEach((item, index) => {
            if (item === child[0]) {
              this.inputForm.testDataChild21DTOList.splice(index, 1, child[1])
            }
          })
        }
      },
      addTestDataChild21Row (child) {
        this.$refs.testDataChild21Form.init('add')
      },
      viewTestDataChild21Row (child) {
        this.$refs.testDataChild21Form.init('view', child)
      },
      editTestDataChild21Row (child) {
        this.$refs.testDataChild21Form.init('edit', child)
      },
      delTestDataChild21Row (child) {
        this.inputForm.testDataChild21DTOList.forEach((item, index) => {
          if (item === child && item.id === '') {
            this.inputForm.testDataChild21DTOList.splice(index, 1)
          } else if (item === child) {
            item.delFlag = '1'
            this.inputForm.testDataChild21DTOList.splice(index, 1, item)
          }
        })
      },
      saveTestDataChild22Row (child) {
        if (child[0] === '') {
          this.inputForm.testDataChild22DTOList.push(child[1])
        } else {
          this.inputForm.testDataChild22DTOList.forEach((item, index) => {
            if (item === child[0]) {
              this.inputForm.testDataChild22DTOList.splice(index, 1, child[1])
            }
          })
        }
      },
      addTestDataChild22Row (child) {
        this.$refs.testDataChild22Form.init('add')
      },
      viewTestDataChild22Row (child) {
        this.$refs.testDataChild22Form.init('view', child)
      },
      editTestDataChild22Row (child) {
        this.$refs.testDataChild22Form.init('edit', child)
      },
      delTestDataChild22Row (child) {
        this.inputForm.testDataChild22DTOList.forEach((item, index) => {
          if (item === child && item.id === '') {
            this.inputForm.testDataChild22DTOList.splice(index, 1)
          } else if (item === child) {
            item.delFlag = '1'
            this.inputForm.testDataChild22DTOList.splice(index, 1, item)
          }
        })
      },
      // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.testDataMainFormService.save(this.inputForm).then(({data}) => {
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          }
        })
      }
    }
  }
</script>

  
