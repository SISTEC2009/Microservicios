package com.whasheng.Reservas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SesionInterceptor())
                .addPathPatterns(
                        "/menus/dashboard",
                        "/productos/**",
                        "/Administrador/categoria/**",
                        "/clientes/**",
                        "/pedidos/gestion",
                        "/pedidos/editar/**",
                        "/pedidos/rechazar/**",
                        "/reservas/listado"
                );
    }
}