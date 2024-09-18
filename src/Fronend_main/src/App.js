import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './hocs/pages/LoginPage';
import AdminDashboard from './hocs/pages/AdminDashboard';
import UserDashboard from './hocs/pages/UserDashboard';
import NotFoundPage from "./hocs/pages/NotFoundPage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="*" element={<NotFoundPage />} />
                <Route path="/" element={<LoginPage />} />
                <Route path="/admin" element={<AdminDashboard />} />
                <Route path="/user" element={<UserDashboard />} />
            </Routes>
        </Router>
    );
}

export default App;
