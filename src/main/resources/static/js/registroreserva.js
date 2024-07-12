// Función para abrir la ventana modal de costos adicionales
function openModalCostosAdicionales() {
    const modal = document.getElementById("costosAdicionalesModal");
    modal.style.display = "block";
}

// Función para cerrar la ventana modal de costos adicionales
function closeModalCostosAdicionales() {
    const modal = document.getElementById("costosAdicionalesModal");
    modal.style.display = "none";
}

// Función para agregar un costo adicional a la tabla
function agregarCosto() {
    const descripcion = document.getElementById("descripcion").value;
    const costo = document.getElementById("costo").value;

    if (descripcion && costo) {
        const table = document.getElementById("tablaCostos").getElementsByTagName('tbody')[0];
        const newRow = table.insertRow();

        const cell1 = newRow.insertCell(0);
        const cell2 = newRow.insertCell(1);
        const cell3 = newRow.insertCell(2);

        cell1.innerHTML = descripcion;
        cell2.innerHTML = costo;
        cell3.innerHTML = '<button onclick="eliminarCosto(this)">Eliminar</button>';

        document.getElementById("descripcion").value = "";
        document.getElementById("costo").value = "";
    } else {
        alert("Por favor, complete ambos campos antes de agregar un costo.");
    }
}

// Función para eliminar un costo adicional de la tabla
function eliminarCosto(btn) {
    const row = btn.parentNode.parentNode;
    row.parentNode.removeChild(row);
}

// Función para registrar todos los costos adicionales
function registrarCostos() {
    const table = document.getElementById("tablaCostos").getElementsByTagName('tbody')[0];
    const rows = table.getElementsByTagName('tr');
    const costos = [];

    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName('td');
        const descripcion = cells[0].innerText;
        const costo = cells[1].innerText;
        costos.push({ descripcion, costo });
    }

    // Lógica para registrar todos los costos adicionales (por ejemplo, enviarlos al servidor)
    console.log("Costos registrados:", costos);
    alert("Costos adicionales registrados con éxito.");

    // Cerrar el modal después de registrar los costos
    closeModalCostosAdicionales();
}

// Cerrar el modal si se hace clic fuera de él
window.onclick = function(event) {
    const modal = document.getElementById("costosAdicionalesModal");
    if (event.target == modal) {
        modal.style.display = "none";
    }
}