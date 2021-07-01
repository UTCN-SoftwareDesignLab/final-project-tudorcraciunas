<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Buy Item </v-toolbar>
        <v-form>
          <v-text-field v-if="!isNew" v-model="item.id" label="ID" readonly />
          <v-text-field v-model="item.name" label="Name" readonly />
          <v-text-field v-model="item.details" label="Details" readonly />
          <v-text-field v-model="item.type" label="Type" readonly />
          <v-text-field
            v-model="item.quantity"
            label="Quantity"
            number
            readonly
          />
          <v-text-field v-model="item.price" label="Price $" readonly />
          <v-text-field
            v-model="sellNumber"
            label="Choose how many to add to cart."
            number
          />
        </v-form>

        <v-card-actions>
          <v-btn v-if="!isNew" v-on:click="addToCart">Add to cart</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
export default {
  name: "SellDialog",
  props: {
    item: Object,
    opened: Boolean,
  },
  data() {
    return {
      sellNumber: null,
    };
  },
  methods: {
    addToCart() {
      console.log(this.item.id);
      console.log(this.$store.getters["auth/userId"]);
      if (this.sellNumber <= this.item.quantity) {
        api.cartItems
          .addToCart({
            item_id: this.item.id,
            quantity: this.sellNumber,
            user_id: this.$store.getters["auth/userId"],
          })
          .then(() => this.$emit("refresh"));
      } else {
        console.log("Quantity exceeded!");
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.item.id;
    },
  },
};
</script>

<style scoped></style>
