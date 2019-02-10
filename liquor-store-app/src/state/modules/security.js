import { http } from '@/main'
import security from '@/components/security'

const defaultState = () => {
  return {
    auth: {
      authenticated: false
    }
  }
}

export default {
  state: defaultState(),
  mutations: {
    SECURITY_LOGIN(state, auth) {
      state.auth = auth;
      http.defaults.headers.common['Authorization'] = security.header()
    },
    SECURITY_LOGOUT(state) {
      let keycloakAuth = state.auth;
      delete http.defaults.headers.common['Authorization']
      state = defaultState();
      keycloakAuth.logout({redirectUri: window.location.origin});
    }
  },
  getters: {
    auth: state => state.auth
  },
  actions: {
    authLogin ({ commit }, auth) {
      commit("SECURITY_LOGIN", auth)
    },
    authLogout ({ commit }) {
      commit("SECURITY_LOGOUT")
    }
  }
}
