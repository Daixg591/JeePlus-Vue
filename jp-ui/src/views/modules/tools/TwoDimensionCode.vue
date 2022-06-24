<template>
<div class="el-scrollbar__wrap wrap-white padding-20">
  <div class="el-scrollbar__view">
    <el-form size="small" :inline="true" :model="inputForm" ref="inputForm" label-width="120px">
      <el-form-item label="二维码内容" prop="encoderContent" :rules="[{required: true, message:'内容不能为空', trigger:'blur'}]">
        <el-input  style="width: 500px" v-model="inputForm.encoderContent" placeholder="请输入要生成二维码的字符串"></el-input> 
          
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="doSubmit">提交</el-button>
      </el-form-item>
    </el-form>
    <div style="padding-left:120px; padding-top:20px" v-if="imageUrl">
        <el-image :src="imageUrl"/>
        <p>请使用微信扫一扫</p>
    </div>
  </div>
</div>
</template>

<script>
  import ToolsService from '@/api/tools/ToolsService'
  export default {
    data () {
      return {
        inputForm: {
          encoderContent: 'http://demo1.jeeplus.org/h5/#/'
        },
        imageUrl: ''
      }
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
            this.toolsService.createTwoDimensionCode({encoderContent: this.inputForm.encoderContent}).then(({data}) => {
              this.imageUrl = data.filePath + `?t=${new Date().getTime()}`
            })
          }
        })
      }
    }
  }
</script>