package com.eroom.utils;


import com.eroom.configs.EnvConfiguration;
import com.eroom.constants.GenericConstants;
import com.eroom.dtos.JWTUserDetails;
import com.eroom.entities.RoleMapping;
import com.eroom.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    private final transient EnvConfiguration envConfiguration;

    @Autowired
    public JwtUtil(EnvConfiguration envConfiguration) {
        this.envConfiguration = envConfiguration;
    }

    public String getToken(User user) {
        long expirationTime = envConfiguration.getAccessTokenExpiryTime();
        Map<String, Object> claims = new HashMap<>();
        JWTUserDetails jwtUserDetails = new JWTUserDetails();
        jwtUserDetails.setUserId(user.getId());
        jwtUserDetails.setUsername(user.getUsername());
        jwtUserDetails.setRoles(user.getRoles()
                .stream()
                .map(RoleMapping::getRoleName)
                .collect(Collectors.joining(",")));
        claims.put(GenericConstants.JWT_TOKEN_CLAIM, jwtUserDetails);
        return buildToken(claims, user.getUsername(), expirationTime);
    }

    private String buildToken(Map<String, Object> claims, String username, long expirationTime) {
        try {
            Date createdDate = new Date();
            Date expirationDate = new Date(createdDate.getTime() + expirationTime);
            return Jwts
                    .builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(createdDate)
                    .setExpiration(expirationDate)
                    .signWith(SignatureAlgorithm.HS512, envConfiguration.getJwtSecret())
                    .compact();
        } catch (Exception e) {
            log.error("Error while building token - ", e);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts
                    .parser()
                    .setSigningKey(envConfiguration.getJwtSecret())
                    .parseClaimsJws(token)
                    .getBody();
            if (claims == null) {
                return false;
            } else {
                return claims.getExpiration().after(new Date());
            }
        } catch (Exception e) {
            log.error("Error while validating token - ", e);
        }
        return true;
    }

    public String doGenerateRefreshToken(Claims claims, String subject) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + envConfiguration.getAccessTokenExpiryTime()))
                .signWith(SignatureAlgorithm.HS512, envConfiguration.getJwtSecret()).compact();

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
