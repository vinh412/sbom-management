import axiosClient from "./axiosClient";

const userApi = {
  getUsers: (params) => {
    const url = "/users";
    return axiosClient.get(url, { params });
  },
  getUserByUsername: (username) => {
    const url = `/users/${username}`;
    return axiosClient.get(url);
  },
};

export default userApi;