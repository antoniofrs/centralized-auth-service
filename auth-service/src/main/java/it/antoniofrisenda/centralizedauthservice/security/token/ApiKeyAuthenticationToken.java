package it.antoniofrisenda.centralizedauthservice.security.token;

import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

    private final String clientId;
    private final String apiKey;

    public ApiKeyAuthenticationToken(String clientId, String apiKey,
                                     Collection<? extends GrantedAuthority> authorities) {

        super(authorities);
        this.clientId = clientId;
        this.apiKey = apiKey;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return apiKey;
    }

    @Override
    public Object getPrincipal() {
        return clientId;
    }
}
