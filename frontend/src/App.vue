<template>
  <Header />
  <div id="main">
    <!-- convert -->
    <!-- <a href="#/main" class="">main</a> -->
    <router-view></router-view>
  </div>
  <Footer />
</template>

<script>
import Header from "./components/layout/Header.vue";
import Footer from "./components/layout/Footer.vue";

export default {
  name: "App",
  components: {
    Header,
    Footer,
  },
};
</script>
