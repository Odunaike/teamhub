package com.naike.teamhub.services;

import com.naike.teamhub.spring_security.AppUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final AppUserDetailsService userDetailsService;

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;
    @Value("${jwt.expiration.time}")
    private long jwtExpirationTime;

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
                .signWith( getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    public String generateRefreshToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime * 10))
                .signWith( getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }



    public UserDetails extractUserDetails(String token){
        String username = extractUsername(token);
        return userDetailsService.loadUserByUsername(username);
    }

    public boolean validateToken(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token) ;
    }

    public boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    public Date extractExpiration(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }

    public String extractUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    private Key getSigningKey(){
        byte[] array = jwtSecretKey.getBytes();
        return Keys.hmacShaKeyFor(array);
    }


}
