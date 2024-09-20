const API_BASE_URL = 'http://localhost:8080/api';

export async function getEventos() {
    try {
        const response = await fetch(`${API_BASE_URL}/eventos`);
        if (!response.ok) {
            throw new Error('Failed to fetch events');
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching events:', error);
        throw error;
    }
}

export default {
    getEventos
};
