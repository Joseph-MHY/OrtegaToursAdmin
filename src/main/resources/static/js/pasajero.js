// Función para abrir la ventana modal de pasajeros
function openModalPasajeros() {
    const modal = document.getElementById("pasajerosModal");
    modal.style.display = "block";
}

// Función para cerrar la ventana modal de pasajeros
function closeModalPasajeros() {
    const modal = document.getElementById("pasajerosModal");
    modal.style.display = "none";
}

// Función para agregar un pasajero a la tabla
function agregarPasajero() {
    const nombre = document.getElementById("nombrePasajero").value;
    const apellido = document.getElementById("apellidoPasajero").value;
    const nacionalidad = document.getElementById("nacionalidadPasajero").value;
    const tipoDocumento = document.getElementById("tipoDocumentoPasajero").value;
    const numeroDocumento = document.getElementById("numeroDocumentoPasajero").value;

    if (nombre && apellido && nacionalidad && tipoDocumento && numeroDocumento) {
        const table = document.getElementById("tablaPasajeros").getElementsByTagName('tbody')[0];
        const newRow = table.insertRow();

        const cell1 = newRow.insertCell(0);
        const cell2 = newRow.insertCell(1);
        const cell3 = newRow.insertCell(2);
        const cell4 = newRow.insertCell(3);
        const cell5 = newRow.insertCell(4);
        const cell6 = newRow.insertCell(5);

        cell1.innerHTML = nombre;
        cell2.innerHTML = apellido;
        cell3.innerHTML = nacionalidad;
        cell4.innerHTML = tipoDocumento;
        cell5.innerHTML = numeroDocumento;
        cell6.innerHTML = '<ion-icon name="trash-outline" onclick="eliminarPasajero(this)" style="cursor: pointer; color: red;"></ion-icon>'; // Usando Ionicons

        document.getElementById("nombrePasajero").value = "";
        document.getElementById("apellidoPasajero").value = "";
        document.getElementById("nacionalidadPasajero").value = "";
        document.getElementById("tipoDocumentoPasajero").value = "";
        document.getElementById("numeroDocumentoPasajero").value = "";
    } else {
        alert("Por favor, complete todos los campos antes de agregar un pasajero.");
    }
}

// Función para eliminar un pasajero de la tabla
function eliminarPasajero(icon) {
    const row = icon.parentNode.parentNode;
    row.parentNode.removeChild(row);
}

// Función para registrar todos los pasajeros
function registrarPasajeros() {
    const table = document.getElementById("tablaPasajeros").getElementsByTagName('tbody')[0];
    const rows = table.getElementsByTagName('tr');
    const pasajeros = [];

    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName('td');
        const nombre = cells[0].innerText;
        const apellido = cells[1].innerText;
        const nacionalidad = cells[2].innerText;
        const tipoDocumento = cells[3].innerText;
        const numeroDocumento = cells[4].innerText;
        pasajeros.push({ nombre, apellido, nacionalidad, tipoDocumento, numeroDocumento });
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
