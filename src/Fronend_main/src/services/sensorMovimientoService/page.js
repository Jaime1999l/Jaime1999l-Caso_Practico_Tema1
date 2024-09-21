const API_BASE_URL = 'http://localhost:8080/api';

// Obtener el token de autenticación desde el localStorage
const token = localStorage.getItem('token');

// Función para obtener todos los sensores de movimiento
export async function getAllSensorMovimientos() {
    try {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/sensores/sensoresMovimiento`, {
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
        console.log('Data received from sensor movement API:', data);
        return data;
    } catch (error) {
        console.error('Error fetching movement sensors:', error);
        throw error;
    }
}
