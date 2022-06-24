<template>
<div class="el-scrollbar__wrap wrap-white padding-20">
  <div class="el-scrollbar__view">
    <el-form size="small" :model="inputForm" ref="inputForm"
             label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="16">
            <el-form-item  label="发送到"  prop="emailAddress"
                :rules="[
                  {required: true, message:'收件地址不能为空', trigger:'blur'}
                 ]">
          		   <el-input v-model="inputForm.emailAddress" placeholder="输入多个邮件地址请用英文符号;隔开"></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="4">
            <el-form-item label-width="10px"> 
                 <el-button type="primary" @click="doSubmit">提交</el-button>
           </el-form-item>
        </el-col>
        <el-col :span="16">
            <el-form-item label="标题" prop="title"
                :rules="[
                  {required: true, message:'标题不能为空', trigger:'blur'}
                 ]">
			        <el-input v-model="inputForm.title" placeholder="请输入标题"     ></el-input>
	         </el-form-item>
        </el-col>
         <el-col :span="20">
            <el-form-item label="内容" prop="content" :rules="[
                  {required: true, message:'内容不能为空', trigger:'blur'}
                 ]">
              <WangeEditor ref="editor"   v-model="inputForm.content"/>
            </el-form-item>
        </el-col>
        </el-row>
    </el-form>
</div>
</div>
</template>

<script>
  import WangeEditor from '@/components/editor/WangEditor'
  import ToolsService from '@/api/tools/ToolsService'
  export default {
    data () {
      return {
        inputForm: {
          emailAddress: '',
          title: '',
          content: ''
        }
      }
    },
    components: {
      WangeEditor
    },
    toolsService: null,
    created () {
      this.toolsService = new ToolsService()
    },
    methods: {
      // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.toolsService.sendEmail(this.inputForm).then(({data}) => {
              this.$message.info({dangerouslyUseHTMLString: true,
                message: data})
            })
          }
        })
      }
    }
  }
</script>