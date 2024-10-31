package qlinx.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final String username;

    // 인증되지 않은 상태의 토큰 (사용자명만 설정)
    public JwtAuthenticationToken(String username) {
        super(null);
        this.username = username;
        setAuthenticated(false);
    }

    // 인증된 상태의 토큰 (사용자명과 권한 설정)
    public JwtAuthenticationToken(String username, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.username = username;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null; // JWT 토큰 자체는 인증 자격 증명으로 사용하지 않음
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }
}
