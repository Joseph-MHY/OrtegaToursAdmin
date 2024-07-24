package com.ortega.admin.config;

import com.ortega.admin.security.CustomEmpDetailService;
import com.ortega.admin.security.CustomSuccesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CustomSuccesHandler customSuccesHandler;

    @Autowired
    CustomEmpDetailService customEmpDetailService;

    private final String[] FREE_URL = {
            "/css/**",
            "/js/**",
            "/images/**",
    };

    private final String[] ADMIN_URL = {
            "/admin/**",
            "/actions/**",
            "/report/**",
            "/utils/estados"
    };

    private final String[] ATTENTION_URL = {
            "/admin/reservas",
            "/admin/reservas/registrarreservas",
            "/admin/reservas/agregarpasajeros"
    };

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers(FREE_URL).permitAll()
                                .requestMatchers(ATTENTION_URL).hasAnyRole("ATTENTION", "ADMIN")
                                .requestMatchers(ADMIN_URL).hasRole("ADMIN")
                                .anyRequest().denyAll())
                .formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login")
                        .successHandler(customSuccesHandler).permitAll())
                .logout(form -> form.invalidateHttpSession(true).clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout").permitAll())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Autowired
    public void configure (AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customEmpDetailService).passwordEncoder(passwordEncoder());
    }
}
