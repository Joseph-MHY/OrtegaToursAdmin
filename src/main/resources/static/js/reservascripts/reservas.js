import { BASE_URL } from '../BASE_URL.js';

let reservas = [];
let reservasFiltradas = [];
const tablaReservas = document.getElementById('tablaReservas');
const nombrePaqueteFilter = document.getElementById('nombrePaqueteFilter');
const itemsPorPagina = 16;
let paginaActual = 1;
const searchBtn = document.getElementById('searchButton');
const estadosTexto = {
    "Enprogreso": "En progreso",
    "Acontactar": "Contactar",
    "Reservado": "Reservado",
    "Enesperapago": "En espera de pago",
    "Cancelado": "Cancelado"
};


// Cargar opciones de paquetes en el select
document.addEventListener('DOMContentLoaded', async function () {
    try {
        const response = await axios.get(BASE_URL + '/actions/paquetes');
        const paquetes = response.data;

        // Limpiar opciones existentes
        nombrePaqueteFilter.innerHTML = '<option value="">Seleccionar</option>';

        // Agregar opciones de tours
        paquetes.tours.forEach(tour => {
            const option = document.createElement('option');
            option.value = tour.nombre_paquete; // Usar nombre del paquete como valor
            option.textContent = `${tour.nombre_paquete} (Tour)`;
            nombrePaqueteFilter.appendChild(option);
        });

        // Agregar opciones de promociones
        paquetes.promociones.forEach(promo => {
            const option = document.createElement('option');
            option.value = promo.nombre_paquete; // Usar nombre del paquete como valor
            option.textContent = `${promo.nombre_paquete} (Promoción)`;
            nombrePaqueteFilter.appendChild(option);
        });

    } catch (error) {
        console.error('Error al cargar los paquetes:', error);
    }
});

// Función para redirigir a la página de registro de reservas
function redirectToRegistrarReserva() {
    window.location.href = '/admin/reservas/registrarreservas';
}

document.getElementById("registerButton").addEventListener("click", () => {
    redirectToRegistrarReserva();
});

// Función para mostrar reservas desde el backend
async function mostrarReservas() {
    try {
        const response = await axios.get(BASE_URL + '/actions/reservas');
        reservas = response.data;
        reservasFiltradas = reservas; // Inicialmente, no hay filtro
        aplicarFiltrosYOrdenar(); // Aplicar filtros y ordenaciones iniciales
        ordenarReservas(reservas, 'inactivos')

    } catch (error) {
        console.error('Error al obtener las reservas:', error);
        mostrarMensajeNoRegistros();
    }
}

function capitalizeFirstLetter(text) {
    if (!text) return text;
    return text.charAt(0).toUpperCase() + text.slice(1).toLowerCase();
}

// Función para mostrar la tabla de reservas
function mostrarTabla(reservas, pagina) {
    tablaReservas.innerHTML = '';
    if (reservas.length === 0) {
        mostrarMensajeNoRegistros();
    } else {
        const inicio = (pagina - 1) * itemsPorPagina;
        const fin = Math.min(inicio + itemsPorPagina, reservas.length);
        const reservasPaginadas = reservas.slice(inicio, fin);

        reservasPaginadas.forEach((reserva) => {
            const estadoTexto = estadosTexto[reserva.nombreEstado] || reserva.nombreEstado; // Obtener el texto correspondiente

            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${reserva.idReserva}</td>
                <td>${capitalizeFirstLetter(reserva.cliente)}</td>
                <td>${reserva.numdocumento}</td>
                <td>${reserva.celular}</td>
                <td>${capitalizeFirstLetter(reserva.nombrePaquete)}</td>
                <td>${capitalizeFirstLetter(reserva.tipoViaje)}</td>
                <td>${capitalizeFirstLetter(estadoTexto)}</td> <!-- Mostrar el texto del estado -->
                <td>${reserva.fechaRegistro}</td>
                <td class="view-icon" style="width: 1px; text-align: center"><a class="ver-reserva" data-id="${reserva.idReserva}"><ion-icon name="eye"></ion-icon></a></td>
            `;
            tablaReservas.appendChild(row);
        });

        // Añadir evento de clic a los íconos de visualización
        const linksVerReserva = document.querySelectorAll('.ver-reserva');
        linksVerReserva.forEach(link => {
            link.addEventListener('click', function (event) {
                event.preventDefault();
                window.location.href = `${BASE_URL}/admin/reservas/modificar/${this.getAttribute('data-id')}`;
            });
        });
    }
}

// Función para crear los botones de paginación
function crearBotonesPaginacion(datos) {
    const paginacionContainer = document.getElementById('paginacion');
    paginacionContainer.innerHTML = '';
    const totalPaginas = Math.ceil(datos.length / itemsPorPagina);

    for (let i = 1; i <= totalPaginas; i++) {
        const boton = document.createElement('button');
        boton.classList.add('paginas');
        boton.textContent = i;
        boton.addEventListener('click', () => {
            paginaActual = i;
            mostrarTabla(datos, paginaActual);
        });
        paginacionContainer.appendChild(boton);
    }
}

// Función para aplicar filtros a las reservas
function aplicarFiltros(reservas) {
    const nombrePaquete = document.getElementById('nombrePaqueteFilter').value;
    const tipoViaje = document.getElementById('tipoViajeFilter').value;
    const estado = document.getElementById('estadoFilter').value;
    const fechaPartida = document.getElementById('fechaPartidaFilter').value;

    let reservasFiltradas = reservas;

    if (nombrePaquete) {
        reservasFiltradas = reservasFiltradas.filter(reserva => reserva.nombrePaquete === nombrePaquete);
    }
    if (tipoViaje) {
        reservasFiltradas = reservasFiltradas.filter(reserva => reserva.tipoViaje === tipoViaje);
    }
    if (estado) {
        reservasFiltradas = reservasFiltradas.filter(reserva => reserva.nombreEstado === estado);
    }
    if (fechaPartida) {
        reservasFiltradas = reservasFiltradas.filter(reserva => reserva.fechaRegistro === fechaPartida);
    }

    return reservasFiltradas;
}

// Función para ordenar las reservas
function ordenarReservas(reservas, criterio) {
    switch (criterio) {
        case 'nombre-az':
            return reservas.sort((a, b) => a.cliente.localeCompare(b.cliente));
        case 'nombre-za':
            return reservas.sort((a, b) => b.cliente.localeCompare(a.cliente));
        case 'activos':
            return reservas.sort((a, b) => new Date(b.fechaRegistro) - new Date(a.fechaRegistro));
        case 'inactivos':
            return reservas.sort((a, b) => new Date(a.fechaRegistro) - new Date(b.fechaRegistro));
        default:
            return reservas;
    }
}

// Función para buscar reservas por cliente o documento
async function buscarReserva() {
    const searchInput = document.getElementById('searchInput');
    const searchTerm = searchInput.value.trim().toLowerCase();

    if (searchTerm === '') {
        return reservas;
    } else {
        return reservas.filter(reserva =>
            reserva.cliente.toLowerCase().includes(searchTerm) ||
            reserva.numdocumento.includes(searchTerm)
        );
    }
}

// Función para aplicar todos los filtros y ordenaciones
async function aplicarFiltrosYOrdenar() {
    let reservasAplicadas = await buscarReserva(); // Buscar reservas si hay término de búsqueda

    reservasAplicadas = aplicarFiltros(reservasAplicadas); // Aplicar filtros

    const criterioOrden = document.getElementById('select-orden').value;
    reservasAplicadas = ordenarReservas(reservasAplicadas, criterioOrden); // Ordenar reservas

    mostrarTabla(reservasAplicadas, paginaActual);
    crearBotonesPaginacion(reservasAplicadas);
}

// Eventos de filtros
document.getElementById('nombrePaqueteFilter').addEventListener('change', aplicarFiltrosYOrdenar);
document.getElementById('tipoViajeFilter').addEventListener('change', aplicarFiltrosYOrdenar);
document.getElementById('estadoFilter').addEventListener('change', aplicarFiltrosYOrdenar);
document.getElementById('fechaPartidaFilter').addEventListener('change', aplicarFiltrosYOrdenar);
document.getElementById('select-orden').addEventListener('change', aplicarFiltrosYOrdenar);

// Evento de búsqueda
searchBtn.addEventListener('click', async () => {
    await aplicarFiltrosYOrdenar();
});

function mostrarMensajeNoRegistros() {
    tablaReservas.innerHTML = ''; // Limpiar tabla antes de mostrar el mensaje
    const row = document.createElement('tr');
    row.innerHTML = `
        <td colspan="9" style="text-align: center;">No hay registros disponibles</td>
    `;
    tablaReservas.appendChild(row);
}

mostrarReservas();
