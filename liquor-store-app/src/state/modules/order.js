import { http } from '@/main'

export default {
  state: {
    orders: []
  },
  mutations: {
    ORDER_PUSH(state, orders) {
      state.orders = orders
    }
  },
  getters: {
    orders: state => state.orders
  },
  actions: {
    fetchOrders({ commit }, customerEmail) {
      return http
        .get(`/orders`, {
          params: {
            customerEmail: customerEmail
          }
        })
        .then(response => {
          console.log("get orders response", response);
          const orders = response.data;
          commit('ORDER_PUSH', orders);
          return orders;
        })
        .catch(error => console.log("error in fetchOrders", error))
    }
  }
}
