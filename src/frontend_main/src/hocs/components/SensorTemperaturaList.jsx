import React, { useState, useEffect } from 'react';
import { getAllSensorTemperaturas } from '../../services/sensorTemperaturaService/page';

export default function SensorTemperaturaList() {
    const [sensors, setSensors] = useState([]);
    const [error, setError] = useState('');
    const temperaturaToken = 'TOKEN_TEMPERATURA_Sensor de Temperatura'; // Token definido

    useEffect(() => {
        const fetchSensors = async () => {
            try {
                const response = await getAllSensorTemperaturas(temperaturaToken);
                setSensors(response);
            } catch (err) {
                setError('Failed to fetch temperature sensors.');
            }
        };
        fetchSensors();
    }, []);

    return (
        <div className="sensor-temperature-list">
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {sensors.length === 0 && <p>No temperature sensors available.</p>}
            {sensors.length > 0 && (
                <ul>
                    {sensors.map((sensor) => (
                        <li key={sensor.id}>
                            {sensor.nombre} - {sensor.datosTemperatura} - Token: {sensor.token}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}


