import axios from 'axios';

/**
 * Axios instance pre-configured with the backend base URL.
 * 
 * Instead of typing http://localhost:8080 in every API call,
 * we create a single axios instance here and import it everywhere.
 * 
 * Usage:
 *   import api from '../api/axios';
 *   const response = await api.get('/students');
 */
const api = axios.create({
    baseURL: 'http://localhost:8080/api',   // Spring Boot backend
    headers: {
        'Content-Type': 'application/json',
    },
    timeout: 10000, // 10 second timeout
});

export default api;
