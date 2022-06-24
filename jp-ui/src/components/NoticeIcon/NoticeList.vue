<template>
  <div>
    <ep-list class="list">
      <template v-for="(item, i) in data">
        <ep-list-item :key="i" class="item">
          <ep-list-item-meta slot="meta"  class="meta">
            <avatar slot="avatar" class="avatar" :src="item.avatar"></avatar>
            <div slot="title" class="title">
              {{item.title}}
            </div>
            <div slot="description">
               <el-button type="text" style="white-space: inherit;"  v-if="item.type==='通知'" @click=" $refs.notifyForm.init('read', item.id)">
                <div class="description" style="height:30px" v-html="item.description"></div>
              </el-button>
               <el-button type="text" style="white-space: inherit;" v-if="item.type==='站内信'" @click="$refs.receivedMailDetail.init(item.id)">
                <div class="description" style="height:30px" v-html="item.description"></div>
              </el-button>
              <div class="datetime">{{item.datetime}}</div>
            </div>
          </ep-list-item-meta>
        </ep-list-item>
      </template>
      <ep-list-item class="item">
          <ep-list-item-meta>
            <div slot="description">
               <router-link :to="url">
                <div class="description" style="text-align:center">查看更多</div>
                </router-link>   
            </div>
          </ep-list-item-meta>
      </ep-list-item>
    </ep-list>
     <ReceivedMailDetail ref="receivedMailDetail"></ReceivedMailDetail>
     <NotifyForm  ref="notifyForm"></NotifyForm>
  </div>
</template>

<script>
import EpList from '@/components/List/index.vue'
import EpListItem from '@/components/List/ListItem.vue'
import EpListItemMeta from '@/components/List/ListItemMeta.vue'
import Avatar from '@/components/avatar/index.vue'
import ReceivedMailDetail from '@/views/modules/mailbox/ReceivedMailDetail'
import NotifyForm from '@/views/modules/notify/NotifyForm'

export default {
  props: {
    data: {
      type: Array,
      default () {
        return []
      }
    },
    url: {
      type: String,
      default () {
        return ''
      }
    }
  },
  components: {
    EpList,
    EpListItem,
    EpListItemMeta,
    ReceivedMailDetail,
    NotifyForm,
    Avatar
  }
}
</script>

<style lang="scss" scoped>
@import '@/assets/scss/theme.scss';
.list {
  max-height: 400px;
  overflow: auto;
  .item {
    transition: all 0.3s;
    overflow: hidden;
    cursor: pointer;
    padding-left: 24px;
    padding-right: 24px;

    &:last-child {
      border-bottom: 0;
    }
    &:hover {
      background: $primary-1;
    }

    .meta {
      width: 100%;
    }
    .avatar {
      background: #fff;
      margin-top: 4px;
    }
    .title {
      font-weight: normal;
      margin-bottom: 8px;
    }
    .description {
      font-size: 12px;
      line-height: $line-height-base;
      display: -webkit-box;/*作为弹性伸缩盒子模型显示*/
      -webkit-line-clamp: 1; /*显示的行数；如果要设置2行加...则设置为2*/
      overflow: hidden; /*超出的文本隐藏*/
      text-overflow: ellipsis; /* 溢出用省略号*/
       -webkit-box-orient: vertical;/*伸缩盒子的子元素排列：从上到下*/
    }
    .datetime {
      font-size: 12px;
      margin-top: 4px;
      line-height: $line-height-base;
    }
  }
}
</style>
