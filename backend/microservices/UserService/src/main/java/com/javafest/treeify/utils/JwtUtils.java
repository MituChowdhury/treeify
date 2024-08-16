package com.javafest.treeify.utils;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

public class JwtUtils {

    private static final String SECRET_KEY_ENV = "blingbangbangbornblingbangbangbornblingbangbangbornblingbangbangbornblingbangbangbornblingbangbangborn";

    // Load the secret key from the environment variable
    private static final SecretKey SECRET_KEY = loadSecretKey();

    private static SecretKey loadSecretKey() {
        String base64Key = System.getenv(SECRET_KEY_ENV);
        if (base64Key == null || base64Key.isEmpty()) {
            throw new IllegalStateException("Secret key environment variable is not set.");
        }
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String id) {
        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(new Date())
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateToken(String token) {
        try {
            JwtParser jwtParser = Jwts.parserBuilder()
                                      .setSigningKey(SECRET_KEY)
                                      .build();
            return jwtParser.parseClaimsJws(token)
                            .getBody()
                            .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}
