import React, { useEffect, useState } from 'react';
import { getAllSensores } from '../../services/sensorService';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';

const SensorChart = () => {
    const [sensorData, setSensorData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const data = await getAllSensores();
            setSensorData(data);
        };
        fetchData();
    }, []);

    return (
        <LineChart width={600} height={300} data={sensorData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="nombre" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Line type="monotone" dataKey="datosMovimiento" stroke="#8884d8" />
        </LineChart>
    );
};

export default SensorChart;