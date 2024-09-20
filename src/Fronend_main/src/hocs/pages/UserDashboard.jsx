'use client';

import React from 'react';
import { useState, useEffect } from 'react';
import SensorTable from '../components/SensorTable';

export default function UserDashboard() {
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        // Simulamos la carga de datos
        setTimeout(() => {
            setIsLoading(false);
        }, 1000);
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
            `}</style>
            <h1>User Dashboard</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <SensorTable sensorType="temperature" />
        </div>
    );
}
