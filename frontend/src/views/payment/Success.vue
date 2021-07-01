<template>
  <div class="text-center">
    <div class="spinner-border" role="status">
      <span class="sr-only">Loading...</span>
      <span class="sr-only">Payment very successful bro...</span>

    </div>
    <div>
      <v-btn class="sr-only" @click="processOrder">Finish</v-btn>
    </div>
  </div>
</template>

<script>
import api from "../../api";
import router from "../../router";
import { BASE_URL } from "../../api/http";

export default {
  name: "Success",
  props: ["baseURL"],
  data() {
    return {
      token: null,
      sessionId: null,
      checkoutBodyArray: [],
    };
  },
  methods: {
     async refreshList() {
       await this.$http
        .get(BASE_URL + "/cart/" + this.$store.getters["auth/userId"])
        .then((response) => {
          if (response.status === 200) {
            let products = response.data;
            console.log(products);
            let len = products.length;
            for (let i = 0; i < len; i++)
            {
              this.checkoutBodyArray.push({
                id: products[i].id,
                quantity: products[i].quantity,
              });
            }
            console.log("refresh");
            console.log(this.checkoutBodyArray);
          }
        });

    },
    processOrder() {
      api.items.sell(this.checkoutBodyArray);
      this.emptyCart();
      router.push("/itemsEmployee");

    },

    emptyCart() {
      api.cartItems
        .deleteCart({
          user_id: this.$store.getters["auth/userId"],
        })
        .then(() => this.$emit("refresh"));
    },
  },
  created() {
    this.refreshList();
  },
  mounted() {
    this.refreshList();
    this.token = localStorage.getItem("token");
    this.sessionId = localStorage.getItem("sessionId");


  },
};
</script>
