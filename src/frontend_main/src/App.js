import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import PrincipalPage from './hocs/pages/PrincipalPage';
import AdminDashboard from './hocs/pages/AdminDashboard';
import UserDashboard from './hocs/pages/UserDashboard';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<PrincipalPage />} />
                <Route path="/admin" element={<AdminDashboard />} />
                <Route path="/user" element={<UserDashboard />} />
            </Routes>
        </Router>
    );
}

export default App;
