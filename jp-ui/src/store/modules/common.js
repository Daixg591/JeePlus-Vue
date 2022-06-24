export default {
  namespaced: true,
  state: {
    // 导航条, 布局风格, defalut(默认) / inverse(反向)
    navbarLayoutType: localStorage.getItem('navbarLayoutType') || '1',
    // 侧边栏, 布局皮肤, light(浅色) / dark(黑色)
    sidebarLayoutSkin: localStorage.getItem('sidebarLayoutSkin') || '1',
    // 侧边栏, 折叠状态
    sidebarFold: false,
    // 开启tab
    isTab: localStorage.getItem('isTab') !== 'false',
    // 侧边栏, 菜单
    leftMenuList: [],
    // 当前活动的tab标签页
    menuActiveName: '',
    // 侧边栏类型名
    leftMenuCategory: '',
    // 主入口标签页
    mainTabs: [{name: 'home', title: '首页', fullPath: '/home'}],
    mainTabsActiveName: 'home',
    // 当前选中的top menu的index
    topMenuActiveIndex: '0'
  },
  mutations: {
    updateNavbarLayoutType (state, type) {
      state.navbarLayoutType = type
    },
    updateSidebarLayoutSkin (state, skin) {
      state.sidebarLayoutSkin = skin
    },
    updateSidebarFold (state, fold) {
      state.sidebarFold = fold
    },
    updateLeftMenuList (state, list) {
      state.leftMenuList = list
    },
    updateMenuActiveName (state, name) {
      state.menuActiveName = name
    },
    updateMainTabs (state, tabs) {
      state.mainTabs = tabs
    },
    updateMainTabsActiveName (state, name) {
      state.mainTabsActiveName = name
    },
    updateLeftMenuCategory (state, leftMenuCategory) {
      state.leftMenuCategory = leftMenuCategory
    },
    updateTopMenuActiveIndex (state, topMenuActiveIndex) {
      state.topMenuActiveIndex = topMenuActiveIndex
    },
    updateIsTab (state, isTab) {
      state.isTab = isTab
    }
  }
}
