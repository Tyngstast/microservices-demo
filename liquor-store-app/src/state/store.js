import Vue from 'vue'
import Vuex from 'vuex'
import beer from './modules/beer'
import order from './modules/order'
import customer from './modules/customer'
import cart from './modules/cart'
import security from './modules/security'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    beer,
    order,
    customer,
    cart,
    security
  }
})

const CART = 'CART';

store.subscribe((mutation, state) => {
  if (mutation.type.indexOf(CART) !== -1 && mutation.type !== 'CART_LOAD') {
    localStorage.setItem(CART, JSON.stringify(state.cart))
  }
})

export default store
