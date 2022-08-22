import { createApp } from "vue";
import App from "./App.vue";

// Optional, since every component import their Bootstrap functionality
// the following line is not necessary
// import 'bootstrap'

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";
import store from "@/store";
import router from "./router";
import http, { setupHttpInterceptors } from "@/http"

const app = createApp(App)
    .use(router)
    .use(store);

// add http properties
setupHttpInterceptors(store)
app.config.globalProperties.http = http;

app.mount("#app");
