const API_BASE_URL = 'http://localhost:8080/api';

const token = localStorage.getItem('token');

export async function getAllSensorAccesos() {
    try {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/sensores/sensoresAcceso`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`, // Incluyendo el token en la solicitud
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

