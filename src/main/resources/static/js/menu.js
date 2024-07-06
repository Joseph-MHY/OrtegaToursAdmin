const cloud = document.getElementById("cloud");
const barraLateral = document.querySelector(".barra-lateral");
const spans = document.querySelectorAll("span");
const palanca = document.querySelector(".switch");
const circulo = document.querySelector(".circulo");
const menu = document.querySelector(".menu");

// Función para actualizar el estado del modo oscuro basado en sessionStorage
function actualizarModoOscuro() {
    let body = document.body;
    let modoOscuro = sessionStorage.getItem("modoOscuro") === "true";

    if (modoOscuro) {
        body.classList.add("dark-mode");
        circulo.classList.add("prendido");
    } else {
        body.classList.remove("dark-mode");
        circulo.classList.remove("prendido");
    }
}

// Función para actualizar el estado de la barra lateral basado en sessionStorage
function actualizarEstadoBarraLateral() {
    let barraMinimizada = sessionStorage.getItem("barraMinimizada") === "true";

    if (barraMinimizada) {
        barraLateral.classList.add("mini-barra-lateral");
        spans.forEach((span) => {
            span.classList.add("oculto");
        });
    } else {
        barraLateral.classList.remove("mini-barra-lateral");
        spans.forEach((span) => {
            span.classList.remove("oculto");
        });
    }
}

function actualizarSombreadoMenu() {
    let urlActual = window.location.href;

    // Selecciona los botones del menú
    let reservasBtn = document.getElementById("reservas");
    let empleadosBtn= document.getElementById("empleados");
    let reportesBtn = document.getElementById("reportes")

    // Quitar la clase sombreada de todos
    reservasBtn.classList.remove("sombreado");
    empleadosBtn.classList.remove("sombreado");
    reportesBtn.classList.remove("sombreado")

    // Aplicar la clase sombreada basado en la URL actual
    if (urlActual.includes("/reservas")) {
        reservasBtn.classList.add("sombreado");
    } else if (urlActual.includes("/empleados")) {
        empleadosBtn.classList.add("sombreado");
    } else if (urlActual.includes("/reportes")) {
        reportesBtn.classList.add("sombreado")
    }
}

// Llamar a las funciones para establecer el estado inicial cuando la página se carga
document.addEventListener("DOMContentLoaded", () => {
    actualizarSombreadoMenu();
    actualizarModoOscuro();
    actualizarEstadoBarraLateral();
});

palanca.addEventListener("click", () => {
    let body = document.body;
    body.classList.toggle("dark-mode");
    circulo.classList.toggle("prendido");

    // Verificar el estado y almacenar en sessionStorage
    let modoOscuro = body.classList.contains("dark-mode");
    sessionStorage.setItem("modoOscuro", modoOscuro);

    // Imprimir el estado
    console.log(!modoOscuro);  // false si está en modo oscuro, true si no está en modo oscuro
});

cloud.addEventListener("click", () => {
    barraLateral.classList.toggle("mini-barra-lateral");
    spans.forEach((span) => {
        span.classList.toggle("oculto");
    });

    // Verificar el estado y almacenar en sessionStorage
    let barraMinimizada = barraLateral.classList.contains("mini-barra-lateral");
    sessionStorage.setItem("barraMinimizada", barraMinimizada);

    // Imprimir el estado
    console.log(barraMinimizada);
});

menu.addEventListener("click", () => {
    barraLateral.classList.toggle("max-barra-lateral");
    if (barraLateral.classList.contains("max-barra-lateral")) {
        menu.children[0].style.display = "none";
        menu.children[1].style.display = "block";
    } else {
        menu.children[0].style.display = "block";
        menu.children[1].style.display = "none";
    }
    if (window.innerWidth <= 320) {
        barraLateral.classList.add("mini-barra-lateral");
        spans.forEach((span) => {
            span.classList.add("oculto");
        });
    }
});
