import React, { useState, useEffect } from 'react';
import { getAllSensorAccesos } from '../../services/sensorAccesoService/page';

export default function SensorAccesoList() {
    const [sensors, setSensors] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchSensors = async () => {
            try {
                const response = await getAllSensorAccesos();
                setSensors(response);
            } catch (err) {
                setError('Failed to fetch access sensors.');
            }
        };
        fetchSensors();
    }, []);

    return (
        <div className="sensor-access-list">
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {sensors.length === 0 && <p>No access sensors available.</p>}
            {sensors.length > 0 && (
                <ul>
                    {sensors.map((sensor) => (
                        <li key={sensor.id}>
                            {sensor.nombre} - {sensor.datosAcceso} - {sensor.respuesta}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

