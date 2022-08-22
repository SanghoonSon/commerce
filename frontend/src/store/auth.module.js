import {
  LOG_OUT,
  SIGN_IN,
  SIGN_UP,
  UPDATE_USER
} from "@/store/actions.type";
import { PURGE_AUTH, SET_AUTH, SET_ERROR } from "@/store/mutations.type";
import { GET_USER_TOKEN, GET_CURRENT_USER, IS_AUTHENTICATED } from "@/store/getters.type";
import TokenService from "@/services/token.service";
import http from "@/http";

const state = {
  errors: null,
  user: {
    "email": null,
    "username": null
  },
  isAuthenticated: false
}

const mutations = {
  [SET_ERROR](state, error) {
    state.errors = error;
  },
  [SET_AUTH](state, {email, username, token}) {
    state.isAuthenticated = true;
    state.user = {
      'email': email,
      'username': username
    };
    state.errors = {};
    TokenService.saveAccessToken(token);
  },
  [PURGE_AUTH](state) {
    state.isAuthenticated = false;
    state.user = {};
    state.errors = {};
    TokenService.destroyAccessToken();
  }
}

const getters = {
  [GET_USER_TOKEN]: () => TokenService.getAccessToken(),
  [GET_CURRENT_USER]: state => state.user,
  [IS_AUTHENTICATED]: state => state.isAuthenticated
}

const actions = {
  [SIGN_IN](context, credentials) {
    return new Promise(resolve => {
      http.post("/api/v1/auth/signin", credentials)
          .then(({data, headers}) => {
            context.commit(SET_AUTH, {
              ...data,
              'token': headers.authorization
            });
            resolve(data);
          })
          .catch((response) => {
            const errors = response.data?.errors ? response.data.errors : response.data?.error;
            context.commit(SET_ERROR, errors);
          })
    });
  },
  [LOG_OUT](context) {
    context.commit(PURGE_AUTH);
  },
  [SIGN_UP](context, credentials) {
    return new Promise((resolve, reject) => {
      http.post("/api/v1/auth/signup", credentials)
          .then(() => {
            resolve(context.dispatch(SIGN_IN, credentials));
          })
          .catch((response) => {
            const errors = response.data?.errors ? response.data.errors : response.data?.error;
            context.commit(SET_ERROR, errors);
            reject(response);
          })
    })
  },
  [UPDATE_USER](context, payload) {
    const { email, username, password, image, bio } = payload;
    const user = {
      email,
      username,
      bio,
      image
    };
    if(password) {
      user.password = password;
    }

    return http.put("/api/v1/user", user).then(({ data }) => {
      context.commit(SET_AUTH, data.user);
      return data;
    })
  }
}

export default {
  state,
  actions,
  mutations,
  getters
};