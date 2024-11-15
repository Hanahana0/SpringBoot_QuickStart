package qlinx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qlinx.config.JwtUtil;
import qlinx.dto.ApiRequest;
import qlinx.dto.ApiResponse;
import qlinx.entity.Translation;
import qlinx.entity.User;
import qlinx.repository.UserRepository;
import qlinx.service.TranslationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/*")
public class DevTestController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final TranslationService translationService;

    public DevTestController(TranslationService translationService) {
        this.translationService = translationService;
    }


    // 로그인 엔드포인트
    @PostMapping("/Login.do")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody ApiRequest request) {
        System.out.println(request.toString());
        Map<String, Object> params = request.getP_PARAM();

        // 로그인 처리
        if ("login".equals(request.getP_ACT())) {
            return handleLogin(params);
        }
        // 토큰 갱신 처리
        else if ("refresh-token".equals(request.getP_ACT())) {
            return handleRefreshToken(params);
        }
        // 지원되지 않는 작업에 대한 처리
        else {
            ApiResponse response = new ApiResponse(null, "Unsupported action", -400);
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 로그인 처리 메서드
    private ResponseEntity<ApiResponse> handleLogin(Map<String, Object> params) {
        String username = (String) params.get("USERID");
        String password = (String) params.get("PASSWORD");

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
            ApiResponse response = new ApiResponse(null, "Invalid username or password", -999); // 커스텀 에러 코드 999
            return ResponseEntity.ok(response); // 항상 200 OK 반환
        }
    }

    // 리프레시 토큰 처리 메서드
    private ResponseEntity<ApiResponse> handleRefreshToken(Map<String, Object> params) {
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
            ApiResponse response = new ApiResponse(null, "Invalid or expired refresh token", -401);
            return ResponseEntity.status(401).body(response);
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
            ApiResponse response = new ApiResponse(null, "Invalid or expired refresh token", -401);
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
                ApiResponse response = new ApiResponse(null, "Invalid or expired token", -498);
                return ResponseEntity.status(498).body(response);
            }
        } catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse(null, "Invalid request data", -400);
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PostMapping("/lang")
    public ResponseEntity<ApiResponse> getTranslation(@RequestBody ApiRequest request) {
        List<Translation> translations = new ArrayList<>();

        // `P_PARAM`에서 값을 추출
        Map<String, Object> params = request.getP_PARAM();
        String lang = params != null ? (String) params.get("lang") : null;
        String msg = params != null ? (String) params.get("msg") : null;

        if (lang == null && msg == null) {
            translations = translationService.getTranslations();
        } else {
            translations = translationService.getTranslations(msg, lang);
        }

        // ApiResponse로 감싸서 반환
        ApiResponse response = new ApiResponse(translations, "Translations fetched successfully", null);
        return ResponseEntity.ok(response);
    }
}
