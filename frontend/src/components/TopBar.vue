<template>
  <v-card color="grey lighten-4" flat tile>
    <v-toolbar>
      <v-app-bar-nav-icon> </v-app-bar-nav-icon>

      <v-btn v-if="isAdmin" icon @click="goToUsers">
        <v-icon>mdi-account</v-icon>
      </v-btn>

      <v-btn v-if="isAdmin" icon @click="goToItems">
        <v-icon>mdi-book-open-page-variant</v-icon>
      </v-btn>

      <v-btn v-if="!isAdmin" icon @click="goToItemsUser">
        <v-icon>mdi-book-open-page-variant</v-icon>
      </v-btn>

      <v-btn v-if="!isAdmin" icon @click="goToCart">
        <v-icon>mdi-cart</v-icon>
      </v-btn>

      <v-toolbar-title>Hardware Store</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon @click="logout">
        <v-icon>logout</v-icon>
      </v-btn>
    </v-toolbar>
  </v-card>
</template>

<script>
//import { mdiBookOpenPageVariant } from "@mdi/js";
import router from "../router";
// import {auth as store} from "../store/auth.module";

export default {
  name: "TopBar",
  computed: {
    isAdmin: function() {
      return this.$store.getters["auth/isAdmin"];

    },
  },
  methods: {
    logout() {
      this.$store.dispatch("auth/logout");
      router.push("/");
    },
    goToUsers() {
      router.push("/users");
    },
    goToItems() {
      router.push("/items");
    },
    goToItemsUser() {
      router.push("/itemsEmployee");
    },
    goToCart() {
      router.push("/cart");
    },
  },
};
</script>

<style scoped></style>
