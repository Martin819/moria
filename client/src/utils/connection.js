const API_HOST = process.env.REACT_APP_API_HOST || 'localhost';
const API_PORT = process.env.REACT_APP_API_PORT || '8080';

export const API_CONN = `http://${API_HOST}:${API_PORT}`;
