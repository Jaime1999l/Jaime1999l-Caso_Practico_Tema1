import React, { useState, useEffect } from 'react';
import { getAllSensorTemperaturas } from "../../services/sensorTemperaturaService/page";
import {getAllEventos, getAllEventosTemperatura} from '../../services/eventService/page';

export default function UserDashboard() {
    const [temperatureSensors, setTemperatureSensors] = useState([]);
    const [events, setEvents] = useState([]);
    const [error, setError] = useState('');
    const [isLoading, setIsLoading] = useState(true);

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
                    justify-content: center;
                    min-height: 100vh;
                    background-color: #f9f9f9;
                    color: #333;
                    font-family: 'Arial', sans-serif;
                }
                h1 {
                    margin-bottom: 1rem;
                    color: #333;
                }
                .section {
                    margin: 20px;
                    padding: 20px;
                    border: 1px solid #ddd;
                    border-radius: 8px;
                    background-color: #fff;
                    width: 80%;
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                }
            `}</style>
            <h1>User Dashboard</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}

            <div className="section">
                <h2>Temperature Sensors</h2>
                {temperatureSensors.length > 0 ? (
                    <ul>
                        {temperatureSensors.map(sensor => (
                            <li key={sensor.id}>{sensor.nombre} - Token: {sensor.token}</li>
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
                            <li key={event.id}>{event.nombre} - Token: {event.token}</li>
                        ))}
                    </ul>
                ) : (
                    <p>No events available</p>
                )}
            </div>
        </div>
    );
}
