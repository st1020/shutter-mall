package com.st1020.shuttermall.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.st1020.shuttermall.domain.User;

public class JwtUtil {
    private static final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ="));
    private static final long EXPIRATION = 1800L; //1800秒

    public static String createToken(User user) {
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        Map<String, String> claims = new HashMap<>();
        claims.put("id", user.getId().toString());
        claims.put("password", user.getPassword());
        return Jwts.builder()
                .setHeader(header)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                .setIssuedAt(new Date())
                .signWith(key)
                .compact();
    }

    public static Claims verifyToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
