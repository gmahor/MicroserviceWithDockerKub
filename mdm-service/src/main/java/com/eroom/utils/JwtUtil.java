package com.eroom.utils;


import com.eroom.configs.EnvConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Slf4j
@Component
public class JwtUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    private final transient EnvConfiguration envConfiguration;

    @Autowired
    public JwtUtil(EnvConfiguration envConfiguration) {
        this.envConfiguration = envConfiguration;
    }

    public Claims getAllClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts
                    .parser()
                    .setSigningKey(envConfiguration.getJwtSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Error while getting all claims - ", e);
        }
        return claims;
    }

}