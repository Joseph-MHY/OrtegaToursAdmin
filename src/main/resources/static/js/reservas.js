function redirectToRegistrarReserva() {
    window.location.href = '/admin/reservas/registrarreservas';  // Esta URL debe coincidir con la ruta definida en tu controlador
}

axios.get('http://localhost:8080/utils/estados')
    .then(response => {
        const estados = response.data;
        const estadoFilter = document.getElementById('estadoFilter');

        estados.forEach(estado => {
            const option = document.createElement('option');
            option.value = estado.id; // O el valor que necesites
            option.textContent = estado.nombreEstado; // Texto visible
            estadoFilter.appendChild(option);
        });
    })
    .catch(error => {
        console.error('Error al obtener los estados:', error);
    });

