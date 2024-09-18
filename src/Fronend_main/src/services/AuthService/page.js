import axios from 'axios';

const API_URL = '/api/auth'; // Usar URL relativa para que el proxy redirija correctamente

const login = (email, password) => {
    return axios.post(`${API_URL}/login`, { correo: email, contrasena: password });
};

const register = (user) => {
    return axios.post(`${API_URL}/register`, user);
};

export default {
    login,
    register,
};
