package qlinx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qlinx.config.JwtUtil;
import qlinx.dto.ApiRequest;
import qlinx.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse> authenticate(@RequestBody ApiRequest request) {
        Map<String, Object> params = request.getP_PARAM();
        String username = (String) params.get("username");
        String password = (String) params.get("password");

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            // 엑세스 토큰과 리프레시 토큰 생성
            String accessToken = jwtUtil.generateAccessToken(username, "ROLE_USER");
            String refreshToken = jwtUtil.generateRefreshToken(username, "ROLE_USER");

            // 응답 데이터 설정
            Map<String, Object> data = Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken,
                    "userInfo", user.get()
            );

            ApiResponse response = new ApiResponse(data, "Login successful", null);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse response = new ApiResponse(null, "Invalid username or password", 999); // 커스텀 에러 코드 999
            return ResponseEntity.ok(response); // 항상 200 OK 반환
        }
    }

    // 엑세스 토큰 갱신 엔드포인트
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse> refreshAccessToken(@RequestBody ApiRequest request) {
        Map<String, Object> params = request.getP_PARAM();
        String refreshToken = (String) params.get("refreshToken");

        if (refreshToken != null && jwtUtil.validateToken(refreshToken)) {
            String username = jwtUtil.extractUsername(refreshToken);
            String role = jwtUtil.extractRole(refreshToken);

            // 새로운 엑세스 토큰 생성
            String newAccessToken = jwtUtil.generateAccessToken(username, role);

            // 응답 데이터 설정
            Map<String, Object> data = Map.of("accessToken", newAccessToken);
            ApiResponse response = new ApiResponse(data, "Token refreshed successfully", null);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse response = new ApiResponse(null, "Invalid or expired refresh token", 401);
            return ResponseEntity.status(401).body(response);
        }
    }

    // 토큰 유효성 검증 엔드포인트
    @PostMapping("/validate")
    public ResponseEntity<ApiResponse> validateToken(@RequestBody ApiRequest request) {
        try {
            request.validate(); // 요청 유효성 검사
            Map<String, Object> params = request.getP_PARAM();
            String token = (String) params.get("token");

            if (token != null && jwtUtil.validateToken(token)) {
                ApiResponse response = new ApiResponse(null, "Token is valid", null);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse response = new ApiResponse(null, "Invalid or expired token", 498);
                return ResponseEntity.status(498).body(response);
            }
        } catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse(null, "Invalid request data", 400);
            return ResponseEntity.badRequest().body(response);
        }
    }
}
