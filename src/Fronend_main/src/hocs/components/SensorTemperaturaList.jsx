import React, { useState, useEffect } from 'react';
import { getAllSensorTemperaturas } from '../../services/sensorTemperaturaService/page';

export default function SensorTemperaturaList() {
    const [sensors, setSensors] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchSensors = async () => {
            try {
                const response = await getAllSensorTemperaturas();
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
                            {sensor.nombre} - {sensor.datosTemperatura}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

