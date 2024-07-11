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
        return "empleados.html";
    }

    @GetMapping("reportes")
    public String reportes() {
        return "reportes.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }
}
