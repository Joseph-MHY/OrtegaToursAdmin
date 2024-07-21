function getIdFromUrl() {
    const path = window.location.pathname;
    const id = path.split('/').pop(); // Obtiene el último segmento de la URL
    return id;
}

function fetchEmployeeDetails(id) {
    const url = `http://localhost:8080/actions/empleados/${id}`;
    axios.get(url)
        .then(response => {
            const data = response.data;
            console.log(response.data)
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

// Obtener el ID de la URL y hacer la solicitud
const id = getIdFromUrl();
if (id) {
    fetchEmployeeDetails(id);
} else {
    console.error('ID no encontrado en la URL.');
}