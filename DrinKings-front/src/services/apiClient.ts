import axios, { AxiosInstance } from 'axios';

const API_URL = import.meta.env.BACK_API_URL || 'http://localhost:9090';

const apiClient: AxiosInstance = axios.create({
    baseURL: API_URL,
    timeout: 5000, // Optional: set timeout in milliseconds
    headers: {
        'Content-Type': 'application/json',
        // Add other headers if needed
    },
});

export default apiClient;