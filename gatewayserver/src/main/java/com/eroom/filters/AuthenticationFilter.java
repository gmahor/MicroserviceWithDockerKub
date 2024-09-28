package com.eroom.filters;

import com.eroom.config.RouterValidator;
import com.eroom.dtos.JWTUserDetails;
import com.eroom.utils.HeaderUtil;
import com.eroom.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationFilter implements GatewayFilter {

    private final RouterValidator routerValidator;

    private final JwtUtil jwtUtil;

    private ModelMapper modelMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (request.getMethod() == HttpMethod.OPTIONS) {
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            return exchange.getResponse().setComplete();
        }
        if (routerValidator.isSecured().test(request)) {
            if (this.isAuthMissing(request)) {
                log.info("Authorization header is missing in request");
                return this.onError(exchange);
            }
            final String token = this.getAuthHeader(request);
            if (jwtUtil.validateToken(token)) {
                final Claims claims = jwtUtil.getAllClaimsFromToken(token);
                if (claims != null) {
                    this.populateRequestWithHeaders(exchange, claims, token);
                } else {
                    log.info("Claim not found");
                    return this.onError(exchange);
                }
            } else {
                log.info("Invalid token");
                return this.onError(exchange);
            }

        }
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().addAll(HeaderUtil.getSecurityHeaders());
        return chain.filter(exchange);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, Claims claims, String token) {
        JWTUserDetails userDetails = modelMapper.map(claims.get("userDetails"), JWTUserDetails.class);
        exchange.getRequest().mutate()
                .header("id", String.valueOf(userDetails.getUserId()))
                .header("username", String.valueOf(userDetails.getUsername()))
                .header("token", token)
                .header("roles", userDetails.getRoles())
                .build();
    }
}
