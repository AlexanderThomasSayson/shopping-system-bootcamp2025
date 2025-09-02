package com.srllc.shopping_system_bootcamp2025.security;

import com.srllc.shopping_system_bootcamp2025.security.jwt.JwtAuthenticationEntryPoint;
import com.srllc.shopping_system_bootcamp2025.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Central brain of the spring security + JWT authentication Setup
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Public Endpoints
    private static  final String[] PUBLIC_ENDPOINTS = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/api/v1/auth/register",
            "/api/v1/auth/login",
            "/api/v1/test/**"
    };

    //  ROLE_USER only endpoints
    private static final String[]   USER_ENDPOINTS ={
            "api/v1/users/**"
    };

    // ROLE_ADMIN endpoints
    private static final String[] ADMIN_ENDPOINTS = {
            "api/v1/admins/**"

    };


    // HEART OF THE CONFIGURATION
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{
        httpSecurity
                // disable CSRF TOKEN since we are using STATELESS
                .csrf(AbstractHttpConfigurer::disable)
                // disable CORS
                .cors(AbstractHttpConfigurer::disable)
                // Allow swagger and iFrame request for API DOCS.
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))

                /**
                 * RBAC - ROLE BASED ACCESS CONTROL
                 * PUBLIC_ENDPOINTS - anyone can access
                 * USER_ENDPOINTS - must have "ROLE_USER"
                 * ADMIN_ENDPOINTS - must have "ROLE_ADMIN"
                 */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(USER_ENDPOINTS).hasAnyAuthority("ROLE_CUSTOMER", "ROLE_ADMIN")
                        .requestMatchers(ADMIN_ENDPOINTS).hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )

                // handle unauthorized request using the custom entry point.
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))

                // Add our custom JWT filter before the spring boot spring filter.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    /**
     * Important for login
     * Spring security uses this to authenticate users based on:
     * CustomUserDetailsService
     * PasswordEncoder
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }


}

