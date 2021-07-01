<template>
  <v-card>
    <v-card-title>
      Items
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="items"
        :search="search"
        @click:row="editItem"
    ></v-data-table>
    <SellDialog
        :opened="dialogVisible"
        :item="selectedItem"
        @refresh="refreshList"
    ></SellDialog>



  </v-card>
</template>

<script>
import api from "../api";
import SellDialog from "../components/SellDialog";

export default {
  name: "ItemList",
  components: { SellDialog },
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
        { text: "Price $", value: "price" },
      ],
      dialogVisible: false,
      selectedItem: {},
      searchBook: "",
    };
  },
  methods: {
    editItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    addItem() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.allItems();
    },


  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
