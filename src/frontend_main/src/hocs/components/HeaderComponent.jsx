import React from 'react';
import { useNavigate } from 'react-router-dom';
import AuthService from '../../services/authService/page';

export default function Header() {
    const navigate = useNavigate();

    const handleLogout = () => {
        AuthService.logout();
        navigate('/');
    };

    return (
        <div className="header">
            <style jsx>{`
                .header {
                    width: 100%;
                    background-color: #333;
                    padding: 20px;
                    color: #fff;
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                }
                .header h2 {
                    margin: 0;
                }
                .header button {
                    background-color: #ff4d4d;
                    color: #fff;
                    border: none;
                    padding: 10px 20px;
                    cursor: pointer;
                    border-radius: 5px;
                }
                .header button:hover {
                    background-color: #d63333;
                }
            `}</style>
            <h2>Admin Dashboard</h2>
            <button onClick={handleLogout}>Logout</button>
        </div>
    );
}

