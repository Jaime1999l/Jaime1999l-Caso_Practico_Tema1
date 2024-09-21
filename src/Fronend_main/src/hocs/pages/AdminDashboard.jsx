import React from 'react';
import SensorMovimientoList from '../components/SensorMovimientoList';
import SensorAccesoList from '../components/SensorAccesoList';
import SensorTemperaturaList from '../components/SensorTemperaturaList';
import EventList from '../components/EventList';
import UserRegisterForm from '../components/UserRegisterForm';

export default function AdminDashboard() {
    return (
        <div className="admin-dashboard-container">
            <h1>Admin Dashboard</h1>
            <div className="section">
                <h2>Register New User</h2>
                <UserRegisterForm />
            </div>

            <div className="section">
                <h2>Movement Sensors</h2>
                <SensorMovimientoList />
            </div>

            <div className="section">
                <h2>Access Sensors</h2>
                <SensorAccesoList />
            </div>

            <div className="section">
                <h2>Temperature Sensors</h2>
                <SensorTemperaturaList />
            </div>

            <div className="section">
                <h2>Event List</h2>
                <EventList />
            </div>
        </div>
    );
}
