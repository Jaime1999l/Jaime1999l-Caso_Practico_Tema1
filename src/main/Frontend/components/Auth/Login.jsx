import React, { useState, useContext } from 'react';
import { AuthContext } from '../../context/AuthContext';
import { login } from '../../services/authService';

const Login = () => {
    const [credentials, setCredentials] = useState({ username: '', password: '' });
    const { login: loginContext } = useContext(AuthContext);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const data = await login(credentials);
            loginContext(data.user, data.token); // Guardar el token y usuario en el contexto
        } catch (error) {
            console.error("Error al iniciar sesión", error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="text"
                placeholder="Usuario"
                value={credentials.username}
                onChange={(e) => setCredentials({ ...credentials, username: e.target.value })}
            />
            <input
                type="password"
                placeholder="Contraseña"
                value={credentials.password}
                onChange={(e) => setCredentials({ ...credentials, password: e.target.value })}
            />
            <button type="submit">Iniciar Sesión</button>
        </form>
    );
};

export default Login;