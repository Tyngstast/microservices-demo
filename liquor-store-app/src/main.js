import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './state/store'
import VueMaterial from 'vue-material'
import './registerServiceWorker'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'
import axios from 'axios'

export const http = axios.create({
  baseURL: process.env.VUE_APP_API_URL,
  withCredentials: true
})

Vue.config.productionTip = false

Vue.use(VueMaterial)

new Vue({
  router,
  store,
  beforeCreate() {
    this.$store.commit('CART_LOAD')
  },
  render: h => h(App)
}).$mount('#app')
