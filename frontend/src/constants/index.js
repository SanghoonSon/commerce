export const API_BASE_URL = 'http://localhost:8000';
export const ACCESS_TOKEN = 'accessToken';

export const OAUTH2_SUCCESS_REDIRECT_URI = 'http://localhost:3000/oauth2/redirect'
export const OAUTH2_FAIL_REDIRECT_URI = 'http://localhost:3000/oauth2/redirect'
export const OAUTH2_REDIRECT_URI = 'redirect_uri=' + OAUTH2_SUCCESS_REDIRECT_URI + '&error_uri=' + OAUTH2_FAIL_REDIRECT_URI

export const GOOGLE_AUTH_URL = API_BASE_URL + '/oauth2/authorize/google?' + OAUTH2_REDIRECT_URI;
export const FACEBOOK_AUTH_URL = API_BASE_URL + '/oauth2/authorize/facebook?' + OAUTH2_REDIRECT_URI;
export const GITHUB_AUTH_URL = API_BASE_URL + '/oauth2/authorize/github?' + OAUTH2_REDIRECT_URI;