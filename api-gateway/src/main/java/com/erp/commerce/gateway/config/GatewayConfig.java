package com.erp.commerce.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Auth Service Routes
                .route("auth-service", r -> r.path("/api/v1/auth/**", "/api/v1/users/**", "/api/v1/roles/**", "/api/v1/branches/**")
                        .uri("http://localhost:8081"))
                // Product Service Routes
                .route("product-service", r -> r.path("/api/v1/products/**")
                        .uri("http://localhost:8082"))
                // Sales Service Routes
                .route("sales-service", r -> r.path("/api/v1/sales/**", "/api/v1/customers/**")
                        .uri("http://localhost:8083"))
                // Accounting Service Routes
                .route("accounting-service", r -> r.path("/api/v1/journal-entries/**", "/api/v1/accounts/**")
                        .uri("http://localhost:8084"))
                // Swagger UI
                .route("swagger-ui", r -> r.path("/swagger-ui.html", "/v3/api-docs/**", "/swagger-resources/**")
                        .uri("http://localhost:8081"))
                .build();
    }
}
