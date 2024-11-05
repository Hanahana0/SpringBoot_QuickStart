package qlinx.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

//    @PostMapping("/authenticate")
//    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
//        // 아래 방식은 jwt로 만든 토큰을 쿠키로 말아서 클라이언트에 던지는부분임!!!
//        Optional<User> user = userRepository.findByUsername(request.getUsername());
//

    /// /        if (user.isPresent() && user.get().getPassword().equals(request.getPassword())) {
    /// /            String token = jwtUtil.generateToken(request.getUsername());
    /// /
    /// /            ResponseCookie jwtCookie = ResponseCookie.from("jwt", token)
    /// /                    .httpOnly(true)
    /// /                    .secure(true)
    /// /                    .path("/")
    /// /                    .maxAge(60 * 60)
    /// /                    .build();
    /// /
    /// /            return ResponseEntity.ok().header("Set-Cookie", jwtCookie.toString()).body("Authentication Successful");
    /// /        } else {
    /// /            return ResponseEntity.status(401).body("Invalid username or password");
    /// /        }
//        if (user.isPresent() && user.get().getPassword().equals(request.getPassword())) {
//            String token = jwtUtil.generateToken(request.getUsername());
//
//            return ResponseEntity.ok().body(token);
//        } else {
//            return ResponseEntity.status(401).body("Invalid username or password");
//        }
//    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        System.out.println(request.toString());
        if (user.isPresent() && user.get().getPassword().equals(request.getPassword())) {
            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok().body(token);
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        if (token != null) {
            String username = jwtUtil.extractUsername(token);
            if (username != null && jwtUtil.validateToken(token)) {
                return ResponseEntity.ok().body("Token is valid");
            }
        }
        return ResponseEntity.status(401).body("Invalid token");
    }
}
