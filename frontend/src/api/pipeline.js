import axiosClient from "./axiosClient";

const pipelineApi = {
  getPipelinesByProjectName: (projectName, params) => {
    const url = `/projects/${projectName}/pipelines`;
    return axiosClient.get(url, { params });
  },
  getLatestBuildDependencies: (projectName, pipelineName) => {
    const url = `/projects/${projectName}/pipelines/${pipelineName}/builds/latest/dependencies`;
    return axiosClient.get(url);
  },
  getLatestBuildComponents: (projectName, pipelineName) => {
    const url = `/projects/${projectName}/pipelines/${pipelineName}/builds/latest/components`;
    return axiosClient.get(url);
  }
};

export default pipelineApi;