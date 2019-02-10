import Keycloak from 'keycloak-js'
import store from '@/state/store'

let keycloakAuth = new Keycloak({
  url: process.env.VUE_APP_KEYCLOAK_URL,
  realm: 'playground',
  clientId: 'ms-app'
})

export default (to, next, roles) => {
  keycloakAuth.init({ onLoad: 'login-required', redirectUri: window.location.origin + to.path })
    .then(() => {
      keycloakAuth.updateToken(10)
        .then(() => {
          console.log("refresh token")
          store.dispatch('authLogin', keycloakAuth)
          if (roles) {
            if (keycloakAuth.hasRealmRole(roles[0])) {
              next({ name: to.name })
            } else {
              next({ name: 'about' })
            }
          } else {
            console.log("next keycloak init")
            next({ name: to.name })
          }
        })
    })
    .catch(error => console.log('failed to login', error))
}
