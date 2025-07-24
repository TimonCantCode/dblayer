package com.tasks.dblayer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers(
                "/register",
                "/login",
                "/auth/register",
                "/auth/login",
                "*.css",
                "/h2-console/**").permitAll()
                .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/", true))
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**, auth/register, auth/login")
        );
        return http.build();
    }
}
