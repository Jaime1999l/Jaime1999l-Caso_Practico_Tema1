// src/components/SensorTable.jsx
'use client'
import React, { useEffect, useState } from 'react';
import SensorService from '../../services/SensorService/page';

const SensorTable = ({ sensorType }) => {
    const [sensors, setSensors] = useState([]);

    useEffect(() => {
        const fetchSensors = async () => {
            const response = await SensorService.getSensors(sensorType);
            setSensors(response.data);
        };

        fetchSensors();
    }, [sensorType]);

    return (
        <div>
            <h2>{sensorType.charAt(0).toUpperCase() + sensorType.slice(1)} Sensors</h2>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Data</th>
                </tr>
                </thead>
                <tbody>
                {sensors.map((sensor) => (
                    <tr key={sensor.id}>
                        <td>{sensor.id}</td>
                        <td>{sensor.name}</td>
                        <td>{sensor.data}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default SensorTable;
