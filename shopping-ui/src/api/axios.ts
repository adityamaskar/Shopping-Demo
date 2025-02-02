import axios from "axios";

const axiosInstance = axios.create({
  // baseURL: "http://localhost", // Your backend API base URL
  headers: {
    "Content-Type": "application/json",
  },
});

export default axiosInstance;
