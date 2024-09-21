const API_BASE_URL = 'http://localhost:8080/api';

export async function login(email, password) {
    try {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ correo: email, contrasena: password })
        });

        if (!response.ok) {
            throw new Error('Error during login');
        }

        const data = await response.json();
        localStorage.setItem('token', data.token);
        localStorage.setItem('role', data.role); // Asegúrate de guardar el role

        return data;
    } catch (error) {
        console.error('Error during login:', error);
        throw error;
    }
}

export async function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
}

export function getCurrentUserRole() {
    return localStorage.getItem('role');
}

export function isAuthenticated() {
    return localStorage.getItem('token') !== null;
}

export async function register(user) {
    try {
        const response = await fetch(`${API_BASE_URL}/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        });
        if (!response.ok) {
            throw new Error('Error during registration');
        }
        return await response.json();
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
