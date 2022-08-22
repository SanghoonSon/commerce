import axios from 'axios'
import TokenService from '../services/token.service'
import store from "@/store";

// Store requests
let sourceRequest = {};

// create an axios instance
const http = axios.create({
  // baseURL: '[베이스 URL를 입력해주세요]', // 스프링 개발용
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 8000, // request timeout
  headers: {
    "Content-Type": "application/json",
    "Allow-Control-Allow-Origin": "*"
  },
})

export const setupHttpInterceptors = () => {
  http.interceptors.request.use(
    config => {
      // If the application exists cancel
      if (sourceRequest[config.url]) {
        sourceRequest[config.url].cancel("Automatic cancellation");
      }

      // Store or update application token
      const axiosSource = axios.CancelToken.source();
      sourceRequest[config.url] = {cancel: axiosSource.cancel};
      config.cancelToken = axiosSource.token;

      const token = TokenService.getAccessToken();
      if (token) {
        config.headers["Authorization"] = `Bearer ${token}`;
        config.headers["cache-control"] = "no-cache";
      }

      return config;
    },
    async (err) => {
      const originalConfig = err.config;
      if (originalConfig.url !== "/auth/signin" && err.response) {
        // Access Token was expired
        if (err.response.status === 401 && !originalConfig._retry) {
          originalConfig._retry = true;
          try {
            const rs = await http.post("/auth/refreshtoken", {
              refreshToken: ''
            });
            const {accessToken} = rs.data;
            await store.dispatch('auth/refreshToken', accessToken);
            return http(originalConfig)
          } catch (_error) {
            return Promise.reject(_error)
          }
        }
      }
      return Promise.reject(err);
    }
  );
};

export default http