<template>
  <div class="div_class">
    <h3>You will be redirected to payment page</h3>

    <div class="alert alert-primary" role="alert">
      While making payment use card number 4242 4242 4242 4242 and enter random
      cvv(3 digit)
    </div>

    <button
      class="checkout_button"
      id="proceed-to-checkout"
      @click="goToCheckout()"
    >
      Make payment
    </button>
  </div>
</template>
<script src="https://js.stripe.com/v3/"></script>
<script>
import authHeader, { BASE_URL, HTTP } from "../api/http";
import api from "../api";
export default {
  data() {
    return {
      items: [],
      stripeAPIToken: `${process.env.VUE_APP_STRIPETOKEN}`,
      stripe: "",
      token: null,
      sessionId: null,
      checkoutBodyArray: [],
    };
  },
  name: "Checkout",
  props: ["BASE_URL"],

  mounted() {
    this.token = localStorage.getItem("token");
    if (typeof this.$route.params.id === "undefined") {
    }
    this.includeStripe(
      "js.stripe.com/v3/",
      function () {
        this.configureStripe();
      }.bind(this)
    );
    this.getAllItems();
  },

  methods: {
    /*
        Includes Stripe.js dynamically
    */
    includeStripe(URL, callback) {
      let documentTag = document,
        tag = "script",
        object = documentTag.createElement(tag),
        scriptTag = documentTag.getElementsByTagName(tag)[0];
      object.src = "//" + URL;
      if (callback) {
        object.addEventListener(
          "load",
          function (e) {
            callback(null, e);
          },
          false
        );
      }
      scriptTag.parentNode.insertBefore(object, scriptTag);
    },

    /*
        Configures Stripe by setting up the elements and
        creating the card element.
    */
    configureStripe() {
      this.stripe = window.Stripe(this.stripeAPIToken);
    },
    async getAllItems() {
      this.$http
        .get(BASE_URL + "/cart/" + this.$store.getters["auth/userId"])
        .then(
          (response) => {
            if (response.status === 200) {
              let products = response.data;
              console.log(products);
              let len = products.length;
              for (let i = 0; i < len; i++)
              {
                console.log(products[i].name);
                console.log(products[i].quantity);
                console.log(products[i].price);
                this.checkoutBodyArray.push({
                  name: products[i].name,
                  quantity: products[i].quantity,
                  price: products[i].price,
                });
              }

            }
          },
          (err) => {
            console.log(err);
          }
        );

      // this.items = await api.cartItems.allItems();
      //
      //
      // console.log(this.items);
      // let len = this.items.length;
      // for (let i = 0; i < len; i++)
      //   this.checkoutBodyArray.push({
      //     productName: this.items[i].item.name,
      //     quantity: this.items[i].item.quantity,
      //     price: this.items[i].item.price,
      //   });
    },

    goToCheckout() {
      this.$http
        .post(
          BASE_URL + "/cart/create-checkout-session",
          this.checkoutBodyArray
        )
        .then((response) => {
          localStorage.setItem("sessionId", response.data.sessionId);
          return response.data;
        })
        .then((session) => {
          return this.stripe.redirectToCheckout({
            sessionId: session.sessionId,
          });
        });
    },
  },
};
</script>

<style>
.alert {
  width: 50%;
}
.div_class {
  margin-top: 5%;
  margin-left: 30%;
}
.checkout_button {
  background-color: #5d3dec;
  border: none;
  color: white;
  margin-left: 15%;
  padding: 15px 30px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 15px;
  font-weight: bold;
  border-radius: 15px;
}
.checkout_button:focus {
  outline: none;
  box-shadow: none;
}
.checkout_button:disabled {
  background-color: #9b86f7;
  border: none;
  color: white;
  margin-left: 15%;
  padding: 15px 30px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 15px;
  font-weight: bold;
  border-radius: 15px;
  cursor: not-allowed;
}
</style>
