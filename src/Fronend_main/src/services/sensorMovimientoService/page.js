// Función para obtener todos los sensores de movimiento con token
export async function getAllSensorMovimientos(token) {
    console.log("Sending token to backend:", token);
    // Cambiar de POST a GET y pasar el token como query parameter
    const response = await fetch(`/api/sensores/sensoresMovimiento?token=${token}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    if (!response.ok) {
        throw new Error('Error al obtener los sensores de movimiento');
    }
    return await response.json();
}





