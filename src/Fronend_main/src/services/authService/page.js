import axios from 'axios';

export async function login(email, password) {
    const response = await axios.post('/api/auth/login', {
        correo: email,
        contrasena: password,
    });
    return response.data;
}

export async function register(user) {
    const response = await axios.post('/api/auth/register', user);
    return response.data;
}

export default {
    login,
    register
}