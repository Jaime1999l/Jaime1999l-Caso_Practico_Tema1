'use client';

import { useState } from 'react';
import AuthService from '../../services/authService/page';

export default function UserRegisterForm() {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('user');
    const [success, setSuccess] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setSuccess('');

        if (password.length < 6) {
            setError('Password must be at least 6 characters long');
            return;
        }

        setLoading(true);
        try {
            await AuthService.register({ name, email, password, role });
            setSuccess('User registered successfully');
            setError('');
            setName('');
            setEmail('');
            setPassword('');
            setRole('user');
        } catch (err) {
            setError(err.response?.data?.message || 'Failed to register user');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container">
            <style jsx>{`
                .container {
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    justify-content: center;
                    min-height: 100vh;
                    background-color: #f0f0f0;
                    font-family: 'Arial', sans-serif;
                }
                .form {
                    background-color: #fff;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                    display: flex;
                    flex-direction: column;
                    width: 300px;
                }
                input, select, button {
                    margin: 10px 0;
                    padding: 10px;
                }
                button {
                    background-color: orangered;
                    color: white;
                    border: none;
                    cursor: pointer;
                }
                button:hover {
                    background-color: darkred;
                }
            `}</style>
            <h2>Register New User</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {success && <p style={{ color: 'green' }}>{success}</p>}
            <form onSubmit={handleSubmit} className="form">
                <input
                    type="text"
                    placeholder="Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <select value={role} onChange={(e) => setRole(e.target.value)}>
                    <option value="user">User</option>
                    <option value="admin">Admin</option>
                </select>
                <button type="submit" disabled={loading}>
                    {loading ? 'Registering...' : 'Register'}
                </button>
            </form>
        </div>
    );
}
