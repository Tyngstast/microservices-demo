<template>
  <div id="app">
    <div class="md-toolbar md-elevation-2">
      <div class="md-toolbar-row">
        <div class="md-toolbar-section-start">
          <router-link to="/">Home</router-link> |
          <router-link to="/store">Store</router-link>
        </div>
        <div class="md-toolbar-section-end">
          <md-button to="/cart" class="md-icon-button">
            <md-icon>shopping_cart</md-icon>
            <label v-if="total && total > 0"><b>{{ total }} :-</b></label>
          </md-button>
          <md-menu v-if="auth.authenticated" md-size="small" md-align-trigger>
            <md-button md-menu-trigger class="no-transform-button">{{ auth.idTokenParsed.name }}</md-button>
            <md-menu-content>
              <md-menu-item class="md-button">Profile</md-menu-item>
              <md-menu-item to="/orders" class="md-button">My Orders</md-menu-item>
              <md-menu-item class="md-button">Analytics</md-menu-item>
              <md-menu-item @click="logout()" class="md-button">Logout</md-menu-item>
            </md-menu-content>
          </md-menu>
          <md-button @click="login()" class="no-transform-button" v-else>Login</md-button>
        </div>
      </div>
    </div>
    <router-view/>
  </div>
</template>

<script>
  import security from '@/components/security'

  export default {
    computed: {
      total() {
        return this.$store.getters.auth ? this.$store.getters.total : 0
      },
      auth() {
        return this.$store.getters.auth;
      }
    },
    methods: {
      login() {
        security.init({path: window.location.pathname})
      },
      logout() {
        this.$store.dispatch("authLogout")
      }
    }
  }
</script>

<style lang="scss">
  #app {
    height: 100%;
    min-height: 100vh;
    margin: 0;
    display: flex;
    flex-flow: column;
    background-color: white;
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;

    .md-content {
      flex: 1 1 auto;
      padding: 16px;

      .md-card {
        width: 60%;
        margin: auto;
      }
    }
    .md-toolbar {
      border-bottom: 1px solid #42b983;
      a {
        padding: 0 5px;
        width: auto;
        font-weight: bold;
        color: #2c3e50;
        &.router-link-exact-active {
          color: #42b983;
          .md-icon {
            color: #42b983;
          }
        }
        .md-icon {
          transition: none;
          padding: 0 5px;
          font-weight: normal;
          width: auto;
        }
      }
    }
    .md-toolbar-section-start {
      margin-right: -50px;
      justify-content: flex-end;
    }
    .md-menu {
      padding: 0 5px;
    }

    /* Custom classes */

    .no-transform-button {
      font-weight: bold;
      text-transform: none;
    }
  }

  /* Vue Material overriders */

  .md-button+.md-button {
    margin-left: 6px !important;
  }
  .md-menu-content {
    max-height: 50vh !important;
  }
</style>
