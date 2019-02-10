<template>
  <div class="md-layout">
    <div class="md-layout-item">
      <md-content>
        <md-card class="no-orders" v-if="orders.length === 0">
          <p>No orders found</p>
        </md-card>

        <md-card v-else>
          <md-table>
            <md-table-toolbar>
              <h1 class="md-title">Orders &ndash; {{ auth.idTokenParsed.name }}</h1>
            </md-table-toolbar>

            <md-table-row>
              <md-table-head>Id</md-table-head>
              <md-table-head>Date</md-table-head>
              <md-table-head>Cost</md-table-head>
              <md-table-head>Details</md-table-head>
            </md-table-row>

            <md-table-row v-for="order in orders">
              <md-table-cell>
                <b>{{ order.id }}</b>
              </md-table-cell>

              <md-table-cell>
                <b>{{ order.date }}</b>
              </md-table-cell>

              <md-table-cell>
                <b>{{ order.totalCost }} :-</b>
              </md-table-cell>

              <md-table-cell>
                <md-button @click="dialog(order)" class="md-icon-button">
                  <md-icon>info</md-icon>
                </md-button>
              </md-table-cell>

            </md-table-row>
          </md-table>
        </md-card>

        <md-dialog :md-active.sync="showDialog">
          <md-dialog-title>Items</md-dialog-title>
          <md-table v-if="expandedOrder">
            <md-table-row>
              <md-table-head>Count</md-table-head>
              <md-table-head>Name</md-table-head>
              <md-table-head>Unit Price</md-table-head>
            </md-table-row>

            <md-table-row v-for="item in expandedOrder.items">
              <md-table-cell>
                <b>{{ item.count }}</b>
              </md-table-cell>

              <md-table-cell>
                <b>{{ item.item.brewery }}</b> &ndash; {{ item.item.name }}
              </md-table-cell>

              <md-table-cell>
                <b>{{ item.item.price }} :-</b>
              </md-table-cell>
            </md-table-row>
          </md-table>

          <md-dialog-actions>
            <md-button class="md-primary" @click="showDialog = false">Close</md-button>
          </md-dialog-actions>
        </md-dialog>

      </md-content>
    </div>
  </div>
</template>

<script>
  export default {
    created() {
      this.$store.dispatch("fetchOrders", this.$store.getters.auth.idTokenParsed.preferred_username)
    },
    data() {
      return {
        expand: false,
        expandedOrder: undefined,
        showDialog: false
      }
    },
    computed: {
      orders() {
        console.log(this.$store.getters.orders)
        return this.$store.getters.orders
      },
      auth() {
        return this.$store.getters.auth
      },
    },
    methods: {
      dialog(order) {
        console.log("order", order)
        this.showDialog = true;
        this.expandedOrder = order;
      }
    }
  }
</script>

<style scoped lang="scss">
  .no-orders {
    text-align: center;
    font-weight: bold;
    padding: 30px;
  }
  #expand-arrow-head {
    width: 5%;
  }
  #expand-arrow {
    width: 5%;

    button {
      height: 30px;
      min-width: 30px;
      width: 30px;
    }
  }
  .md-dialog {
    width: 50%;
    padding: 16px;
  }
</style>
