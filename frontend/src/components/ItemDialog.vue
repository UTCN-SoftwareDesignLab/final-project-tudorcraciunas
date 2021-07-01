<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create item" : "Edit item" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-if="!isNew" v-model="item.id" label="ID" readonly />
          <v-text-field v-model="item.name" label="Name" />
          <v-text-field v-model="item.details" label="Details" />
          <v-text-field v-model="item.type" label="Type" />
          <v-text-field v-model="item.quantity" label="Quantity" number />
          <v-text-field v-model="item.price" label="Price" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" v-on:click="deleteItem">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "ItemDialog",
  props: {
    item: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.items
          .create({
            name: this.item.name,
            details: this.item.details,
            type: this.item.type,
            quantity: this.item.quantity,
            price: this.item.price,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.items
          .edit({
            id: this.item.id,
            name: this.item.name,
            details: this.item.details,
            type: this.item.type,
            quantity: this.item.quantity,
            price: this.item.price,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteItem() {
      api.items
        .delete({
          id: this.item.id,
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
