import axios from 'axios';

const API_BASE_URL = '/api';

export async function getEventos() {
    return await axios.get(`${API_BASE_URL}/eventos`);
}

export default {
    getEventos,
};

