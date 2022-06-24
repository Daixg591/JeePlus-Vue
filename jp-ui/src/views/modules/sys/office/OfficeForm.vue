<template>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form size="small" :model="inputForm" ref="inputForm" @keyup.enter.native="doSubmit()"
             label-width="80px" v-loading="loading" :class="method==='view'?'readonly':''" :disabled="method==='view'" @submit.native.prevent>
      <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item label="上级机构" prop="parent.id">
           <SelectTree 
             ref="officeTree"
             :props="{
                value: 'id',             // ID字段名
                label: 'name',         // 显示名称
                children: 'children'    // 子级字段名
              }"
            :url="`/sys/office/treeData?extId=${inputForm.id}&&showAll=1`"
            :value="inputForm.parent.id"
            :clearable="true" 
            :accordion="true"
            v-if="visible"
            @getValue="(value) => {inputForm.parent.id=value}"/>
          </el-form-item>
        </el-col>
         <el-col :span="12">
          <el-form-item label="归属区域" prop="areaDTO.id">
               <SelectTree 
                ref="areaTree"
                :props="{
                    value: 'id',             // ID字段名
                    label: 'name',         // 显示名称
                    children: 'children'    // 子级字段名
                  }"
                url="/sys/area/treeData"
                :value="inputForm.areaDTO.id"
                :clearable="true"
                :accordion="true"
                v-if="visible"
                @getValue="(value) => {inputForm.areaDTO.id=value}"/>

          </el-form-item>
          
        </el-col>
        <el-col :span="12">
          <el-form-item label="机构名称" prop="name" :rules=" [{required: true, message: '名称不能为空', trigger: 'blur'}]">
            <el-input v-model="inputForm.name" placeholder="机构名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="机构编码" prop="code" :rules="[{max: 50, message: '最大长度不能超过50个字符', trigger: 'blur'}]">
            <el-input v-model="inputForm.code" placeholder="机构编码"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="机构类型" prop="type" :rules=" [{required: true, message: '机构类型不能为空', trigger: 'blur'}]">
            <el-select v-model="inputForm.type" placeholder="请选择"  style="width: 100%;">
                <el-option
                  v-for="item in this.$dictUtils.getDictList('sys_office_type')"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="机构等级" prop="grade" :rules="[{required: true, message: '机构等级不能为空', trigger: 'blur'}]">
             <el-select v-model="inputForm.grade" placeholder="请选择"  style="width: 100%;">
                <el-option
                  v-for="item in this.$dictUtils.getDictList('sys_office_grade')"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否可用" prop="useable" :rules="[{required: true, message: '是否可用不能为空', trigger: 'blur'}]">
            <el-select v-model="inputForm.useable" placeholder="请选择"  style="width: 100%;">
                <el-option
                  v-for="item in this.$dictUtils.getDictList('yes_no')"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
            </el-select>
          </el-form-item>
        </el-col>
         <el-col :span="12">
          <el-form-item label="排序号" prop="sort">
            <el-input-number style="width:100%" :step="30" v-model="inputForm.sort"></el-input-number>
          </el-form-item>
         </el-col>
      </el-row>
   
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false" icon="el-icon-circle-close">关闭</el-button>
      <el-button size="small" v-if="method != 'view'" type="primary" @click="doSubmit()" icon="el-icon-circle-check" v-noMoreClick>确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import SelectTree from '@/components/treeSelect/treeSelect.vue'
  import OfficeService from '@/api/sys/OfficeService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          name: '',
          parent: {
            id: ''
          },
          sort: '30',
          areaDTO: {
            id: '',
            name: ''
          }, // 归属区域
          code: '', // 机构编码
          type: '', // 机构类型（1：公司；2：部门）
          grade: '', // 机构等级（1：一级；2：二级；3：三级；4：四级）
          address: '', // 联系地址
          zipCode: '', // 邮政编码
          master: '', // 负责人
          phone: '', // 电话
          fax: '', // 传真
          email: '', // 邮箱
          useable: '' // 是否可用
        }
      }
    },
    components: {
      SelectTree
    },
    officeService: null,
    created () {
      this.officeService = new OfficeService()
    },
    methods: {
      init (method, obj) {
        this.method = method
        if (method === 'add') {
          this.title = '新增机构'
        } else if (method === 'addChild') {
          this.title = '添加下级机构'
        } else if (method === 'edit') {
          this.title = '修改机构'
        } else if (method === 'view') {
          this.title = '查看机构'
        }
        this.visible = true
        this.$nextTick(() => {
          this.$refs['inputForm'].resetFields()
          this.inputForm.id = obj.id
          this.inputForm.parent.id = obj.parent.id
          this.inputForm.parent.name = obj.parent.name
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.officeService.queryById(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.officeService.save(this.inputForm).then(({data}) => {
              this.loading = false
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500
              })
              this.visible = false
              this.$emit('refreshDataList')
            }).catch(() => {
              this.loading = false
            })
          }
        })
      }
    }
  }
</script>
