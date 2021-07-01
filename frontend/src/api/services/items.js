import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allItems() {
    return HTTP.get(BASE_URL + "/items", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(item) {
    return HTTP.post(BASE_URL + "/items", item, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  delete(item) {
    return HTTP.delete(BASE_URL + "/items/" + item.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(item) {
    return HTTP.patch(BASE_URL + "/items/" + item.id, item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  sell(items){
    return HTTP.patch(BASE_URL + "/items", items, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },




  pdfBoxDownload() {
    return HTTP.get(BASE_URL + "/items/export/PDF", {
      responseType: "arraybuffer",
      headers: authHeader(),
    }).then((response) => {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", "Items_Out_Of_Stock.pdf");
      document.body.appendChild(link);
      link.click();
    });
  },
  generatePDF() {
    return HTTP.get(BASE_URL + "/items/export/PDF", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  generateCSV() {
    return HTTP.get(BASE_URL + "/items/export/CSV", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
