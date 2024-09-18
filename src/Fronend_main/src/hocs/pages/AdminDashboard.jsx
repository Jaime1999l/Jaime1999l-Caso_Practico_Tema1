// src/pages/AdminDashboard.jsx
'use client'
import React from 'react';
import SensorTable from '../components/SensorTable';
import UserRegisterForm from '../components/UserRegisterForm';

const AdminDashboard = () => {
    return (
        <div>
            <h1>Admin Dashboard</h1>
            <UserRegisterForm />
            <SensorTable sensorType="movement" />
            <SensorTable sensorType="access" />
            <SensorTable sensorType="temperature" />
        </div>
    );
};

export default AdminDashboard;
