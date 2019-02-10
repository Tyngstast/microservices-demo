import store from '@/state/store'

export default (role) => {
  var keycloakAuth = store.getters.auth
  if (keycloakAuth.authenticated) {
    return keycloakAuth.hasClientRole(role) || keycloakAuth.hasRealmRole(role)
  } else {
    return false
  }
}
