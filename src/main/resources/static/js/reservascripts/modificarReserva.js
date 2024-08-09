import {BASE_URL} from '../BASE_URL.js';

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

        let totalCost = (precioBase * (pasajeros_local.length || 1)) + (precioFijo * (pasajeros_local.length || 1)) + montoTotal;
        if (isNaN(totalCost)) {
            console.error('totalCost no es un número');
            costeTotalInput.value = '0.00';
            tituloCosto.innerText = 'Costo Total';
        } else {
            const igv = totalCost * 0.18;
            const total = totalCost + igv;
            costeTotalInput.value = total.toFixed(2);
            tituloCosto.innerText = `Costo Total + IGV (${igv.toFixed(2)}):`;
        }
    } else {
        costeBaseInput.value = '0.00';
        costeFijoInput.value = '0.00';
        costeTotalInput.value = '0.00';
    }
}

document.addEventListener('DOMContentLoaded', async function () {
    // Función para llenar los selects
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
            paqueteSelect.addEventListener('change', function () {
                updateCosts(montoTotal);
            });

        } catch (error) {
            console.error('Error al cargar los datos:', error);
        }
    }

    async function loadReserva() {
        try {
            const url = window.location.href;
            const parsedUrl = new URL(url);
            const pathname = parsedUrl.pathname;
            const parts = pathname.split('/');
            const id = parts[parts.length - 1];
            const urlAPI = `${BASE_URL}/actions/reserva/obtener/${id}`;
            const response = await axios.get(urlAPI);
            const reserva = response.data;

            // Completar los campos del formulario con la información de la reserva
            document.getElementById('nombre').value = reserva.cliente.nombres || '';
            document.getElementById('apellido').value = reserva.cliente.apellidos || '';
            document.getElementById('dni').value = reserva.cliente.numdocumento || '';
            document.getElementById('correo').value = reserva.cliente.correo || '';
            document.getElementById('telefono').value = reserva.cliente.celular || '';
            document.getElementById('fechaRegistro').value = reserva.fecha_registro || '';
            document.getElementById('fechaPartida').value = reserva.fecha_partida || '';
            document.getElementById('nombrePaquete').value = reserva.id_paquete || '';
            document.getElementById('tipoViaje').value = reserva.tipo_viaje || '';
            document.getElementById('conductor').value = reserva.id_conductor || '';
            document.getElementById('estado').value = reserva.id_estado || '';
            document.getElementById('notas').value = reserva.notas_adicionales || '';
            document.getElementById('tipoMoneda').value = reserva.tipo_moneda || '';

            // Inicializar los arrays locales con los datos de la reserva
            pasajeros_local = reserva.pasajeros || [];
            costos_local = reserva.costosAdicionales || [];
            
            // Llenar la tabla con los datos de los pasajeros
            const tablaPasajeros = document.getElementById('tabla-pasajeros');
            tablaPasajeros.innerHTML = ''; // Limpiar la tabla antes de llenarla

            pasajeros_local.forEach(pasajero => {
                const fila = document.createElement('tr');

                const celdaNombre = document.createElement('td');
                celdaNombre.textContent = pasajero.nombres || '';
                fila.appendChild(celdaNombre);

                const celdaApellido = document.createElement('td');
                celdaApellido.textContent = pasajero.apellidos || '';
                fila.appendChild(celdaApellido);

                const celdaNacionalidad = document.createElement('td');
                celdaNacionalidad.textContent = pasajero.nacionalidad.nombre_nacionalidad || '';
                fila.appendChild(celdaNacionalidad);

                const celdaDocumento = document.createElement('td');
                celdaDocumento.textContent = pasajero.num_documento || '';
                fila.appendChild(celdaDocumento);

                const celdaCorreo = document.createElement('td');
                celdaCorreo.textContent = pasajero.correo || 'N/A';
                fila.appendChild(celdaCorreo);

                const celdaCelular = document.createElement('td');
                celdaCelular.textContent = pasajero.celular || 'N/A';
                fila.appendChild(celdaCelular);

                const celdaAcciones = document.createElement('td');
                const botonEliminar = document.createElement('ion-icon');
                botonEliminar.name = "trash-outline";
                botonEliminar.style.cursor = "pointer";
                botonEliminar.style.color = "red";
                botonEliminar.onclick = function () {
                    eliminarPasajero(this);
                };
                celdaAcciones.appendChild(botonEliminar);
                fila.appendChild(celdaAcciones);

                tablaPasajeros.appendChild(fila);
            });

            // Llenar la tabla con los datos de costos adicionales
            const tablaCostos = document.getElementById('tabla-costos');
            tablaCostos.innerHTML = '';
            montoTotal = 0; // Reiniciar montoTotal
            costos_local.forEach(costo => {
                const fila = document.createElement('tr');

                const celdaDescripcion = document.createElement('td');
                celdaDescripcion.textContent = costo.descripcion || '';
                fila.appendChild(celdaDescripcion);

                const celdaCosto = document.createElement('td');
                const costoMonto = parseFloat(costo.monto) || 0;
                celdaCosto.textContent = costoMonto.toFixed(2);
                montoTotal += costoMonto; // Sumar al montoTotal
                fila.appendChild(celdaCosto);

                const celdaAcciones = document.createElement('td');
                const botonEliminar = document.createElement('ion-icon');
                botonEliminar.name = "trash-outline";
                botonEliminar.style.cursor = "pointer";
                botonEliminar.style.color = "red";
                botonEliminar.onclick = function () {
                    eliminarCosto(this);
                };
                celdaAcciones.appendChild(botonEliminar);
                fila.appendChild(celdaAcciones);

                tablaCostos.appendChild(fila);
            });

            // Actualizar los costos del paquete
            updateCosts();
        } catch (error) {
            console.error('Error al cargar la reserva:', error);
        }
    }


    // Llamar a las funciones
    await loadCombos();
    await loadReserva();
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
        costos_local.push({descripcion, monto});
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

    if (nombres && apellidos && !isNaN(id_nacionalidad) && num_documento) {
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
        pasajeros_local.push({ nombres, apellidos, nacionalidad: { id_nacionalidad, nombre_nacionalidad: nacionalidad }, num_documento, correo, celular });

        const pasajeros_actualizados = pasajeros_local.map(pasajero => ({
            nombres: pasajero.nombres,
            apellidos: pasajero.apellidos,
            num_documento: pasajero.num_documento,
            correo: pasajero.correo,
            celular: pasajero.celular,
            id_nacionalidad: pasajero.nacionalidad.id_nacionalidad
        }));

        console.log('Pasajeros Actualizados:', JSON.stringify(pasajeros_actualizados, null, 2));

        // Actualizar costos cuando se agrega un nuevo pasajero
        updateCosts();
    } else {
        alert("Por favor, complete todos los campos obligatorios antes de agregar un pasajero.");
    }
}

window.eliminarPasajero = function (icon) {
    const row = icon.parentNode.parentNode;
    const nombre = row.getElementsByTagName('td')[0].innerText;
    const apellido = row.getElementsByTagName('td')[1].innerText;
    const numeroDocumento = row.getElementsByTagName('td')[3].innerText;

    // Encuentra el índice del pasajero a eliminar
    const index = pasajeros_local.findIndex(pasajero => pasajero.nombres === nombre && pasajero.apellidos === apellido && pasajero.num_documento === numeroDocumento);

    if (index !== -1) {
        // Elimina el pasajero del array
        pasajeros_local.splice(index, 1);
    } else {
        console.error('Pasajero no encontrado en el array.');
    }

    // Elimina la fila de la tabla
    row.parentNode.removeChild(row);

    // Actualiza costos después de eliminar un pasajero
    updateCosts();
}

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

document.getElementById('modificar').addEventListener('click', updateReservation);

function updateReservation() {
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

    let transaccion = {
        tipo_moneda: monedaOption.value,
        estado_pago: ''
    };

    if (idEstado === 1) {
        transaccion.estado_pago = 'Pendiente';
    } else if (idEstado === 3) {
        transaccion.estado_pago = 'Completado';
    } else if (idEstado === 5) {
        transaccion.estado_pago = 'Fallido';
    }

    const pasajeros_actualizados = pasajeros_local.map(pasajero => {
        return {
            nombres: pasajero.nombres,
            apellidos: pasajero.apellidos,
            num_documento: pasajero.num_documento,
            correo: pasajero.correo,
            celular: pasajero.celular,
            id_nacionalidad: pasajero.nacionalidad.id_nacionalidad
        };
    });

    console.log('Pasajeros Actualizados:', pasajeros_actualizados);

    const reservaActualizada = {
        cliente,
        fecha_partida,
        tipo_viaje,
        pasajeros: pasajeros_actualizados,
        idEstado,
        idPaquete,
        idEmpleado,
        costos: costos_local.length > 0 ? costos_local : null, // Enviar null si no hay costos
        costo_total,
        notas_adicionales,
        transaccion
    };

    // Obtener el ID de la reserva desde la URL
    const reservaId = window.location.pathname.split('/').pop();

    console.log('Datos de la reserva a actualizar:', JSON.stringify(reservaActualizada, null, 2));

    axios.put(`${BASE_URL}/actions/reserva/actualizar/${reservaId}`, reservaActualizada, {
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(res => {
            toastr["success"]("Reserva actualizada exitosamente");
            setTimeout(function () {
                location.reload();
            }, 3500);
        })
        .catch(error => {
            console.error('Error al actualizar la reserva:', error);
            if (error.response && error.response.data) {
                console.error('Respuesta del servidor:', error.response.data);
                console.log(reserva);
            }
            toastr["error"]("Error al actualizar Reserva");
        });
}

function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

