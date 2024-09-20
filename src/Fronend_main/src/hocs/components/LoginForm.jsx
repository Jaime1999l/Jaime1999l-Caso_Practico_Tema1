'use client';

import React from 'react';
import { useState } from 'react';
import AuthService from '../../services/authService/page.js';
import { useNavigate } from 'react-router-dom';

export default function LoginForm() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');
        try {
            const response = await AuthService.login(email, password);
            const { token, role } = response.data;
            localStorage.setItem('token', token);
            if (role === 'admin') {
                navigate('/admin');
            } else {
                navigate('/user');
            }
        } catch (err) {
            setError(err.response?.data?.message || 'Invalid credentials. Please try again.');
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
                    background-color: black;
                    color: white;
                    font-family: 'Arial', sans-serif;
                }
                .form {
                    display: flex;
                    flex-direction: column;
                    background-color: #333;
                    padding: 20px;
                    border-radius: 5px;
                    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
                }
                label, input, button {
                    margin: 10px 0;
                }
                button {
                    background-color: orangered;
                    color: white;
                    border: none;
                    padding: 10px;
                    cursor: pointer;
                    transition: background-color 0.3s ease;
                }
                button:hover {
                    background-color: darkred;
                }
            `}</style>
            <h2>Login</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <form onSubmit={handleSubmit} className="form">
                <label htmlFor="email">Email</label>
                <input
                    type="email"
                    id="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    disabled={loading}
                />
                <label htmlFor="password">Password</label>
                <input
                    type="password"
                    id="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    disabled={loading}
                />
                <button type="submit" disabled={loading}>
                    {loading ? 'Logging in...' : 'Login'}
                </button>
            </form>
        </div>
    );
}
