<template>
  <div class="md-layout">
    <div class="md-layout-item">
      <md-content v-for="beer in beers">
        <md-card>
          <md-card-header>

            <md-card-media md-medium>
              <img v-if="beer.logo !== undefined" :src="beer.logo" alt="Logo">
              <img v-else src="../assets/logo.png" alt="Logo">
            </md-card-media>

            <md-card-header-text>
              <div class="md-title"><b>{{ beer.brewery }}</b> &ndash; {{ beer.name }}</div>
              <div class="md-subhead">Price: {{ beer.price }}:-</div>
            </md-card-header-text>

          </md-card-header>

          <md-card-actions>
            <md-button v-on:click="buyItem(beer)">Buy</md-button>
          </md-card-actions>

        </md-card>
      </md-content>
    </div>
  </div>
</template>

<script>
export default {
  created() {
    this.$store.dispatch("fetchBeers")
  },
  computed: {
    beers() {
      return this.$store.getters.beers
    },
  },
  methods: {
    buyItem(item) {
      this.$store.dispatch("addToCart", item)
    }
  }
}
</script>

<style scoped lang="scss">
  .md-card {
    .md-card-header {
      .md-card-media.md-medium {
        flex: 0 0 80px;
      }
    }
  }
  .md-title {
    padding: 0 0 0 15px;
    text-align: left;
  }
  .md-subhead {
    padding: 0 0 0 15px;
    text-align: left;
  }
</style>
