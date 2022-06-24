<template>
 <div v-loading="loading" style="min-height:50px">
        <fm-generate-form 
        
              :data="options" 
              :edit="edit"
              v-if="visible"
              class="readonly"
              :value = "formData"
              ref="generateForm"
            >
        </fm-generate-form>
 </div>
</template>

<script>
  import TaskService from '@/api/flowable/TaskService'
  import FormDefinitionService from '@/api/flowable/FormDefinitionService'
  import FormService from '@/api/flowable/FormService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        formData: {},
        dataBindMap: new Map(),
        options: {'list': [], 'config': {'labelWidth': 100, 'labelPosition': 'right', 'size': 'small', 'customClass': ''}}
      }
    },
    props: {
      processDefinitionId: {
        type: String,
        required: true
      },
      taskFormData: {
        type: Array,
        required: false,
        default: []
      },
      edit: {
        type: Boolean,
        default: true
      }
    },
    taskService: null,
    formDefinitionService: null,
    formService: null,
    created () {
      this.taskService = new TaskService()
      this.formDefinitionService = new FormDefinitionService()
      this.formService = new FormService()
    },
    methods: {
      generateModel (genList) {
        for (let i = 0; i < genList.length; i++) {
          if (genList[i].type === 'grid') {
            genList[i].columns.forEach(item => {
              this.generateModel(item.list)
            })
          } else if (genList[i].type === 'tabs') {
            genList[i].tabs.forEach(item => {
              this.generateModel(item.list)
            })
          } else {
            // 处理老版本没有dataBind值的情况，默认绑定数据
            if (genList[i].options.dataBind) {
              this.dataBindMap.set(genList[i].model, genList[i])
            }
          }
        }
      },
      createForm (id) {
        if (id) {
          this.loading = true
          this.visible = false
          this.formData = {}
          this.formDefinitionService.queryByJsonId(id).then(({data}) => {
            this.options = JSON.parse(data.formDefinitionJson.json)
            this.dataBindMap.clear()
            this.generateModel(this.options.list)
            setTimeout(() => {
              this.loading = false
              this.visible = true
              this.$nextTick(() => {
                let disabledArra = []
                let showArra = []
                this.taskFormData.forEach((item) => {
                  if (item.value !== undefined) {
                    this.formData[`${item.id}`] = item.value
                  }

                  if (item.readable === true) {
                    showArra.push(`${item.id}`)
                  }
                  if (item.writable === false) {
                    disabledArra.push(`${item.id}`)
                  }
                })
                let hideArra = this.$refs.generateForm.getDataBindFields().filter((field) => {
                  if (!showArra.includes(field)) {
                    return true
                  }
                })
                for (let key in this.formData) {
                  let dataField = this.dataBindMap.get(key)
                  if (dataField && (dataField['type'] === 'checkbox' ||
                        dataField['type'] === 'imgupload' ||
                        dataField['type'] === 'table' ||
                        (dataField['type'] === 'select' && dataField.options.multiple) ||
                        dataField['type'] === 'fileupload')) {
                    if (this.formData[key] && typeof this.formData[key] === 'string') {
                      this.formData[key] = JSON.parse(this.formData[key])
                    } else if (!this.formData[key]) {
                      this.formData[key] = []
                    }
                  }
                  if (dataField && (dataField['type'] === 'number' || dataField['options'].dataType === 'number')) {
                    if (this.formData[key] !== undefined && this.formData[key] !== '' && typeof this.formData[key] === 'string') {
                      this.formData[key] = JSON.parse(this.formData[key])
                    }
                  }
                }
                this.$refs.generateForm.hide(hideArra)
                this.$refs.generateForm.disabled(disabledArra, true)
                this.$refs.generateForm.setData(this.formData)
              })
            }, 500)
          }).catch(() => {
            this.loading = false
            this.visible = true
          })
        } else {
          this.$message.info('没有关联流程表单!')
          this.visible = true
        }
      },
      submitStartFormData (vars, callback) {
        this.$refs.generateForm.getData().then(data => {
          this.loading = true
          this.formService.submitStartFormData({
            id: this.id,
            ...vars,
            data: JSON.stringify(data)
          }).then(({data}) => {
            this.visible = false
            this.loading = false
            this.$message.success('启动成功')
            callback(data)
          })
        }).catch(e => {
          this.loading = false
        })
      },
      submitTaskFormData (vars, procInsId, taskId, assignee, comment, callback) {
        this.$refs.generateForm.getData().then(data => {
          data = {...vars, ...data}
          this.loading = true
          this.formService.submitTaskFormData({

            id: this.id,
            procInsId: procInsId,
            taskId: taskId,
            assignee: assignee,
            comment: comment,
            data: JSON.stringify(data)
  
          }).then(({data}) => {
            this.visible = false
            this.loading = false
            this.$message.success('提交成功')
            callback(data)
          })
        }).catch(e => {
          this.loading = false
        })
      }
    }
  }
</script>