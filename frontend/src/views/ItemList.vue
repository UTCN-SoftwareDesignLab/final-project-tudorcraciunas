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
      <v-btn @click="addItem">Add Item</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="items"
      :search="search"
      @click:row="editItem"
    ></v-data-table>
    <ItemDialog
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></ItemDialog>
    <v-btn @click="generatePDF">PDF Report</v-btn>
    <v-btn @click="generateCSV">CSV Report</v-btn>
    <v-spacer></v-spacer>

  </v-card>
</template>

<script>
import api from "../api";
import ItemDialog from "../components/ItemDialog";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export default {
  name: "ItemList",
  components: { ItemDialog },
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
    generatePDF() {
      api.items.pdfBoxDownload();
    },
    generateCSV() {
      api.items.generateCSV();
    },
  },
  created() {
    this.notificationSocket = new SockJS("http://localhost:8088/notification");
    this.notificationStomp = new Stomp.over(this.notificationSocket);
    this.notificationStomp.connect({}, (note) => {console.log(note); this.notificationStomp.subscribe("/items", (out) => {console.log(out.body)})});

    this.refreshList();
  },
};
</script>

<style scoped></style>
