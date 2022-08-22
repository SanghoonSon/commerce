const ID_TOKEN_KEY = "id_token";

export const getAccessToken = () => {
  return window.localStorage.getItem(ID_TOKEN_KEY);
}

export const saveAccessToken = (token) => {
  localStorage.setItem(ID_TOKEN_KEY, token);
}

export const destroyAccessToken = () => {
  window.localStorage.removeItem(ID_TOKEN_KEY);
}

export default {getAccessToken, saveAccessToken, destroyAccessToken}