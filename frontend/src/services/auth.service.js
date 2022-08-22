import http from "../http"
import UserService from "./user.service"

class AuthService {
  #JWT_EXPIRY_TIME = 24 * 3600 * 1000; // 만료 시간 (24시간 밀리 초로 표현)

  async signIn(username, password, token) {
    return http
      .post("/api/v1/auth/signin", {
        username,
        password,
        token
      })
      .then((response) => {
        if (response.data.accessToken) {
          UserService.setUser(response.data)
        }
        return response.data;
      })
      .catch(error => {
        console.log(error)
      });
  }

  async signUp(user) {
    return http.post("/api/v1/auth/signup", user)
  }

  async onSilentRefresh() {
    http.post('/api/v1/auth/reissue')
      .then(this.onSignInSuccess)
      .catch(error => {
        console.log(error);
      })
  }

  async onSignInSuccess(response) {
    const accessToken = response.headers.authorization;
    const refreshtoken = response.headers.refreshtoken;
    http.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
    console.log(`Bearer ${accessToken}`);

    // accessToken 만료하기 1분 전에 로그인 연장
    setTimeout(this.onSilentRefresh, this.#JWT_EXPIRY_TIME - 60000);
  }
}

export default new AuthService()