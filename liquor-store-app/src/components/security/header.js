import store from '@/state/store'

export default () => {
  var keycloakAuth = store.getters.auth
  return 'Bearer ' + keycloakAuth.token
}
