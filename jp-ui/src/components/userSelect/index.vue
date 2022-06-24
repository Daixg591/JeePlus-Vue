<template>
<div>
    <el-input placeholder="请选择" :size="size" :disabled="disabled"  :readonly="readonly" style="line-hight:40px" v-model="name" class="input-with-select">
      <el-button slot="append" :disabled="disabled"  :readonly="readonly" @click="showUserSelect" icon="el-icon-search"></el-button>
    </el-input>
    <user-select ref="userSelect" @doSubmit="selectUsersToInput" :limit="limit" :selectData="selectData"></user-select>
</div>
</template>
<script>
import userSelect from './UserSelectDialog'
import UserService from '@/api/sys/UserService'
export default {
  data () {
    return {
      name: '',
      labelValue: this.value,
      selectData: []
    }
  },
  props: {
    limit: Number,
    value: String,
    size: {
      type: String,
      default: () => { return 'small' }
    },
    readonly: {
      type: Boolean,
      default: () => { return false }
    },
    disabled: {
      type: Boolean,
      default: () => { return false }
    }
  },
  components: {
    userSelect
  },
  userService: null,
  beforeCreate () {
    this.userService = new UserService()
  },
  watch: {
    value: {
      handler (newVal) {
        this.selectData = []
        if (newVal) {
          newVal.split(',').forEach((id) => {
            this.userService.queryById(id).then(({data}) => {
              if (data && data.id !== '') {
                this.selectData.push(data)
              }
            })
          })
        }
      },
      immediate: true,
      deep: false
    },
    selectData: {
      handler (newVal) {
        this.name = newVal.map(user => { return user.name }).join(',')
      },
      immediate: false,
      deep: false
    }
  },
  methods: {
    selectUsersToInput (users) {
      this.selectData = users
      this.labelValue = users.map(user => { return user.id }).join(',')
      this.name = users.map(user => { return user.name }).join(',')
      this.$emit('getValue', this.labelValue, this.name)
    },
    showUserSelect () {
      this.$refs.userSelect.init()
    }
  }
}
</script>
<style>
  .el-form-item__content .el-input-group {
      vertical-align: middle;
  }
 .el-tag + .el-tag {
    margin-left: 5px;
    margin-bottom: 5px;
  }
</style>


