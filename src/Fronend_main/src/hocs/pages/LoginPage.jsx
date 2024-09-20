import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AuthService from '../../services/authService/page';

export default function LoginPage() {
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
            console.log('Login response:', response); // Verificar la respuesta del backend
            const { token, role } = response; // Extraer el token y rol del objeto de respuesta

            if (role) { // Verificar que el rol esté presente
                // Guardar el token y rol en el almacenamiento local
                localStorage.setItem('token', token);
                localStorage.setItem('role', role);

                // Redirigir al usuario dependiendo del rol
                if (role === 'admin') {
                    navigate('/admin');
                } else {
                    navigate('/user');
                }
            } else {
                setError('Role not recognized');
            }
        } catch (err) {
            setError(err.message || 'Error during login');
        } finally {
            setLoading(false);
        }
    };




    return (
        <div className="login-page-container">
            <style jsx>{`
                .login-page-container {
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    justify-content: center;
                    min-height: 100vh;
                    background-color: #f9f9f9;
                    color: #333;
                    font-family: 'Arial', sans-serif;
                }
                .login-form {
                    max-width: 400px;
                    width: 100%;
                    background-color: white;
                    padding: 2rem;
                    border-radius: 8px;
                    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                }
                h1 {
                    color: firebrick;
                    text-align: center;
                }
                label {
                    margin-top: 1rem;
                    color: #555;
                    display: block;
                    font-size: 1rem;
                }
                input {
                    width: 100%;
                    padding: 10px;
                    margin: 0.5rem 0 1rem 0;
                    border: 1px solid #ccc;
                    border-radius: 4px;
                    font-size: 1rem;
                }
                button {
                    width: 100%;
                    padding: 10px 15px;
                    background-color: orangered;
                    color: white;
                    border: none;
                    border-radius: 4px;
                    cursor: pointer;
                    font-size: 1rem;
                    transition: background-color 0.3s ease;
                }
                button[disabled] {
                    background-color: #ccc;
                }
                button:hover:not([disabled]) {
                    background-color: darkred;
                }
                p {
                    text-align: center;
                    color: red;
                }
            `}</style>
            <h1>Login</h1>
            <form className="login-form" onSubmit={handleSubmit}>
                {error && <p>{error}</p>}
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
