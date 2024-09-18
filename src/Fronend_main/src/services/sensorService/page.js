import axios from 'axios';

// Definir las rutas base de cada API
const API_BASE_URL = '/api';

// Función genérica que obtiene sensores por tipo
const getSensors = (sensorType) => {
    switch (sensorType) {
        case 'temperature':
            return axios.get(`${API_BASE_URL}/sensorTemperaturas`);
        case 'movement':
            return axios.get(`${API_BASE_URL}/sensorMovimientos`);
        case 'access':
            return axios.get(`${API_BASE_URL}/sensorAccesos`);
        default:
            throw new Error(`Unknown sensor type: ${sensorType}`);
    }
};

export default {
    getSensors,
};

