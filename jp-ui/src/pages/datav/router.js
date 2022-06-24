import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/design',
      name: 'design',
      component: () => import('./page/build.vue')
    },
    {
      path: '/view',
      name: 'view',
      component: () => import('./page/view.vue')
    }
  ]
})
