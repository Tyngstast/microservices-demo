import { http } from '@/main'

export default {
  state: {
    customer: {}
  },
  mutations: {
    CUSTOMER_PUSH(state, customer) {
      state.customer = customer;
    }
  },
  getters: {
    customer: state => state.customer
  },
  actions: {
    fetchCustomer({ commit }, customerEmail) {
      console.log("get customer", this.state.customer);
      return http
        .get(`/customers`, {
          params: {
            customerEmail: customerEmail
          }
        })
        .then(response => {
          console.log("get customer response", response);
          const customer = response.data;
          commit('CUSTOMER_PUSH', customer);
          return customer;
        })
        .catch(error => console.log("error in fetchCustomer", error))
    }
  }
}
