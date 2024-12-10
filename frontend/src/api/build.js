import axiosClient from "./axiosClient";

const buildApi = {
  getBuilds: (projectName, pipelineName, params) => {
    const url = `/projects/${projectName}/pipelines/${pipelineName}/builds`;
    return axiosClient.get(url, { params });
  },
  getBuildById: (id) => {
    const url = `/builds/${id}`;
    return axiosClient.get(url);
  },
};

export default buildApi;