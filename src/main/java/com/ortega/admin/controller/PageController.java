package com.ortega.admin.controller;

import com.ortega.admin.service.EmpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Controller
@AllArgsConstructor
public class PageController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private EmpService empService;

    @GetMapping("admin/reservas")
    public String reservas(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("empleado", userDetails);
        return "reserves/reservas";
    }

    @GetMapping("admin/empleados")
    public String empleados(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("empleado", userDetails);
        return "employees/empleados";
    }

    @GetMapping("admin/empleados/viewEmpleado")
    public String empleados_register(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("empleado", userDetails);
        return "employees/verEmpleado";
    }

    @GetMapping("admin/reportes")
    public String reportes(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("empleado", userDetails);
        return "reportes.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/admin/reservas/registrarreservas")
    public String registroreserva(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("empleado", userDetails);
        return "reserves/registroreserva.html";
    }

    @GetMapping("/admin/reservas/agregarpasajeros")
    public String agregarpasajeros(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("empleado", userDetails);
        return "reserves/pasajeros.html";
    }
}
