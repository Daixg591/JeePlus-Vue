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
            <el-form-item label="学生" prop="student.id"
                :rules="[
                  {required: true, message:'学生不能为空', trigger:'blur'}
                 ]">
          <GridSelect
            title="选择学生"
            labelName = 'name'
            labelValue = 'id'
            :value = "inputForm.student.id"
            :limit="1"
            @getValue='(value) => {inputForm.student.id=value}'
            :columns="[
            {
              prop: 'name',
              label: '姓名'
            }
            ]"
            :searchs="[
            {
              prop: 'name',
              label: '姓名'
            }
            ]"
            dataListUrl="/test/manytomany/student/list"
            queryEntityUrl="/test/manytomany/student/queryById">
          </GridSelect>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="课程" prop="course.id"
                :rules="[
                  {required: true, message:'课程不能为空', trigger:'blur'}
                 ]">
          <GridSelect
            title="选择课程"
            labelName = 'name'
            labelValue = 'id'
            :value = "inputForm.course.id"
            :limit="1"
            @getValue='(value) => {inputForm.course.id=value}'
            :columns="[
            {
              prop: 'name',
              label: '课程名'
            }
            ]"
            :searchs="[
            {
              prop: 'name',
              label: '课程名'
            }
            ]"
            dataListUrl="/test/manytomany/course/list"
            queryEntityUrl="/test/manytomany/course/queryById">
          </GridSelect>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="分数" prop="score"
                :rules="[
                  {required: true, message:'分数不能为空', trigger:'blur'},
                  {validator: validator.isDigits, trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.score" placeholder="请填写分数"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="备注信息" prop="remarks"
                :rules="[
                 ]">
          <el-input type="textarea" v-model="inputForm.remarks" placeholder="请填写备注信息"     ></el-input>
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
  import GridSelect from '@/components/gridSelect'
  import StudentCourseService from '@/api/test/manytomany/StudentCourseService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          student: {
            id: ''
          },
          course: {
            id: ''
          },
          score: '',
          remarks: ''
        }
      }
    },
    components: {
      GridSelect
    },
    studentCourseService: null,
    created () {
      this.studentCourseService = new StudentCourseService()
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建学生课程记录`
        } else if (method === 'edit') {
          this.title = '修改学生课程记录'
        } else if (method === 'view') {
          this.title = '查看学生课程记录'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.studentCourseService.queryById(this.inputForm.id).then(({data}) => {
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
            this.studentCourseService.save(this.inputForm).then(({data}) => {
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

  
