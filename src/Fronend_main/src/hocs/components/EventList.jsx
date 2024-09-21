import React, { useState, useEffect } from 'react';
import { getAllEvents } from '../../services/eventService/page';

export default function EventList() {
    const [events, setEvents] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchEvents = async () => {
            try {
                const response = await getAllEvents();
                setEvents(response);
            } catch (err) {
                setError('Failed to fetch events.');
            }
        };
        fetchEvents();
    }, []);

    return (
        <div className="event-list">
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {events.length === 0 && <p>No events available.</p>}
            {events.length > 0 && (
                <ul>
                    {events.map((event) => (
                        <li key={event.id}>
                            {event.nombre} - {event.datos}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}
