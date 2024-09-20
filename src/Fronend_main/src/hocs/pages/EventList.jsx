'use client';

import React from 'react';
import { useState, useEffect } from 'react';
import EventService from '../../services/eventService/page';

export default function EventList() {
    const [events, setEvents] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchEvents = async () => {
            setLoading(true);
            try {
                const response = await EventService.getEventos();
                setEvents(response.data || []);
            } catch (err) {
                setError('Failed to fetch events.');
            } finally {
                setLoading(false);
            }
        };

        fetchEvents();
    }, []);

    return (
        <div className="event-list-container">
            <style jsx>{`
                .event-list-container {
                    padding: 1rem;
                    background-color: #f9f9f9;
                    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                    border-radius: 8px;
                    max-width: 800px;
                    margin: 0 auto;
                }
                table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 1rem;
                }
                th, td {
                    padding: 10px;
                    text-align: left;
                    border-bottom: 1px solid #ddd;
                }
            `}</style>
            <h2>Event List</h2>
            {loading && <p>Loading events...</p>}
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {!loading && !error && events.length > 0 && (
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Data</th>
                    </tr>
                    </thead>
                    <tbody>
                    {events.map((event) => (
                        <tr key={event.id}>
                            <td>{event.id}</td>
                            <td>{event.nombre}</td>
                            <td>{event.datos}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}
