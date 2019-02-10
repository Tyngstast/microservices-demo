<template>
  <div class="md-layout">
    <div class="md-layout-item">
      <md-content>
        <md-card v-if="cart.length > 0">

          <div class="order-complete" v-if="orderComplete">
            <md-toolbar>
              <div class="md-toolbar-row">
                <p>Order Completed</p>
                <md-icon class="md-size-4x">check_circle_outline</md-icon>
              </div>
            </md-toolbar>
          </div>

          <md-table>

            <md-table-toolbar>
              <h1 class="md-title">Cart &ndash; {{ auth.idTokenParsed.name }}</h1>
            </md-table-toolbar>

            <md-table-row>
              <md-table-head>Name</md-table-head>
              <md-table-head :class="{'head-padding' : !orderComplete}">Amount</md-table-head>
              <md-table-head>Cost</md-table-head>
              <md-table-head v-if="!orderComplete">Delete</md-table-head>
            </md-table-row>

            <md-table-row v-for="item in cart">

              <md-table-cell>
                <b>{{ item.item.brewery }}</b> &ndash; {{ item.item.name }}
              </md-table-cell>

              <md-table-cell>
                <md-button v-show="!orderComplete" @click="decreaseCount(item)" class="md-icon-button">
                  <md-icon>remove</md-icon>
                </md-button>
                <span :class="[{'boxed' : !orderComplete}, 'counter']" >
                  <b>{{ item.count }}</b>
                </span>
                <md-button v-show="!orderComplete" @click="increaseCount(item)" class="md-icon-button">
                  <md-icon>add</md-icon>
                </md-button>
              </md-table-cell>

              <md-table-cell>
                <b>{{ item.count * item.item.price }} :-</b>
              </md-table-cell>

              <md-table-cell v-if="!orderComplete">
                <md-button @click="remove(item)" class="md-icon-button">
                  <md-icon>delete</md-icon>
                </md-button>
              </md-table-cell>

            </md-table-row>

          </md-table>

          <span class="bottom-right">
            <b>Total: {{ total }} :-</b>
          </span>

          <div v-if="!orderComplete" class="bottom-right">
            <md-button @click="order(cart)" class="md-raised">Order</md-button>
          </div>

        </md-card>

      </md-content>
    </div>
  </div>
</template>

<script>
  export default {
    beforeDestroy() {
      if (this.$store.getters.orderComplete) {
        this.$store.commit('CART_RESET')
      }
    },
    computed: {
      cart() {
        return this.$store.getters.cart
      },
      total() {
        return this.$store.getters.total
      },
      auth() {
        return this.$store.getters.auth
      },
      orderComplete() {
        return this.$store.getters.orderComplete
      }
    },
    methods: {
      remove(item) {
        this.$store.dispatch("removeFromCart", item)
      },
      increaseCount(item) {
        this.$store.dispatch("increaseCount", item.item)
      },
      decreaseCount(item) {
        this.$store.dispatch("decreaseCount", item.item)
      },
      order(cart) {
        this.$store.dispatch("order", {
          cart,
          userEmail: this.$store.getters.auth.idTokenParsed.preferred_username
        })
      }
    }
  }
</script>

<style scoped lang="scss">
  .bottom-right {
    display: flex;
    justify-content: flex-end;
    padding: 0 32px 16px 16px;

    .md-button {
      margin: 0;
    }
  }
  .boxed {
    border: solid 1px black;
  }
  .counter {
    display: inline-block;
    height: 28px;
    width: 28px;
    line-height: 28px;
    font-size: 16px;
    text-align: center;
    margin-top: 5px;
  }
  .head-padding {
    padding-left: 40px;
  }
  .order-complete {
    .md-toolbar {
      box-shadow: none;
      background-color: springgreen;
      .md-toolbar-row {
        width: auto;
        margin: auto;
        color: white;

        .md-icon {
          color: white;
        }

        p {
          text-align: center;
          font-size: 20px;
          font-weight: bold;
        }
      }
    }
  }
</style>
