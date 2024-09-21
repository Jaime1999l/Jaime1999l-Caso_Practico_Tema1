const API_BASE_URL = 'http://localhost:8080/api';

export async function getAllSensorTemperaturas() {
    try {
        const response = await fetch(`${API_BASE_URL}/sensores/sensoresTemperatura`);
        if (!response.ok) {
            throw new Error('Failed to fetch temperature sensors.');
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching temperature sensors:', error);
        throw error;
    }
}

