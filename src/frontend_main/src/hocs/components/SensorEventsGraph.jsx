import React, { useState, useEffect } from 'react';
import { Bar } from 'react-chartjs-2';
import axios from 'axios';

const SensorEventsGraph = () => {
    const [sensorEvents, setSensorEvents] = useState({
        acceso: 0,
        movimiento: 0,
        temperatura: 0
    });

    useEffect(() => {
        const fetchEvents = async () => {
            try {
                const responseAcceso = await axios.get('/api/sensores/sensoresAcceso');
                const responseMovimiento = await axios.get('/api/sensores/sensoresMovimiento');
                const responseTemperatura = await axios.get('/api/sensores/sensoresTemperatura');

                setSensorEvents({
                    acceso: responseAcceso.data.count,
                    movimiento: responseMovimiento.data.count,
                    temperatura: responseTemperatura.data.count
                });
            } catch (error) {
                console.error(error);
            }
        };

        fetchEvents();
    }, []);

    const chartData = {
        labels: ['Sensor Acceso', 'Sensor Movimiento', 'Sensor Temperatura'],
        datasets: [{
            label: 'Cantidad de eventos',
            data: [sensorEvents.acceso, sensorEvents.movimiento, sensorEvents.temperatura],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)'
            ],
            borderWidth: 1
        }]
    };

    return (
        <div>
            <h2>Gráfico de eventos de sensores</h2>
            <Bar
                data={chartData}
                options={{
                    scales: {
                        x: {
                            type: 'category',
                            labels: ['Sensor Acceso', 'Sensor Movimiento', 'Sensor Temperatura']
                        },
                        y: {
                            type: 'linear',
                            title: {
                                display: true,
                                text: 'Cantidad de eventos'
                            }
                        }
                    }
                }}
            />
        </div>
    );
};

export default SensorEventsGraph;