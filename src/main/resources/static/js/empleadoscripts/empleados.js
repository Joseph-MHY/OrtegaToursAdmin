const EMPLEADOS_URL = 'http://localhost:8080';
const searchBtn = document.getElementById('searchBtn');
const tablaEmpleados = document.getElementById('tablaEmpleados');
const modal = document.getElementById("modal");
const selectOrden = document.getElementById('select-orden');

toastr.options = {
    "closeButton": false,
    "debug": false,
    "newestOnTop": false,
    "progressBar": true,
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

document.addEventListener("DOMContentLoaded", () => {
    window.openModal = function () {
        modal.style.display = "block";
    }

    window.closeModal = function () {
        modal.style.display = "none";
    }

    window.onclick = function (event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }

    async function mostrarEmpleados() {
        try {
            const response = await axios.get(EMPLEADOS_URL + '/actions/empleados');
            const empleados = response.data;
            mostrarTabla(empleados);
        } catch (error) {
            console.error('Error al obtener los empleados:', error);
        }
    }

    function mostrarTabla(empleados) {
        tablaEmpleados.innerHTML = '';
        if (empleados.length === 0) {
            mostrarMensajeNoRegistros();
        } else {
            empleados.forEach((empleado, index) => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td style="text-align: center;">${index + 1}</td>
                    <td>${empleado.nombreApellidos}</td>
                    <td style="text-align: center;">${empleado.numDocumento}</td>
                    <td style="text-align: center;">${empleado.puesto}</td>
                    <td style="text-align: center;">${empleado.telefono}</td>
                    <td style="text-align: center;">S/.${empleado.salario}</td>
                    <td style="text-align: center;">${empleado.estado}</td>
                    <td class="view-icon"><a href="#" class="ver-empleado" data-id="${empleado.id}"><ion-icon name="eye"></ion-icon></a></td>
                `;
                tablaEmpleados.appendChild(row);
            });

            // Añadir evento de clic a los íconos de visualización
            const linksVerEmpleado = document.querySelectorAll('.ver-empleado');
            linksVerEmpleado.forEach(link => {
                link.addEventListener('click', function (event) {
                    event.preventDefault();
                    const empleadoId = this.getAttribute('data-id');
                    verEmpleado(empleadoId);
                });
            });
        }
    }

    async function buscarEmpleado() {
        const searchInput = document.getElementById('searchInput');
        const searchTerm = searchInput.value.trim().toLowerCase();

        try {
            const response = await axios.get(EMPLEADOS_URL + '/actions/empleados');
            const empleados = response.data;

            // Filtrar empleados según el término de búsqueda
            const empleadosFiltrados = empleados.filter(empleado =>
                empleado.nombreApellidos.toLowerCase().includes(searchTerm) ||
                empleado.numDocumento.includes(searchTerm)
            );

            mostrarTabla(empleadosFiltrados);
        } catch (error) {
            console.error('Error al obtener los empleados:', error);
        }
    }

    searchBtn.addEventListener('click', async () => {
        await buscarEmpleado();
    });

    document.getElementById('registroEmpleadoForm').addEventListener('submit', async function (event) {
        event.preventDefault();

        // Obtener los valores de cada campo del formulario
        const nombreApellidos = document.getElementById('nombre').value.trim();
        const direccion = document.getElementById('direccion').value.trim();
        const idTipoDocumento = document.getElementById('tipo-documento').value.trim();
        const numDocumento = document.getElementById('dni').value.trim();
        const correo = document.getElementById('correo').value.trim();
        const password = document.getElementById('contrasena').value.trim();
        const fechaNac = document.getElementById('fecha-nacimiento').value.trim();
        const telefono = document.getElementById('telefono').value.trim();
        const contactoEmergencia = document.getElementById('contacto-emergencia').value.trim();
        const idRol = document.getElementById('puesto').value.trim();
        const horarioTrabajo = document.getElementById('horario-trabajo').value.trim();
        const cuentaBancaria = document.getElementById('cuenta-bancaria').value.trim();
        const idTipoContrato = document.getElementById('tipo-contrato').value.trim();
        const salario = parseFloat(document.getElementById('salario').value.trim());
        const observaciones = document.getElementById('notas-adicionales').value.trim();

        // Crear el objeto de datos para enviar
        const data = {
            nombreApellidos,
            direccion,
            idTipoDocumento: idTipoDocumento ? parseInt(idTipoDocumento) : null,
            numDocumento,
            correo,
            password,
            fechaNac,
            telefono,
            contactoEmergencia: contactoEmergencia || null,
            idRol: idRol ? parseInt(idRol) : null,
            idTipoContrato: idTipoContrato ? parseInt(idTipoContrato) : null,
            horarioTrabajo,
            cuentaBancaria: cuentaBancaria || null,
            salario: isNaN(salario) ? null : salario,
            observaciones: observaciones || null
        };

        // Log para verificar que los datos están formateados correctamente
        console.log("Data en formato JSON:", JSON.stringify(data));

        // Enviar los datos al servidor
        axios.post(`${EMPLEADOS_URL}/actions/empleados/crear`, data, {
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(res => {
                toastr["success"]("Empleado registrado exitosamente");
            })
            .catch(error => {
                console.error('Error al registrar el empleado:', error);
                toastr["error"]("Error al registrar Empleado");
            });
    });

    function mostrarMensajeNoRegistros() {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td colspan="8" style="text-align: center;">No hay registros disponibles</td>
        `;
        tablaEmpleados.appendChild(row);
    }

    function verEmpleado(id) {
        console.log('ID del empleado:', id);
    }

    mostrarEmpleados();

    function aplicarOrden(empleados) {
        const opcion = selectOrden.value;
        let empleadosOrdenados;

        switch (opcion) {
            case 'nombre-az':
                empleadosOrdenados = empleados.sort((a, b) => a.nombreApellidos.localeCompare(b.nombreApellidos));
                break;
            case 'nombre-za':
                empleadosOrdenados = empleados.sort((a, b) => b.nombreApellidos.localeCompare(a.nombreApellidos));
                break;
            case 'activos':
                empleadosOrdenados = empleados.filter(e => e.estado === 'Activo');
                break;
            case 'inactivos':
                empleadosOrdenados = empleados.filter(e => e.estado === 'Inactivo');
                break;
            default:
                empleadosOrdenados = empleados; // Sin ordenar
        }

        mostrarTabla(empleadosOrdenados);
    }

    selectOrden.addEventListener('change', async () => {
        try {
            const response = await axios.get(EMPLEADOS_URL + '/actions/empleados');
            const empleados = response.data;
            aplicarOrden(empleados); // Aplicar orden según la selección
        } catch (error) {
            console.error('Error al obtener los empleados:', error);
        }
    });
});


