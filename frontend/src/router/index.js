import { createWebHistory, createRouter } from "vue-router";
import Home from "@/views/Home.vue";
import SigninPage from "@/views/user/SigninPage.vue";
import SignupPage from "@/views/user/SignupPage.vue";
import PageNotFound from "@/views/PageNotFound.vue";
import OAuth2RedirectPage from "@/views/auth/OAuth2RedirectPage.vue";

const routes = [
  {
    path: "/",
    name: 'Index',
    redirect: "/home",
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/home",
    name: 'home',
    component: Home,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/sign-up",
    name: 'signUp',
    component: SignupPage,
  },
  {
    path: "/sign-in",
    name: 'signIn',
    component: SigninPage,
  },
  {
    path: "/oauth2/redirect",
    name: 'oAuth2Redirect',
    component: OAuth2RedirectPage,
  },
  {
    path: "/products",
    name: 'products',
    component: Home,
    meta: {
      auth: true
    }
  },
  {
    path: "/:pathMatch(.*)*",
    redirect: "/404",
  },
  {
    path: "/404",
    component: PageNotFound,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  next();
  /*debugger;
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if(!store.getters["auth/isAuthenticated"]) {
      next({ name: 'SignIn' })
    } else {
      next();
    }
  } else {
    next();
  }*/
});

export default router;
