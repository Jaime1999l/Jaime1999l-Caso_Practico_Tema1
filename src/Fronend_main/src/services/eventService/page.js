// Función para obtener todos los eventos
export async function getAllEventos() {
    console.log("Fetching all events from backend");
    const response = await fetch('/api/eventos/events_2', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    if (!response.ok) {
        throw new Error('Error al obtener todos los eventos');
    }
    return await response.json();
}


