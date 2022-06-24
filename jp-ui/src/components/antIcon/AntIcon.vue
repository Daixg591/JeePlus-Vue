
<script>
import Vue from 'vue'

export default Vue.component('ant-icon', {
  props: {
    type: {
      type: String,
      required: true
    }
  },
  render (h) {
    const { type } = this
    return h('i', {
      class: ['anticon', `anticon-${type}`],
      on: this.$listeners
    })
  }
})
</script>
