import Vue from 'vue'
import moment from 'moment'

Vue.filter('formatDate', function (value, formatString) {
  formatString = formatString || 'YYYY-MM-DD HH:mm:ss'
  if (value) {
    return moment(value).format(formatString)
  } else {
    return '--'
  }
})
