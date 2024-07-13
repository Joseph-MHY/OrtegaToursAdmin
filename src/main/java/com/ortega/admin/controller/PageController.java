package com.ortega.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class PageController {

    // Pagina inicial
    @GetMapping("/reservas")
    public String reservas() {
        return "reservas.html";
    }

    // Pagina de empleados
    @GetMapping("/empleados")
    public String empleados() {
        return "employees/empleados";
    }

    @GetMapping("/empleados/viewEmpleado")
    public String empleados_register() {
        return "employees/verEmpleado";
    }

    @GetMapping("reportes")
    public String reportes() {
        return "reportes.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/registrarreservas")
    public String registroreserva() {
        return "registroreserva.html";
    }

    @GetMapping("/agregarpasajeros")
    public String agregarpasajeros() {
        return "pasajeros.html";
    }
}
