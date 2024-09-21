import React, { useState, useEffect } from 'react';
import SensorTemperaturaList from "../components/SensorTemperaturaList";

export default function UserDashboard() {
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        setIsLoading(false);
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

            <div className="section">
                <h2>Temperature Sensors</h2>
                <SensorTemperaturaList />
            </div>
        </div>
    );
}
