import { BASE_URL } from '../BASE_URL.js';

const tituloCosto = document.getElementById('tituloCosto');
let montoTotal = 0;
let pasajeros_local = [];
let costos_local = [];
tituloCosto.innerText = 'Costo Total';

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

// Actualizar costos totales
function updateCosts() {
    const select = document.getElementById('nombrePaquete');
    const costeBaseInput = document.getElementById('costeBase');
    const costeFijoInput = document.getElementById('costeFijo');
    const costeTotalInput = document.getElementById('costeTotal');

    const selectedOption = select.options[select.selectedIndex];

    if (selectedOption) {
        const precioBase = parseFloat(selectedOption.dataset.precioBase) || 0;
        const precioFijo = parseFloat(selectedOption.dataset.precioFijo) || 0;

        costeBaseInput.value = precioBase.toFixed(2);
        costeFijoInput.value = precioFijo.toFixed(2);

        let totalCost;
        if (pasajeros_local.length > 1) {
            totalCost = (precioBase * pasajeros_local.length) + (precioFijo * pasajeros_local.length) + montoTotal;
        } else {
            totalCost = precioBase + precioFijo + montoTotal;
        }

        if (isNaN(totalCost)) {
            console.error('totalCost no es un número');
            costeTotalInput.value = '0.00';
            tituloCosto.innerText = 'Costo Total';
        } else {
            const igv = totalCost * 0.18;
            const total = totalCost + igv
            costeTotalInput.value = total.toFixed(2);
            tituloCosto.innerText = `Costo Total + IGV (${igv.toFixed(2)}):`;
        }
    } else {
        costeBaseInput.value = '0.00';
        costeFijoInput.value = '0.00';
        costeTotalInput.value = '0.00';
    }
}

// Cargar combos de nacionalidades, conductores y paquetes
document.addEventListener('DOMContentLoaded', async function () {
    async function loadCombos() {
        try {
            const [nacionalidadesResponse, conductoresResponse, paquetesResponse] = await Promise.all([
                axios.get(BASE_URL + '/actions/nacionalidades'),
                axios.get(BASE_URL + '/actions/empleados/conductores'),
                axios.get(BASE_URL + '/actions/paquetes')
            ]);

            const nacionalidades = nacionalidadesResponse.data;
            const conductores = conductoresResponse.data;
            const paquetes = paquetesResponse.data;

            // Agregar opciones de nacionalidades
            const nacionalidadSelect = document.getElementById('nacionalidadPasajero');
            nacionalidades.forEach(nacionalidad => {
                const option = document.createElement('option');
                option.value = nacionalidad.id;
                option.textContent = nacionalidad.nombre;
                nacionalidadSelect.appendChild(option);
            });

            // Agregar opciones de conductores
            const conductorSelect = document.getElementById('conductor');
            conductores.forEach(conductor => {
                const option = document.createElement('option');
                option.value = conductor.id;
                option.textContent = conductor.nombre_apellidos;
                conductorSelect.appendChild(option);
            });

            // Agregar opciones de paquetes
            const paqueteSelect = document.getElementById('nombrePaquete');
            paquetes.tours.forEach(tour => {
                const option = document.createElement('option');
                option.value = tour.id;
                option.textContent = `${tour.nombre_paquete} (Tour)`;
                option.dataset.precioBase = tour.precio_base;
                option.dataset.precioFijo = tour.precio_fijo;
                paqueteSelect.appendChild(option);
            });
            paquetes.promociones.forEach(promo => {
                const option = document.createElement('option');
                option.value = promo.id;
                option.textContent = `${promo.nombre_paquete} (Promoción)`;
                option.dataset.precioBase = promo.precio_base;
                option.dataset.precioFijo = promo.precio_fijo;
                paqueteSelect.appendChild(option);
            });

            // Manejar cambio de selección
            paqueteSelect.addEventListener('change', updateCosts);

        } catch (error) {
            console.error('Error al cargar los datos:', error);
        }
    }

    await loadCombos();
});

// Abrir el modal de costos adicionales
window.openModalCostosAdicionales = function () {
    const modal = document.getElementById("costosAdicionalesModal");
    modal.style.display = "block";
};

// Cerrar el modal de costos adicionales
window.closeModalCostosAdicionales = function () {
    const modal = document.getElementById("costosAdicionalesModal");
    modal.style.display = "none";
}

// Agregar un costo adicional a la tabla
window.agregarCosto = function () {
    const descripcion = document.getElementById("descripcion").value.trim();
    const monto = parseFloat(document.getElementById("costo").value) || 0;

    if (descripcion && monto) {
        const table = document.getElementById("tablaCostos").getElementsByTagName('tbody')[0];
        const newRow = table.insertRow();

        const cell1 = newRow.insertCell(0);
        const cell2 = newRow.insertCell(1);
        const cell3 = newRow.insertCell(2);

        cell1.innerHTML = descripcion;
        cell2.innerHTML = monto.toFixed(2);
        cell3.innerHTML = '<ion-icon name="trash-outline" onclick="eliminarCosto(this)" style="cursor: pointer; color: red;"></ion-icon>'; // Usando Ionicons

        // Limpiar campos después de agregar
        document.getElementById("descripcion").value = "";
        document.getElementById("costo").value = "";

        // Sumar el nuevo costo al montoTotal
        montoTotal += monto;
        updateCosts();

        // Agregar el costo a costos_local
        costos_local.push({ descripcion, monto });
        console.log(costos_local);
    } else {
        alert("Por favor, complete ambos campos antes de agregar un costo.");
    }
}

// Eliminar un costo adicional de la tabla
window.eliminarCosto = function (icon) {
    const row = icon.parentNode.parentNode;
    const costo = parseFloat(row.getElementsByTagName('td')[1].innerText) || 0;
    const descripcion = row.getElementsByTagName('td')[0].innerText;

    // Filtrar el costo a eliminar de la lista
    costos_local = costos_local.filter(costo_local => !(costo_local.descripcion === descripcion && costo_local.monto === costo));

    // Restar el costo eliminado del montoTotal
    montoTotal -= costo;
    row.parentNode.removeChild(row);

    // Actualizar los costos
    updateCosts();
}

// Función para abrir la ventana modal de pasajeros
window.openModalPasajeros = function () {
    const modal = document.getElementById("pasajerosModal");
    modal.style.display = "block";
}

// Función para cerrar la ventana modal de pasajeros
window.closeModalPasajeros = function () {
    const modal = document.getElementById("pasajerosModal");
    modal.style.display = "none";
}

// Función para mostrar u ocultar el botón de pasajeros
function toggleButtons() {
    const paqueteSelect = document.getElementById('nombrePaquete');
    const pasajerosButton = document.getElementById('Pasajero');
    const costosButton = document.getElementById('costesAdicionales');
    const guaardarButton = document.getElementById('guardar');
    if (paqueteSelect.value) {
        pasajerosButton.style.display = 'block';
        costosButton.style.display = 'block';
        guaardarButton.style.display = 'block';
    } else {
        pasajerosButton.style.display = 'none';
        costosButton.style.display = 'none';
        guaardarButton.style.display = 'none';
    }
}

// Event listener para mostrar u ocultar el botón de pasajeros cuando cambia la selección del paquete
document.getElementById('nombrePaquete').addEventListener('change', toggleButtons);



// Agregar un pasajero a la tabla
window.agregarPasajero = function () {
    const nacionalidadSelect = document.getElementById("nacionalidadPasajero");
    const selectedOption = nacionalidadSelect.options[nacionalidadSelect.selectedIndex];

    const nombres = document.getElementById("nombrePasajero").value.trim();
    const apellidos = document.getElementById("apellidoPasajero").value.trim();
    const nacionalidad = selectedOption.text;
    const id_nacionalidad = parseInt(selectedOption.value, 10);
    const num_documento = document.getElementById("numeroDocumentoPasajero").value.trim();
    const correo = document.getElementById("correoPasajero").value.trim() || null;
    const celular = document.getElementById("celularPasajero").value.trim() || null;

    if (nombres && apellidos && id_nacionalidad && num_documento) {
        const table = document.getElementById("tablaPasajeros").getElementsByTagName('tbody')[0];
        const newRow = table.insertRow();

        const cell1 = newRow.insertCell(0);
        const cell2 = newRow.insertCell(1);
        const cell3 = newRow.insertCell(2);
        const cell4 = newRow.insertCell(3);
        const cell5 = newRow.insertCell(4);
        const cell6 = newRow.insertCell(5);
        const cell7 = newRow.insertCell(6);

        cell1.innerHTML = nombres;
        cell2.innerHTML = apellidos;
        cell3.innerHTML = nacionalidad;
        cell4.innerHTML = num_documento;
        cell5.innerHTML = correo || 'N/A';
        cell6.innerHTML = celular || 'N/A';
        cell7.innerHTML = '<ion-icon name="trash-outline" onclick="eliminarPasajero(this)" style="cursor: pointer; color: red;"></ion-icon>'; // Usando Ionicons

        // Limpiar campos después de agregar
        document.getElementById("nombrePasajero").value = "";
        document.getElementById("apellidoPasajero").value = "";
        document.getElementById("nacionalidadPasajero").value = "";
        document.getElementById("numeroDocumentoPasajero").value = "";
        document.getElementById("correoPasajero").value = "";
        document.getElementById("celularPasajero").value = "";

        // Agregar pasajero a la lista
        pasajeros_local.push({ nombres, apellidos, id_nacionalidad, num_documento, correo, celular });

        // Actualizar costos cuando se agrega un nuevo pasajero
        updateCosts();
    } else {
        alert("Por favor, complete todos los campos obligatorios antes de agregar un pasajero.");
    }
}

// Eliminar un pasajero de la tabla
window.eliminarPasajero = function (icon) {
    const row = icon.parentNode.parentNode;
    const nombre = row.getElementsByTagName('td')[0].innerText;
    const apellido = row.getElementsByTagName('td')[1].innerText;
    const numeroDocumento = row.getElementsByTagName('td')[3].innerText;

    // Filtrar el pasajero a eliminar de la lista
    pasajeros_local = pasajeros_local.filter(pasajero => !(pasajero.nombre === nombre && pasajero.apellido === apellido && pasajero.numeroDocumento === numeroDocumento));
    row.parentNode.removeChild(row);

    // Actualizar costos después de eliminar un pasajero
    updateCosts();
}

// Cerrar el modal si se hace clic fuera de él
window.onclick = function (event) {
    const modalCostosAdicionales = document.getElementById("costosAdicionalesModal");
    const modalPasajeros = document.getElementById("pasajerosModal");
    if (event.target == modalCostosAdicionales) {
        modalCostosAdicionales.style.display = "none";
    }
    if (event.target == modalPasajeros) {
        modalPasajeros.style.display = "none";
    }
}

document.getElementById('guardar').addEventListener('click', sendReservation);

function sendReservation() {
    const estadoSelect = document.getElementById('estado');
    const selectedOption = estadoSelect.options[estadoSelect.selectedIndex];

    const monedaSelect = document.getElementById('tipoMoneda');
    const monedaOption = monedaSelect.options[monedaSelect.selectedIndex];

    const conductorSelect = document.getElementById('conductor');
    const conductorOption = conductorSelect.options[conductorSelect.selectedIndex];

    const tipoSelect = document.getElementById('tipoViaje');
    const tipoOption = tipoSelect.options[tipoSelect.selectedIndex];

    const paqueteSelect = document.getElementById('nombrePaquete');
    const paqueteOption = paqueteSelect.options[paqueteSelect.selectedIndex];

    const cliente = {
        nombre: document.getElementById('nombre').value.trim(),
        apellido: document.getElementById('apellido').value.trim(),
        document: document.getElementById('dni').value.trim(),
        correo: document.getElementById('correo').value.trim(),
        telefono: document.getElementById('telefono').value.trim()
    };

    if (!cliente.nombre || !cliente.apellido || !cliente.document || !cliente.correo || !cliente.telefono) {
        alert("Por favor, complete todos los campos del cliente.");
        return;
    }

    if (!validateEmail(cliente.correo)) {
        alert("Por favor, ingrese un correo electrónico válido.");
        return;
    }

    if (pasajeros_local.length === 0) {
        alert("Por favor, agregue al menos un pasajero.");
        return;
    }

    if (!selectedOption.value || !monedaOption.value || !conductorOption.value || !tipoOption.value || !paqueteOption.value) {
        alert("Por favor, seleccione todas las opciones requeridas.");
        return;
    }

    let transaccion = {
        tipo_moneda: monedaOption.value,
        estado_pago: '',
        monto_pagado: 0.0
    };

    const fecha_partida = document.getElementById('fechaPartida').value;
    const tipo_viaje = tipoOption.value;
    const idEstado = parseInt(selectedOption.value, 10);
    const idPaquete = parseInt(paqueteOption.value, 10);
    const idEmpleado = parseInt(conductorOption.value, 10);
    const costo_total = parseFloat(document.getElementById('costeTotal').value) || 0.0;
    const notas_adicionales = document.getElementById('notas').value.trim() || null;

    if (!fecha_partida) {
        alert("Por favor, seleccione una fecha de partida.");
        return;
    }

    if (costo_total === 0.0) {
        alert("El costo total no puede ser 0.0");
        return;
    }

    if (idEstado === 1 || idEstado === 2 || idEstado === 4) {
        transaccion.estado_pago = 'Pendiente';
    } else if (idEstado === 3) {
        transaccion.estado_pago = 'Completado';
        transaccion.monto_pagado = costo_total;
    } else if (idEstado === 5) {
        transaccion.estado_pago = 'Fallido';
    }

    const reserva = {
        cliente,
        fecha_partida,
        tipo_viaje,
        pasajeros: pasajeros_local,
        idEstado,
        idPaquete,
        idEmpleado,
        costos: costos_local.length > 0 ? costos_local : null, // Enviar null si no hay costos
        costo_total,
        notas_adicionales,
        transaccion
    };

    console.log('Datos de la reserva a enviar:', JSON.stringify(reserva, null, 2));

    axios.post(`${BASE_URL}/actions/reserva/registrar`, reserva, {
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(res => {
            toastr["success"]("Reserva registrada exitosamente");
            setTimeout(function() {
                location.reload();
            }, 3500);
        })
        .catch(error => {
            console.error('Error al registrar la reserva:', error);
            if (error.response && error.response.data) {
                console.error('Respuesta del servidor:', error.response.data);
                console.log(reserva);
            }
            toastr["error"]("Error al registrar Reserva");
        });
}

function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}