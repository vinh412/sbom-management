import axiosClient from "./axiosClient";

const projectApi = {
  getProjects: () => {
    const url = "/projects";
    return axiosClient.get(url);
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
  getAllMembers: (name) => {
    const url = `/projects/${name}/members`;
    return axiosClient.get(url);
  },
  addMember: (data) => {
    const url = `/projects/members`;
    return axiosClient.post(url, data);
  },
  removeMember: (projectId, userId) => {
    const url = `/projects/${projectId}/members/${userId}`;
    return axiosClient.delete(url);
  },
  updateMember: (projectId, userId, data) => {
    const url = `/projects/${projectId}/members/${userId}`;
    return axiosClient.put(url, data);
  }
};

export default projectApi;