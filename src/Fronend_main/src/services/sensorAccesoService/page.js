const API_BASE_URL = 'http://localhost:8080/api';

export async function getAllSensorAccesos() {
    try {
        const response = await fetch(`${API_BASE_URL}/sensores/sensoresAcceso`);
        if (!response.ok) {
            throw new Error('Failed to fetch access sensors.');
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching access sensors:', error);
        throw error;
    }
}

