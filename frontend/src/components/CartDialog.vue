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
          <v-text-field v-model="item.price" label="Price" readonly />
          <v-text-field
            v-model="sellNumber"
            label="Choose how many you want in cart."
            number
          />
        </v-form>

        <v-card-actions>
          <v-btn v-if="!isNew" v-on:click="updateCart">Update amount in cart</v-btn>
        </v-card-actions>

        <v-card-actions>
          <v-btn v-if="!isNew" v-on:click="deleteCart">Delete Item</v-btn>
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
    updateCart() {
      console.log(this.item.id);
      api.cartItems
        .updateCart({
          item_id: this.item.id,
          quantity: this.sellNumber,
          user_id: this.$store.getters["auth/userId"],
        })
        .then(() => this.$emit("refresh"));
    },
    deleteCart() {
      api.cartItems
        .delete({
          item_id: this.item.id,
          user_id: this.$store.getters["auth/userId"],
        })
        .then(() => this.$emit("refresh"));
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
