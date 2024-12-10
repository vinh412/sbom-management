import axiosClient from "./axiosClient";

const permissionApi = {
  getPermissions: (params) => {
    const url = "/permissions";
    return axiosClient.get(url, { params });
  }
};

export default permissionApi;