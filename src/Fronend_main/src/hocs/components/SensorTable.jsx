'use client';

import { useState, useEffect } from 'react';
import SensorService from '../../services/sensorService/page';

export default function SensorTable({ sensorType }) {
    const [sensors, setSensors] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    useEffect(() => {
        async function fetchSensors() {
            setLoading(true);
            try {
                const response = await SensorService.getSensors(sensorType);
                setSensors(response.data);
            } catch (err) {
                setError('Failed to fetch sensors.');
            } finally {
                setLoading(false);
            }
        }

        fetchSensors();
    }, [sensorType]);

    return (
        <div className="sensor-table-container">
            <style jsx>{`
                .sensor-table-container {
                    padding: 1rem;
                    background-color: #f9f9f9;
                    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                    border-radius: 8px;
                    max-width: 800px;
                    margin: 0 auto;
                }
                table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 1rem;
                }
                th, td {
                    padding: 10px;
                    text-align: left;
                    border-bottom: 1px solid #ddd;
                }
            `}</style>
            <h2>{sensorType} Sensors</h2>
            {loading && <p>Loading sensors...</p>}
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {!loading && !error && sensors.length > 0 && (
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Data</th>
                    </tr>
                    </thead>
                    <tbody>
                    {sensors.map(sensor => (
                        <tr key={sensor.id}>
                            <td>{sensor.id}</td>
                            <td>{sensor.name}</td>
                            <td>{sensor.data}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}
