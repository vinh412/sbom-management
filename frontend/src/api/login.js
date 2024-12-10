import axiosClient from "./axiosClient";

export const login = (credentials) => {
  const url = "/auth/login";
  return axiosClient.post(url, credentials);
};