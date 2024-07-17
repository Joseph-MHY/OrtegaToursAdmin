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
        String role = userDetails.getAuthorities().toString();
        System.out.println("User details: " + userDetails);

        if (role.contains("ROLE_ATTENTION")) {
            System.out.println("El usuario es atención al cliente? " + true + " " + role);
        } else {
            System.out.println("Este es el rol: " + role);
        }
        return "reserves/reservas";
    }

    @GetMapping("/admin/empleados")
    public String empleados(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("empleado", userDetails);
        model.addAttribute("rol", userDetails.getAuthorities().toString());
        return "employees/empleados";
    }

    @GetMapping("admin/empleados/viewEmpleado")
    public String empleados_register(Model model, Principal principal) {
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

    @GetMapping("/admin/reservas/agregarpasajeros")
    public String agregarpasajeros(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("empleado", userDetails);
        model.addAttribute("rol", userDetails.getAuthorities().toString());
        return "reserves/pasajeros.html";
    }
}
