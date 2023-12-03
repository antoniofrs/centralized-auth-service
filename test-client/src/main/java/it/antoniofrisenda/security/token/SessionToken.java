package it.antoniofrisenda.security.token;

import it.antoniofrisenda.centralizedauthservice.shared.model.SessionDetails;
import it.antoniofrisenda.security.model.Principal;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class SessionToken extends AbstractAuthenticationToken {


    private final SessionDetails sessionDetails;

    public SessionToken(SessionDetails sessionDetails) {
        super(AuthorityUtils.createAuthorityList(String.valueOf(sessionDetails.getAuthorities())));
        this.sessionDetails = sessionDetails;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return Principal.builder()
                .email(sessionDetails.getEmail())
                .username(sessionDetails.getUsername())
                .build();
    }

    public SessionDetails getSessionDetails() {
        return sessionDetails;
    }
}
