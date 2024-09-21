export async function getAllEventos() {
    try {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/eventos/events_1`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`, // Incluyendo el token en la solicitud
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ token }) // Enviar el token en el cuerpo también, si es necesario
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




