import React, { useState, useEffect } from 'react';
import { getAllSensorMovimientos } from '../../services/sensorMovimientoService/page';

export default function SensorMovimientoList() {
    const [sensors, setSensors] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchSensors = async () => {
            try {
                const response = await getAllSensorMovimientos();
                setSensors(response);
            } catch (err) {
                setError('Failed to fetch movement sensors.');
            }
        };
        fetchSensors();
    }, []);

    return (
        <div className="sensor-movement-list">
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {sensors.length === 0 && <p>No movement sensors available.</p>}
            {sensors.length > 0 && (
                <ul>
                    {sensors.map((sensor) => (
                        <li key={sensor.id}>
                            {sensor.nombre} - {sensor.datosMovimiento}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

