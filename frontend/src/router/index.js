import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import CheckOut from "../views/CheckOut";
import ItemList from "../views/ItemList.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import ItemListEmployee from "../views/ItemListEmployee";
import ShoppingCart from "../views/ShoppingCart";
import Success from "../views/payment/Success";
import Failure from "../views/payment/Failure";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/checkOut",
    name: "Checkout",
    component: CheckOut,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },

  {
    path: "/payment/success",
    name:"Success",
    component: Success,

  },
  {
    path: "/payment/failure",
    name:"Failure",
    component: Failure,

  },
  {
    path: "/users",
    name: "Users",
    component: UserList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "ItemListEmployee" });
      }
    },
  },
  {
    path: "/items",
    name: "Items",
    component: ItemList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "ItemListEmployee" });
      }
    },
  },
  {
    path: "/itemsEmployee",
    name: "Items as regular user",
    component: ItemListEmployee,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/cart",
    name: "Shopping Cart",
    component: ShoppingCart,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
];

const router = new VueRouter({
  routes,
});

export default router;
