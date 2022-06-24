<template>
<div>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form size="small" :model="inputForm" ref="inputForm" :class="method==='view'?'readonly':''" :disabled="method==='view'" v-loading="loading" @keyup.enter.native="doSubmit()"
             label-width="120px" @submit.native.prevent>
      <el-row  :gutter="15">
        <el-col :span="24">
            <el-form-item label="名称" prop="name"
                :rules="[
                  {required: true, message:'名称不能为空', trigger:'blur'}
                 ]">
			        <el-input v-model="inputForm.name" placeholder="请填写名称"></el-input>
	         </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="监听器类型" prop="listenerType"
                :rules="[
                 {required: true, message:'监听器类型不能为空', trigger:'blur'}
                 ]">
                    <el-radio-group v-model="inputForm.listenerType">
                          <el-radio v-model="listenerType" label="1">执行监听器</el-radio>
                          <el-radio v-model="listenerType" label="2">任务监听器</el-radio>
                    </el-radio-group>
	         </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="事件" prop="event"
                :rules="[
                 {required: true, message:'事件不能为空', trigger:'blur'}
                 ]">
		            <el-select v-model="inputForm.event" placeholder="请选择"  style="width: 100%;">
                          <el-option
                            v-for="item in events"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                          </el-option>
                      </el-select>
	         </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="值类型" prop="valueType"
                :rules="[
                 {required: true, message:'值类型不能为空', trigger:'blur'}
                 ]">
                    <el-radio-group v-model="inputForm.valueType">
                        <el-radio  label="1">类</el-radio>
                        <el-radio  label="2">表达式</el-radio>
                        <el-radio label="3">委托表达式</el-radio>
                    </el-radio-group>
	         </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item :label="valueLabel" prop="value"
                :rules="[
                 {required: true, message:'值不能为空', trigger:'blur'}
                 ]">
			        <el-input v-model="inputForm.value" placeholder="请填写值"     ></el-input>
	         </el-form-item>
        </el-col>
        </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false" icon="el-icon-circle-close">关闭</el-button>
      <el-button size="small" type="primary" v-if="method != 'view'" @click="doSubmit()" icon="el-icon-circle-check" v-noMoreClick>确定</el-button>
    </span>
  </el-dialog>
</div>
</template>

<script>
  import ListenerService from '@/api/flowable/ListenerService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        listenerTab: '0',
        valueLabel: '类',
        events: [{label: 'start', value: 'start'}, {label: 'take', value: 'take'}, {label: 'end', value: 'end'}],
        inputForm: {
          id: '',
          name: '',
          listenerType: '1',
          event: '',
          valueType: '1',
          value: ''
        }
      }
    },
    listenerService: null,
    created () {
      this.listenerService = new ListenerService()
    },
    watch: {
      'inputForm.listenerType' (val) {
        if (val === '1') {
          this.inputForm.event = ''
          this.events = [{label: 'start', value: 'start'}, {label: 'take', value: 'take'}, {label: 'end', value: 'end'}]
        } else {
          this.inputForm.event = ''
          this.events = [{label: 'start', value: 'start'}, {label: 'assignment', value: 'assignment'}, {label: 'complete', value: 'complete'}, {label: 'delete', value: 'delete'}]
        }
      },
      'inputForm.valueType' (val) {
        if (val === '1') {
          this.valueLabel = '类'
        } else if (val === '2') {
          this.valueLabel = '表达式'
        } else {
          this.valueLabel = '委托表达式'
        }
      }
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建监听器`
        } else if (method === 'edit') {
          this.title = '修改监听器'
        } else if (method === 'view') {
          this.title = '查看监听器'
        }
        this.visible = true
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.listenerTab = '0'
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.listenerService.queryById(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
            })
          }
        })
      },
      // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.listenerService.save(this.inputForm).then(({data}) => {
              this.loading = false
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
            })
          }
        })
      }
    }
  }
</script>