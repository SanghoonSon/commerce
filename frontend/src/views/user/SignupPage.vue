<template xmlns="http://www.w3.org/1999/html">
  <section class="vh-100" style="background-color: #eee">
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
                  <ul v-if="errors" class="error-messages">
                    <li v-for="(v, k) in errors" :key="k">{{ k }} {{ v | error }}</li>
                  </ul>

                  <form class="mx-1 mx-md-4" @submit.prevent="onSubmit">
                    <div class="d-flex flex-row align-items-center mb-4">
                      <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
                      <div class="form-outline flex-fill mb-0">
                        <label class="form-label" for="email">Email</label>
                        <input
                            type="email"
                            id="email"
                            class="form-control"
                            v-model="email"
                        />
                      </div>
                    </div>

                    <div class="d-flex flex-row align-items-center mb-4">
                      <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                      <div class="form-outline flex-fill mb-0">
                        <label class="form-label" for="password">비밀번호</label>
                        <input
                            type="password"
                            id="password"
                            class="form-control"
                            v-model="password"
                        />
                      </div>
                    </div>

                    <div class="d-flex flex-row align-items-center mb-4">
                      <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                      <div class="form-outline flex-fill mb-0">
                        <label class="form-label" for="passwordConfirm">비밀번호 확인</label>
                        <input
                            type="password"
                            id="passwordConfirm"
                            class="form-control"
                            v-model="passwordConfirm"
                        />
                      </div>
                    </div>

                    <div class="d-flex flex-row align-items-center mb-4">
                      <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                      <div class="form-outline flex-fill mb-0">
                        <label class="form-label" for="name">이름</label>
                        <input
                            type="text"
                            id="name"
                            class="form-control"
                            v-model="username"
                        />
                      </div>
                    </div>

<!--                    <div class="input-group d-flex flex-row align-items-center mb-4">
                      <Address :homeAddress="homeAddress" @changeAddress="changeAddress($event)"/>
                    </div>

                    <div class="d-flex flex-row align-items-center mb-4">
                      <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
                      <div class="form-outline flex-fill mb-0">
                        <label class="form-label" for="phoneNumber">휴대전화</label>
                        <input
                            type="tel"
                            id="phoneNumber"
                            class="form-control"
                            :value="phoneNumber"
                            @input="setUserPhoneNumber($event.target.value)"
                        />
                      </div>
                    </div>-->

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
                      <label class="form-check-label" for="agree_privacy_check0" >
                        [필수] 개인정보 수집 및 이용 동의
                      </label>
                    </div>

                    <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                      <button type="submit" class="btn btn-primary btn-lg">
                        회원가입
                      </button>

                      <a class="btn btn-primary btn-lg mx-2" :href="`${GITHUB_AUTH_URL}`">
                        SignUp With GitHub
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
import Address from "@/components/member/Address.vue";
import { GITHUB_AUTH_URL } from "@/constants";
import { mapState } from "vuex";
import { SIGN_UP } from "@/store/actions.type";

export default {
  components:{
    // eslint-disable-next-line vue/no-unused-components
    Address
  },
  data() {
    return {
      GITHUB_AUTH_URL: Object.freeze(GITHUB_AUTH_URL),
      email: '',
      username: '',
      password: '',
      passwordConfirm: '',
      checkRequired: []
    };
  },
  computed: {
    ...mapState({
      errors: state => state.auth.errors
    })
  },
  methods: {
    onSubmit() {
      this.$store.dispatch(SIGN_UP, {
        email: this.email,
        password: this.password,
        username: this.username
      })
      .then(() => {
        debugger;
        this.$router.push({ name: "home" })
      });
    },
    validEmail: function (email) {
      const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(email);
    },
    changeAddress: function (val) {
      this.homeAddress = val;
    }
  }
};
</script>
