const API_BASE_URL = 'http://localhost:8080/api';

export async function getAllSensorMovimientos() {
    try {
        const response = await fetch(`${API_BASE_URL}/sensores/sensoresMovimiento`);
        console.log('Data received from sensor movement API:', response.data); // Mostrar datos en consola
        
        if (!response.ok) {
            throw new Error('Failed to fetch movement sensors.');
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching movement sensors:', error);
        throw error;
    }
}
