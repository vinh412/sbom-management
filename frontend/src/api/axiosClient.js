import axios from "axios";

const publicUrl = [
  "/auth/login",
]

const axiosClient = axios.create({
  baseURL: "http://localhost:8888/api/v1",
  headers: {
    "Content-Type": "application/json",
  },
});

axiosClient.interceptors.request.use(
  (config) => {
    if (publicUrl.includes(config.url)) {
      return config;
    }
    const token = localStorage.getItem("jwt");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axiosClient.interceptors.response.use(
  (response) => {
    if (response && response.data) {
      return response.data.data;
    }
    return response;
  },
  (error) => {
    if (error.response && error.response.status === 401) {
      // Token hết hạn, chuyển hướng đến trang đăng nhập
      window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

export default axiosClient;