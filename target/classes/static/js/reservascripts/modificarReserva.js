import { BASE_URL } from '../BASE_URL.js';

document.addEventListener('DOMContentLoaded', function () {
    // Función para llenar el select con las nacionalidades
    function loadNacionalidades() {
        axios.get(BASE_URL + '/actions/nacionalidades')
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

    // Función para cargar los detalles de la reserva
    function loadReserva() {
        const url = window.location.href;
        const parsedUrl = new URL(url);
        const pathname = parsedUrl.pathname;
        const parts = pathname.split('/');
        const id = parts[parts.length - 1];
        console.log('ID:', id);

        const urlAPI = `${BASE_URL}/actions/reserva/obtener/${id}`;
        console.log('URL API para reserva:', urlAPI);

        axios.get(urlAPI)
            .then(response => {
                const reserva = response.data;
                console.log('Reserva data:', reserva);

                // Completar los campos del formulario con la información de la reserva
                document.getElementById('nombre').value = reserva.cliente.nombre || '';
                document.getElementById('apellido').value = reserva.cliente.apellido || '';
                document.getElementById('dni').value = reserva.cliente.documento || '';
                document.getElementById('correo').value = reserva.cliente.correo || '';
                document.getElementById('telefono').value = reserva.cliente.telefono || '';
                document.getElementById('fechaRegistro').value = reserva.fechaRegistro || '';
                document.getElementById('fechaPartida').value = reserva.fechaPartida || '';
                document.getElementById('nombrePaquete').value = reserva.nombrePaquete || '';
                document.getElementById('tipoViaje').value = reserva.tipoViaje || '';
                document.getElementById('conductor').value = reserva.conductor || '';
                document.getElementById('costeBase').value = reserva.costoBase || '';
                document.getElementById('costeFijo').value = reserva.costoFijo || '';
                document.getElementById('costeTotal').value = reserva.costoTotal || '';
                document.getElementById('estado').value = reserva.estado || '';
                document.getElementById('notas').value = reserva.notas || '';
                document.getElementById('tipoMoneda').value = reserva.tipoMoneda || '';

            })
            .catch(error => console.error('Error al cargar la reserva:', error));
    }

    // Llamar a las funciones
    loadNacionalidades();
    loadReserva();
});
