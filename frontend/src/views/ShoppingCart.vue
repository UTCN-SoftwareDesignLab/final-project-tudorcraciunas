<template>
  <v-card>
    <v-card-title>
      Your Shopping Cart
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>

      <div class="grand-total">
        <div class="total">
          <h3>Total</h3>
          <h3>{{ computePrice }} $</h3>
        </div>
      </div>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="items"
      :search="search"
      @click:row="editItem"
    ></v-data-table>

    <CartDialog
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></CartDialog>
    <v-btn @click="checkOut">Check Out</v-btn>
  </v-card>
</template>

<script>
import CartDialog from "../components/CartDialog";
import api from "../api";
import router from "../router";

//import router from "../router";

export default {
  name: "ShoppingCart",
  components: { CartDialog },
  data() {
    return {
      items: [],
      search: "",
      headers: [
        {
          text: "Name",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Details", value: "details" },
        { text: "Type", value: "type" },
        { text: "Quantity", value: "quantity" },
        { text: "Price", value: "price" },
      ],
      dialogVisible: false,
      selectedItem: {},
      searchBook: "",
    };
  },
  computed: {
    computePrice() {
      return this.items.reduce((acc, item) => {
        return acc + item.quantity * item.price;
      }, 0);
    },
  },
  methods: {
    editItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },

    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.cartItems.allItems();
      console.log(this.items);
    },

    checkOut() {
      // api.cartItems
      // .checkOutSession(this.items);

      router.push("/checkOut");
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
