const API_BASE_URL = 'http://localhost:8080/api';

export async function getSensors(sensorType) {
    try {
        let endpoint = '';
        switch (sensorType) {
            case 'temperature':
                endpoint = 'sensorTemperaturas';
                break;
            case 'movement':
                endpoint = 'sensorMovimientos';
                break;
            case 'access':
                endpoint = 'sensorAccesos';
                break;
            default:
                throw new Error(`Unknown sensor type: ${sensorType}`);
        }

        const response = await fetch(`${API_BASE_URL}/${endpoint}`);
        if (!response.ok) {
            throw new Error('Failed to fetch sensors');
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching sensors:', error);
        throw error;
    }
}

export default {
    getSensors
};
