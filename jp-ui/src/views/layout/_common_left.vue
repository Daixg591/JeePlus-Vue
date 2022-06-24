<template>
  <aside class="jp-sidebar" :style="sidebarLayoutSkin === '5' || sidebarLayoutSkin === '9' ?`background:${defaultTheme}`: ''" :class="'jp-sidebar--' + sidebarLayoutSkin">
    <div class="jp-sidebar__inner">
      <el-menu unique-opened
               :default-active="menuActiveName || 'home'"
               :collapse="sidebarFold"
               :collapseTransition="false"
               class="jp-sidebar__menu">
        <li class="jp-menu-category" v-if="!sidebarFold">{{leftMenuCategory}}</li>
        <sub-menu
          v-for="menu in leftMenuList"
          :key="menu.id"
          :menu="menu"
          :dynamicMenuRoutes="dynamicMenuRoutes">
        </sub-menu>
      </el-menu>
    </div>
  </aside>
</template>
<style>
.jp-menu-category {
    padding: 0 20px;
    margin-top: 20px;
    font-size: 12px;
    line-height: 38px;
    color: #76838f;
    text-transform: uppercase;
    -webkit-transition: all .25s,font .1s .15s,color .1s .15s;
    transition: all .25s,font .1s .15s,color .1s .15s;
}
</style>

<script>
  import SubMenu from './_common_left_submenu'

  export default {
    data () {
      return {
        dynamicMenuRoutes: []
      }
    },
    components: {
      SubMenu
    },
    computed: {
      sidebarLayoutSkin: {
        get () {
          return this.$store.state.common.sidebarLayoutSkin
        }
      },
      sidebarFold: {
        get () {
          return this.$store.state.common.sidebarFold
        }
      },
      isTab: {
        get () {
          return this.$store.state.common.isTab
        }
      },
      leftMenuList: {
        get () {
          return this.$store.state.common.leftMenuList
        },
        set (val) {
          this.$store.commit('common/updateLeftMenuList', val)
        }
      },
      leftMenuCategory: {
        get () {
          return this.$store.state.common.leftMenuCategory
        },
        set (val) {
          this.$store.commit('common/updateLeftMenuCategory', val)
        }
      },
      menuActiveName: {
        get () {
          return this.$store.state.common.menuActiveName
        },
        set (val) {
          this.$store.commit('common/updateMenuActiveName', val)
        }
      },
      mainTabs: {
        get () {
          return this.$store.state.common.mainTabs
        },
        set (val) {
          this.$store.commit('common/updateMainTabs', val)
        }
      },
      defaultTheme () {
        return this.$store.state.config.defaultTheme
      },
      mainTabsActiveName: {
        get () {
          return this.$store.state.common.mainTabsActiveName
        },
        set (val) {
          this.$store.commit('common/updateMainTabsActiveName', val)
        }
      }
    },
    watch: {
      $route: 'routeHandle'
    },
    created () {
      // this.menuList = JSON.parse(sessionStorage.getItem('menuList') || '[]')
      this.dynamicMenuRoutes = JSON.parse(sessionStorage.getItem('dynamicMenuRoutes') || '[]')
      this.routeHandle(this.$route)
    },
    methods: {
      // 路由操作
      routeHandle (route) {
        if (this.isTab) {
          // tab选中, 不存在先添加
          let tab = this.mainTabs.filter(item => item.fullPath === route.fullPath)[0]
          if (!tab) {
            if (route.meta.isDynamic) {
              route = this.dynamicMenuRoutes.filter(item => item.name === route.name)[0]
              if (!route) {
                return console.error('未能找到可用标签页!')
              }
            }
            tab = {
              menuId: route.meta.menuId || route.name,
              name: route.name,
              title: this.$router.currentRoute.query.title || route.meta.title,
              type: route.meta.type,
              iframeUrl: route.meta.iframeUrl || '',
              query: this.$router.currentRoute.query,
              parmas: this.$router.currentRoute.parmas,
              fullPath: this.$router.currentRoute.fullPath
            }
            this.mainTabs = this.mainTabs.concat(tab)
          }
          tab.title = this.$router.currentRoute.query.title || route.meta.title
          this.menuActiveName = tab.menuId + ''
          this.mainTabsActiveName = tab.fullPath
          let topMenuActiveIndex = route.meta.parentIds && route.meta.parentIds.split(',').length > 2 ? route.meta.parentIds.split(',')[2] : '0'
          this.$store.commit('common/updateTopMenuActiveIndex', topMenuActiveIndex)
        }
      }
    }
  }
</script>
