import Vue from 'vue'
import { http } from '@/main'
import axios from 'axios'

export default {
  state: {
    beers: []
  },
  mutations: {
    BEER_PUSH(state, beers) {
      state.beers = beers
    }
  },
  getters: {
    beers: state => state.beers
  },
  actions: {
    fetchBeers({ commit, dispatch }) {
      http
        .get(`/beers`)
        .then(response => {
          const beers = response.data;
          commit('BEER_PUSH', beers);
          return beers;
        })
        .then(beers => dispatch('fetchLogos', beers))
        .catch(error => console.error("error in fetchBeers", error))
    },
    fetchLogos({ commit }, beers) {
      axios.defaults.headers.common = {};
      beers.forEach(beer => {
        let cachedLogo = localStorage.getItem(beer.id)
        if (cachedLogo) {
          Vue.set(beer, 'logo', cachedLogo)
        } else {
          const queryString =  beer.brewery + "%20" + beer.name
          const url = 'https://www.googleapis.com/customsearch/v1?key=AIzaSyBCMwo5A5d_AAqLVaEmZ6qkixGtPOa1cuk&cx=011761428944393032488:p1yuigy3txw&q=' + queryString;
          axios
            .get(url)
            .then(response => {
              let logo = response.data.items[0].pagemap.cse_thumbnail[0].src
              Vue.set(beer, 'logo', logo)
              localStorage.setItem(beer.id, logo)
            })
            .catch(error => console.log(error))
        }
      });
    }
  }
}
