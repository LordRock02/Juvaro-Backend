package com.backend.juvaro.security;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor // Usa Lombok para inyectar las dependencias en el constructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        // Creamos una instancia del filtro de autenticación para nuestro proyecto
        com.backend.integraservicios.security.JWTAuthenticationFilter jwtAuthenticationFilter = new com.backend.integraservicios.security.JWTAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login"); // Endpoint para el login

        return http
                // Deshabilitamos la protección CSRF ya que usaremos tokens JWT (común en APIs REST)
                .csrf(AbstractHttpConfigurer::disable)
                // Usamos lambdas para configurar las reglas de autorización, reemplazando .antMatchers()
                .authorizeHttpRequests(auth -> auth
                        // Permitimos el acceso público al endpoint de registro y login
                        .requestMatchers("/auth/register", "/auth/login").permitAll()
                        // Para cualquier otra petición, el usuario debe estar autenticado
                        .anyRequest().authenticated()
                )
                // Configuramos la gestión de sesiones para que sea STATELESS (sin estado)
                // Spring Security no creará ni usará sesiones HTTP.
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Añadimos nuestros filtros JWT personalizados a la cadena de filtros de Spring Security
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Define el bean del AuthenticationManager.
     * Le dice a Spring Security cómo encontrar usuarios (UserDetailsService)
     * y cómo verificar sus contraseñas (PasswordEncoder).
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }

    /**
     * Define el bean para encriptar contraseñas.
     * Siempre debe estar disponible en el contexto de Spring.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}