import axiosClient from "./axiosClient";

const projectApi = {
  getProjects: (params) => {
    const url = "/projects";
    return axiosClient.get(url, { params });
  },
  getProjectByName: (name) => {
    const url = `/projects/${name}`;
    return axiosClient.get(url);
  },
};

export default projectApi;