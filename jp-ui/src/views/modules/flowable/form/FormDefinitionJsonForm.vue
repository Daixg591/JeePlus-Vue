<template>
<div>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
         <fm-generate-form 
              :data="options" 
              ref="generateForm"
              v-if="visible"
            >
        </fm-generate-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false" icon="el-icon-circle-close">关闭</el-button>
      <el-button size="small" type="primary" @click="visible = false">确定</el-button>
    </span>
  </el-dialog>
</div>
</template>

<script>
  import FormDefinitionJsonService from '@/api/flowable/FormDefinitionJsonService'
  export default {
    data () {
      return {
        title: '预览表单',
        method: '',
        visible: false,
        loading: false,
        options: {list: []}
      }
    },
    formDefinitionJsonService: null,
    created () {
      this.formDefinitionJsonService = new FormDefinitionJsonService()
    },
    methods: {
      init (id) {
        this.loading = true
        this.formDefinitionJsonService.queryById(id).then(({data}) => {
          this.options = JSON.parse(data.json)
          this.visible = true
          this.loading = false
        })
      }
    }
  }
</script>