import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allItems() {
    return HTTP.get(BASE_URL + "/cart", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  //TODO: nuj daca functioneaza, am incercat sa trimit la /cart/{id} tot item-ul si sa-l construiesc in controller
  addToCart(item) {
    return HTTP.post(BASE_URL + "/cart", item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  updateCart(item){

    return HTTP.patch(BASE_URL + "/cart/" + item.item_id, item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(item) {
    return HTTP.delete(
      BASE_URL + "/cart/" + item.item_id + "/" + item.user_id,
      {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  deleteCart(user){
    return HTTP.delete(
        BASE_URL + "/cart/" + user.user_id,
        {
          headers: authHeader(),
        }).then((response) => {
      return response.data;
    });
  },
  checkOutSession(checkout){
    return HTTP.post(BASE_URL + "/cart", checkout, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  }
};
