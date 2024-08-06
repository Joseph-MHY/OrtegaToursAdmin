import { BASE_URL } from './BASE_URL.js';

let reporte = [];
let reporteFiltrado = [];
const tablaReporte = document.getElementById("tablaReporte");
const itemsPorPagina = 10;
// ojo el numero que pongas sera -2 al numero de items que habra en la tabla

let paginaActual = 1;

toastr.options = {
    "closeButton": false,
    "debug": false,
    "newestOnTop": false,
    "progressBar": false,
    "positionClass": "toast-top-center",
    "preventDuplicates": false,
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "60",
    "timeOut": "3500",
    "extendedTimeOut": "3500",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
};

async function mostrarDatos() {
    try {
        const response = await axios.get(`${BASE_URL}/report/reservas`);
        reporte = response.data;
        mostrarTabla(reporte, paginaActual);
        crearBotonesPaginacion(reporte);
        actualizarBotonExcel(reporte);
        console.log(reporte);
    } catch (error) {
        console.error('Error al obtener los datos: ', error);
    }
}
function capitalizeFirstLetter(text) {
    if (!text) return text;
    return text.charAt(0).toUpperCase() + text.slice(1).toLowerCase();
}
function mostrarTabla(datos, pagina) {
    tablaReporte.innerHTML = '';
    if (datos.length === 0) {
        mostrarMensajeNoRegistros();
    } else {
        const inicio = (pagina - 1) * itemsPorPagina;
        const fin = Math.min(inicio + itemsPorPagina, datos.length);
        const datosPaginados = datos.slice(inicio, fin);

        datosPaginados.forEach((dato) => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td style="text-align: center">${dato.id_reserva}</td>
                <td>${dato.fecha_registro}</td>
                <td>${dato.cliente}</td>
                <td>${dato.numdocumento}</td>
                <td style="text-align: center">${dato.num_pasajeros}</td>
                <td>${capitalizeFirstLetter(dato.nombre_paquete)}</td>
                <td style="text-align: center">${dato.categoria_paquete}</td>
                <td style="text-align: center">${capitalizeFirstLetter(dato.tipo_viaje)}</td>
                <td>${dato.conductor}</td>
                <td>${dato.costo_total}</td>
            `;
            tablaReporte.appendChild(row);
        });
    }
}

function crearBotonesPaginacion(datos) {
    const paginacionContainer = document.getElementById('paginacion');
    paginacionContainer.innerHTML = ''; // Limpiar el contenido anterior
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

function mostrarMensajeNoRegistros() {
    const row = document.createElement('tr');
    row.innerHTML = `
        <td colspan="10" style="text-align: center;">No hay registros disponibles</td>
    `;
    tablaReporte.appendChild(row);
}

function filtrarDatos() {
    const fechaInicio = document.getElementById('fechaInicio').value;
    const fechaFin = document.getElementById('fechaFin').value;

    if (fechaInicio === '' && fechaFin === '') {
        reporteFiltrado = reporte;
    } else {
        reporteFiltrado = reporte.filter(dato => {
            const fechaRegistro = new Date(dato.fecha_registro);
            return (!fechaInicio || new Date(fechaInicio) <= fechaRegistro) &&
                (!fechaFin || new Date(fechaFin) >= fechaRegistro);
        });
    }

    paginaActual = 1;
    mostrarTabla(reporteFiltrado, paginaActual);
    crearBotonesPaginacion(reporteFiltrado);
    actualizarBotonExcel(reporteFiltrado);

    if (reporteFiltrado.length === 0) {
        mostrarMensajeNoRegistros();
    }
}

function actualizarBotonExcel(datos) {
    const botonExcel = document.querySelector('.excel-float a');
    if (datos.length === 0) {
        botonExcel.classList.add('disabled'); // Agrega la clase para deshabilitar el botón
        botonExcel.removeEventListener('click', exportarDatosAExcel); // Elimina el manejador de eventos
        botonExcel.href = '#'; // Evita que el enlace haga algo si se hace clic
    } else {
        botonExcel.classList.remove('disabled'); // Quita la clase de deshabilitado
        botonExcel.addEventListener('click', exportarDatosAExcel); // Agrega el manejador de eventos para exportar
        botonExcel.href = '#'; // Puedes mantener el enlace vacío o configurarlo como desees
    }
}

async function exportarDatosAExcel(event) {
    const datosAExportar = reporteFiltrado.length ? reporteFiltrado : reporte;
    console.log(datosAExportar)

    try {
        const response = await axios.post(`${BASE_URL}/report/exportar`, datosAExportar, {
            headers: {
                'Content-Type': 'application/json'
            },
            responseType: 'blob' // Asegúrate de recibir la respuesta como un blob
        });

        // Generar un identificador único
        const uuid = generarUUID();

        // Crear un enlace para descargar el archivo
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        // Incluir UUID en el nombre del archivo
        link.setAttribute('download', `reporte_${uuid}.xlsx`);
        document.body.appendChild(link);
        link.click();
        link.remove();
    } catch (error) {
        console.error('Error al exportar los datos: ', error);
        toastr.error('Error al exportar los datos.');
    }
}

function generarUUID() {
    // Generar un identificador único basado en números y letras
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0, v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

document.addEventListener('DOMContentLoaded', () => {
    mostrarDatos();
    document.getElementById('fechaInicio').addEventListener('change', filtrarDatos);
    document.getElementById('fechaFin').addEventListener('change', filtrarDatos);
    document.querySelector('.excel-float a').addEventListener('click', exportarDatosAExcel);
});
