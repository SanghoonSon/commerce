<template>
  <header>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
      <div class="container-fluid">
        <router-link to="/">
          <a class="navbar-brand">Carousel</a>
        </router-link>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarCollapse"
          aria-controls="navbarCollapse"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
          <ul class="navbar-nav me-auto mb-2 mb-md-0">
            <li class="nav-item">
              <router-link to="/">
                <a class="nav-link active" aria-current="page" href="#">Home</a>
              </router-link>
            </li>
            <li class="nav-item">
              <router-link to="/">
                <a class="nav-link" href="#">Link</a>
              </router-link>
            </li>
            <li class="nav-item">
              <a
                class="nav-link disabled"
                href="#"
                tabindex="-1"
                aria-disabled="true"
                >Disabled</a
              >
            </li>
          </ul>
          <form class="d-flex">
            <input
              class="form-control me-2"
              type="search"
              placeholder="Search"
              aria-label="Search"
            />
            <button class="btn btn-outline-success">
              Search
            </button>
          </form>
          <div class="px-3" v-if="!isAuthenticated">
            <router-link to="/sign-in">
              <a class="btn btn-outline-primary" type="submit">Sign In</a>
            </router-link>
            <router-link to="/sign-up">
              <a class="btn btn-outline-primary ms-2" type="submit">Sign Up</a>
            </router-link>
          </div>
          <div class="px-3" v-if="isAuthenticated">
            <span>{{ getCurrentUser.username }}</span>
            <button class="btn btn-outline-primary ms-2" v-on:click="logout">Logout</button>
          </div>
        </div>
      </div>
    </nav>
  </header>
</template>
<script>
import { mapGetters } from "vuex";
import { GET_CURRENT_USER, IS_AUTHENTICATED } from "@/store/getters.type";
import { LOG_OUT } from "@/store/actions.type";
import http from "@/http";

export default {
  computed: {
    ...mapGetters({
      'getCurrentUser': GET_CURRENT_USER,
      'isAuthenticated': IS_AUTHENTICATED
    })
  },
  methods: {
    logout() {
      this.$store.dispatch(LOG_OUT);
      this.$router.push({ name: "home" });
    }
  }
};
</script>
