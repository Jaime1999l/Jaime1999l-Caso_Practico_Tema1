'use client';

// UserDashboard.jsx
import React from 'react';
import { Navigate } from 'react-router-dom';
import AuthService from '../../services/authService/page';

export default function UserDashboard() {
    if (!AuthService.isAuthenticated() || AuthService.getCurrentUserRole() !== 'user') {
        return <Navigate to="/login" />;
    }

    return (
        <div>
            <h1>Welcome to User Dashboard</h1>
            {/* Aquí se puede colocar la lógica de contenido del dashboard */}
        </div>
    );
}

