package com.ihechi.jobTrek.security;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import java.security.Key;

import java.util.Date;

@Component
public class JwtUtil {
  @Value("${jwt.secret}")
  private String secretKey;
  @Value("${jwt.expiration}")
  private long expirationTime;

  private Key key;

  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  /**
   * Generate JWT token for a given username (email).
   */
  public String generateToken(String email) {
    return Jwts.builder()
        .setSubject(email)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  /**
   * Extract username (email) from JWT token.
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extract any claim from the token.
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Validate JWT token.
   */
  public boolean validateToken(String token, String userEmail) {
    final String extractedEmail = extractUsername(token);
    return (extractedEmail.equals(userEmail) && !isTokenExpired(token));
  }

  /**
   * Check if the token is expired.
   */
  private boolean isTokenExpired(String token) {
    return extractClaim(token, Claims::getExpiration).before(new Date());
  }

  /**
   * Extract all claims from the JWT token.
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
}
