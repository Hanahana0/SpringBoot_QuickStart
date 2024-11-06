package qlinx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qlinx.config.JwtUtil;
import qlinx.entity.AuthRequest;
import qlinx.entity.User;
import qlinx.repository.UserRepository;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    // 로그인 엔드포인트
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isPresent() && user.get().getPassword().equals(request.getPassword())) {
            // 엑세스 토큰과 리프레시 토큰 생성
            String accessToken = jwtUtil.generateAccessToken(request.getUsername(), "ROLE_USER");
            String refreshToken = jwtUtil.generateRefreshToken(request.getUsername(), "ROLE_USER");

            // 리프레시 토큰을 HttpOnly 쿠키에 저장
            ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60) // 7일 유효
                    .sameSite("Strict")
                    .build();

            // 엑세스 토큰은 바디에 포함하여 클라이언트로 전달
            return ResponseEntity.ok()
                    .header("Set-Cookie", refreshCookie.toString())
                    .body(Map.of("accessToken", accessToken));
        } else {
            return ResponseEntity.status(999).body("Invalid username or password");
        }
    }

    // 엑세스 토큰 갱신 엔드포인트
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        if (refreshToken != null && jwtUtil.validateToken(refreshToken)) {
            String username = jwtUtil.extractUsername(refreshToken);
            String role = jwtUtil.extractRole(refreshToken);

            // 새로운 엑세스 토큰 생성
            String newAccessToken = jwtUtil.generateAccessToken(username, role);

            // 엑세스 토큰을 바디에 포함하여 클라이언트에 전달
            return ResponseEntity.ok().body(Map.of("accessToken", newAccessToken));
        } else {
            return ResponseEntity.status(401).body("Invalid or expired refresh token");
        }
    }

    // 토큰 유효성 검증 엔드포인트
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        if (token != null && jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().body("Token is valid");
        }
        return ResponseEntity.status(498).body("Invalid or expired token");
    }
}
