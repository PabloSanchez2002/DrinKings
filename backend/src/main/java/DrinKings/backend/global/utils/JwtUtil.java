package DrinKings.backend.global.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "yoursecretkeydgfkjhragkajhsrgoahsfkgjhaskfgjhawsrkgjhaskrjghaskrjghaskrjghaksrjgha"; // Keep
                                                                                                                                   // this
                                                                                                                                   // secret!

    // Generate JWT token
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                // .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 *
                // 30)) // 10 hours expiration
                // no expiration
                .setExpiration(null)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Parse JWT token
    public static String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate token
    public static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private static Date extractExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    // Validate token against user
    public static boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
}
