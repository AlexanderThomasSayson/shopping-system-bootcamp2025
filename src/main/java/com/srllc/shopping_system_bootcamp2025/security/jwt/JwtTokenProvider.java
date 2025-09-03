package com.srllc.shopping_system_bootcamp2025.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class JwtTokenProvider {

    private String jwtSecret;
    private long jwtExpirationMilliseconds;

    // Generate JWT Token
    // STEP 1: Extract the username from the authenticated user
    // STEP 2: Set issue date and expiration date
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationMilliseconds);

        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }

    // Convert and encode secret key from the Base64
    // STEP 1: Take the jwtSecrets
    // STEP 2: Decode
    // STEP 3: Use hmacShaKeyFor to generate secured Key.
    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Extract username from the bearer token
    // STEP 1: Parse JWT and verifies its signature using the signing key.
    // STEP 2: Extracts the payload (claims) from the token
    // STEP 3: Gets the subject(sub) field, which is username
    public String getUsername(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();

    }

    // Validate the token
    public boolean isValidToken(String token){
        try{
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return  true;
        } catch (IllegalArgumentException | JwtException e){
            return  false; // token is invalid or expired
        }
    }
}
