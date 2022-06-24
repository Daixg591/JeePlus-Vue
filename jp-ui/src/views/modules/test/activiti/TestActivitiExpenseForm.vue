<template>
<div>
    <el-form size="small" :model="inputForm" ref="inputForm" v-loading="loading" :disabled="formReadOnly" label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="24">
            <el-form-item label="员工姓名" prop="user.id"
                :rules="[
                  {required: true, message:'员工姓名不能为空', trigger:'blur'}
                 ]">
                <user-select :limit='1' :value="inputForm.user.id" @getValue='(value) => {inputForm.user.id=value}'></user-select>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="报销费用" prop="cost"
                :rules="[
                  {required: true, message:'报销费用不能为空', trigger:'blur'},
                  {validator: validator.isFloatGtZero, trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.cost" placeholder="请填写报销费用"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="归属部门" prop="office.id"
                :rules="[
                  {required: true, message:'归属部门不能为空', trigger:'blur'}
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
        <el-col :span="24">
            <el-form-item label="报销事由" prop="reason"
                :rules="[
                  {required: true, message:'报销事由不能为空', trigger:'blur'}
                 ]">
          <el-input type="textarea" v-model="inputForm.reason" placeholder="请填写报销事由"     ></el-input>
           </el-form-item>
        </el-col>
        </el-row>
    </el-form>
</div>
</template>

<script>
  import UserSelect from '@/components/userSelect'
  import SelectTree from '@/components/treeSelect/treeSelect.vue'
  import TestActivitiExpenseService from '@/api/test/activiti/TestActivitiExpenseService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          user: {
            id: ''
          },
          cost: '',
          office: {
            id: ''
          },
          reason: ''
        }
      }
    },
    props: {
      businessId: {
        type: String,
        default: ''
      },
      formReadOnly: {
        type: Boolean,
        default: false
      }
    },
    components: {
      UserSelect,
      SelectTree
    },
    testActivitiExpenseService: null,
    created () {
      this.testActivitiExpenseService = new TestActivitiExpenseService()
    },
    watch: {
      'businessId': {
        handler (newVal) {
          if (this.businessId) {
            this.init(this.businessId)
          } else {
            this.$nextTick(() => {
              this.$refs.inputForm.resetFields()
            })
          }
        },
        immediate: true,
        deep: false
      }
    },
    methods: {
      init (id) {
        if (id) {
          this.loading = true
          this.inputForm.id = id
          this.$nextTick(() => {
            this.$refs.inputForm.resetFields()
            this.testActivitiExpenseService.queryById(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          })
        }
      },
      // 表单提交
      saveForm (callback) {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.testActivitiExpenseService.save(this.inputForm).then(({data}) => {
              callback(data.businessTable, data.businessId)
            }).catch(() => {
              this.loading = false
            })
          }
        })
      }
    }
  }
</script>

  
