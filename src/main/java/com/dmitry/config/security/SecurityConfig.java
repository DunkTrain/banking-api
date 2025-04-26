package com.dmitry.config.security;

import com.dmitry.config.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Конфигурация безопасности приложения на основе Spring Security.
 * <p>
 * Настройки:
 * <ul>
 *     <li><b>Отключение CSRF-защиты</b> — не требуется для REST API (стейтлес взаимодействие).</li>
 *     <li><b>Разрешение без аутентификации</b> для эндпоинта {@code /api/auth/login} — чтобы пользователи могли получить JWT токен.</li>
 *     <li><b>Требование аутентификации</b> для всех остальных запросов — доступ только с валидным токеном.</li>
 *     <li><b>Добавление фильтра</b> {@link JwtAuthenticationFilter} — проверяет наличие и валидность JWT в каждом запросе.</li>
 * </ul>
 * <p>
 * Порядок фильтров:
 * <ul>
 *     <li>{@code JwtAuthenticationFilter} добавляется <b>перед</b> стандартным {@code UsernamePasswordAuthenticationFilter}.</li>
 * </ul>
 * Это позволяет обрабатывать JWT токены вместо стандартной формы аутентификации Spring Security.
 */

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
