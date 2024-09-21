const API_BASE_URL = 'http://localhost:8080/api';

export async function getAllSensorAccesos() {
    try {
        const response = await fetch(`${API_BASE_URL}/sensores/sensoresAcceso`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`Error: ${response.statusText}`);
        }

        const data = await response.json();
        console.log('Data received from sensor access API:', data);
        return data;
    } catch (error) {
        console.error('Error fetching access sensors:', error);
        throw error;
    }
}




