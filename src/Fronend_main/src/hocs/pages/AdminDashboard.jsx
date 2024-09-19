'use client';

// AdminDashboard.jsx
import React from 'react';
import { Navigate } from 'react-router-dom';
import AuthService from '../../services/authService/page';

export default function AdminDashboard() {
    if (!AuthService.isAuthenticated() || AuthService.getCurrentUserRole() !== 'admin') {
        return <Navigate to="/login" />;
    }

    return (
        <div>
            <h1>Welcome to Admin Dashboard</h1>
            {/* Lógica de contenido del dashboard */}
        </div>
    );
}

