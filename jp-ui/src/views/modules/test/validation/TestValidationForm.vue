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
            <el-form-item label="浮点数字" prop="num"
                :rules="[
                  {required: true, message:'浮点数字不能为空', trigger:'blur'},
                  {validator: validator.isFloat, trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.num" placeholder="请填写浮点数字"    max="69.3"  min="20.1" ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="整数" prop="num2"
                :rules="[
                  {required: true, message:'整数不能为空', trigger:'blur'},
                  {validator: validator.isDigits, trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.num2" placeholder="请填写整数"    max="30"  min="10" ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="字符串" prop="str"
                :rules="[
                  {required: true, message:'字符串不能为空', trigger:'blur'},
                  { min: 5, message: '最小长度是5个字符', trigger: 'blur' }
                 ]">
              <el-input v-model="inputForm.str" placeholder="请填写字符串"  maxlength="65"  minlength="5"   ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="邮件" prop="email"
                :rules="[
                  {required: true, message:'邮件不能为空', trigger:'blur'},
                  { min: 10, message: '最小长度是10个字符', trigger: 'blur' },
                  {validator: validator.isEmail, trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.email" placeholder="请填写邮件"  maxlength="60"  minlength="10"   ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="网址" prop="url"
                :rules="[
                  {required: true, message:'网址不能为空', trigger:'blur'},
                  { min: 10, message: '最小长度是10个字符', trigger: 'blur' },
                  {validator: validator.isURL, trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.url" placeholder="请填写网址"  maxlength="30"  minlength="10"   ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="日期" prop="newDate"
                :rules="[
                  {required: true, message:'日期不能为空', trigger:'blur'}
                 ]">
                <el-date-picker
                      style="width: 100%;"
                      v-model="inputForm.newDate"
                      type="datetime"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      placeholder="选择日期时间">
                    </el-date-picker>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="备注信息" prop="remarks"
                :rules="[
                  {required: true, message:'备注信息不能为空', trigger:'blur'}
                 ]">
          <el-input type="textarea" v-model="inputForm.remarks" placeholder="请填写备注信息"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="浮点数小于等于0" prop="c1"
                :rules="[
                  {required: true, message:'浮点数小于等于0不能为空', trigger:'blur'},
                  {validator: validator.isFloatLteZero, trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.c1" placeholder="请填写浮点数小于等于0"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="身份证号码" prop="c2"
                :rules="[
                  {required: true, message:'身份证号码不能为空', trigger:'blur'},
                  {validator: validator.isCardId, trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.c2" placeholder="请填写身份证号码"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="QQ号码" prop="c3"
                :rules="[
                  {required: true, message:'QQ号码不能为空', trigger:'blur'},
                  {validator: validator.isQq, trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.c3" placeholder="请填写QQ号码"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="手机号码" prop="c4"
                :rules="[
                  {required: true, message:'手机号码不能为空', trigger:'blur'},
                  {validator: validator.isMobile, trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.c4" placeholder="请填写手机号码"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="中英文数字下划线" prop="c5"
                :rules="[
                  {required: true, message:'中英文数字下划线不能为空', trigger:'blur'},
                  {validator: validator.stringCheck, trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.c5" placeholder="请填写中英文数字下划线"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="合法字符(a-z A-Z 0-9)" prop="c6"
                :rules="[
                  {required: true, message:'合法字符(a-z A-Z 0-9)不能为空', trigger:'blur'},
                  {validator: validator.isRightfulString, trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.c6" placeholder="请填写合法字符(a-z A-Z 0-9)"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="英语" prop="en"
                :rules="[
                  {validator: validator.isEnglish, trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.en" placeholder="请填写英语"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="汉字" prop="zn"
                :rules="[
                  {validator: validator.isChinese, trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.zn" placeholder="请填写汉字"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="汉英字符" prop="enzn"
                :rules="[
                  {validator: validator.isChineseChar, trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.enzn" placeholder="请填写汉英字符"     ></el-input>
           </el-form-item>
        </el-col>
        </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false">关闭</el-button>
      <el-button size="small" type="primary" v-if="method != 'view'" @click="doSubmit()" v-noMoreClick>确定</el-button>
    </span>
  </el-dialog>
</div>
</template>

<script>
  import TestValidationService from '@/api/test/validation/TestValidationService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          num: '',
          num2: '',
          str: '',
          email: '',
          url: '',
          newDate: '',
          remarks: '',
          c1: '',
          c2: '',
          c3: '',
          c4: '',
          c5: '',
          c6: '',
          en: '',
          zn: '',
          enzn: ''
        }
      }
    },
    components: {
    },
    testValidationService: null,
    created () {
      this.testValidationService = new TestValidationService()
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建测试校验`
        } else if (method === 'edit') {
          this.title = '修改测试校验'
        } else if (method === 'view') {
          this.title = '查看测试校验'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.testValidationService.queryById(this.inputForm.id).then(({data}) => {
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
            this.testValidationService.save(this.inputForm).then(({data}) => {
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

  
