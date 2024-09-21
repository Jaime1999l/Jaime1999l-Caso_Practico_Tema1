const API_BASE_URL = 'http://localhost:8080/api';

export async function getAllSensorMovimientos() {
    try {
        const response = await fetch(`${API_BASE_URL}/sensores/sensoresMovimiento`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`Error: ${response.statusText}`);
        }

        const data = await response.json();
        console.log('Data received from sensor movement API:', data);
        return data;
    } catch (error) {
        console.error('Error fetching movement sensors:', error);
        throw error;
    }
}



