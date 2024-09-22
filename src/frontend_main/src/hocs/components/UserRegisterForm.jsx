// UserRegisterForm.jsx
import React, { useState } from 'react';
import { register } from '../../services/authService/page';

export default function UserRegisterForm() {
    const [name, setName] = useState('');
    const [lastName1, setLastName1] = useState('');
    const [lastName2, setLastName2] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [phone, setPhone] = useState('');
    const [address, setAddress] = useState('');
    const [role, setRole] = useState('user');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        try {
            await register({
                nombre: name,
                apellido1: lastName1,
                apellido2: lastName2,
                correo: email,
                contrasena: password,
                telefono: phone,
                direccion: address,
                role // se está utilizando para la URL del backend
            });
            alert('User registered successfully');
        } catch (err) {
            setError('Error during registration');
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input type="text" placeholder="First Name" value={name} onChange={(e) => setName(e.target.value)} required />
            <input type="text" placeholder="Last Name" value={lastName1} onChange={(e) => setLastName1(e.target.value)} required />
            <input type="text" placeholder="Second Last Name" value={lastName2} onChange={(e) => setLastName2(e.target.value)} required />
            <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required />
            <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required />
            <input type="text" placeholder="Phone" value={phone} onChange={(e) => setPhone(e.target.value)} required />
            <input type="text" placeholder="Address" value={address} onChange={(e) => setAddress(e.target.value)} required />
            <select value={role} onChange={(e) => setRole(e.target.value)} required>
                <option value="user">User</option>
                <option value="admin">Admin</option>
            </select>
            <button type="submit">Register</button>
            {error && <p style={{ color: 'red' }}>{error}</p>}
        </form>
    );
}
