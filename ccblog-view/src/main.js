import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import tagCloud from 'v-tag-cloud'
import Viewer from 'v-viewer'

import 'viewerjs/dist/viewer.css'
import 'element-ui/lib/theme-chalk/index.css'
import 'semantic-ui-css/semantic.min.css'
import './assets/css/base.css'
import './assets/icon/iconfont.css'
import './assets/css/typo.css'
import './utils/dateTimeFormatUtils'
import './utils/directive'


Vue.use(ElementUI)
Vue.use(tagCloud)
Vue.use(Viewer)
Vue.config.productionTip = false

const cubic = value => Math.pow(value, 3);
const easeInOutCubic = value => value < 0.5 ? cubic(value * 2) / 2 : 1 - cubic((1 - value) * 2) / 2;
//滚动至页面顶部，使用 Element-ui 回到顶部 组件中的算法
Vue.prototype.scrollToTop = function () {
  const el = document.documentElement
  const beginTime = Date.now()
  const beginValue = el.scrollTop
  const rAF = window.requestAnimationFrame || (func => setTimeout(func, 16))
  const frameFunc = () => {
    const progress = (Date.now() - beginTime) / 500;
    if (progress < 1) {
      el.scrollTop = beginValue * (1 - easeInOutCubic(progress))
      rAF(frameFunc)
    } else {
      el.scrollTop = 0
    }
  }
  rAF(frameFunc)
}

Vue.prototype.msgSuccess = function (msg) {
  this.$message.success(msg)
}

Vue.prototype.msgError = function (msg) {
  this.$message.error(msg)
}

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
