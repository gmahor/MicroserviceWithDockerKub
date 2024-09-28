package com.eroom.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwtSecret}")
    private String jwtSecret;


    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts
                    .parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            if (claims == null) {
                return false;
            } else {
                return claims.getExpiration().after(new Date());
            }
        } catch (Exception e) {
            log.error("Error while validating token - ", e);
            return false;
        }
    }

    public Claims getAllClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts
                    .parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Exception in token verification - ", e);
        }
        return claims;
    }

}
