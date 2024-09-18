'use client';

import { useState, useEffect } from 'react';
import SensorTable from '../components/SensorTable';
import UserRegisterForm from '../components/UserRegisterForm';

export default function AdminDashboard() {
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        setIsLoading(false);
    }, []);

    if (isLoading) {
        return <p>Loading dashboard...</p>;
    }

    return (
        <div className="admin-dashboard-container">
            <style jsx>{`
                .admin-dashboard-container {
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
            <h1>Admin Dashboard</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <UserRegisterForm />
            <SensorTable sensorType="movement" />
            <SensorTable sensorType="access" />
            <SensorTable sensorType="temperature" />
        </div>
    );
}
