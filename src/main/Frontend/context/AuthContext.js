import React, { createContext, useState, useEffect } from 'react';

const AuthContext = createContext();

const AuthProvider = ({ children }) => {
    const [auth, setAuth] = useState({ user: null, token: null });

    const login = (user, token) => {
        setAuth({ user, token });
        localStorage.setItem('token', token);
    };

    const logout = () => {
        setAuth({ user: null, token: null });
        localStorage.removeItem('token');
    };

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            // Aquí puedes hacer una validación del token
        }
    }, []);

    return (
        <AuthContext.Provider value={{ auth, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export { AuthContext, AuthProvider };