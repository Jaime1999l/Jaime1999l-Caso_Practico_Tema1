// Función para obtener todos los sensores de temperatura con token
export async function getAllSensorTemperaturas(token) {
    console.log("Sending token to backend:", token);
    // Cambiar de POST a GET y pasar el token como query parameter
    const response = await fetch(`/api/sensores/sensoresTemperatura?token=${token}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    if (!response.ok) {
        throw new Error('Error al obtener los sensores de temperatura');
    }
    return await response.json();
}

