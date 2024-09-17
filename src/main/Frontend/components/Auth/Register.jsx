import React, { useState } from 'react';
import { register } from '../../services/authService';

const Register = () => {
    const [userData, setUserData] = useState({ username: '', password: '', role: '' });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await register(userData);
            alert('Usuario registrado con éxito');
        } catch (error) {
            console.error("Error al registrar", error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="text"
                placeholder="Usuario"
                value={userData.username}
                onChange={(e) => setUserData({ ...userData, username: e.target.value })}
            />
            <input
                type="password"
                placeholder="Contraseña"
                value={userData.password}
                onChange={(e) => setUserData({ ...userData, password: e.target.value })}
            />
            <select
                value={userData.role}
                onChange={(e) => setUserData({ ...userData, role: e.target.value })}
            >
                <option value="USER">Usuario</option>
                <option value="ADMIN">Administrador</option>
            </select>
            <button type="submit">Registrar</button>
        </form>
    );
};

export default Register;