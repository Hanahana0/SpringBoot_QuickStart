package qlinx.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateAccessToken(String username, String role) {
        return generateToken(username, role, 15 * 60 * 1000); // 15분
    }

    public String generateRefreshToken(String username, String role) {
        return generateToken(username, role, 7 * 24 * 60 * 60 * 1000); // 7일
    }

    // JWT 생성
    private String generateToken(String username, String role, long validityInMilliseconds) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role); // 역할 추가
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey)
                .compact();
    }

    // 역할 추출 메소드 추가
    public String extractRole(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("role", String.class);
    }

    // JWT에서 사용자 이름 추출
    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
