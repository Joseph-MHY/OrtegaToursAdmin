import {BASE_URL} from '../BASE_URL.js';

let montoTotal = 0;

function updateCosts(total) {
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

        const totalCost = precioBase + precioFijo + total;

        if (isNaN(totalCost)) {
            console.error('totalCost no es un número');
            costeTotalInput.value = '0.00';
        } else {
            costeTotalInput.value = totalCost.toFixed(2);
        }
    } else {
        costeBaseInput.value = '0.00';
        costeFijoInput.value = '0.00';
        costeTotalInput.value = '0.00';
    }
}

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
            paqueteSelect.addEventListener('change', function () {
                updateCosts(montoTotal);
            });

        } catch (error) {
            console.error('Error al cargar los datos:', error);
        }
    }

    await loadCombos();
});

// Función para abrir la ventana modal de costos adicionales
window.openModalCostosAdicionales = function () {
    const modal = document.getElementById("costosAdicionalesModal");
    modal.style.display = "block";
};

// Función para cerrar la ventana modal de costos adicionales
window.closeModalCostosAdicionales = function () {
    const modal = document.getElementById("costosAdicionalesModal");
    modal.style.display = "none";
}

// Función para agregar un costo adicional a la tabla
window.agregarCosto = function () {
    const descripcion = document.getElementById("descripcion").value;
    const costo = parseFloat(document.getElementById("costo").value) || 0;

    if (descripcion && costo) {
        const table = document.getElementById("tablaCostos").getElementsByTagName('tbody')[0];
        const newRow = table.insertRow();

        const cell1 = newRow.insertCell(0);
        const cell2 = newRow.insertCell(1);
        const cell3 = newRow.insertCell(2);

        cell1.innerHTML = descripcion;
        cell2.innerHTML = costo.toFixed(2);
        cell3.innerHTML = '<ion-icon name="trash-outline" onclick="eliminarCosto(this)" style="cursor: pointer; color: red;"></ion-icon>'; // Usando Ionicons

        document.getElementById("descripcion").value = "";
        document.getElementById("costo").value = "";
        // Sumar el nuevo costo al montoTotal
        montoTotal += costo;
        updateCosts(montoTotal);
    } else {
        alert("Por favor, complete ambos campos antes de agregar un costo.");
    }
}

// Función para eliminar un costo adicional de la tabla
window.eliminarCosto = function (icon) {
    const row = icon.parentNode.parentNode;
    const costo = parseFloat(row.getElementsByTagName('td')[1].innerText) || 0;

    // Restar el costo eliminado del montoTotal
    montoTotal -= costo;
    row.parentNode.removeChild(row);
    // Actualizar los costos
    updateCosts(montoTotal);
}

// Cerrar el modal si se hace clic fuera de él
window.onclick = function (event) {
    const modal = document.getElementById("costosAdicionalesModal");
    if (event.target == modal) {
        modal.style.display = "none";
    }
}