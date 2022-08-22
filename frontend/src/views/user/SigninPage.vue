<template>
  <section class="vh-100" style="background-color: #eee">
    <div class="container h-100">
      <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col-lg-12 col-xl-11">
          <div class="card text-black" style="border-radius: 25px">
            <div class="card-body p-md-5">
              <div class="row justify-content-center">
                <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">
                  <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">
                    로그인
                  </p>

                  <form class="mx-1 mx-md-4" @submit.prevent="onSubmit">
                    <div class="d-flex flex-row align-items-center mb-4">
                      <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
                      <div class="form-outline flex-fill mb-0">
                        <input
                            type="email"
                            id="form3Example3c"
                            class="form-control"
                            v-model="email"
                            :placeholder="emailPlaceholder"
                        />
                      </div>
                    </div>

                    <div class="d-flex flex-row align-items-center mb-4">
                      <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                      <div class="form-outline flex-fill mb-0">
                        <input
                            type="password"
                            id="form3Example4cd"
                            class="form-control"
                            v-model="password"
                            :placeholder="passwordPlaceholder"
                        />
                      </div>
                    </div>

                    <div class="form-check d-flex justify-content-center mb-5">
                      <input
                          class="form-check-input me-2"
                          type="checkbox"
                          value=""
                          id="form2Example3"
                      />
                      <label class="form-check-label" for="form2Example3">
                        I agree all statements in
                        <a href="#!">Terms of service</a>
                      </label>
                    </div>

                    <div
                        class="d-flex justify-content-center mx-4 mb-3 mb-lg-4"
                    >
                      <button type="submit" class="btn btn-primary btn-lg">
                        Login
                      </button>

                      <a class="btn btn-primary btn-lg mx-2" :href="`${GITHUB_AUTH_URL}`">
                        Login With GitHub
                      </a>
                    </div>
                  </form>
                </div>
                <div
                    class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2"
                >
                  <img
                      src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/draw1.webp"
                      class="img-fluid"
                      alt="Sample image"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
<script>
import { mapState } from "vuex";
import { GITHUB_AUTH_URL } from "@/constants";
import { SIGN_IN } from "@/store/actions.type";

export default {
  data: () => ({
    email: '',
    emailPlaceholder: '아이디(e-mail)',
    password: '',
    passwordPlaceholder: '패스워드',
    GITHUB_AUTH_URL: Object.freeze(GITHUB_AUTH_URL)
  }),
  computed: {
    ...mapState({
      errors: state => state.auth.errors
    })
  },
  methods: {
    onSubmit() {
      this.$store.dispatch(SIGN_IN, {
        email: this.email,
        password: this.password
      })
      .then(() => this.$router.push({ name: "home" }));
    }
  }
};
</script>
