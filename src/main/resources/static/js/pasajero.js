document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("modal");
    const closeModalBtn = document.querySelector(".close");

    window.openModal = function() {
        modal.style.display = "block";
    }

    window.closeModal = function() {
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
});

function redirectToRegistrarpasajeros() {
    window.location.href = '/admin/agregarpasajeros';  // Esta URL debe coincidir con la ruta definida en tu controlador
}
