package qlinx.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // JWT 생성 메서드: 만료 시간을 Date 객체로 받도록 수정
    private String generateToken(String username, String role, Date expirationDate) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role); // 역할 추가
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate) // Date 객체로 만료 시간 설정
                .signWith(secretKey)
                .compact();
    }

    // Access Token 생성: 30초 후 만료
    public String generateAccessToken(String username, String role) {
        Date expirationDate = new Date(System.currentTimeMillis() + 30 * 1000); // 30초 후 만료
        return generateToken(username, role, expirationDate);
    }

    // Refresh Token 생성: 7일 후 만료
    public String generateRefreshToken(String username, String role) {
        Date expirationDate = new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000)); // 7일 후 만료
        return generateToken(username, role, expirationDate);
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
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token is expired");
            return false; // 또는 원하는 방식으로 예외 처리
        } catch (Exception e) {
            System.out.println("Invalid JWT Token");
            return false;
        }
    }
}
