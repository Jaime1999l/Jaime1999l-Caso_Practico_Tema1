const API_BASE_URL = 'http://localhost:8080/api';

export async function getAllSensorTemperaturas() {
    try {
        const response = await fetch(`${API_BASE_URL}/sensores/sensoresTemperatura`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`Error: ${response.statusText}`);
        }

        const data = await response.json();
        console.log('Data received from sensor temperature API:', data);
        return data;
    } catch (error) {
        console.error('Error fetching temperature sensors:', error);
        throw error;
    }
}




