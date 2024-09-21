const API_BASE_URL = 'http://localhost:8080/api';

// Obtener el token de autenticación desde el localStorage
const token = localStorage.getItem('token');

// Función para obtener todos los eventos
export async function getAllEventos() {
    try {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/eventos/events`, {
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
        console.log('Data received from events API:', data);
        return data;
    } catch (error) {
        console.error('Error fetching events:', error);
        throw error;
    }
}


