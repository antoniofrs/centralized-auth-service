package it.antoniofrisenda.centralizedauthservice.security.token;

import it.antoniofrisenda.centralizedauthservice.security.model.JWTPayload;
import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final JWTPayload jwtPayload;

    public JwtAuthenticationToken(JWTPayload jwtPayload, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.jwtPayload = jwtPayload;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return jwtPayload.getRefreshToken();
    }

    @Override
    public Object getPrincipal() {
        return jwtPayload.getPrincipal();
    }

    public JWTPayload getJwtPayload() {
        return jwtPayload;
    }
}
