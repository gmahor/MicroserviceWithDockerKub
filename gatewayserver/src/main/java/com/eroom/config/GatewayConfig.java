package com.eroom.config;

import com.eroom.filters.AuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter authenticationFilter;


    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("mdm-service-route", r -> r.path(
                                "/api/v1/user/**",
                                "/api/v1/role/**",
                                "/api/v1/division/**",
                                "/api/v1/documentType/**",
                                "/api/v1/documentAssignment/**",
                                "/api/v1/geographicalTerritory/**",
                                "/api/v1/employee/**",
                                "/api/v1/menuAndPage/**",
                                "/api/v1/supplyVertical/**",
                                "/api/v1/organisation/**",
                                "/api/v1/projectManager/**"
                        )
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://mdm-service"))
                .route("user-auth-route", r -> r.path(
                                "/api/v1/auth/**"
                        )
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://user-auth"))
//                .route("case-utility-route", r -> r.path(
//                                "/api/v1/caseDetails/**",
//                                "/api/v1/approvalLevel/**",
//                                "/api/v1/documentAssignmentApproval/**",
//                                "/api/v1/icsDocument/**",
//                                "/api/v1/dossier/**"
//                        )
//                        .filters(f -> f.filter(authenticationFilter))
//                        .uri("lb://case-utility"))
                .build();
    }
}