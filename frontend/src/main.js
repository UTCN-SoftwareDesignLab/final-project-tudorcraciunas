import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import vuetify from "./plugins/vuetify";
import "material-design-icons-iconfont/dist/material-design-icons.css";
import "./api";
import store from "./store";
import axios from 'axios'

Vue.prototype.$http = axios

window.axios = require('axios')
new Vue({
  router,
  vuetify,
  store,
  render: (h) => h(App),
}).$mount("#app");
