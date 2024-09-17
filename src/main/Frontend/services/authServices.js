import axios from 'axios';

const API_URL = "http://localhost:8080/api/auth"; // Cambia la URL según tu backend

export const login = async (credentials) => {
    // const response = await axios.post(${API_URL}/login, credentials);
    return response.data;
};

export const register = async (userData) => {
    // const response = await axios.post(${API_URL}/register, userData);
    return response.data;
};