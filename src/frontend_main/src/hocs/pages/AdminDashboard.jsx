import React, { useState, useEffect } from 'react';
import { getAllSensorMovimientos } from "../../services/sensorMovimientoService/page";
import { getAllSensorAccesos } from "../../services/sensorAccesoService/page";
import { getAllSensorTemperaturas } from "../../services/sensorTemperaturaService/page";
import { getAllEventos } from '../../services/eventService/page';
import UserRegisterForm from '../components/UserRegisterForm';

export default function AdminDashboard() {
    const [movementSensors, setMovementSensors] = useState([]);
    const [accessSensors, setAccessSensors] = useState([]);
    const [temperatureSensors, setTemperatureSensors] = useState([]);
    const [events, setEvents] = useState([]);
    const [error, setError] = useState('');

    const movimientoTokenBase = 'TOKEN_MOVIMIENTO_Sensor_';
    const accesoTokenBase = 'TOKEN_ACCESO_Sensor_';
    const temperaturaTokenBase = 'TOKEN_TEMPERATURA_Sensor_';

    useEffect(() => {
        const fetchData = async () => {
            try {
                const movementData = [];
                const accessData = [];
                const temperatureData = [];

                // Fetch data for each sensor type for ids 1 to 5
                for (let i = 1; i <= 5; i++) {
                    movementData.push(...await getAllSensorMovimientos(`${movimientoTokenBase}${i}`));
                    accessData.push(...await getAllSensorAccesos(`${accesoTokenBase}${i}`));
                    temperatureData.push(...await getAllSensorTemperaturas(`${temperaturaTokenBase}${i}`));
                }

                const eventData = await getAllEventos(); // Obteniendo todos los eventos, sin filtrarlos

                setMovementSensors(movementData);
                setAccessSensors(accessData);
                setTemperatureSensors(temperatureData);
                setEvents(eventData); // Se establecen todos los eventos en el estado
            } catch (error) {
                setError('Failed to fetch data.');
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);

    return (
        <div className="admin-dashboard-container">
            <style jsx>{`
                .admin-dashboard-container {
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    justify-content: center;
                    min-height: 100vh;
                    background-color: #eef2f7;
                    color: #333;
                    font-family: 'Arial', sans-serif;
                }
                h1 {
                    margin-bottom: 1.5rem;
                    color: #222;
                }
                .section {
                    margin: 20px;
                    padding: 20px;
                    border: 1px solid #ddd;
                    border-radius: 12px;
                    background-color: #fff;
                    width: 90%;
                    max-width: 800px;
                    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
                    transition: transform 0.3s ease;
                }
                .section:hover {
                    transform: translateY(-3px);
                }
                .section h2 {
                    margin-bottom: 10px;
                    color: #555;
                }
                ul {
                    list-style-type: none;
                    padding: 0;
                }
                li {
                    padding: 10px 15px;
                    background-color: #f9f9f9;
                    border-bottom: 1px solid #eee;
                    border-radius: 8px;
                    margin-bottom: 8px;
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    transition: background-color 0.3s ease;
                }
                li:hover {
                    background-color: #f1f1f1;
                }
                p {
                    color: #888;
                    font-size: 14px;
                }
            `}</style>
            <h1>Admin Dashboard</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}

            <div className="section">
                <h2>Register New User</h2>
                <UserRegisterForm />
            </div>

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
        </div>
    );
}


