import axios from 'axios';

const API_URL = '/api/sensores'; // Usar URL relativa para que el proxy redirija correctamente

const getSensors = (sensorType) => {
    return axios.get(`${API_URL}/${sensorType}`);
};

export default {
    getSensors,
};
