package qlinx.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    // 인증이 필요하지 않은 경로 목록
    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/auth",   // auth 경로 전체를 허용
            "/h2"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 요청 URI가 인증이 필요 없는 경로에 포함되어 있다면 필터 통과
        String requestURI = request.getRequestURI();
        if (EXCLUDED_PATHS.stream().anyMatch(requestURI::startsWith)) {
            chain.doFilter(request, response);
            return;
        }

        // Authorization 헤더에서 JWT 토큰 가져오기
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }

        // 토큰이 유효한지 확인하고 인증 객체 생성
        if (token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.extractUsername(token);
            String role = jwtUtil.extractRole(token); // JWT에서 역할 추출

            // 역할 정보와 함께 인증 객체 생성
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username, null, Collections.singletonList(new SimpleGrantedAuthority(role))
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            System.out.println("Authenticated user: " + username + " with role: " + role); // 로그 추가
        } else {
            System.out.println("Token validation failed or token is null.");
        }
        chain.doFilter(request, response);
    }
}
