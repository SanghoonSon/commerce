<template>
  <p v-if="this.$route.query.error">
    <br>
    {{ this.$route.query.error }}
  </p>
  <section v-else class="vh-100" style="background-color: #eee">
    <p>{{ this.$route }}</p>
    <div class="container h-100">
      <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col-lg-12 col-xl-11">
          <div class="card text-black" style="border-radius: 25px">
            <div class="card-body p-md-5">
              <div class="row justify-content-center">
                <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">
                  <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">
                    회원가입
                  </p>

                  <form class="mx-1 mx-md-4" @submit="submitForm">
                    <div class="form-check d-flex mb-1">
                      <input
                          class="form-check-input me-2"
                          type="checkbox"
                          value=""
                          id="agree_service_check0"
                          v-model="checkRequired"
                      />
                      <label class="form-check-label" for="agree_service_check0">
                        [필수] 이용약관 동의
                      </label>
                    </div>

                    <div class="form-check d-flex mb-5">
                      <input
                          class="form-check-input me-2"
                          type="checkbox"
                          value=""
                          id="agree_privacy_check0"
                          v-model="checkRequired"
                      />
                      <label class="form-check-label" for="agree_privacy_check0">
                        [필수] 개인정보 수집 및 이용 동의
                      </label>
                    </div>

                    <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                      <button type="submit" class="btn btn-primary btn-lg">
                        회원가입
                      </button>
                    </div>
                  </form>
                </div>
                <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">
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

import { mapActions, mapMutations } from "vuex";

const authStore = 'auth';

export default {
  data: () => ({
    checkRequired: []
  }),
  created() {
    const token = this.$route.query.token;
    console.log('token', token);

    if(token) {
      this.setToken(token)
    }
    this.$router.push('/sign-in')
  },
  methods: {
    ...mapActions(authStore, [
        'signin'
    ]),
    ...mapMutations(authStore, ['setToken']),
    async submitForm(e) {
      e.preventDefault();


    }
  }
}
</script>
