import http from "@/http";

class AuthApiService {
  #JWT_EXPIRY_TIME = 24 * 3600 * 1000; // 만료 시간 (24시간 밀리 초로 표현)

  async signUp(sendData) {
    await http.post('/api/v1/auth/signup', JSON.stringify(sendData))
      .then(() => {
        return this.signIn(sendData.email, sendData.password)
      })
      .catch(error => console.log(error.response))
  }

  async signIn(email, password) {
    const data = {
      'email': email,
      'password': password
    };
    return http.post('/api/v1/auth/signin', JSON.stringify(data))
      .then(this.onSignInSuccess)
  }

  async onSilentRefresh() {
    http.post('/api/v1/auth/reissue')
      .then(this.onSignInSuccess)
      .catch(error => {
        console.log(error);
      })
  }

  onSignInSuccess(response) {
    const accessToken = response.headers.authorization;
    const refreshtoken = response.headers.refreshtoken;
    http.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
    console.log(`Bearer ${accessToken}`);

    // accessToken 만료하기 1분 전에 로그인 연장
    setTimeout(this.onSilentRefresh, this.#JWT_EXPIRY_TIME - 60000);
  }
}

export const authApiService = new AuthApiService();



