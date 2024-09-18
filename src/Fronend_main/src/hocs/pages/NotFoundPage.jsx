'use client';

import React from 'react';
import { Link } from 'react-router-dom';

export default function NotFoundPage() {
    return (
        <div className="not-found-container">
            <style jsx>{`
                .not-found-container {
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    justify-content: center;
                    min-height: 100vh;
                    background-color: #f9f9f9;
                    color: #333;
                    font-family: 'Arial', sans-serif;
                }
                h1 {
                    margin-bottom: 1rem;
                    color: #ff0000;
                }
                a {
                    color: #007bff;
                    text-decoration: none;
                    font-size: 1.2rem;
                }
                a:hover {
                    text-decoration: underline;
                }
            `}</style>
            <h1>404 - Page Not Found</h1>
            <Link to="/">Go back to Home</Link>
        </div>
    );
}
