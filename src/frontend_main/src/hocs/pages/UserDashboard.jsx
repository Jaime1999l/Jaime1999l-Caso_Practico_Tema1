import React, { useState, useEffect } from 'react';
import { getAllSensorTemperaturas } from "../../services/sensorTemperaturaService/page";
import { getAllEventosTemperatura } from '../../services/eventService/page';
import { useNavigate } from 'react-router-dom';

export default function UserDashboard() {
    const [temperatureSensors, setTemperatureSensors] = useState([]);
    const [events, setEvents] = useState([]);
    const [error, setError] = useState('');
    const [isLoading, setIsLoading] = useState(true);
    const navigate = useNavigate();

    const temperaturaTokenBase = 'TOKEN_TEMPERATURA_Sensor_';

    useEffect(() => {
        const fetchData = async () => {
            try {
                const temperatureData = [];

                // Fetch data for temperature sensors for ids 1 to 5
                for (let i = 1; i <= 5; i++) {
                    temperatureData.push(...await getAllSensorTemperaturas(`${temperaturaTokenBase}${i}`));
                }

                // Fetch events related to temperature sensors
                const eventData = await getAllEventosTemperatura();

                setTemperatureSensors(temperatureData);
                setEvents(eventData);
            } catch (error) {
                setError('Failed to fetch data.');
                console.error('Error fetching data:', error);
            } finally {
                setIsLoading(false);
            }
        };

        fetchData();
    }, []);

    const handleLogout = () => {
        // Lógica para eliminar tokens u otros datos del usuario.
        alert('Logged out successfully');
        navigate('/login'); // Redirigir a la página de inicio de sesión
    };

    if (isLoading) {
        return <p>Loading dashboard...</p>;
    }

    return (
        <div className="user-dashboard-container">
            <style jsx>{`
                .user-dashboard-container {
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
            <h1>User Dashboard</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}

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

            <div className="section">
                <h2>Temperature Sensor Events</h2>
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
        </div>
    );
}
