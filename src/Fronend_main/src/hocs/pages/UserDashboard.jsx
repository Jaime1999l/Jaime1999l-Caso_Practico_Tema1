// src/pages/UserDashboard.jsx
'use client'
import React from 'react';
import SensorTable from '../components/SensorTable';

const UserDashboard = () => {
    return (
        <div>
            <h1>User Dashboard</h1>
            <SensorTable sensorType="temperature" />
        </div>
    );
};

export default UserDashboard;
