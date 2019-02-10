import { http } from '@/main'

const CART = 'CART'

export default {
  state: {
    cart: [],
    total: 0,
    orderComplete: false
  },
  mutations: {
    CART_LOAD(state) {
      let cached = localStorage.getItem(CART)
      if (cached) {
        Object.assign(state, JSON.parse(cached))
      }
    },
    CART_ADD(state, item) {
      let existing = state.cart.findIndex(existing => {
        return existing.item.name === item.name
      });
      existing !== -1 ? state.cart[existing].count += 1 : state.cart.push({item, count: 1})
      state.total += item.price
    },
    CART_REMOVE(state, item) {
      let existing = state.cart.findIndex(existing => existing.item.name === item)
      state.cart.splice(existing, 1)
      state.total -= (item.count * item.item.price)
    },
    CART_INCREASE_COUNT(state, item) {
      state.cart.map(existing => {
        if (existing.item === item) {
          existing.count += 1
          state.total += item.price
        }
      });
    },
    CART_DECREASE_COUNT(state, item) {
      let index = state.cart.findIndex(existing => existing.item === item)
      let existing = state.cart[index];
      existing.count -= 1;
      state.total -= item.price
      if (existing.count <= 0) {
        state.cart.splice(index, 1)
      }
    },
    CART_SET_ORDER_COMPLETE(state, complete) {
      state.orderComplete = complete;
    },
    CART_RESET(state) {
      state.cart = []
      state.total = 0;
      state.orderComplete = false
    }
  },
  getters: {
    cart: state => state.cart,
    total: state => state.total,
    orderComplete: state => state.orderComplete
  },
  actions: {
    addToCart({ commit }, item) {
      commit('CART_ADD', item)
    },
    removeFromCart({ commit }, item) {
      commit('CART_REMOVE', item)
    },
    increaseCount({ commit }, item) {
      commit('CART_INCREASE_COUNT', item)
    },
    decreaseCount({ commit }, item) {
      commit('CART_DECREASE_COUNT', item)
    },
    order({ commit }, { cart, userEmail }) {
      let mappedCart = cart.map(item => {
        return {
          count: item.count,
          item: item.item.id
        }
      })
      var orderDto = {
        customerEmail: userEmail,
        items: mappedCart
      }
      http
        .post(`/orders`, orderDto)
        .then(() => {
          commit('CART_SET_ORDER_COMPLETE', true)
        })
        .catch(error => console.log("error in post orders", error))
    }
  }
}
