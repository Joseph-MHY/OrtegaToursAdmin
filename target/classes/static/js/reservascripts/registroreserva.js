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
// Función para agregar un costo adicional a la tabla
function agregarCosto() {
    const descripcion = document.getElementById("descripcion").value.trim();
    const costo = document.getElementById("costo").value.trim();

    // Validar que ambos campos estén llenos
    if (!descripcion || !costo) {
        alert("Por favor, complete ambos campos antes de agregar un costo.");
        return;
    }

    // Validar que el costo sea un número decimal válido
    const costoDecimal = parseFloat(costo);
    if (isNaN(costoDecimal) || costoDecimal <= 0) {
        alert("Por favor, ingrese un monto válido.");
        return;
    }

    // Agregar el costo a la tabla
    const table = document.getElementById("tablaCostos").getElementsByTagName('tbody')[0];
    const newRow = table.insertRow();

    const cell1 = newRow.insertCell(0);
    const cell2 = newRow.insertCell(1);
    const cell3 = newRow.insertCell(2);

    cell1.innerHTML = descripcion;
    cell2.innerHTML = costoDecimal.toFixed(2); // Mostrar el costo con dos decimales
    cell3.innerHTML = '<ion-icon name="trash-outline" onclick="eliminarCosto(this)" style="cursor: pointer; color: red;"></ion-icon>'; // Usando Ionicons

    // Limpiar los campos
    document.getElementById("descripcion").value = "";
    document.getElementById("costo").value = "";
}

// Función para eliminar un costo adicional de la tabla
function eliminarCosto(icon) {
    const row = icon.parentNode.parentNode;
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
