import React, { useState, useEffect } from 'react';
import { getAllSensorMovimientos } from "../../services/sensorMovimientoService/page";
import { getAllSensorAccesos } from "../../services/sensorAccesoService/page";
import { getAllSensorTemperaturas } from "../../services/sensorTemperaturaService/page";
import { getAllEventos } from '../../services/eventService/page';
import UserRegisterForm from '../components/UserRegisterForm';
import { useNavigate } from 'react-router-dom';

export default function AdminDashboard() {
    const [movementSensors, setMovementSensors] = useState([]);
    const [accessSensors, setAccessSensors] = useState([]);
    const [temperatureSensors, setTemperatureSensors] = useState([]);
    const [events, setEvents] = useState([]);
    const [error, setError] = useState('');
    const [visibleSection, setVisibleSection] = useState(''); // Para mostrar secciones

    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const movementData = [];
                const accessData = [];
                const temperatureData = [];

                for (let i = 1; i <= 5; i++) {
                    movementData.push(...await getAllSensorMovimientos(`TOKEN_MOVIMIENTO_Sensor_${i}`));
                    accessData.push(...await getAllSensorAccesos(`TOKEN_ACCESO_Sensor_${i}`));
                    temperatureData.push(...await getAllSensorTemperaturas(`TOKEN_TEMPERATURA_Sensor_${i}`));
                }

                const eventData = await getAllEventos();

                setMovementSensors(movementData);
                setAccessSensors(accessData);
                setTemperatureSensors(temperatureData);
                setEvents(eventData);
            } catch (error) {
                setError('Failed to fetch data.');
                console.error('Error fetching data:' +
                    'Try again with another configuration.', error);
            }
        };

        fetchData();
    }, []);

    const handleLogout = () => {
        // Lógica de logout, como eliminar tokens, etc.
        alert('Logged out successfully');
        navigate('/');
    };

    return (
        <div className="admin-dashboard-container">
            <style jsx>{`
                .admin-dashboard-container {
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    justify-content: flex-start;
                    min-height: 100vh;
                    background-color: #2b2b2b;
                    color: #f0f0f0;
                    font-family: 'Arial', sans-serif;
                    padding: 20px;
                }
                h1 {
                    margin-bottom: 1.5rem;
                    color: #f0f0f0;
                }
                .nav-buttons {
                    display: flex;
                    justify-content: center;
                    gap: 15px;
                    margin-bottom: 20px;
                }
                .nav-buttons button {
                    padding: 12px 20px;
                    font-size: 16px;
                    border: none;
                    border-radius: 8px;
                    cursor: pointer;
                    background-color: #007bff;
                    color: white;
                    transition: background-color 0.3s ease, transform 0.3s ease;
                }
                .nav-buttons button:hover {
                    background-color: #0056b3;
                    transform: translateY(-2px);
                }
                .logout-button {
                    align-self: flex-end;
                    margin-bottom: 20px;
                    padding: 10px 20px;
                    font-size: 14px;
                    border: none;
                    border-radius: 8px;
                    cursor: pointer;
                    background-color: #e74c3c;
                    color: white;
                    transition: background-color 0.3s ease, transform 0.3s ease;
                }
                .logout-button:hover {
                    background-color: #c0392b;
                    transform: translateY(-2px);
                }
                .section {
                    margin: 20px;
                    padding: 20px;
                    border: 1px solid #444;
                    border-radius: 12px;
                    background-color: #333;
                    width: 90%;
                    max-width: 800px;
                    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
                }
                .section h2 {
                    margin-bottom: 10px;
                    color: #f0f0f0;
                }
                ul {
                    list-style-type: none;
                    padding: 0;
                }
                li {
                    padding: 10px 15px;
                    background-color: #444;
                    border-bottom: 1px solid #555;
                    border-radius: 8px;
                    margin-bottom: 8px;
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                }
                p {
                    color: #ccc;
                }
                .title {
                    font-size: 1.2em;
                    margin-bottom: 10px;
                    color: #f39c12;
                }
                .fact-item {
                    display: flex;
                    align-items: center;
                    margin-bottom: 12px;
                    color: #f0f0f0;
                }
                .fact-icon {
                    width: 40px;
                    height: 40px;
                    margin-right: 15px;
                }
                .fact-text {
                    font-size: 16px;
                }
            `}</style>
            <div className="logout-button" onClick={handleLogout}>Logout</div>
            <h1>Admin Dashboard</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}

            <div className="nav-buttons">
                <button onClick={() => setVisibleSection('user')}>Show User Registration Form</button>
                <button onClick={() => setVisibleSection('movement')}>Show Movement Sensors</button>
                <button onClick={() => setVisibleSection('access')}>Show Access Sensors</button>
                <button onClick={() => setVisibleSection('temperature')}>Show Temperature Sensors</button>
                <button onClick={() => setVisibleSection('events')}>Show Event List</button>
            </div>

            {visibleSection === 'user' && (
                <div className="section">
                    <h2>Register New User</h2>
                    <UserRegisterForm />
                </div>
            )}

            {visibleSection === 'movement' && (
                <div className="section">
                    <h2>Movement Sensors</h2>
                    {movementSensors.length > 0 ? (
                        <ul>
                            {movementSensors.map(sensor => (
                                <li key={sensor.id}>
                                    {sensor.nombre} <span>Token: {sensor.token}</span>
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>No movement sensors available</p>
                    )}
                </div>
            )}

            {visibleSection === 'access' && (
                <div className="section">
                    <h2>Access Sensors</h2>
                    {accessSensors.length > 0 ? (
                        <ul>
                            {accessSensors.map(sensor => (
                                <li key={sensor.id}>
                                    {sensor.nombre} <span>Token: {sensor.token}</span>
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>No access sensors available</p>
                    )}
                </div>
            )}

            {visibleSection === 'temperature' && (
                <div className="section">
                    <h2>Temperature Sensors</h2>
                    {temperatureSensors.length > 0 ? (
                        <ul>
                            {temperatureSensors.map(sensor => (
                                <li key={sensor.id}>
                                    {sensor.nombre} <span>Token: {sensor.token}</span>
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>No temperature sensors available</p>
                    )}
                </div>
            )}

            {visibleSection === 'events' && (
                <div className="section">
                    <h2>Event List</h2>
                    {events.length > 0 ? (
                        <ul>
                            {events.map(event => (
                                <li key={event.id}>
                                    {event.nombre} <span>Token: {event.token}</span>
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>No events available</p>
                    )}
                </div>
            )}

            <div className="section">
                <h2 className="title">Tech Facts</h2>
                <div className="fact-item">
                    <img src="https://cdn-icons-png.flaticon.com/512/3225/3225887.png" alt="Computer Bug" className="fact-icon" />
                    <div className="fact-text">
                        <strong>The First Computer Bug</strong><br />
                        En 1947, Grace Hopper encontró una polilla que causaba problemas en una computadora Harvard Mark II y la calificó de "error".
                    </div>
                </div>
                <div className="fact-item">
                    <img src="https://cdn-icons-png.flaticon.com/512/3225/3225886.png" alt="Moore's Law" className="fact-icon" />
                    <div className="fact-text">
                        <strong>Moore's Law</strong><br />
                        Gordon Moore predijo que el número de transistores en un microchip se duplicaría cada dos años.
                    </div>
                </div>
                <div className="fact-item">
                    <img src="https://cdn-icons-png.flaticon.com/512/3225/3225877.png" alt="1GB Hard Drive" className="fact-icon" />
                    <div className="fact-text">
                        <strong>The First 1GB Hard Drive</strong><br />
                        IBM presentó el primer disco duro de un gigabyte en 1980, pesaba más de 500 libras y costaba 40.000 dólares.
                    </div>
                </div>
            </div>
        </div>
    );
}

