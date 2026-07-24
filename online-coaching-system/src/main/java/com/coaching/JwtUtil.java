package com.coaching;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET ="mySecretKeymySecretKeymySecretKey123456";

    private final long EXPIRATION = 1000 * 60 * 60 * 24;

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

//    // Extract all claims
//    public Claims extractClaims(String token) {
//
//        return Jwts.parserBuilder()
//                .setSigningKey(getKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    // Extract username
//    public String extractUsername(String token) {
//        return extractClaims(token).getSubject();
//    }
//
//    // Extract role
//    public String extractRole(String token) {
//        return extractClaims(token).get("role", String.class);
//    }

    // Generate JWT
	public String generateToken(String email, String role) {

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getKey())
                .compact();
    }

    public String getEmailFromToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    
    public String getRoleFromToken(String token) {

    	return  Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }
    
    // Validate JWT
    public boolean validateToken(String token) {

        try {

            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);

            return true;

        } catch (ExpiredJwtException e) {

            System.out.println("Token Expired");
            return false;

        } catch (Exception e) {

            return false;
        }
    }

//    // Check expiry
//    private boolean isTokenExpired(String token) {
//
//        return extractClaims(token)
//                .getExpiration()
//                .before(new Date());
//    }
}
