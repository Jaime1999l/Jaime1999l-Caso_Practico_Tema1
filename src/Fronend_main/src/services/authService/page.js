import axios from 'axios';

const API_BASE_URL = '/api';

export async function login(email, password) {
    try {
        const response = await axios.post(`${API_BASE_URL}/auth/login`, {
            correo: email,
            contrasena: password,
        });

        // Almacenar el token y el rol del usuario en localStorage
        const {token, role} = response.data;
        localStorage.setItem('token', token);
        localStorage.setItem('role', role); // Almacenar el rol del usuario

        return response.data;
    } catch (error) {
        console.error('Error during login:', error);
        throw error;
    }
}

export async function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
}

export async function getCurrentUserRole() {
    return localStorage.getItem('role');
}

export async function isAuthenticated() {
    return localStorage.getItem('token');
}

export async function register(user) {
    try {
        const response = await axios.post(`${API_BASE_URL}/auth/register`, user);
        return response.data;
    } catch (error) {
        console.error('Error during registration:', error);
        throw error;
    }
}

export default {
    login,
    register,
    logout,
    getCurrentUserRole,
    isAuthenticated
};
