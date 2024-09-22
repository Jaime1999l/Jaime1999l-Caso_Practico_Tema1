import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AuthService from '../../services/authService/page';

export default function MainPage() {
    const [showLogin, setShowLogin] = useState(false);
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
            const { token, role } = response;

            if (role) {
                localStorage.setItem('token', token);
                localStorage.setItem('role', role);

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
        <div className="main-page-container">
            <style jsx>{`
                .main-page-container {
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    justify-content: flex-start;
                    min-height: 100vh;
                    background-color: #1c1c1c;
                    color: #f0f0f0;
                    font-family: 'Arial', sans-serif;
                    padding: 20px;
                }
                .navbar {
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    width: 100%;
                    max-width: 1200px;
                    padding: 20px;
                    background-color: #333;
                    border-radius: 12px;
                    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
                    margin-bottom: 20px;
                    transition: background-color 0.3s ease, box-shadow 0.3s ease;
                }
                .navbar:hover {
                    background-color: #444;
                    box-shadow: 0 12px 24px rgba(0, 0, 0, 0.4);
                }
                .navbar-title {
                    font-size: 28px;
                    font-weight: bold;
                    color: #f0f0f0;
                    text-transform: uppercase;
                    letter-spacing: 1px;
                }
                .login-toggle-button {
                    padding: 12px 25px;
                    font-size: 18px;
                    color: white;
                    background-color: #3498db;
                    border: none;
                    border-radius: 8px;
                    cursor: pointer;
                    transition: background-color 0.3s ease, transform 0.3s ease;
                }
                .login-toggle-button:hover {
                    background-color: #2980b9;
                    transform: translateY(-3px);
                }
                .login-form {
                    display: ${showLogin ? 'flex' : 'none'};
                    flex-direction: column;
                    align-items: center;
                    justify-content: center;
                    background-color: #2e2e2e;
                    padding: 25px;
                    border-radius: 12px;
                    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
                    width: 100%;
                    max-width: 400px;
                    margin-bottom: 20px;
                    transition: opacity 0.5s ease, transform 0.5s ease;
                    opacity: ${showLogin ? 1 : 0};
                    transform: ${showLogin ? 'scale(1)' : 'scale(0.95)'};
                }
                .login-form input {
                    width: calc(100% - 20px);
                    padding: 15px;
                    margin: 10px 10px;
                    border: 1px solid #555;
                    border-radius: 8px;
                    font-size: 18px;
                    background-color: #444;
                    color: #f0f0f0;
                    transition: border-color 0.3s ease, background-color 0.3s ease;
                }
                .login-form input:focus {
                    border-color: #3498db;
                    background-color: #555;
                }
                .login-form input::placeholder {
                    color: #aaa;
                }
                .login-form button {
                    width: calc(100% - 20px);
                    padding: 15px;
                    margin: 10px 10px;
                    background-color: #e74c3c;
                    color: white;
                    border: none;
                    border-radius: 8px;
                    cursor: pointer;
                    font-size: 18px;
                    font-weight: bold;
                    transition: background-color 0.3s ease, transform 0.3s ease;
                }
                .login-form button:hover {
                    background-color: #c0392b;
                    transform: translateY(-3px);
                }
                .login-form button:disabled {
                    background-color: #555;
                    cursor: not-allowed;
                }
                .content-section {
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    width: 100%;
                    max-width: 1200px;
                    margin-top: 50px;
                }
                .content-section h2 {
                    font-size: 32px;
                    color: #f39c12;
                    margin-bottom: 30px;
                    text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
                }
                .info-container {
                    display: flex;
                    justify-content: space-around;
                    flex-wrap: wrap;
                    width: 100%;
                }
                .info-card {
                    background-color: #2a2a2a;
                    width: 320px;
                    margin: 15px;
                    border-radius: 12px;
                    overflow: hidden;
                    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
                    transition: transform 0.3s ease, box-shadow 0.3s ease;
                }
                .info-card:hover {
                    transform: translateY(-8px);
                    box-shadow: 0 12px 24px rgba(0, 0, 0, 0.4);
                }
                .info-card img {
                    width: 100%;
                    height: 200px;
                    object-fit: cover;
                    transition: transform 0.3s ease;
                }
                .info-card:hover img {
                    transform: scale(1.05);
                }
                .info-card-content {
                    padding: 25px;
                }
                .info-card-content h3 {
                    margin-top: 0;
                    color: #f0f0f0;
                    font-size: 22px;
                }
                .info-card-content p {
                    color: #ccc;
                    line-height: 1.6;
                }
                .error-message {
                    color: #e74c3c;
                    text-align: center;
                    margin-bottom: 10px;
                }
            `}</style>
            <div className="navbar">
                <div className="navbar-title">SENSOR MONITORING</div>
                <button
                    className="login-toggle-button"
                    onClick={() => setShowLogin(!showLogin)}
                >
                    {showLogin ? 'Hide Login' : 'Show Login'}
                </button>
            </div>

            {showLogin && (
                <form className="login-form" onSubmit={handleSubmit}>
                    {error && <p className="error-message">{error}</p>}
                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                        disabled={loading}
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        disabled={loading}
                    />
                    <button type="submit" disabled={loading}>
                        {loading ? 'Logging in...' : 'Login'}
                    </button>
                </form>
            )}

            <div className="content-section">
                <h2>Interesting Facts</h2>
                <div className="info-container">
                    <div className="info-card">
                        <img src="https://veyca.es/29884-large_default/FOTOCELULA-SENSOR-DE-REFLEXION-DIRECTA-DETECTOR-OPTICO-SOEG-RT-M18-NA-S-2L-REF--FESTO-547911.jpg" alt="Sensor Technology" />
                        <div className="info-card-content">
                            <h3>Sensor Technology</h3>
                            <p>
                                Sensors play a crucial role in various industries, from monitoring environmental changes to ensuring safety in manufacturing processes.
                            </p>
                        </div>
                    </div>
                    <div className="info-card">
                        <img src="https://img.freepik.com/vector-gratis/ilustracion-composicion-concepto-isometrico-desarrollo-web_1284-55922.jpg" alt="Software Engineering" />
                        <div className="info-card-content">
                            <h3>Software Evolution</h3>
                            <p>
                                Software has evolved from simple programs to complex systems that power everything from smartphones to spacecraft.
                            </p>
                        </div>
                    </div>
                    <div className="info-card">
                        <img src="https://st3.depositphotos.com/10325516/19023/i/450/depositphotos_190235636-stock-photo-robot-with-education-hud.jpg" alt="Machine Learning" />
                        <div className="info-card-content">
                            <h3>Machine Learning</h3>
                            <p>
                                Machine learning algorithms are capable of learning and improving over time, enabling applications like autonomous driving and personal assistants.
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}


