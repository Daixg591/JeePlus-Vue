<template>
  <div class="wrapper" :class="{ 'popover-open': popoverOpen }">
    <el-popover ref="notice-popover" v-model="popoverOpen" popper-class="notice-popover"
      placement="bottom-end" :offset="-3">
      <el-tabs class="tabs" v-model="activeTab" @tab-click="onTabChange">
        <template v-for="tab in tabOptions">
          <el-tab-pane class="tab-pane" :key="tab.title" :label="tab.titleShow"
            :name="tab.title">
            <!-- {{tab.emptyText}} -->
            <notice-list :url="tab.url" :data="getNoticeData(tab.list)" title="通知"></notice-list>
          </el-tab-pane>
        </template>
      </el-tabs>
    </el-popover>

    <div class="noticeButton" v-popover:notice-popover>
      <el-badge :value="unreadSize" class="badge">
        <i class="fa fa-bell-o"></i>
      </el-badge>
    </div>
  </div>

</template>

<script>
import moment from 'moment'
import NoticeList from './NoticeList.vue'

export default {
  data () {
    const activeTab =
      this.tabs && this.tabs.length > 0 ? this.tabs[0].title : ''
    return {
      popoverOpen: false,
      activeTab
    }
  },
  props: {
    tabs: {
      type: Array,
      default () {
        return []
      }
    }
  },
  computed: {
    tabOptions () {
      return this.tabs.map(tab => {
        const titleShow =
          tab.list && tab.list.length > 0
            ? `${tab.title} (${tab.count})`
            : tab.title
        return {
          ...tab,
          titleShow
        }
      })
    },
    unreadSize () {
      let total = 0
      this.tabs.forEach((tab) => {
        total = total + tab.count
      })
      return total
    }
  },
  components: {
    NoticeList
  },
  methods: {
    onTabChange (tab, event) {
      // console.log('NoticeIcon/index, onTabChange, tab:', tab, 'event:', event)
    },
    getNoticeData (noticeList) {
      if (!noticeList || noticeList.length < 0) {
        return []
      }
      return noticeList.map(item => {
        return {
          ...item,
          datetime: moment(item.datetime).fromNow()
        }
      })
    }
  }
}
</script>

<style lang="scss">
.notice-popover {
  width: 356px;
  // padding: 4px 0 0 0;

  &[x-placement^='bottom'] {
    margin-top: -8px;
  }
}
</style>

<style lang="scss" scoped>
.noticeButton {
  width: 100%;
  height: 100%;
  padding: 0 12px;

  display: flex;
  align-items: center;

  transition: all 0.3s;
}

.badge {
  .el-badge__content {
    z-index: 1;
  }
}

.tabs {
  .el-tabs__nav-scroll {
    display: flex;
    justify-content: center;
  }

  .el-tabs__header {
    margin-bottom: 4px;
  }
}
</style>


