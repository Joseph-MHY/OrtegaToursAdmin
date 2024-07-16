package com.ortega.admin.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CustomSuccesHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException
    {
        var authorities = authentication.getAuthorities();
        var roles = authorities.stream().map(GrantedAuthority::getAuthority).findFirst();
        System.out.println(roles);
        if (roles.orElse("").equals("ROLE_ADMIN")) {
            response.sendRedirect("/admin/reservas");
        } else if (roles.orElse("").equals("ROLE_ATTENTION")) {
            response.sendRedirect("/admin/reservas");
        } else {
            response.sendRedirect("/error");
        }
    }
}
