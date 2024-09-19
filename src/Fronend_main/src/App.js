import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import AuthService from './services/authService/page';
import LoginPage from './hocs/pages/LoginPage';
import AdminDashboard from './hocs/pages/AdminDashboard';
import UserDashboard from './hocs/pages/UserDashboard';
import NotFoundPage from './hocs/pages/NotFoundPage';

function App() {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [userRole, setUserRole] = useState('');
    const [loading, setLoading] = useState(true);

    useEffect(async () => {
        // Verificar si el usuario está autenticado
        if (await AuthService.isAuthenticated()) {
            setIsAuthenticated(true);
            setUserRole(await AuthService.getCurrentUserRole());
        } else {
            setIsAuthenticated(false);
        }
        setLoading(false);
    }, []);

    if (loading) {
        return <p>Loading...</p>;
    }

    return (
        <Router>
            <Routes>
                <Route path="/" element={<Navigate to={isAuthenticated ? `/${userRole}` : '/login'} />} />
                <Route path="/login" element={isAuthenticated ? <Navigate to={`/${userRole}`} /> : <LoginPage />} />
                <Route path="/admin" element={isAuthenticated && userRole === 'admin' ? <AdminDashboard /> : <Navigate to="/login" />} />
                <Route path="/user" element={isAuthenticated && userRole === 'user' ? <UserDashboard /> : <Navigate to="/login" />} />
                <Route path="*" element={<NotFoundPage />} />
            </Routes>
        </Router>
    );
}

export default App;
