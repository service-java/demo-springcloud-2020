// polyfill
import 'babel-polyfill'
// Vue
import Vue from 'vue'
import App from './App'
// store
import store from '@/store/index'
// 模拟数据
import '@/mock'
// 多国语
import i18n from './i18n'
// 核心插件
import d2Admin from '@/plugin/d2admin'
import D2Crud from '@d2-projects/d2-crud'
// 路由设置
import router from './router'
import { frameInRoutes } from '@/router/routes'
import ViewPlus from 'vue-viewplus'
import viewPlusOptions, { mixinConfig as viewPlusMixinConfig } from '@/plugin/vue-viewplus'
import jsComponents from '@/plugin/vue-viewplus/js-ui-component.js'
import ZkTable from 'vue-table-with-tree-grid'
import '@/assets/style/custom.scss'

const {
  debug,
  errorHandler
} = viewPlusOptions

ViewPlus.mixin(Vue, viewPlusMixinConfig, {
  debug,
  errorHandler,
  moduleName: '自定义常量模块'
})

ViewPlus.mixin(Vue, jsComponents, {
  debug,
  errorHandler,
  moduleName: '自定义jsComponents模块'
})

Vue.use(ViewPlus, {
  store,
  ...viewPlusOptions
})

// 核心插件
Vue.use(d2Admin)

Vue.use(D2Crud, { size: 'small' })

Vue.component(ZkTable.name, ZkTable)

new Vue({
  router,
  store,
  i18n,
  render: h => h(App),
  created() {
    // 处理路由 得到每一级的路由设置
    this.$store.commit('d2admin/page/init', frameInRoutes)
  },
  mounted() {
    // 展示系统信息
    this.$store.commit('d2admin/releases/versionShow')
    // 用户登录后从数据库加载一系列的设置
    this.$store.dispatch('d2admin/account/load')
    // 获取并记录用户 UA
    this.$store.commit('d2admin/ua/get')
    // 初始化全屏监听
    this.$store.dispatch('d2admin/fullscreen/listen')
  }
}).$mount('#app')
