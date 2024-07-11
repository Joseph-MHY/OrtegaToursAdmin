// empleados.js
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
