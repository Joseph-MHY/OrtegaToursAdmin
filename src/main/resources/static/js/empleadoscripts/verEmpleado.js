const BASE_URL = "http://localhost:8080";

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

function getIdFromUrl() {
    const path = window.location.pathname;
    const id = path.split('/').pop(); // Obtiene el último segmento de la URL
    console.log(id);
    return id;
}

function fetchEmployeeDetails(id) {
    const url = `${BASE_URL}/actions/empleados/${id}`;
    axios.get(url)
        .then(response => {
            const data = response.data;
            console.log(response.data);
            // Actualiza los campos del formulario con los datos obtenidos
            document.getElementById('nombres-apellidos').value = data.nombreApellidos || '';
            document.getElementById('direccion').value = data.direccion || '';
            document.getElementById('tipo-documento').value = data.idTipoDocumento;
            document.getElementById('num-identity').value = data.numDocumento || '';
            document.getElementById('correo').value = data.correo || '';
            document.getElementById('fecha-nacimiento').value = data.fechaNac ? data.fechaNac.split('T')[0] : '';
            document.getElementById('fecha-contratacion').value = data.fechaContratacion ? data.fechaContratacion.split('T')[0] : '';
            document.getElementById('telefono').value = data.telefono || '';
            document.getElementById('contacto-emergencia').value = data.contactoEmergencia || '';
            document.getElementById('puesto').value = data.idRol;
            document.getElementById('horario-trabajo').value = data.horarioTrabajo || '';
            document.getElementById('num-cuenta-bancaria').value = data.cuentaBancaria || '';
            document.getElementById('salario').value = data.salario || '';
            document.getElementById('tipo-contrato').value = data.idTipoContrato;
            document.getElementById('notas-adicionales').value = data.observaciones || '';
            document.getElementById('estado').value = data.estadoCuenta ? 'true' : 'false';
        })
        .catch(error => {
            console.error('Error al obtener los detalles del empleado:', error);
        });
}

function updateEmployee(id) {
    const url = `${BASE_URL}/actions/empleados/${id}`;
    const empleado = {
        nombreApellidos: document.getElementById('nombres-apellidos').value,
        direccion: document.getElementById('direccion').value,
        idTipoDocumento: parseInt(document.getElementById('tipo-documento').value),
        numDocumento: document.getElementById('num-identity').value,
        correo: document.getElementById('correo').value,
        fechaNac: document.getElementById('fecha-nacimiento').value,
        fechaContratacion: document.getElementById('fecha-contratacion').value,
        telefono: document.getElementById('telefono').value,
        contactoEmergencia: document.getElementById('contacto-emergencia').value,
        idRol: parseInt(document.getElementById('puesto').value),
        idTipoContrato: parseInt(document.getElementById('tipo-contrato').value),
        horarioTrabajo: document.getElementById('horario-trabajo').value,
        cuentaBancaria: document.getElementById('num-cuenta-bancaria').value,
        salario: parseFloat(document.getElementById('salario').value),
        observaciones: document.getElementById('notas-adicionales').value,
        estadoCuenta: document.getElementById('estado').value === 'true'
    };

    axios.put(url, empleado)
        .then(response => {
            console.log('Empleado actualizado:', response.data);
            toastr["success"](response.data);
            setTimeout(function() {
                window.location.href = `${BASE_URL}/admin/empleados`;
            }, 3500);
        })
        .catch(error => {
            console.error('Error al actualizar el empleado:', error);
            alert('Error al actualizar el empleado');
        });
}

// Obtener el ID de la URL y hacer la solicitud
const id = getIdFromUrl();
if (id) {
    fetchEmployeeDetails(id);
} else {
    console.error('ID no encontrado en la URL.');
}

// Añadir event listener al botón de modificar
document.getElementById('btnModificar').addEventListener('click', function (event) {
    event.preventDefault(); // Prevenir el comportamiento por defecto del formulario
    updateEmployee(id);
});
