<template>
  <div class="jp-container">
      <tags-view :style="{ '--defaultTheme': defaultTheme}" v-if="isTab"></tags-view>
      <div class="jp-center" :style="isTab?'top: 34px;':'top:0px'">
            <div v-if="!isTab">
              <el-breadcrumb separator="/" style="padding-top:6px; padding-bottom:14px;">
                  <el-breadcrumb-item><router-link to="/home">首页</router-link></el-breadcrumb-item>
                  <el-breadcrumb-item :key="index" v-for="(breadcrumb, index) in breadcrumbs">{{breadcrumb}}</el-breadcrumb-item>
              </el-breadcrumb>
            </div>
            <iframe
              v-if="$route.meta.type === 'iframe'"
              :src="$route.query.iframeUrl ||$route.meta.iframeUrl"
              width="100%" height="100%" frameborder="0" scrolling="yes">
            </iframe>
            <keep-alive v-else>
              <router-view v-if="isRouterAlive"/>
            </keep-alive>
      </div>
  </div>
</template>

<script>
  import TagsView from './components/TagsView'
  import { mapState } from 'vuex'
  import Vue from 'vue'
  export default {
    data () {
      return {
        contentmenuX: '',
        contentmenuY: '',
        contextmenuFlag: false,
        isRouterAlive: true,
        selectTabName: '',
        breadcrumbs: [],
        eventHub: new Vue()
      }
    },
    components: {
      TagsView
    },
  
    computed: {
      ...mapState({
        isTab: state => state.common.isTab,
        defaultTheme: state => state.config.defaultTheme
      })
    },
    watch: {
      $route: {
        handler (val, from) {
          if (val && from && val.path === from.path && val.fullPath !== from.fullPath) { // 强制刷新参数不同的路由页面
            this.$router.replace({
              path: '/redirect' + val.fullPath
            })
          }
          this.breadcrumbs = []
          if (val.meta && val.meta.parentIds) {
            let ids = val.meta.parentIds.split(',')
            ids.forEach((id) => {
              if (id && id !== '0' && id !== '1') {
                let obj = {title: ''}
                this.getTitle(JSON.parse(sessionStorage.getItem('allMenuList') || '[]'), id, obj)
                this.breadcrumbs.push(obj.title)
              }
            })
            this.breadcrumbs.push(this.$route.query.title || this.$route.meta.title)
          }
        },
        immediate: true,
        deep: false
      }
    },
    methods: {
      // 获取路由名字
      getTitle (menus, id, obj) {
        menus.forEach((menu) => {
          if (menu.id === id) {
            obj.title = menu.name
          } else if (menu.children) {
            this.getTitle(menu.children, id, obj)
          }
        })
      }
    }
  }
</script>

<style>
.jp-tags__contentmenu {
    position: fixed;
    width: 150px;
    background-color: #fff;
    z-index: 2024;
    border-radius: 5px;
    -webkit-box-shadow: 1px 2px 10px #ccc;
    box-shadow: 1px 2px 10px #ccc;
}
.jp-navbar__menu .el-badge__content.is-fixed {
    top: 15px;
}
</style>
