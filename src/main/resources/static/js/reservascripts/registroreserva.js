import { BASE_URL } from '../BASE_URL.js';

document.addEventListener('DOMContentLoaded', function () {
    // URL de la API de nacionalidades
    const apiUrl = BASE_URL + '/actions/nacionalidades';

    // Función para llenar el select con las nacionalidades
    function loadNacionalidades() {
        axios.get(apiUrl)
            .then(response => {
                const select = document.getElementById('nacionalidadPasajero');
                const nacionalidades = response.data;

                // Agregar opciones al select
                nacionalidades.forEach(nacionalidad => {
                    const option = document.createElement('option');
                    option.value = nacionalidad.id;
                    option.textContent = nacionalidad.nombre;
                    select.appendChild(option);
                });
            })
            .catch(error => console.error('Error al cargar nacionalidades:', error));
    }

    // Llamar a la función para cargar las nacionalidades cuando se cargue la página
    loadNacionalidades();
});