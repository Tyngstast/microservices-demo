import Vue from 'vue'
import Router from 'vue-router'
import Home from './components/Home.vue'
import Store from './components/LiquorStore.vue'
import Cart from './components/Cart.vue'
import Orders from './components/Orders.vue'
import store from '@/state/store'
import security from '@/components/security'

Vue.use(Router);

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
    },
    {
      path: '/store',
      name: 'store',
      component: Store,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/cart',
      name: 'cart',
      component: Cart,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/orders',
      name: 'orders',
      component: Orders,
      meta: {
        requiresAuth: true
      }
    }
  ]
})

router.beforeEach((to, from, next) => {
  console.log("to in router", to)
  if (to.meta.requiresAuth) {
    const auth = store.state.security.auth
    if (!auth.authenticated) {
      security.init(to, next, to.meta.roles)
    } else {
      if (to.meta.roles) {
        if (security.roles(to.meta.roles[0])) {
          next()
        } else {
          next({ name: 'home' })
        }
      } else {
        next()
      }
    }
  } else {
    console.log("else in router")
    next()
  }
})

export default router
