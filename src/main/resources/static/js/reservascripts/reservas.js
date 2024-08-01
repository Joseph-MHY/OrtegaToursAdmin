import { BASE_URL } from '../BASE_URL.js';

let reservas = [];
let reservasFiltradas = [];
const tablaReservas = document.getElementById('tablaReservas');
const itemsPorPagina = 30;
let paginaActual = 1;
const searchBtn = document.getElementById('searchButton');

function redirectToRegistrarReserva() {
    window.location.href = '/admin/reservas/registrarreservas';
}

document.getElementById("registerButton").addEventListener("click", () => {
    redirectToRegistrarReserva();
});

async function mostrarReservas() {
    try {
        const response = await axios.get(BASE_URL + '/actions/reservas');
        reservas = response.data;
        mostrarTabla(reservas, paginaActual);
        crearBotonesPaginacion(reservas);
    } catch (error) {
        console.error('Error al obtener las reservas:', error);
        mostrarMensajeNoRegistros();
    }
}

function mostrarTabla(reservas, pagina) {
    tablaReservas.innerHTML = '';
    if (reservas.length === 0) {
        mostrarMensajeNoRegistros();
    } else {
        const inicio = (pagina - 1) * itemsPorPagina;
        const fin = Math.min(inicio + itemsPorPagina, reservas.length);
        const reservasPaginadas = reservas.slice(inicio, fin);

        reservasPaginadas.forEach((reserva) => {
            const row = document.createElement('tr');
            row.innerHTML = `
                    <td>${reserva.idReserva}</td>
                    <td>${reserva.cliente}</td>
                    <td>${reserva.numdocumento}</td>
                    <td>${reserva.celular}</td>
                    <td>${reserva.nombrePaquete}</td>
                    <td>${reserva.tipoViaje}</td>
                    <td>${reserva.nombreEstado}</td>
                    <td>${reserva.fechaRegistro}</td>
                    <td class="view-icon" style="width: 1px; text-align: center"><a class="ver-reserva" data-id="${reserva.idReserva}"><ion-icon name="eye"></ion-icon></a></td>
                    <td class="delete-icon"><ion-icon name="trash-outline"></ion-icon></td>
                `;
            tablaReservas.appendChild(row);
        });

        // Añadir evento de clic a los íconos de visualización
        const linksVerReserva = document.querySelectorAll('.ver-reserva');
        linksVerReserva.forEach(link => {
            link.addEventListener('click', function (event) {
                event.preventDefault();
                window.location.href = `${BASE_URL}/admin/reservas/modificar/${this.getAttribute('data-id')}`
            });
        });
    }
}

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

async function buscarReserva() {
    const searchInput = document.getElementById('searchInput');
    const searchTerm = searchInput.value.trim().toLowerCase();

    if (searchTerm === '') {
        reservasFiltradas = reservas;
    } else {
        reservasFiltradas = reservas.filter(reserva =>
            reserva.cliente.toLowerCase().includes(searchTerm) ||
            reserva.numdocumento.includes(searchTerm)
        );
    }

    mostrarTabla(reservasFiltradas, paginaActual);
    crearBotonesPaginacion(reservasFiltradas);
}

searchBtn.addEventListener('click', async () => {
    await buscarReserva();
});

function mostrarMensajeNoRegistros() {
    const row = document.createElement('tr');
    row.innerHTML = `
            <td colspan="9" style="text-align: center;">No hay registros disponibles</td>
        `;
    tablaReservas.appendChild(row);
}

mostrarReservas();

