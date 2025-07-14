package com.membership.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JWTUtils {
    private static String signingKey = "hinna";
    private static Long expire = 43200000L;

    /**
     * generate JWT
     */
    public static String createJWT(Map<String, Object> claims) {
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .addClaims(claims)
                .setExpiration(Date.from(Instant.now().plusSeconds(expire)))
                .compact();
        return jwt;

    }

    public static Claims parseJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(jwt).getBody();
        return claims;

    }



}
