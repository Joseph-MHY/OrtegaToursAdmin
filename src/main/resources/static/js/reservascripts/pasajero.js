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

// Validar que solo se permitan números
function isNumber(value) {
    return /^\d+$/.test(value);
}

// Validar formato de correo
function isValidEmail(email) {
    const re = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    return re.test(email);
}

// Función para agregar un pasajero a la tabla
window.agregarPasajero = function () {
    const nombre = document.getElementById("nombrePasajero").value;
    const apellido = document.getElementById("apellidoPasajero").value;
    const nacionalidad = document.getElementById("nacionalidadPasajero").value;
    const numeroDocumento = document.getElementById("numeroDocumentoPasajero").value;
    const correo = document.getElementById("correoPasajero").value;
    const celular = document.getElementById("celularPasajero").value;

    // Validaciones
    if (!nombre || !apellido || !nacionalidad || !numeroDocumento || !correo || !celular) {
        alert("Por favor, complete todos los campos antes de agregar un pasajero.");
        return;
    }
    if (!isNumber(numeroDocumento) || numeroDocumento.length > 20) {
        alert("Por favor, ingrese un número de documento válido (solo números y máximo 20 caracteres).");
        return;
    }
    if (!isValidEmail(correo)) {
        alert("Por favor, ingrese un formato de correo válido.");
        return;
    }
    if (!isNumber(celular) || celular.length !== 9) {
        alert("Por favor, ingrese un número de celular válido (solo números y 9 caracteres).");
        return;
    }

    const table = document.getElementById("tablaPasajeros").getElementsByTagName('tbody')[0];
    const newRow = table.insertRow();

    const cell1 = newRow.insertCell(0);
    const cell2 = newRow.insertCell(1);
    const cell3 = newRow.insertCell(2);
    const cell4 = newRow.insertCell(3);
    const cell5 = newRow.insertCell(4);
    const cell6 = newRow.insertCell(5);
    const cell7 = newRow.insertCell(6);

    cell1.innerHTML = nombre;
    cell2.innerHTML = apellido;
    cell3.innerHTML = nacionalidad;
    cell4.innerHTML = numeroDocumento;
    cell5.innerHTML = correo;
    cell6.innerHTML = celular;
    cell7.innerHTML = '<ion-icon name="trash-outline" onclick="eliminarPasajero(this)" style="cursor: pointer; color: red;"></ion-icon>'; // Usando Ionicons

    document.getElementById("nombrePasajero").value = "";
    document.getElementById("apellidoPasajero").value = "";
    document.getElementById("nacionalidadPasajero").value = "";
    document.getElementById("numeroDocumentoPasajero").value = "";
    document.getElementById("correoPasajero").value = "";
    document.getElementById("celularPasajero").value = "";
}

// Función para eliminar un pasajero de la tabla
window.eliminarPasajero = function (icon) {
    const row = icon.parentNode.parentNode;
    row.parentNode.removeChild(row);
}

// Función para registrar todos los pasajeros
window.registrarPasajeros = function () {
    const table = document.getElementById("tablaPasajeros").getElementsByTagName('tbody')[0];
    const rows = table.getElementsByTagName('tr');
    const pasajeros = [];

    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName('td');
        const nombre = cells[0].innerText;
        const apellido = cells[1].innerText;
        const nacionalidad = cells[2].innerText;
        const numeroDocumento = cells[3].innerText;
        const correo = cells[4].innerText;
        const celular = cells[5].innerText;
        pasajeros.push({ nombre, apellido, nacionalidad, numeroDocumento, correo, celular });
    }

    // Lógica para registrar todos los pasajeros (por ejemplo, enviarlos al servidor)
    console.log("Pasajeros registrados:", pasajeros);
    alert("Pasajeros registrados con éxito.");

    // Cerrar el modal después de registrar los pasajeros
    closeModalPasajeros();
}

// Cerrar el modal si se hace clic fuera de él
window.onclick = function(event) {
    const modal = document.getElementById("pasajerosModal");
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
