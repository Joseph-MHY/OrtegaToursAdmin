package com.ortega.admin.controller;

import com.ortega.admin.service.EmpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/admin/reservas")
    public String reservas(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("empleado", userDetails);
        model.addAttribute("rol", userDetails.getAuthorities().toString());
        return "reserves/reservas";
    }

    @GetMapping("/admin/empleados")
    public String empleados(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("empleado", userDetails);
        model.addAttribute("rol", userDetails.getAuthorities().toString());
        return "employees/empleados";
    }

    @GetMapping("admin/empleados/viewEmpleado/{id}")
    public String empleados_register(Model model, Principal principal, @PathVariable(value = "id") Integer id) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("empleado", userDetails);
        model.addAttribute("rol", userDetails.getAuthorities().toString());
        return "employees/verEmpleado";
    }

    @GetMapping("admin/reportes")
    public String reportes(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("empleado", userDetails);
        model.addAttribute("rol", userDetails.getAuthorities().toString());
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
        model.addAttribute("rol", userDetails.getAuthorities().toString());
        return "reserves/registroreserva.html";
    }

    @GetMapping("/admin/reservas/modificar/{id}")
    public String modificacionreserva(Model model, Principal principal, @PathVariable("id") Integer id) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("empleado", userDetails);
        model.addAttribute("rol", userDetails.getAuthorities().toString());
        return "reserves/modificarreserva.html";
    }
}
