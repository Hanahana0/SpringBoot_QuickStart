package qlinx.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal;

    // 기본 권한 추가
    public JwtAuthenticationToken(String principal) {
        super(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))); // 기본 권한 추가
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
