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
  createProject: (data) => {
    const url = "/projects";
    return axiosClient.post(url, data);
  },
  updateProject: (id, data) => {
    const url = `/projects/${id}`;
    return axiosClient.put(url, data);
  },
  deleteProject: (id) => {
    const url = `/projects/${id}`;
    return axiosClient.delete(url);
  },
};

export default projectApi;