package DrinKings.backend.global.utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

    private static final String SECRET = "calamardoeluapoedftresxcfresdcfresxcfredxcfrdcfredcftrdftredcftrdgtrd";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    // private static final SecretKey KEY = generateRandomKey();

    public static SecretKey generateRandomKey() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            keyGenerator.init(256); // Secure 256-bit key
            return keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Error generating random key", e);
        }
    }

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                // .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 *
                // 30)) // 10 hours expiration
                // no expiration
                .setExpiration(null)
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Claims extractClaims(String token) {
        System.out.println("aqui llegamos");
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public static boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public static boolean validateTokenUsername(String token, String username) {
        Claims claims = extractClaims(token);
        return username.equals(claims.getSubject()) && !isTokenExpired(token);
    }

}